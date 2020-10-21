package com.example.tarea_14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Inventario extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        mAuth = FirebaseAuth.getInstance();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_inventario, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.mVenta) {
            Intent i = new Intent(this,Ventas.class);
            startActivity(i);
        }
        if (id==R.id.mCompra) {
            Intent i = new Intent(this,Compras.class);
            startActivity(i);
        }
        if (id==R.id.mLista) {
            Intent i = new Intent(this,ListaProductos.class);
            startActivity(i);
        }
        if (id==R.id.mCerrar) {
            mAuth.signOut();
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}