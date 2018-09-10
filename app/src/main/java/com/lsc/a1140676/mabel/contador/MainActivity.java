package com.lsc.a1140676.mabel.contador;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        if(savedInstanceState!=null){
            contador=Integer.parseInt(savedInstanceState.getString("count"));
        }
        texto.setText("Contador= "+contador);
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
        Uri uri = Uri.parse(edittext.getText().toString());
        Intent it = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(it);
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
}
