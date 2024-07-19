package cmpt276.as2.assigment3.TextUI;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import cmpt276.as2.assigment3.Logic.GameLogic;
import cmpt276.as2.assigment3.R;

/**
 * Represents the help screen
 * Data includes methods to display information about authors, game and also about the course
 */
public class HelpScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);

        displayMessage();
    }

    //Sets the text of the help screen according to the game logic
    private void displayMessage() {
        Animation textViewAnimation = AnimationUtils.loadAnimation(this,R.anim.fadein);
        textViewAnimation.setDuration(2000);
        GameLogic logic = new GameLogic();

        TextView help = findViewById(R.id.Text_help);
        help.startAnimation(textViewAnimation);

        TextView citationScan =findViewById(R.id.Text_citationScan);
        citationScan.startAnimation(textViewAnimation);

        TextView gameBasics = findViewById(R.id.Text_gameBasics);
        gameBasics.setText(logic.gameBasics());
        gameBasics.startAnimation(textViewAnimation);

        TextView gameRules = findViewById(R.id.Text_gameRules);
        gameRules.setText(logic.gameRules());
        gameRules.startAnimation(textViewAnimation);

        TextView endGame =  findViewById(R.id.Text_endGame);
        endGame.setText(logic.endGame());
        endGame.startAnimation(textViewAnimation);

        TextView rulesText = findViewById(R.id.Text_Rules);
        rulesText.startAnimation(textViewAnimation);

        TextView aboutAuthors = findViewById(R.id.Text_About);
        String text = "The authors ApurvNerurkar and PreetinderSingh are second year CompSci students from Simon Fraser University. This game is made as a part of an Assignment for CMPT 276 below is the website for the course";
        SpannableStringBuilder ss = new SpannableStringBuilder(text);

        ClickableSpan clickableSpanAp = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://www.linkedin.com/in/apurv-nerurkar-30aab018a/"));
                startActivity(browserIntent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.GREEN);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpanAp,12,25,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan clickableSpanPr = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("http://linkedin.com/in/preet-saini-95580618a"));
                startActivity(browserIntent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.GREEN);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpanPr,30,45,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        aboutAuthors.startAnimation(textViewAnimation);
        aboutAuthors.setText(ss);
        aboutAuthors.setMovementMethod(LinkMovementMethod.getInstance());

        TextView tactics =  findViewById(R.id.Text_tactics);
        tactics.setText(logic.tactics());
        tactics.startAnimation(textViewAnimation);

        TextView courseLink = findViewById(R.id.Text_Link);
        courseLink.startAnimation(textViewAnimation);

        TextView citationStart =  findViewById(R.id.Text_citation_startGame);
        citationStart.setText("https://tinyurl.com/yd7zusvy");
        citationStart.startAnimation(textViewAnimation);

        TextView citationHelp =  findViewById(R.id.Text_citation_HelpButton);
        citationHelp.setText("https://tinyurl.com/y7rw3bqm");
        citationHelp.startAnimation(textViewAnimation);

        TextView citationOptions =  findViewById(R.id.Text_citation_OptionsButton);
        citationOptions.setText("https://tinyurl.com/ycpf8cpv");
        citationOptions.startAnimation(textViewAnimation);

        TextView citationIcon =  findViewById(R.id.Text_citationIcon);
        citationIcon.setText("https://tinyurl.com/ycjsnbob");
        citationIcon.startAnimation(textViewAnimation);

        TextView citationBackground =  findViewById(R.id.Text_citationBackground);
        citationBackground.setText("https://tinyurl.com/yaj5pgh4");
        citationBackground.startAnimation(textViewAnimation);

        TextView citationVirus =  findViewById(R.id.Text_citationVirus);
        citationVirus.setText("https://tinyurl.com/ybzg3cem");
        citationVirus.startAnimation(textViewAnimation);

        TextView citationAudioSkip = findViewById(R.id.TextCitationSkip);
        citationAudioSkip.startAnimation(textViewAnimation);

        TextView citationOther = findViewById(R.id.TextCitationOther);
        citationOther.startAnimation(textViewAnimation);

        TextView citationVirusScan = findViewById(R.id.Text_citationVirusScan);
        citationVirusScan.startAnimation(textViewAnimation);

   }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back to Main Menu", Toast.LENGTH_SHORT).show();
        finish();
    }
}