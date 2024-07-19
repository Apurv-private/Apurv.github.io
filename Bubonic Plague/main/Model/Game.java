package cmpt276.as2.assigment3.Model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import cmpt276.as2.assigment3.MessageFragment;
import cmpt276.as2.assigment3.R;
import cmpt276.as2.assigment3.TextUI.Options;

/**
 * Represents the game
 * Data includes number of rows, columns, times played, high score, virus counter, scans
 */
public class Game extends AppCompatActivity {

    private  int NUM_ROWS = 4;
    private  int NUM_COL = 6;


    private static final int VIRUS = 1016;

    private int timesPlayed = 0;
    private int highestScore = 0;
    private int virusImageID = R.drawable.virus_game;

    private MediaPlayer player;

    private int VIRUS_COUNTER = 0;
    private int noOfViruses = 6;

    private int scans = 1;

    private Toast t;

    private boolean done = false;

    Button buttons[][] = new Button[NUM_ROWS][NUM_COL];
    Virus[][] virusArray = new Virus[NUM_ROWS][NUM_COL];

    //Checks if the given button has a virus id or not
    public boolean isVirus(Button button){
        return button.getId() == VIRUS;
    }

    public void changeText(){
        /*for(int i = 0 ; i < row ; i++) {
            if(virusArray[i][col].getText() != "notClicked"){
            int textR = Integer.parseInt(String.valueOf(virusArray[i][col].getText()));
            buttons[i][col].setText(textR-- + "");}
        }
        for(int j = 0 ; j < col ; j++) {
            if(virusArray[row][j].getText() != "notClicked"){
            int textC = Integer.parseInt(String.valueOf(virusArray[row][j].getText()));
            buttons[row][j].setText(textC-- + "");}
        }*/
        for(int i = 0 ; i < NUM_ROWS ; i ++){
            for(int j = 0 ; j < NUM_COL;j++){
                if(virusArray[i][j] != null  && virusArray[i][j].getStatus().equals("revealed") )
                    buttons[i][j].setText(getVirusNotRevealedCounter(i,j)+"");
            }
        }
    }

    public int getVirusNotRevealedCounter(int row, int col)
    {
        int virusCounter = 0;

        for(int i = 0 ; i < NUM_ROWS ; i++){
            if(virusArray[i][col].getIsVirus().equals("yes") && virusArray[i][col].getStatus().equals("notRevealed"))
                virusCounter++;
        }

        for(int j = 0 ; j < NUM_COL ; j++){
            if(virusArray[row][j].getIsVirus().equals("yes") && virusArray[row][j].getStatus().equals("notRevealed"))
                virusCounter++;
        }
        if(buttons[row][col].getId() == VIRUS)
            return virusCounter-1;
        return virusCounter;
    }

    public int getVirusCounter(int row, int col)
    {
        int virusCounter = 0;

        for(int i = 0 ; i < NUM_ROWS ; i++){
            if(virusArray[i][col].getIsVirus().equals("yes"))
                virusCounter++;
        }

        for(int j = 0 ; j < NUM_COL ; j++){
            if(virusArray[row][j].getIsVirus().equals("yes"))
                virusCounter++;
        }
        if(buttons[row][col].getId() == VIRUS)
            return virusCounter-1;
        return virusCounter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //getJson();
        timesPlayed++;
        noOfViruses = Options.getNumVirus(this);
        /*String boardSize = Options.getBoardSize(this);
        NUM_ROWS = Character.getNumericValue(boardSize.charAt(0))*10+Character.getNumericValue(boardSize.charAt(1));
        NUM_COL = Character.getNumericValue(boardSize.charAt(5))*10+Character.getNumericValue(boardSize.charAt(6));*/

        TextView play = findViewById(R.id.Text_TimePlayed);
        play.setText("#Times Played: "+timesPlayed);

        TextView minesFound = findViewById(R.id.Text_virusFound);
        minesFound.setText("Found " + 0 + " of " + noOfViruses +" mines");


        populateVirusCell();
        populateViruses();

    }

    //Creates a 2d array of viruses which are not revealed and aren't a virus
    private void populateVirusCell() {
        for (int i = 0 ; i < NUM_ROWS;i++){
            for(int j = 0 ; j < NUM_COL ; j++){
                virusArray[i][j] = new Virus(NUM_ROWS,NUM_COL,"notRevealed","no");
            }
        }
    }

    //Helps to get the data of times played and high score from a json file
    private void getJson() {

        FileReader reader = null;
        try {
            reader = new FileReader("stats.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        if(reader != null) {
            try {
                jsonObject = (JSONObject) jsonParser.parse(reader);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                if (jsonObject != null) {
                    highestScore = Integer.parseInt(String.valueOf(jsonObject.getJSONObject("HighScore")));
                    timesPlayed = Integer.parseInt(String.valueOf(jsonObject.getInt("TimesPlayed")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        TextView highScore = findViewById(R.id.Text_highestScore);
        highScore.setText("HighScore: " + highestScore);

        TextView Played = findViewById(R.id.Text_TimePlayed);
        Played.setText("#Times Played: " + timesPlayed);

    }

    //Makes a table layout made of table rows and dynamic buttons
    private void populateViruses() {
        final Animation textAnimation = AnimationUtils.loadAnimation(this,R.anim.fadein);
        final Animation tableAnimation = AnimationUtils.loadAnimation(this,R.anim.lefttoright);

        TextView highScore = findViewById(R.id.Text_highestScore);
        highScore.startAnimation(textAnimation);

        TextView virusFound = findViewById(R.id.Text_virusFound);
        virusFound.startAnimation(textAnimation);

        TextView scansUsed = findViewById(R.id.Text_ScansUsed);
        scansUsed.startAnimation(textAnimation);

        TextView timesPlayed = findViewById(R.id.Text_TimePlayed);
        timesPlayed.startAnimation(textAnimation);

        TableLayout table =  findViewById(R.id.Table_virus);

        table.startAnimation(tableAnimation);

        for (int row = 0 ; row < NUM_ROWS;row++){
            TableRow tableRow = new TableRow(this);

            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);
            for(int col = 0 ; col < NUM_COL;col++){
                final int FINAL_ROW = row;
                final int FINAL_COL = col;


                Button tableButton = new Button(this);
                tableButton.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                tableButton.setPadding(0,0,0,0);

                tableButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        scanAnimation(FINAL_ROW,FINAL_COL);
                        TextView scansUsed = findViewById(R.id.Text_ScansUsed);
                        scansUsed.setText("# Scans Used: " + scans++);
                        gridButtonClicked(FINAL_ROW,FINAL_COL);
                    }
                });

                tableRow.addView(tableButton);
                buttons[row][col] = tableButton;
            }
        }
    }

    //On pressing any button this function locks the size of the button and sets the image of the virus and starts the scan
    private void gridButtonClicked(int row , int col) {

        if(t!= null)
            t.cancel();
        t = Toast.makeText(this,"Scan started",Toast.LENGTH_SHORT);
        t.show();
        lockButtonSizes();

        while (!done){
        setViruses(VIRUS);
        done = true;
        }

        scanningVirus(row,col,virusImageID);
    }

    //Image of the virus is set
    private void setViruses(int id) {
        Game g = new Game();
        for(int i = 0 ; i < noOfViruses ; i++){
            int virusRow, virusCol;
            do {
                virusRow = (int) (Math.random() * NUM_ROWS);
                virusCol = (int) (Math.random() * NUM_COL);
            } while (g.isVirus(buttons[virusRow][virusCol]));
            Button button = buttons[virusRow][virusCol];
            virusArray[virusRow][virusCol].setIsVirus("yes");
            button.setId(id);
        }
    }

    //Scans the row and column of the button pressed to check how many viruses are there and sets the text
    private void scanningVirus(int row, int col,int id) {

        int virusCounter = getVirusCounter(row,col);
        Button buttonT = buttons[row][col];
        buttonT.setTextColor(Color.MAGENTA);
        buttonT.setText( virusCounter + "" );



        Game g = new Game();



        Button button = buttons[row][col];


        if(g.isVirus(button) && VIRUS_COUNTER < noOfViruses && virusArray[row][col].getStatus().equals("notRevealed")) {
            t = Toast.makeText(this, "You Hit a Mine, Scan started", Toast.LENGTH_SHORT);
            t.show();
            VIRUS_COUNTER++;
            int resID = id + virusCounter;
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap orginalBitmap = BitmapFactory.decodeResource(getResources(),resID);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(orginalBitmap,newWidth,newHeight,true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable( resource , scaledBitmap ));
            g.changeText();
        }
        virusArray[row][col].setStatus("revealed");

        TextView virusesFound = findViewById(R.id.Text_virusFound);
        virusesFound.setText("Found " + VIRUS_COUNTER + " of " + noOfViruses +" mines");

        //TO FINISH THE GAME
        if(VIRUS_COUNTER == noOfViruses) {
            if(t!= null)
                t.cancel();
            putJSON();
        }

    }

    //shows animation on click
    private void scanAnimation(final int row, final int col) {
        Game g = new Game();
        final Animation textAnimation = AnimationUtils.loadAnimation(this,R.anim.fadein);
        textAnimation.setDuration(500);

        if(g.isVirus(buttons[row][col])) {
            player = MediaPlayer.create(this,R.raw.fatality);
        }
        else{
            player = MediaPlayer.create(this,R.raw.scan);
        }
        player.start();
        for(int i = 0; i< NUM_ROWS; i++)
            buttons[i][col].startAnimation(textAnimation);

        for(int j = 0 ; j < NUM_COL ; j++)
            buttons[row][j].startAnimation(textAnimation);
    }

    //Puts the current times played and high score in a json file
    private void putJSON() {
        //Storing in Json
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(scans);
        try {
            jsonObject.put("HighScore",jsonArray);
        } catch (JSONException e) {
            try {
                Log.i("ERROR",jsonObject.get("HighScore")+"");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        //jsonObject.put("TimesPlayed",timesPlayed);


        try {
            FileWriter file = new FileWriter("stats.json");
            BufferedWriter bufferedWriter = new BufferedWriter(file);
            bufferedWriter.write("Hello");
            bufferedWriter.close();
            Log.i("ERROR",jsonObject.get("HighScore")+"");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try {
            //gson.toJson(scans,new FileWriter("stats.json"));
                 Writer write = Files.newBufferedWriter(Paths.get("stats.json"));
                Log.i("ERROR","ERROR");
                gson.toJson(scans,write);
                write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(Game.this.openFileOutput("stats.json", Context.MODE_PRIVATE));
            outputStreamWriter.write("1");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }*/

        //Creating the dialog
        FragmentManager manager = getSupportFragmentManager();
        MessageFragment dialog = new MessageFragment();
        dialog.setCancelable(false);
        dialog.show(manager,"MessageDialog");
    }

    //Locks the size of the dynamic buttons
    private void lockButtonSizes() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COL; col++) {
                Button button = buttons[row][col];
                button.setSoundEffectsEnabled(false);

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    //when back button is pressed this function helps in going back to main menu
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back to Main Menu", Toast.LENGTH_SHORT).show();
        finish();
    }

}