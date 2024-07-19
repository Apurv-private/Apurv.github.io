package cmpt276.project.GUI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import cmpt276.project.Model.GameLogic;
import cmpt276.project.MusicPlayer;
import cmpt276.project.R;

public class Game extends AppCompatActivity {
    private MusicPlayer musicPlayer = MusicPlayer.getInstance();
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private final String TAG = "ERROR_GAME";

    private final String TRANSPARENT = "#00FFFFFF";
    private final int[] defScores = {5, 6, 7, 8, 9};
    private final int[] orders = {2, 3, 5};
    private Button[][] deckButtons = null;
    private Button[][] discardButtons = null;
    private Chronometer chronometer;
    private GameLogic logic;
    private final String tag = "game activity";

    int cardCounter = 1;
    ImageView imageView;
    String dateString;
    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
        logic = GameLogic.getInstance();
        logic.setOrder(settings.getInt("gameOrder", 2));
        logic.setImgSet(settings.getInt("imgSet", 0));
        logic.setCardsLeft(settings.getInt("deckSize", -1));
        logic.setMode(settings.getInt("gameMode", 0));
        logic.setDifficulty(settings.getInt("difficulty", 0));
        logic.initLocations();
        musicPlayer.setPlayer(0);
        deckButtons = new Button[3][3];
        discardButtons = new Button[3][3];
        populateDeck(3, 3);
        populateDiscard(3, 3);
        readyButton();

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateString = format.format(date);

    }
    public static Intent makeIntent(Context context) {
        return new Intent(context, Game.class);
    }

    // Make table and buttons for the deck
    private void populateDeck(int x, int y) {
        TableLayout deck = findViewById(R.id.gameDeck);
        for (int row = 0; row < y; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            deck.addView(tableRow);

            for (int col = 0; col < x; col++) {
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                Button btn = new Button(this);
                btn.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                btn.setPadding(0, 0, 0, 0);
                btn.setBackgroundColor(android.graphics.Color.parseColor(TRANSPARENT));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // if the card at that position is a match, check game over or move cards and place images.
                        Log.e(TAG, "" + logic.getCardsLeft());
                        if (logic.isMatch(logic.deckIndex(FINAL_COL, FINAL_ROW))) {
                            takingScreenShot(v);
                            if (logic.getCardsLeft() == 0) {
                                gameOver();
                            } else {
                                startMediaPlayer(R.raw.correctsound);
                                logic.moveCards();
                                placeImages();
                            }
                        }
                        else
                            startMediaPlayer(R.raw.incorrectsound);
                    }
                });
                tableRow.addView(btn);
                deckButtons[col][row] = btn;
            }
        }
    }

    private void takingScreenShot(View v) {
        if(musicPlayer.getCardsSaved() == 1) {
            verifyStoragePermissions(Game.this);
            if(toast != null)
                toast.cancel();
            saveBitmap(cardCounter);
            toast = Toast.makeText(getApplicationContext(), "Image Saved in Find DaMatch Images Folder in Internal Storage", Toast.LENGTH_SHORT);
            toast.show();
            cardCounter++;
        }
    }

    // Make table and buttons for discard
    private void populateDiscard(int x, int y) {
        TableLayout discard = findViewById(R.id.gameDiscard);
        for (int row = 0; row < y; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            discard.addView(tableRow);

            for (int col = 0; col < x; col++) {
                Button btn = new Button(this);
                btn.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                btn.setPadding(0,0,0,0);
                btn.setBackgroundColor(android.graphics.Color.parseColor(TRANSPARENT));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startMediaPlayer(R.raw.discard_clicked_sound);
                        Log.e(TAG, "Discard pile clicked");
                    }
                });
                tableRow.addView(btn);
                discardButtons[col][row] = btn;
            }
        }
    }

    private void readyButton() {
        Button ready = findViewById(R.id.gameReadyBtn);
        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                lockButtonSizes();
                placeImages();
                startChronometer();
            }
        });
    }

    private void lockButtonSizes() {
        for (Button[] row : deckButtons) {
            for (Button btn : row) {
                int width = btn.getWidth();
                btn.setMinWidth(width);
                btn.setMaxWidth(width);

                int height = btn.getHeight();
                btn.setMinHeight(height);
                btn.setMaxHeight(height);
            }
        }
        for (Button[] row : discardButtons) {
            for (Button btn : row) {
                int width = btn.getWidth();
                btn.setMinWidth(width);
                btn.setMaxWidth(width);

                int height = btn.getHeight();
                btn.setMinHeight(height);
                btn.setMaxHeight(height);
            }
        }
    }

    // Scale images and place them according to the positions given by logic class
    private void placeImages() {
        for (int y = 0; y < deckButtons.length; y++) {
            for (int x = 0; x < deckButtons[y].length; x++) {
                Button btn = deckButtons[x][y];
                int imgNumber = this.logic.deckIndex(x, y);
                if (imgNumber > 0 && this.logic.getDeckIsImg(imgNumber)) {
                    Resources resource = getResources();
                    int newWidth = (int) (btn.getWidth() * this.logic.getDeckSize(imgNumber));
                    int newHeight = (int) (btn.getHeight() * this.logic.getDeckSize(imgNumber));
                    SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
                    int mode = settings.getInt("imgSet", 0);
                    if (mode != 3) {
                    Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(this.logic.getImgName(imgNumber), "drawable", getPackageName()));
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                    // code for rotation if difficulty is >easy
                        if (this.logic.getDeckRotation(imgNumber) != 0) {
                            Matrix matrix = new Matrix();
                            matrix.postRotate(this.logic.getDeckRotation(imgNumber));
                            Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                            btn.setBackground(new BitmapDrawable(resource, rotatedBitmap));
                        } else {
                            btn.setBackground(new BitmapDrawable(resource, scaledBitmap));
                        }
                    btn.setText("");
                    } else {
                        if (logic.getOwn().isEmpty()) {
                            setUri();
                        }
                        Bitmap bmImg = logic.getOwnAt(imgNumber);
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bmImg, newWidth, newHeight, true);
                        if (this.logic.getDeckRotation(imgNumber) != 0) {
                            Matrix matrix = new Matrix();
                            matrix.postRotate(this.logic.getDeckRotation(imgNumber));
                            Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                            btn.setBackground(new BitmapDrawable(resource, rotatedBitmap));
                        } else {
                            btn.setBackground(new BitmapDrawable(resource, scaledBitmap));
                        }
//                        btn.setBackground(new BitmapDrawable(resource, scaledBitmap));
                        btn.setText("");
                    }
                } else if (imgNumber > 0 && !this.logic.getDeckIsImg(imgNumber)) {
                    btn.setBackgroundResource(0);
                    btn.setRotation(this.logic.getDeckRotation(imgNumber));
                    btn.setText(this.logic.getImgName(imgNumber));
                    btn.setTextSize((float) (20 * this.logic.getDeckSize(imgNumber)));
                } else {
                    btn.setBackgroundResource(0);
                    btn.setRotation(0);
                    btn.setText("");
                }
            }
        }

        for (int y = 0; y < discardButtons.length; y++) {
            for (int x = 0; x < discardButtons[y].length; x++) {
                Button btn = discardButtons[x][y];
                int imgNumber = this.logic.discardIndex(x, y);
                if (imgNumber > 0 && this.logic.getDiscardIsImg(imgNumber)) {
                    SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
                    int mode = settings.getInt("imgSet", 0);
                    Resources resource = getResources();
                    int newWidth = (int) (btn.getWidth() * this.logic.getDiscardSize(imgNumber));
                    int newHeight = (int) (btn.getHeight() * this.logic.getDiscardSize(imgNumber));
                    if (mode != 3) {
                        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(this.logic.getImgName(imgNumber), "drawable", getPackageName()));
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                    if (this.logic.getDiscardRotation(imgNumber) != 0) {
                        Matrix matrix = new Matrix();
                        matrix.postRotate(this.logic.getDiscardRotation(imgNumber));
                        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                        btn.setBackground(new BitmapDrawable(resource, rotatedBitmap));
                    } else {
                        btn.setBackground(new BitmapDrawable(resource, scaledBitmap));
                    }
                    btn.setText("");
                    } else {
                        Bitmap bmImg = logic.getOwnAt(imgNumber);
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bmImg, newWidth, newHeight, true);
                        if (this.logic.getDeckRotation(imgNumber) != 0) {
                            Matrix matrix = new Matrix();
                            matrix.postRotate(this.logic.getDeckRotation(imgNumber));
                            Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                            btn.setBackground(new BitmapDrawable(resource, rotatedBitmap));
                        } else {
                            btn.setBackground(new BitmapDrawable(resource, scaledBitmap));
                        }
                        btn.setText("");
                    }
                } else if (imgNumber > 0 && !this.logic.getDiscardIsImg(imgNumber)) {
                    btn.setBackgroundResource(0);
                    btn.setRotation(this.logic.getDiscardRotation(imgNumber));
                    btn.setText(this.logic.getImgName(imgNumber));
                    btn.setTextSize((float) (20 * this.logic.getDiscardSize(imgNumber)));
                } else {
                    btn.setBackgroundResource(0);
                    btn.setRotation(0);
                    btn.setText("");
                }
            }
        }
    }

    private void setUri() {
        SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
        Set<String> own = settings.getStringSet("ownSelect", null);
        assert own != null;
        List<String> ownSelect = new ArrayList<String>(own);
        for (int i = 0; i < ownSelect.size(); ++i) {
            String myUri = ownSelect.get(i);
            Uri myRealUri = Uri.parse(myUri);
            final InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(myRealUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                logic.addOwn(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void startChronometer(){
        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    private void gameOver() {
        // stop clock and get val in seconds
        chronometer.stop();
        int playerScore = (int) (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
        // open settings and get highest score
        SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        int orderPos = settings.getInt("gameOrderPos", 0);
        int deckSizePos = settings.getInt("deckSizePos", 0);
        int highScore = settings.getInt("hs_score0" + orderPos + deckSizePos, 10);
        // Get default names in case of fresh install, nothing in settings
        String[] defNames = getResources().getStringArray(R.array.default_names);
        String[] defDates = getResources().getStringArray(R.array.default_dates);
        int scoreIndex = 0;
        // loop through all high scores
        while (scoreIndex < 5) {
            String scoreKey = "hs_score" + scoreIndex + "," + orderPos + "," + deckSizePos;;
            highScore = settings.getInt(scoreKey, (scoreIndex + 1)*10);
            // if player score is higher than high score at index, move subsequent scores down
            if (playerScore < highScore) {
                for (int i = 4; i > scoreIndex; i--) {
                    String oldScoreKey = "hs_score" + i + "," + orderPos + "," + deckSizePos;
                    String newScoreKey = "hs_score" + (i - 1) + "," + orderPos + "," + deckSizePos;
                    String oldNameKey = "hs_name" + i + "," + orderPos + "," + deckSizePos;
                    String newNameKey = "hs_name" + (i - 1) + "," + orderPos + "," + deckSizePos;
                    String oldDateKey = "hs_date" + i + "," + orderPos + "," + deckSizePos;
                    String newDateKey = "hs_date" + (i - 1) + "," + orderPos + "," + deckSizePos;
                    editor.putInt(oldScoreKey, settings.getInt(newScoreKey, GameLogic.defaultScore(i, orderPos, deckSizePos)));
                    editor.putString(oldNameKey, settings.getString(newNameKey, defNames[i]));
                    editor.putString(oldDateKey, settings.getString(newDateKey, defDates[i]));
                }
                // replace high score at index with player score
                String nameKey = "hs_name" + scoreIndex + "," + orderPos + "," + deckSizePos;
                String dateKey = "hs_date" + scoreIndex + "," + orderPos + "," + deckSizePos;
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy,MM,dd");
                String dateString = formatter.format(date);
                editor.putInt(scoreKey, playerScore);
                editor.putString(nameKey, settings.getString("username", "You"));
                editor.putString(dateKey, dateString);
                editor.apply();
                break;
            }
            scoreIndex++;
        }

        alertBox();
    }

    private void alertBox() {
        startMediaPlayer(R.raw.winningsound);
        FragmentManager manager = getSupportFragmentManager();
        MessageFragment dialog = new MessageFragment();
        dialog.setCancelable(false);
        dialog.show(manager,"MessageDialog");
    }

    /**Suggestions - Use API 29*/
    private void saveBitmap(int screenCount) {
        File file = new File(Environment.getExternalStorageDirectory() + "/Find DaMatch Images:"+ dateString );
        file.mkdir();
        View v1 = getWindow().getDecorView().getRootView(); //v1.getHeight() = 1080,v1.getWidth = 1920
        v1.setDrawingCacheEnabled(true);
        Bitmap bm = Bitmap.createBitmap(v1.getDrawingCache(),0,(v1.getHeight()/4)-v1.getHeight()/53,v1.getWidth()/2 - v1.getWidth()/19 , v1.getHeight()-(v1.getHeight()/4)-v1.getHeight()/53);
        v1.setDrawingCacheEnabled(false);

        File newFile1 = new File(file, "DeckPile_Card"+screenCount+".jpg");
            try {
                OutputStream fileOutputStream = new FileOutputStream(newFile1);
                imageView = new ImageView(Game.this);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(Game.this,
                        "Something wrong: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
            if(cardCounter == 1){
                View v2 = getWindow().getDecorView().getRootView();
                v1.setDrawingCacheEnabled(true);
                Bitmap discardBitmap = Bitmap.createBitmap(v1.getDrawingCache(),v1.getWidth()/2,(v1.getHeight()/4)-v1.getHeight()/53,v1.getWidth()/2 - v1.getWidth()/19, v1.getHeight()-(v1.getHeight()/4)-v1.getHeight()/53);
                v1.setDrawingCacheEnabled(false);
                File newFile2 = new File(file,"1st DiscardPile_Card.jpg");
                try {
                    OutputStream fileOutputStream = new FileOutputStream(newFile2);
                    ImageView newImageView = new ImageView(Game.this);
                    discardBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    newImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Game.this,
                            "Something wrong: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void startMediaPlayer(int music) {
        MediaPlayer mediaPlayer = MediaPlayer.create(Game.this,music);
        mediaPlayer.start();
        mediaPlayer.setLooping(false);
    }

}