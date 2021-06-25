package com.example.segundoparcial;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogFoundUser extends DialogFragment {

    String rol;
    String username;

    public DialogFoundUser(String rol , String username) {

        this.rol = rol;
        this.username = username;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstance){


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        ListenerClick click = new ListenerClick();

        if (rol != null){
            builder.setTitle("Usuario Encontrado");
            builder.setMessage("El rol del usuario es " + rol);
            builder.setPositiveButton("CERRAR",click);
        }else {
            builder.setTitle("Usuario no encontrado");
            builder.setMessage("El usuario " + username + " no esta dentro de la lista");
            builder.setPositiveButton("CERRAR",click);
        }


        return builder.create();

    }
}
