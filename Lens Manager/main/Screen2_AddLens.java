package cmpt276.as2.assignment2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class Screen2_AddLens extends AppCompatActivity {
    private String lens_name;
    private static double aperture;
    private static double focal_length;

    private EditText lensInput;
    private EditText apertureInput;
    private EditText focalLength_Input;

    //To make the back button i.e android.R.id.home and to make the save button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(getApplicationContext(), "Back to Home Page", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.Button_Save:
                Toast.makeText(getApplicationContext(), "New Lens Added", Toast.LENGTH_SHORT).show();
                save_openScreen1();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.screen2_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    //Function to save user input and send it to MainActivity via intent
    private void save_openScreen1() {
        lens_name = lensInput.getText().toString();
        aperture = Double.parseDouble(apertureInput.getText().toString());
        focal_length = Double.parseDouble(focalLength_Input.getText().toString());
        if(lens_name.length() == 0 || aperture <= 1.4 || focal_length < 0) {
            Toast.makeText(getApplicationContext(),"Error Input",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(Screen2_AddLens.this,MainActivity.class);
            intent.putExtra("RESULT_LENS_MESSAGE",lens_name );
            intent.putExtra("RESULT_APERTURE_MESSAGE",aperture );
            intent.putExtra("RESULT_FOCAL_LENGTH_MESSAGE",focal_length );
            setResult(Activity.RESULT_OK,intent);
            }
        finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2__add_lens);
        lensInput = (EditText) findViewById(R.id.Text_Canon_screen2);
        apertureInput = (EditText) findViewById(R.id.Text_Focal_length);
        focalLength_Input = (EditText)findViewById(R.id.Text_distance_screen2);

        Toolbar toolbar = findViewById(R.id.Toolbar_Screen2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static String getLensMessage (Intent intent){
        return intent.getStringExtra("RESULT_LENS_MESSAGE");
    }
    public static double getApertureMessage (Intent intent){
        return intent.getDoubleExtra("RESULT_APERTURE_MESSAGE", 0);
    }
    public static double getFocalLengthMessage (Intent intent){
        return intent.getDoubleExtra("RESULT_FOCAL_LENGTH_MESSAGE",0);
    }
}
