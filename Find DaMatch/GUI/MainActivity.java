package cmpt276.project.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import cmpt276.project.R;

public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupWelcomeScreen();
    }

    private void setupWelcomeScreen() {

        setContentView(R.layout.activity_main);
        Handler handler = new Handler();

        setupSkipButton();
        animate();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(!MainActivity.this.isFinishing()) {
                    Intent intent = new Intent(MainActivity.this, MainMenu.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIMEOUT);
    }

    private void setupSkipButton() {
        Button skip = findViewById(R.id.skipButton);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MainActivity.this, MainMenu.class);
                startActivity(intent);
            }
        });
    }

    private void animate() {
        ObjectAnimator animation1 = ObjectAnimator.ofFloat(findViewById(R.id.welcomeLeft), "translationY", 300f);
        animation1.setDuration(3000);
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(findViewById(R.id.welcomeLeft), "translationX", 150f);
        animation2.setDuration(3000);
        ObjectAnimator animation3 = ObjectAnimator.ofFloat(findViewById(R.id.welcomeRight), "translationY", 300f);
        animation3.setDuration(3000);
        ObjectAnimator animation4 = ObjectAnimator.ofFloat(findViewById(R.id.welcomeRight), "translationX", -150f);
        animation4.setDuration(3000);
        ObjectAnimator animation5 = ObjectAnimator.ofFloat(findViewById(R.id.welcomeLeft), "rotation", 0,360);
        animation5.setDuration(3000);
        ObjectAnimator animation6 = ObjectAnimator.ofFloat(findViewById(R.id.welcomeRight), "rotation", 0, 360);
        animation6.setDuration(3000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animation1, animation2, animation3, animation4, animation5, animation6);
        animatorSet.start();
    }
}