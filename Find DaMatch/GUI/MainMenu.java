package cmpt276.project.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import cmpt276.project.MusicPlayer;
import cmpt276.project.Model.GameLogic;
import cmpt276.project.R;

public class MainMenu extends AppCompatActivity {
    private MusicPlayer musicPlayer = MusicPlayer.getInstance();
    private MediaPlayer mediaPlayer = new MediaPlayer();
    int index ;
    TextView playText;
    TextView optionsText;
    TextView helpText;

    private final int[] defScores = {5, 6, 7, 8, 9};
    private final int[] orders = {2, 3, 5};

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "App Closed", Toast.LENGTH_SHORT).show();
        moveTaskToBack(true);
        System.exit(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ImageButton playButton = findViewById(R.id.Button_Game);
        ImageButton optionButton = findViewById(R.id.Button_Options);
        ImageButton helpButton = findViewById(R.id.Button_help);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fadein);
        animation.setDuration(2000);

        TextView mainMenu = findViewById(R.id.Text_mainMenu);
        mainMenu.startAnimation(animation);

        playText = findViewById(R.id.Text_playText);
        playText.startAnimation(animation);

        optionsText = findViewById(R.id.Text_optionText);
        optionsText.startAnimation(animation);

        helpText = findViewById(R.id.Text_helpText);
        helpText.startAnimation(animation);


        playButton.startAnimation(animation);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Game.makeIntent(MainMenu.this);
                startActivity(intent);
            }
        });


        optionButton.startAnimation(animation);
        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Options.makeIntent(MainMenu.this);
                startActivity(intent);
            }
        });


        helpButton.startAnimation(animation);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Help.makeIntent(MainMenu.this);
                startActivity(intent);
            }
        });
        displayScores();
    }

    public void setButtonImages() {
        ImageButton playButton = findViewById(R.id.Button_Game);
        ImageButton optionButton = findViewById(R.id.Button_Options);
        ImageButton helpButton = findViewById(R.id.Button_help);
        SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
        index = settings.getInt("imgSet",0);
        if(settings.getInt("imgSet", 0) == 0)
        {
            playText.setTextColor(Color.WHITE);
            optionsText.setTextColor(Color.WHITE);
            helpText.setTextColor(Color.WHITE);
            playButton.setImageResource(R.drawable.playbutton);
            optionButton.setImageResource(R.drawable.optionbutton);
            helpButton.setImageResource(R.drawable.helpbutton);
            if(musicPlayer.getPlayer() == 0 && index == 0){
                mediaPlayer = MediaPlayer.create(MainMenu.this,R.raw.anime);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                musicPlayer.setPlayer(1);
            }
        }
        else  if (settings.getInt("imgSet", 0) == 1){
            playText.setTextColor(Color.WHITE);
            optionsText.setTextColor(Color.WHITE);
            helpText.setTextColor(Color.WHITE);
            playButton.setImageResource(R.drawable.katarina_play);
            optionButton.setImageResource(R.drawable.option_lol);
            helpButton.setImageResource(R.drawable.yasuo_help);
            if (musicPlayer.getPlayer() == 0 && index == 1) {
                musicPlayer.setPlayer(0);
                mediaPlayer = MediaPlayer.create(MainMenu.this,R.raw.gamechar);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        }
        else{
            playText.setTextColor(Color.BLUE);
            optionsText.setTextColor(Color.BLUE);
            helpText.setTextColor(Color.BLUE);
            playButton.setImageResource(R.drawable.whiteman_play);
            optionButton.setImageResource(R.drawable.whiteman_options);
            helpButton.setImageResource(R.drawable.whiteman_help);
            if (musicPlayer.getPlayer() == 0 && index == 2) {
                musicPlayer.setPlayer(2);
                mediaPlayer = MediaPlayer.create(MainMenu.this,R.raw.whiteman);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        }
    }


    private void displayScores() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fadein);
        animation.setDuration(2000);
        SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
        TextView scoreTxt = findViewById(R.id.mainScores);
        StringBuilder scoreString = new StringBuilder(getResources().getString(R.string.high_scores));
        String[] defNames = getResources().getStringArray(R.array.default_names);
        String[] months = getResources().getStringArray(R.array.months);
        String[] defDates = getResources().getStringArray(R.array.default_dates);
        int orderPos = settings.getInt("gameOrderPos", 0);
        int deckSizePos = settings.getInt("deckSizePos", 0);
        for (int i = 0; i < 5; i++) {
            String scoreKey = "hs_score" + i + "," + orderPos + "," + deckSizePos;
            String nameKey = "hs_name" + i + "," + orderPos + "," + deckSizePos;
            String dateKey = "hs_date" + i + "," + orderPos + "," + deckSizePos;
            String dateString = settings.getString(dateKey, defDates[i]);
            String[] splitDate = dateString.split(",");
            String stringToAppend = String.format("%d by %s on %s %s, %s\n",
                    settings.getInt(scoreKey, GameLogic.defaultScore(i, orderPos, deckSizePos)),
                    settings.getString(nameKey, defNames[i]),
                    months[Integer.parseInt(splitDate[1])-1],
                    splitDate[2],
                    splitDate[0]);
            scoreString.append(stringToAppend);
        }
        scoreTxt.startAnimation(animation);
        scoreTxt.setText(scoreString.toString());
    }


    @Override
    public void onResume() {
        if(musicPlayer.isPlaying()){mediaPlayer.stop();}
        super.onResume();
        setButtonImages();
        displayScores();
    }

}