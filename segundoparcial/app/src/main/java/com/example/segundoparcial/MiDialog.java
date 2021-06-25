package com.example.segundoparcial;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MiDialog extends DialogFragment implements View.OnClickListener {

    private final SharedPreferences.Editor editor;
    private final SharedPreferences contacts;
    private final TextView textViewMain;

    EditText usernameEdit;
    Spinner rolEdit;
    ToggleButton adminEdit;

    public MiDialog(SharedPreferences.Editor editor, SharedPreferences contacts, TextView textView) {

        this.editor = editor;
        this.contacts = contacts;
        this.textViewMain = textView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstance) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog, null);

        usernameEdit = view.findViewById(R.id.username_edit);
        adminEdit = view.findViewById(R.id.admin_toggle);
        rolEdit = view.findViewById(R.id.rol_spinner);

        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Construction Manager");
        spinnerArray.add("Supervisor");
        spinnerArray.add("Project Manager");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        rolEdit.setAdapter(adapter);


        adminEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    adminEdit.setChecked(true);
                } else {
                    // The toggle is disabled
                    adminEdit.setChecked(false);
                }
            }
        });


        Button cancelBtn = view.findViewById(R.id.cancelar);
        cancelBtn.setOnClickListener(view1 -> {
            Log.d("cancel dialog", "dismiss()");
            dismiss();
        });

        Button guardarBtn = view.findViewById(R.id.guardar);
        guardarBtn.setOnClickListener(this);


        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onClick(View view) {
        Log.d("guardar datos", "guardar()");

        //validar que los campos no esten vacios

        if (usernameEdit.getText().toString().trim().length() > 0 && rolEdit.getSelectedItem().toString().trim().length() > 0) {

            List<Usuario> listUpdated = new ArrayList<>();
            //guardo los datos en el shared preference

            String stringContacts = contacts.getString("usuarios", "");

            try {
                JSONArray jsonArray = new JSONArray(stringContacts);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    listUpdated.add(new Usuario(Long.valueOf(jsonObject.getString("id")), jsonObject.getString("username"), jsonObject.getString("rol"), Boolean.parseBoolean(jsonObject.getString("admin"))));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Usuario newUsuario = new Usuario(Long.valueOf(listUpdated.size() + 1), usernameEdit.getText().toString(), rolEdit.getSelectedItem().toString(), adminEdit.isChecked());

            listUpdated.add(newUsuario);

            editor.putString("usuarios", listUpdated.toString());

            editor.commit();

            textViewMain.setText(contacts.getString("usuarios", ""));

            dismiss();

        }

    }
}
