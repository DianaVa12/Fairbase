package com.example.tarea_14;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import Model.DatosProd;

public class Producto extends AppCompatActivity {

    private EditText etcodigo,etproducto,etstock,etcosto,etventa;
    private Button guardar,actualizar,borrar,cerrar;
    private ListView list;

    private List<DatosProd> listPersona = new ArrayList<DatosProd>();
    ArrayAdapter<DatosProd> arrayAdapterPersona;

    private DatabaseReference mDataBase;
    private FirebaseDatabase mFirebase;
    private FirebaseAuth mAuth;
    DatosProd productoSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        //BOTONES
        guardar = (Button)findViewById(R.id.btnGuardar);
        actualizar = (Button)findViewById(R.id.btnActualizar);
        borrar = (Button)findViewById(R.id.btnBorrar);
        cerrar = (Button)findViewById(R.id.btnRe);
        //TEXTOS
        etcodigo = (EditText) findViewById(R.id.etCodigo);
        etproducto = (EditText) findViewById(R.id.etNomPro);
        etstock = (EditText) findViewById(R.id.etStock);
        etcosto = (EditText) findViewById(R.id.etCosto);
        etventa = (EditText) findViewById(R.id.etVenta);
        //FIREBASE
        inicializarFirebase();
        //LISTAS
        list = (ListView)findViewById(R.id.listView);
        listarDatos();
        //
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productoSelected = (DatosProd) parent.getItemAtPosition(position);
                etcodigo.setText(productoSelected.getCodigo());
                etproducto.setText(productoSelected.getProducto());
                etstock.setText(productoSelected.getStock());
                etcosto.setText(productoSelected.getCosto());
                etventa.setText(productoSelected.getVenta());
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = etcodigo.getText().toString();
                String nom =    etproducto.getText().toString();
                String stock =  etstock.getText().toString();
                String costo =  etcosto.getText().toString();
                String venta =  etventa.getText().toString();

                cargarProducto(codigo, nom, stock, costo, venta);

            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatosProd p = new DatosProd();
                p.setUid(productoSelected.getUid());
                p.setCodigo(etcodigo.getText().toString().trim());
                p.setProducto(etproducto.getText().toString().trim());
                p.setStock(etstock.getText().toString().trim());
                p.setCosto(etcosto.getText().toString().trim());
                p.setVenta(etventa.getText().toString().trim());
                mDataBase.child("Producto").child(p.getUid()).setValue(p);
                Toast.makeText(Producto.this, "¡Producto Actualizado Exitosamente!", Toast.LENGTH_LONG).show();
                limpiarCampos();
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatosProd p = new DatosProd();
                p.setUid(productoSelected.getUid());
                mDataBase.child("Producto").child(p.getUid()).removeValue();
                Toast.makeText(Producto.this, "¡Producto Eliminado Exitosamente!", Toast.LENGTH_LONG).show();
                limpiarCampos();
            }
        });


   }

   public void cerrar(View view){
       startActivity(new Intent( this, MainActivity.class));
       finish();
   }

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
                    DatosProd p = objSnapshot1.getValue(DatosProd.class);
                    listPersona.add(p);

                    arrayAdapterPersona = new ArrayAdapter<DatosProd>(Producto.this, android.R.layout.simple_list_item_1, listPersona);
                    list.setAdapter(arrayAdapterPersona);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void cargarProducto(String codigo, String nom, String stock, String costo, String venta) {
        DatosProd producto = new DatosProd();
        producto.setUid(UUID.randomUUID().toString());
        producto.setCodigo(codigo);
        producto.setProducto(nom);
        producto.setStock(stock);
        producto.setCosto(costo);
        producto.setVenta(venta);
        mDataBase.child("Producto").child(producto.getUid()).setValue(producto);
        Toast.makeText(Producto.this, "¡Producto Agregado Exitosamente!", Toast.LENGTH_SHORT).show();
        limpiarCampos();
}

    private void limpiarCampos(){
        etcodigo.setText("");
        etproducto.setText("");
        etstock.setText("");
        etcosto.setText("");
        etventa.setText("");
    }

}
