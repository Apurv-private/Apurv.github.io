package cmpt276.as2.assigment3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import cmpt276.as2.assigment3.Model.MainMenu;

/**
 * Represents the alert dialog
 * Data includes methods to make the alert box
 */
public class MessageFragment extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Create the view to show
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.message_layout,null);

        //Create a button listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getContext(), MainMenu.class);
                getContext().startActivity(intent);
            }
        };

        //Build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Game Over")
                .setIcon(R.mipmap.ic_plague_icon_launcher_round)
                .setView(v)
                .setPositiveButton(android.R.string.ok,listener)
                .setCancelable(false)
                .create();
    }
}
