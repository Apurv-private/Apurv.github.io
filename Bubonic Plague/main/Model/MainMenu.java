package cmpt276.as2.assigment3.Model;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cmpt276.as2.assigment3.R;
import cmpt276.as2.assigment3.TextUI.HelpScreen;
import cmpt276.as2.assigment3.TextUI.Options;

/**
 * Represents the main activity with splash screen
 * Data includes sound pool
 */
public class MainMenu extends AppCompatActivity {
    private SoundPool spool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Animation textAnimation = AnimationUtils.loadAnimation(this,R.anim.fadein);
        textAnimation.setDuration(2000);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        spool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .build();

        final int audio = spool.load(this,R.raw.other_sound,1);

        TextView mainMenu = findViewById(R.id.Text_mainMenu);
        mainMenu.startAnimation(textAnimation);



        ImageButton buttonHelp = findViewById(R.id.Button_Help);

        buttonHelp.startAnimation(textAnimation);
        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spool.play(audio,1,1,0,0,1);
                Intent intent = new Intent (MainMenu.this, HelpScreen.class);
                Toast.makeText(getApplicationContext(), "Help Page", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


        ImageButton buttonOptions = findViewById(R.id.Button_Options);
        buttonOptions.startAnimation(textAnimation);
        buttonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spool.play(audio,1,1,0,0,1);
                Intent intent = new Intent (MainMenu.this, Options.class);
                Toast.makeText(getApplicationContext(), "Options Page", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        ImageButton buttonPlay =  findViewById(R.id.Button_StartGame);
        buttonPlay.startAnimation(textAnimation);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spool.play(audio,1,1,0,0,1);
                Intent intent = new Intent (MainMenu.this,Game.class);
                Toast.makeText(getApplicationContext(), "Start Game ", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        refreshTextMainMenu();
    }

    private void refreshTextMainMenu() {
        TextView num_virus =  findViewById(R.id.NumVirusSelected);
        int virus = Options.getNumVirus(this);
        num_virus.setText("" + virus);
        TextView board_size = findViewById(R.id.boardSizeSelected);
        String board = Options.getBoardSize(this);
        board_size.setText(board);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshTextMainMenu();
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "App Closed", Toast.LENGTH_SHORT).show();
        moveTaskToBack(true);
        System.exit(1);
    }
}