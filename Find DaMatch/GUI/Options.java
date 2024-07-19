package cmpt276.project.GUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.ClipData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import cmpt276.project.Model.GameLogic;

import cmpt276.project.MusicPlayer;
import cmpt276.project.R;
import cmpt276.project.editOwn;
import cmpt276.project.photogallery.DownloadTask;
import cmpt276.project.photogallery.FlickrImageSet;
import cmpt276.project.photogallery.PhotoGalleryActivity;

import static cmpt276.project.GUI.Game.verifyStoragePermissions;


public class Options extends AppCompatActivity{
    private MusicPlayer musicPlayer = MusicPlayer.getInstance();

    private EditText name;
    private final int[] orders = {2, 3, 5};
    private final int[] deckSizes = {5, 10, 15, 20, -1};
    public static Vector<String> strings = new Vector<>();
    private static final int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        name = findViewById(R.id.optionsEditUser);

        setdownload();
        setupSpinners();
        setupHighScoreReset();
        setupNameEdit();
        setupSelectOwnBtn();
        setupEdit();

        settingFlickrButton();

        final Button cardsSaved = findViewById(R.id.Button_cardsSaved);
        cardsSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicPlayer.getCardsSaved() == 0){
                Toast.makeText(Options.this,"Cards Will be saved",Toast.LENGTH_SHORT).show();
                verifyStoragePermissions(Options.this);
                musicPlayer.setCardsSaved(1);
                }
                else {
                    Toast.makeText(Options.this,"Cards Will not be saved",Toast.LENGTH_SHORT).show();
                    musicPlayer.setCardsSaved(0);
                }
            }
        });
    }

    private void setupEdit() {
        final Button editPhoto = findViewById(R.id.editPhoto);
        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = edit.makeIntent(Options.this);
                startActivity(intent);
            }
        });
    }

    private void setupSelectOwnBtn() {
        Button selectOwn = findViewById(R.id.SelectOwnPicFromGallery);
        selectOwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                }
            }
        });
    }

    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            // check version of Android on device
            if(Build.VERSION.SDK_INT > 27){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            GameLogic gameLogic = GameLogic.getInstance();
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                ArrayList mArrayUri = new ArrayList<Uri>();
                ArrayList mBitmapsSelected = new ArrayList<Bitmap>();

                for (int i = 0; i < mClipData.getItemCount(); i++) {
                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    mArrayUri.add(uri);
                    Bitmap bitmap = loadFromUri(uri);
                    mBitmapsSelected.add(bitmap);
                    gameLogic.setAdd(uri.toString());
                    SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putStringSet("ownSelect", gameLogic.getSave());
                    editor.apply();
                    for(int j = 0; j < mBitmapsSelected.size(); j++) {
                        gameLogic.addOwn((Bitmap) mBitmapsSelected.get(j));
                    }
                }
            } else if (data.getData() != null) {
                try {
                    final Uri imageUri = data.getData();
                    gameLogic.setAdd(imageUri.toString());
                    SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putStringSet("ownSelect", gameLogic.getSave());
                    editor.apply();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    gameLogic.addOwn(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setdownload() {
        Button flickrDownload = findViewById(R.id.optionsBtnDownload);
        flickrDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlickrImageSet flickrImageSet = FlickrImageSet.getInstance();
                Vector<String> urls = flickrImageSet.getUrls();

                for (int i = 0; i < urls.size(); i++) {
                    DownloadTask downloadTask = new DownloadTask(Options.this);
                    downloadTask.execute(urls.get(i));
                    Toast.makeText(Options.this, "S" + i, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void settingFlickrButton() {
        Button flickr = findViewById(R.id.optionsBtnFlickr);
        flickr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Options.this, PhotoGalleryActivity.class));
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, Options.class);
    }

    //Sets up dropdown menus. Assuming we'll need more, put them here
    private void setupSpinners() {
        SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);

        Spinner spinImgSet = findViewById(R.id.optionsSpinImgs);
        String[] imgSets = getResources().getStringArray(R.array.img_sets);
        ArrayAdapter<String> imgSetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, imgSets);
        spinImgSet.setAdapter(imgSetAdapter);
        spinImgSet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saveImgSet(position);
                musicPlayer.setPlayer(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("spinImgSet", "Nothing selected");
            }
        });
        spinImgSet.setSelection(settings.getInt("imgSet", 0));

        Spinner spinGameMode = findViewById(R.id.optionsSpinMode);
        String[] gameModes = getResources().getStringArray(R.array.game_modes);
        ArrayAdapter<String> gameModeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, gameModes);
        spinGameMode.setAdapter(gameModeAdapter);
        spinGameMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saveGameMode(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("spinGameMode", "Nothing selected");
            }
        });
        spinGameMode.setSelection(settings.getInt("gameMode", 0));

        final Spinner spinGameOrder = findViewById(R.id.optionsSpinOrder);
        String[] gameOrders = getResources().getStringArray(R.array.game_orders);
        ArrayAdapter<String> gameOrderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, gameOrders);
        spinGameOrder.setAdapter(gameOrderAdapter);
        spinGameOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!saveGameOrder(position)) {
                    spinGameOrder.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("spinGameOrder", "Nothing selected");
            }
        });
        spinGameOrder.setSelection(settings.getInt("gameOrderPos", 0));

        final Spinner spinDeckSize = findViewById(R.id.optionsSpinSize);
        String[] deckSizes = getResources().getStringArray(R.array.deck_sizes);
        ArrayAdapter<String> deckSizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, deckSizes);
        spinDeckSize.setAdapter(deckSizeAdapter);
        spinDeckSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!saveDeckSize(position)) {
                    spinDeckSize.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("spinDeckSize", "Nothing selected");
            }
        });
        spinDeckSize.setSelection(settings.getInt("deckSizePos", 0));

        Spinner spinDifficulty = findViewById(R.id.optionsSpinDifficulty);
        String[] difficulties = getResources().getStringArray(R.array.difficulties);
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, difficulties);
        spinDifficulty.setAdapter(difficultyAdapter);
        spinDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saveDifficulty(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("spinImgSet", "Nothing selected");
            }
        });
        spinDifficulty.setSelection(settings.getInt("difficulty", 0));
    }

    private void saveImgSet(int position) {
        SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("imgSet", position);
        editor.apply();
    }

    private void saveGameMode(int position) {
        SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("gameMode", position);
        editor.apply();
    }

    private boolean saveGameOrder(int position) {
        SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        GameLogic logic = GameLogic.getInstance();
        if (logic.isValidOrder(settings.getInt("imgSet", 0), orders[position])) {
            editor.putInt("gameOrder", orders[position]);
            editor.putInt("gameOrderPos", position);
            editor.apply();
            return true;
        } else {
            Toast.makeText(this, "Sorry, that game order is not valid for this Theme", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean saveDeckSize(int position) {
        SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        int order = settings.getInt("gameOrder", 2);
        int maxCards = order * order + order + 1;
        if (deckSizes[position] <= maxCards) {
            editor.putInt("deckSize", deckSizes[position]);
            editor.putInt("deckSizePos", position);
            editor.apply();
            return true;
        } else {
            Toast.makeText(this, "Sorry, that draw pile size is not valid for this order", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void saveDifficulty(int position) {
        SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("difficulty", position);
        editor.apply();
    }

    // When btn clicked, replace keys with default values
    private void setupHighScoreReset() {
        Button btn = findViewById(R.id.optionsBtnReset);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Options.this, "High Scores reset", Toast.LENGTH_SHORT).show();
                SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                String[] names = getResources().getStringArray(R.array.default_names);
                String[] dates = getResources().getStringArray(R.array.default_dates);
                // i is the position, j is order, k is draw pile size
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < 5; k++) {
                            String scoreKey = "hs_score" + i + "," + j + "," + k;
                            String nameKey = "hs_name" + i + "," + j + "," + k;
                            String dateKey = "hs_date" + i + "," + j + "," + k;
                            editor.putInt(scoreKey, GameLogic.defaultScore(i, j, k));
                            editor.putString(nameKey, names[i]);
                            editor.putString(dateKey, dates[i]);
                        }
                    }
                }
                editor.apply();
            }
        });
    }

    // When the confirm button is pressed put the new username in shared prefs
    private void setupNameEdit() {
        Button btn = findViewById(R.id.optionsConfirmBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                String nameString = name.getText().toString();
                editor.putString("username", nameString);
                editor.apply();
                Toast.makeText(Options.this, "Your username is now " + nameString, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back to Main Menu", Toast.LENGTH_SHORT).show();
        finish();
    }
}