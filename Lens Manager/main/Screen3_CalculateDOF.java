package cmpt276.as2.assignment2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cmpt276.as2.assignment2.Model.DepthOfFieldCalculator;
import cmpt276.as2.assignment2.Model.Lens;
import cmpt276.as2.assignment2.Model.LensManager;

public class Screen3_CalculateDOF extends AppCompatActivity {
    private Lens lens;
    private String newLensName;
    private double newAperture;
    private double newFocalLength;
    private int position;
    private LensManager manager;

    private double aperture;
    private double subjectDistance;
    private double coc;

    private DepthOfFieldCalculator dof;

    public static Intent makeIntent(Context context, int position) {
        Intent intent = new Intent(context, Screen3_CalculateDOF.class);
        intent.putExtra("position", position);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(getApplicationContext(), "Back to Home Page", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.Button_Edit:
                Intent intent = new Intent(Screen3_CalculateDOF.this,Screen4_EditLens.class);
                startActivityForResult(intent,RESULT_FIRST_USER);
                break;
            case R.id.Button_Delete:
                Intent data = new Intent();
                data.putExtra("SELECTED_LENS","delete");
                data.putExtra("POSITION",position);
                setResult(Activity.RESULT_OK,data);
                Toast.makeText(getApplicationContext(), "Lens Has been Removed", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case RESULT_FIRST_USER:
                newLensName = Screen4_EditLens.getLensMessage(data);
                newAperture = Screen4_EditLens.getApertureMessage(data);
                newFocalLength = Screen4_EditLens.getFocalLengthMessage(data);
                manager = LensManager.getInstance();
                manager.getIndex(position).setLens(newLensName);
                manager.getIndex(position).setMaximum_aperture(newAperture);
                manager.getIndex(position).setFocal_length(newFocalLength);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3__calculate_d_o_f);
        final EditText distanceInput = (EditText) findViewById(R.id.Text_Distance_to_subject);
        final EditText cocInput = (EditText) findViewById(R.id.Text_COC);
        final EditText apertureInput = (EditText) findViewById(R.id.Text_aperture_screen3);
        final TextView nearDistanceInput = (TextView) findViewById(R.id.Text_nearDistance);
        final TextView farDistanceInput = (TextView) findViewById(R.id.Text_farDistance);
        final TextView dofInput = (TextView) findViewById(R.id.Text_DOF);
        final TextView hyperFocalDistanceInput = (TextView) findViewById(R.id.Text_hyperFocalDistance);

        Toolbar toolbar = findViewById(R.id.ToolBar_Screen3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        extractDataFromIntent();
        setText();



        toolbar = findViewById(R.id.ToolBar_Screen3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Calculate DOF
        Button button4 = (Button) findViewById(R.id.Button_DOF);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
                calculateDOF();
            }

            private void calculateDOF()
            {
                coc = Double.parseDouble(cocInput.getText().toString());
                if (coc < 0) {
                    Toast.makeText(getApplicationContext(), "Invalid COC", Toast.LENGTH_SHORT).show();
                }

                subjectDistance = Double.parseDouble(distanceInput.getText().toString());
                if (subjectDistance < 0) {
                    Toast.makeText(getApplicationContext(), "Invalid distance to Subject", Toast.LENGTH_SHORT).show();
                }

                aperture = Double.parseDouble(apertureInput.getText().toString());
                if (aperture < 1.4) {
                    Toast.makeText(getApplicationContext(), "Invalid Aperture", Toast.LENGTH_SHORT).show();
                }
                else {
                    dof = new DepthOfFieldCalculator(lens, subjectDistance, aperture);
                    if (lens.getMaximum_aperture() > aperture) {
                        nearDistanceInput.setText("Invalid aperture");
                        farDistanceInput.setText("Invalid aperture");
                        hyperFocalDistanceInput.setText("Invalid aperture");
                        dofInput.setText("Invalid aperture");
                    } else {
                        nearDistanceInput.setText(roundOffTo2DecPlaces(dof.nearFocalPoint()));
                        farDistanceInput.setText(roundOffTo2DecPlaces(dof.farFocalPoint()));
                        hyperFocalDistanceInput.setText(roundOffTo2DecPlaces(dof.hyperFocalDistance()));
                        dofInput.setText(roundOffTo2DecPlaces(dof.DOF()));
                    }
                }

            }
        });

    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0) ;
    }

    private void setText() {
        LensManager manager = LensManager.getInstance();
        lens = manager.getIndex(position);
        TextView selectedInput = findViewById(R.id.Text_Lens);
        selectedInput.setText(lens.toString());
    }

    private String roundOffTo2DecPlaces(double val) {
        return String.format("%.2f", val);
    }

}







