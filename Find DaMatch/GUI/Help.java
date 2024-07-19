package cmpt276.project.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import cmpt276.project.MusicPlayer;
import cmpt276.project.R;

public class Help extends AppCompatActivity {

    private MusicPlayer musicPlayer = MusicPlayer.getInstance();
    public static Intent makeIntent(Context context) {
        return new Intent(context, Help.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        musicPlayer.setPlayer(0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);
        displayHelpScreen();
    }

    private void displayHelpScreen() {
        Animation textViewAnimation = AnimationUtils.loadAnimation(this,R.anim.fadein);
        textViewAnimation.setDuration(2000);
        setClickableText(textViewAnimation);
        setText(textViewAnimation);

    }

    private void setText(Animation textViewAnimation) {
        TextView help = findViewById(R.id.Text_helpScreen);
        help.startAnimation(textViewAnimation);

        TextView website = findViewById(R.id.Text_link);
        website.startAnimation(textViewAnimation);

        TextView gameRulesText = findViewById(R.id.Text_gameRules);
        gameRulesText.startAnimation(textViewAnimation);

        TextView gameBasics = findViewById(R.id.Text_gameBasics);
        gameBasics.startAnimation(textViewAnimation);

        TextView howToPlay = findViewById(R.id.Text_howToPlay);
        howToPlay.startAnimation(textViewAnimation);

        TextView endGame =  findViewById(R.id.Text_endGame);
        endGame.startAnimation(textViewAnimation);

        TextView citationPlay = findViewById(R.id.Text_citationPlay);
        citationPlay.startAnimation(textViewAnimation);

        TextView citationOption = findViewById(R.id.Text_citationOption);
        citationOption.startAnimation(textViewAnimation);

        TextView citationHelp = findViewById(R.id.Text_citationHelp);
        citationHelp.startAnimation(textViewAnimation);

        TextView citation = findViewById(R.id.Text_citation);
        citation.startAnimation(textViewAnimation);
    }

    public void setClickableText(Animation textViewAnimation) {
        TextView aboutAuthors = (TextView) findViewById(R.id.Text_authors);
        aboutAuthors.setLinkTextColor(Color.BLUE);
        aboutAuthors.startAnimation(textViewAnimation);
        aboutAuthors.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back to Main Menu", Toast.LENGTH_SHORT).show();
        finish();
    }
}