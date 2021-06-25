package com.example.segundoparcial;

import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;

public class ListenerClick implements DialogInterface.OnClickListener {
    @Override
    public void onClick(DialogInterface dialogInterface, int which) {

        if (Dialog.BUTTON_NEGATIVE == which){
            Log.d("Llego click" , "cancelo la operacion");
        }else if (Dialog.BUTTON_POSITIVE == which){
            Log.d("Llego click" , "acepto la operacion");
        }


    }
}