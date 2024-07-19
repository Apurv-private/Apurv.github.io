package cmpt276.project.GUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cmpt276.project.Model.GameLogic;
import cmpt276.project.R;

public class edit extends AppCompatActivity {

    private GameLogic gameLogic = GameLogic.getInstance();
    public static Intent makeIntent(Context context) {
        return new Intent(context, edit.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        populateListView();
        registerClickCallback();
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.homepageListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = "image removed";
                GameLogic gameLogic = GameLogic.getInstance();
                Set<String> names = gameLogic.getSave();
                assert names != null;
                List<String> ownName = new ArrayList<String>(names);
                String remove = ownName.get(position);
                Set<String> saveSet = gameLogic.getSave();
                saveSet.remove(remove);
                SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putStringSet("ownSelect" , gameLogic.getSave());
                editor.apply();
                Uri uri = Uri.parse(remove);
                final InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(uri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    gameLogic.getOwn().remove(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                populateListView();
                Toast.makeText(edit.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateListView() {
        ArrayAdapter<Bitmap> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.homepageListView);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Bitmap> {
        public MyListAdapter () {
            super(cmpt276.project.GUI.edit.this, R.layout.content_edit, gameLogic.getOwn());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.content_edit, parent, false);
            }

            GameLogic gameLogic = GameLogic.getInstance();
            Set<String> names = gameLogic.getSave();
            assert names != null;
            List<String> ownName = new ArrayList<String>(names);
            TextView name = (TextView) itemView.findViewById(R.id.listVieww);
            if ( position < ownName.size()) {
                name.setText(ownName.get(position));
                ImageView imageView = itemView.findViewById(R.id.imageView4);
                Uri uri = Uri.parse(ownName.get(position));
                final InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(uri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imageView.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return itemView;
        }
    }
}