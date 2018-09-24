package com.lsc.a1140676.mabel.contador;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView texto;
    private int contador=0;
    private EditText edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texto=(TextView) findViewById(R.id.texto);
        edittext=(EditText) findViewById(R.id.buscar);
        registerForContextMenu(texto);
        if(savedInstanceState!=null){
            contador=Integer.parseInt(savedInstanceState.getString("count"));
        }
        texto.setText("Contador= "+contador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo contextInfo){
        super.onCreateContextMenu(menu,v,contextInfo);
        getMenuInflater().inflate(R.menu.menu_context,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.itemContextEdit:
                contador=0;
                texto.setText("Contador= "+contador);
                Toast.makeText(getApplicationContext(),"Se limpio el contador",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemContextShow:
                Toast.makeText(getApplicationContext(),"El contador esta en "+contador,Toast.LENGTH_SHORT).show();
                return true;
                default:
                    return super.onContextItemSelected(item);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.item1:
                Toast.makeText(getApplicationContext(), "Valor 1",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(getApplicationContext(),"Valor 2",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(getApplicationContext(),"Hola",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item4:
                Toast.makeText(getApplicationContext(),"Salvate tu solo",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item5:
                Toast.makeText(getApplicationContext(),"Valor 5",Toast.LENGTH_SHORT).show();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    public void iniciarIntent(View view){
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        String query = edittext.getText().toString();
        intent.putExtra(SearchManager.QUERY,query);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }

    public void buscarURL(View view){
        String direccion=edittext.getText().toString();
        if(validarProtocolo(direccion)){
            Uri uri = Uri.parse(direccion);
            Intent it = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(it);
        }else{
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);

            alertDialog.setTitle("Alto");
            alertDialog.setMessage(R.string.alerta);
            alertDialog.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT);
                }
            });
            AlertDialog alert=alertDialog.create();
            alert.show();
        }
    }

    public void sumar(View view){
        contador++;
        texto.setText("Contador= "+contador);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("count",String.valueOf(contador));
    }
    public Boolean validarProtocolo(String url){
        Boolean cumpleProtocolo= true;
        if(url.length()>7) {
            String protocolo = "";
            try {
                for (int o = 0; o < 7; o++) {
                    protocolo = protocolo + String.valueOf(url.charAt(o));
                }
                if (protocolo.equalsIgnoreCase("http://"))
                    cumpleProtocolo = true;
                else {
                    if(url.length()>8){
                        protocolo = protocolo + String.valueOf(url.charAt(7));
                        if (protocolo.equalsIgnoreCase("https://"))
                            cumpleProtocolo = true;
                        else
                            cumpleProtocolo = false;
                    }else{
                        cumpleProtocolo = false;
                    }
                }
            } catch (IllegalStateException e) {
                cumpleProtocolo = false;
            } catch (ArrayIndexOutOfBoundsException e) {
                cumpleProtocolo = false;
            } catch (NullPointerException e) {
                cumpleProtocolo = false;
            }
        }else{
            cumpleProtocolo=false;
        }
        return cumpleProtocolo;
    }
}
