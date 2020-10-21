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
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Model.DatosProd;

public class Compras extends AppCompatActivity {
    private EditText etCantidad;
    private Button pagar,comprar,cerrar;
    private TextView tvnombre,tvstock,tvventa,tvpagar;
    private ListView list;

    private List<DatosProd> listPersona = new ArrayList<DatosProd>();
    ArrayAdapter<DatosProd> arrayAdapterPersona;

    private DatabaseReference mDataBase;
    private FirebaseDatabase mFirebase;
    private FirebaseAuth mAuth;
    DatosProd productoSelected;

    //DEICIMAL
    DecimalFormat currency = new DecimalFormat("$###,##.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);
        //BOTONES
        comprar = (Button) findViewById(R.id.btnComprarPro);
        pagar = (Button)findViewById(R.id.btnPagarPro);
        cerrar = (Button)findViewById(R.id.btnRe);
        //TEXTOS
        etCantidad = (EditText) findViewById(R.id.etCantidadPro);
        tvnombre = (TextView) findViewById(R.id.tvNombrePro);
        tvstock = (TextView) findViewById(R.id.tvStockPro);
        tvventa = (TextView) findViewById(R.id.tvPrecioC);
        tvpagar = (TextView) findViewById(R.id.tvPagarPro);
        //FIREBASE
        inicializarFirebase();
        //LISTAS
        list = (ListView)findViewById(R.id.listViewPro);
        listarDatos();

        //
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productoSelected = (DatosProd) parent.getItemAtPosition(position);
                tvnombre.setText(productoSelected.getProducto());
                tvstock.setText(productoSelected.getStock());
                tvventa.setText(productoSelected.getCosto());

            }
        });
        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagarProducto();
            }
        });
        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprarProducto();
            }
        });

    }
    private void comprarProducto() {

    }

    private void pagarProducto() {
        String valor1=etCantidad.getText().toString();
        String valor2=tvventa.getText().toString();
        int nro1= Integer.parseInt(valor1);
        double nro2= Double.parseDouble(valor2);
        double suma = nro1 * nro2;
        //String resu= String.valueOf(suma);
        tvpagar.setText("Su total a pagar es :"+ currency.format(suma));

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

                    arrayAdapterPersona = new ArrayAdapter<DatosProd>(Compras.this, android.R.layout.simple_list_item_1, listPersona);
                    list.setAdapter(arrayAdapterPersona);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}