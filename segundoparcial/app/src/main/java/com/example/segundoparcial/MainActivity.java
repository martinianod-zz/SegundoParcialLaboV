package com.example.segundoparcial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, Handler.Callback {

    Handler handler;
    SharedPreferences.Editor editor;
    SharedPreferences usuarios;
    TextView tvUsuarios;

    List<Usuario> usuarioList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarios = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        editor = usuarios.edit();

        String usuariosString = usuarios.getString("usuarios", "");

        if (usuarios.getString("usuarios", "").equals("")) {

            //llamar a la api para traer usuarios
            handler = new Handler(this);
            HiloConexion hiloUsuarios = new HiloConexion(handler);
            hiloUsuarios.start();


        } else {

            //recuperar usuarios del sharedpreference
            tvUsuarios = findViewById(R.id.usuarios);
            tvUsuarios.setText(usuarios.getString("usuarios", ""));

        }


    }


    @Override
    public boolean handleMessage(@NonNull Message message) {


        tvUsuarios = findViewById(R.id.usuarios);
        tvUsuarios.setText(message.obj.toString());


        try {

            JSONArray jsonArray = new JSONArray(message.obj.toString());

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                usuarioList.add( new Usuario(Long.valueOf(jsonObject.getString("id"))  , jsonObject.getString("username") , jsonObject.getString("rol") , Boolean.parseBoolean(jsonObject.getString("admin")) ));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        editor.putString("usuarios" , usuarioList.toString() );

        editor.commit();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_principal , menu);

        MenuItem menuItem = menu.findItem(R.id.buscar);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addUser) {
            Log.d("menu","Click en add contacts");

            MiDialog miDialog = new MiDialog(editor , usuarios , tvUsuarios);

            miDialog.show(getSupportFragmentManager(), "dialogo");


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("activity", "Hago una busqueda con:" + query);

        String stringContacts = usuarios.getString("usuarios", "");

        try {

            boolean personaEncontada = false;

            JSONArray jsonArray = new JSONArray(stringContacts);

            for (int i = 0 ; i < jsonArray.length() ; i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (query.toLowerCase().equals(jsonObject.getString("username").toLowerCase())){

                    personaEncontada = true;

                    DialogFoundUser dialogFoundUser = new DialogFoundUser( jsonObject.getString("rol") , null );
                    dialogFoundUser.show(getSupportFragmentManager(), "dialogo");

                    break;
                }

            }

            if(!personaEncontada){
                DialogFoundUser dialogFoundUser = new DialogFoundUser( null , query );
                dialogFoundUser.show(getSupportFragmentManager(), "dialogo");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}