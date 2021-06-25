package com.example.segundoparcial;

import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HiloConexion extends Thread {

    private Handler handler;

    public HiloConexion(Handler handler) {

        this.handler = handler;
    }

    @Override
    public void run() {

        ConexionHTTP conexionHTTP = new ConexionHTTP();


        byte[] paisesJson = conexionHTTP.obtenerRespuesta("http://192.168.1.9:3001/usuarios");

        String s = new String(paisesJson);

        Message msg = new Message();
        msg.obj = this.parserJson(s);

        handler.sendMessage(msg);


    }

    public List<Usuario> parserJson(String s) {

        List<Usuario> usuarios = new ArrayList<>();

        try {

            JSONArray jsonArray = new JSONArray(s);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                usuarios.add( new Usuario(Long.valueOf(jsonObject.getString("id"))  , jsonObject.getString("username") , jsonObject.getString("rol") , Boolean.parseBoolean(jsonObject.getString("admin")) ));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return usuarios;

    }

}
