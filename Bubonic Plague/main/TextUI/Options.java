package cmpt276.as2.assigment3.TextUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cmpt276.as2.assigment3.R;

/**
 * Represents the options screen
 * Data includes shared preference methods
 */
public class Options extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Animation textViewAnimation = AnimationUtils.loadAnimation(this,R.anim.fadein);
        textViewAnimation.setDuration(2000);

        TextView optionText = findViewById(R.id.Text_options);
        optionText.startAnimation(textViewAnimation);

        TextView sizeText = findViewById(R.id.boardSizeSelection);
        sizeText.startAnimation(textViewAnimation);

        TextView virusText = findViewById(R.id.numberOfVirusesSelection);
        virusText.startAnimation(textViewAnimation);

        createRadioButtonForBoardSize();
        createRadioButtonForNumberOfViruses();


        int savedNumViruses = getNumVirus(this);
        String savedBoardSize = getBoardSize(this);
        Toast.makeText(this, "Selected "  + " board size is " + savedBoardSize + " and viruses are " + savedNumViruses + ".", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("DefaultLocale")
    private void createRadioButtonForNumberOfViruses() {
        Animation textViewAnimation = AnimationUtils.loadAnimation(this,R.anim.fadein);
        textViewAnimation.setDuration(2000);
        RadioGroup boardSizeGroup =  findViewById(R.id.numVirusRadioGroup);
        boardSizeGroup.startAnimation(textViewAnimation);
        final int[] number_viruses = getResources().getIntArray(R.array.number_of_viruses);
        for (final int numberOfViruses : number_viruses) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setTextColor(Color.WHITE);
            radioButton.setText(String.format("%d viruses", numberOfViruses));
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Options.this,numberOfViruses + " selected", Toast.LENGTH_SHORT).show();
                    saveNumberOfViruses(numberOfViruses);
                }
            });
            boardSizeGroup.addView(radioButton);
            if (numberOfViruses == getNumVirus(this)) {
                radioButton.setChecked(true);
            }
        }
    }
    private void createRadioButtonForBoardSize() {
        Animation textViewAnimation = AnimationUtils.loadAnimation(this,R.anim.fadein);
        textViewAnimation.setDuration(2000);
        RadioGroup boardSizeGroup =  findViewById(R.id.boardSizeRadioGroup);
        boardSizeGroup.startAnimation(textViewAnimation);
        String [] board_size = getResources().getStringArray(R.array.boardSize);
        for (final String board : board_size) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setTextColor(Color.WHITE);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Options.this,board + " selected",Toast.LENGTH_SHORT).show();
                    saveBoardSize(board);
                }
            });
            radioButton.setText(board);
            boardSizeGroup.addView(radioButton);
            if (board.equals(getBoardSize(this))) {
                radioButton.setChecked(true);
            }
        }
    }

    private void saveBoardSize(String board) {
        SharedPreferences prefs = this.getSharedPreferences("numBoardPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("boardSize", board);
        editor.apply();
    }

    private void saveNumberOfViruses(int numViruses) {
        SharedPreferences prefs = this.getSharedPreferences("numMinesPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("number_of_viruses", numViruses);
        editor.apply();
    }

    public static int getNumVirus(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("numMinesPref", MODE_PRIVATE);
        return prefs.getInt("number_of_viruses", 6);
    }

    public static String getBoardSize(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("numBoardPref", MODE_PRIVATE);
        return prefs.getString("boardSize", "04 x 06");
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back to Main Menu", Toast.LENGTH_SHORT).show();
        finish();
    }
}