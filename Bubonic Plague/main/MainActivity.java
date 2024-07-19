package cmpt276.as2.assigment3;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cmpt276.as2.assigment3.Model.MainMenu;

public class MainActivity extends AppCompatActivity {
    private SoundPool spool;
    private final static int SPLASH_TIME_OUT = 5000;
    private Handler handler = new Handler();

    private boolean animationDone = false;


    @Override
    protected void onStop() {
        super.onStop();
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Animation textViewAnimation = AnimationUtils.loadAnimation(this,R.anim.fadein);
        textViewAnimation.setDuration(2000);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        spool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .build();

         final int audio = spool.load(MainActivity.this,R.raw.main_button,1);


        
        TextView textName = findViewById(R.id.Text_gameName);
        textName.startAnimation(textViewAnimation);


        TextView byText =  findViewById(R.id.Text_By);
        byText.startAnimation(textViewAnimation);

        TextView authorsName =  findViewById(R.id.Text_Name);
        authorsName.startAnimation(textViewAnimation);

        ImageView apurvImage = findViewById(R.id.Image_Apurv);
        Animation apurvAnimation = AnimationUtils.loadAnimation(this,R.anim.righttoleft);
        apurvImage.animate().alpha(0f).setDuration(SPLASH_TIME_OUT).setListener(new AnimatorListenerAdapter() {
           @Override
           public void onAnimationEnd(Animator animation) {
               if (!animationDone) {
                   handler = new Handler();
                   handler.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           startActivity(new Intent(getApplicationContext(), MainMenu.class));
                           finish();
                       }
                   }, SPLASH_TIME_OUT);
               }
           }
       });

        apurvImage.startAnimation(apurvAnimation);


        final ImageView preetImage = findViewById(R.id.Image_Preet);
        final Animation preetAnimation = AnimationUtils.loadAnimation(this,R.anim.lefttoright);



        final Button buttonSkipAnimation = findViewById(R.id.Button_skipAnimation);
        preetImage.animate().alpha(0f).setDuration(SPLASH_TIME_OUT).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                if (!animationDone) {
                    if (handler != null)
                        handler.removeCallbacksAndMessages(null);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getApplicationContext(), MainMenu.class));
                            finish();
                        }

                    }, SPLASH_TIME_OUT);
                }
            }
        });


                preetImage.startAnimation(preetAnimation);

        buttonSkipAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               animationDone = true;

                spool.play(audio,1,1,0,0,1);
                Toast.makeText(getApplicationContext(), "Animations Skipped", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainMenu.class));
                finish();
            }
        });

    }

}