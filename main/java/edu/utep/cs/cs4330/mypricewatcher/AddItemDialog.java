package edu.utep.cs.cs4330.mypricewatcher;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Objects;

public class AddItemDialog extends AppCompatDialogFragment {

    private EditText productName;
    private EditText productURL;
    private addItemListener listener;

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_item_dialog, null);
        productName = view.findViewById(R.id.nameString);
        productURL = view.findViewById(R.id.urlString);
        builder.setView(view).setTitle("Add Item")
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .setPositiveButton("OK", (dialog, which) -> {
                    String name = productName.getText().toString();
                    String url = productURL.getText().toString();
                    if (!name.equals("") && !url.equals("")) {
                        listener.addItem(name, url);
                    }
                });
        if (getArguments() != null) {
            productURL.setText(getArguments().getString("url"));
        }
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (addItemListener) context;
    }

    public interface addItemListener {
        void addItem(String name, String url);
    }
}

