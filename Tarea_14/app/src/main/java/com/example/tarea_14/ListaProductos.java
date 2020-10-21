package com.example.tarea_14;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Model.DatosProd;
import Model.DatosProd2;

public class ListaProductos extends AppCompatActivity {

    private ListView list;

    private List<DatosProd2> listPersona = new ArrayList<DatosProd2>();
    ArrayAdapter<DatosProd2> arrayAdapterPersona;

    private DatabaseReference mDataBase;
    private FirebaseDatabase mFirebase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        //FIREBASE
        inicializarFirebase();

        //LISTAS
        list = (ListView)findViewById(R.id.listViewMostrar);
        listarDatos();


    }
    //public boolean onCreateOptionsMenu(Menu menu) {
    //    getMenuInflater().inflate(R.menu.menucerrar, menu);
    //    return true;
    //}
    //public boolean onOptionsItemSelected(MenuItem item) {
    //    int id = item.getItemId();
    //    if (id==R.id.mCerrar) {
    //        startActivity(new Intent(this,Inventario.class));
    //        finish();
    //    }
//
    //    return super.onOptionsItemSelected(item);
    //    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebase = FirebaseDatabase.getInstance();
        mFirebase.setPersistenceEnabled(true);
        mDataBase = mFirebase.getReference();
    }
    private void listarDatos() {
        mDataBase.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPersona.clear();
                for (DataSnapshot objSnapshot1 : snapshot.getChildren()){
                    DatosProd2 p = objSnapshot1.getValue(DatosProd2.class);
                    listPersona.add(p);

                    arrayAdapterPersona = new ArrayAdapter<DatosProd2>(ListaProductos.this, android.R.layout.simple_list_item_1, listPersona);
                    list.setAdapter(arrayAdapterPersona);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}