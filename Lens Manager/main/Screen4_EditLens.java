package cmpt276.as2.assignment2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Screen4_EditLens extends AppCompatActivity {
    private String newLens;
    private double newFocalLength;
    private double newAperture;

    private EditText newLensInput;
    private EditText newApertureInput;
    private EditText newFocalLengthInput;

    public static String getLensMessage(Intent data) {
        return data.getStringExtra("RESULT_NEW_LENS"); }

    public static double getApertureMessage(Intent data) {
        return data.getDoubleExtra("RESULT_NEW_APERTURE",0); }

    public static double getFocalLengthMessage(Intent data) {
        return data.getDoubleExtra("RESULT_NEW_FOCAL_LENGTH",0); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen4__edit_lens);

        newLensInput = (EditText) findViewById(R.id.Text_newLens);
        newFocalLengthInput = (EditText) findViewById(R.id.Text_NewFocalLength);
        newApertureInput = (EditText) findViewById(R.id.Text_NewAperture);

        //Back button
        Toolbar toolbar = findViewById(R.id.ToolBar_screen4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Pressing edit button will take you to the home screen
        Button buttonEdit = findViewById(R.id.Button_Edit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLens();
            }

            private void editLens() {
                newLens = newLensInput.getText().toString();
                newFocalLength = Double.parseDouble(newFocalLengthInput.getText().toString());
                newAperture = Double.parseDouble(newApertureInput.getText().toString());
                Intent intent = new Intent(Screen4_EditLens.this,MainActivity.class);
                intent.putExtra("RESULT_NEW_LENS",newLens );
                intent.putExtra("RESULT_NEW_APERTURE",newAperture );
                intent.putExtra("RESULT_NEW_FOCAL_LENGTH",newFocalLength );
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }
}