package cmpt276.as2.assignment2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cmpt276.as2.assignment2.Model.Lens;
import cmpt276.as2.assignment2.Model.LensManager;


public class MainActivity extends AppCompatActivity {
    private LensManager manager ;
    private String lensName;
    private double aperture;
    private double focalLength = 0;
    private ArrayAdapter<String> adapter;
    private String[] myList;

    private static final int REQUEST_CODE_GETLENS = 1014;
    private static final int RESULT_SELECTEDLENS = 1013;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateLensManager();
        populateListView();
        registerClickCallback();

        Toolbar toolbar = findViewById(R.id.ToolBar_Screen1);
        setSupportActionBar(toolbar);

        //Making "+" button
        FloatingActionButton fab = findViewById(R.id.FAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Add a lens",Toast.LENGTH_SHORT).show();
                openScreen2();
            }


            private void openScreen2() {
                Intent intent = new Intent(MainActivity.this, Screen2_AddLens.class);
                startActivityForResult(intent,REQUEST_CODE_GETLENS);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //Getting the result from screen 2,screen 3 and adding it to the LensManager
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_GETLENS:
                if (resultCode == Activity.RESULT_OK) {
                    lensName = Screen2_AddLens.getLensMessage(data);
                    aperture = Screen2_AddLens.getApertureMessage(data);
                    focalLength = Screen2_AddLens.getFocalLengthMessage(data);
                }
                if(lensName != null){
                manager = LensManager.getInstance();
                manager.add(new Lens(lensName,focalLength,aperture,R.drawable.ic_baseline_lens_24));
                populateListView();
                break;
                }
                manager = LensManager.getInstance();
                populateListView();
            case RESULT_SELECTEDLENS:
                int position = data.getIntExtra("POSITION",0);
                manager = LensManager.getInstance();
                manager.delete(position);
                populateListView();
                break;
        }

    }

    //On clicking ListView items
    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.List_View);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(getApplicationContext(),"Calculate DoF",Toast.LENGTH_SHORT).show();
                Intent intent = Screen3_CalculateDOF.makeIntent(MainActivity.this,position);
                startActivityForResult(intent,RESULT_SELECTEDLENS);
            }
        });
    }
    //Populating LensManager ArrayList
    private void populateLensManager() {
        manager = LensManager.getInstance();
        manager.add(new Lens("Canon", 1.8, 50,R.drawable.ic_baseline_lens_24));
        manager.add(new Lens("Tamron",2.8,90,R.drawable.ic_baseline_lens_24));
        manager.add(new Lens("Sigma",2.8,200,R.drawable.ic_baseline_lens_24));
        manager.add(new Lens("Nikon",4.0,200,R.drawable.ic_baseline_lens_24));
    }

    //Populating ListView using ArrayAdapter
    private void populateListView() {
        myList = new String[manager.length()];

        //Build Adapter
        for (int i = 0 ; i < manager.length();i++)
            myList[i] = manager.getIndex(i).toString();
        adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.List_View);
        list.setAdapter(adapter);
    }
    public static Intent makeIntent(Screen2_AddLens context) {
        return new Intent();
    }

    private class MyListAdapter extends ArrayAdapter<String> {
        public MyListAdapter(){
            super(MainActivity.this,R.layout.layout_screen1,myList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.layout_screen1,parent,false);
            }
            Lens lens = manager.getIndex(position);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.image_icon);
            imageView.setImageResource(lens.getIconID());

            TextView lensText = (TextView) itemView.findViewById(R.id.Text_LENS);
            lensText.setText(lens.toString());
            return itemView;
        }
    }
}
