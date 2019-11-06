package edu.utep.cs.cs4330.mypricewatcher;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

public class AddItemDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_add_item_dialog, null));

        return builder.create();
    }
}
