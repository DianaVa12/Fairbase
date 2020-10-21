package com.example.tarea_14;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Persona extends AppCompatActivity {
    private TextView tvCorreo;
    private EditText cedula,nombre,provincia;
    private RadioButton mGenerOption;
    private RadioGroup mGener;
    private Spinner spinner2;
    private Button actualizar, salir;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona);



        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        tvCorreo = (TextView)findViewById(R.id.tvCorreo);
        cedula = (EditText) findViewById(R.id.etCedula);
        nombre = (EditText)findViewById(R.id.etNombre);
        provincia = (EditText)findViewById(R.id.etProvincia);
        mGener = (RadioGroup) findViewById(R.id.rgSexo);
        actualizar = (Button) findViewById(R.id.btnActualizar);
        salir = (Button) findViewById(R.id.btnSalir);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        final String []opciones={"Paises","Ecuador","Colombia","Venezuela"};
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item, opciones);
        spinner2.setAdapter(adapter);
        /* actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ced = cedula.getText().toString();
                nom = nombre.getText().toString();
                pro = provincia.getText().toString();
                Map<String, Object> personaMap = new HashMap<>();
                personaMap.put("cedula",ced);
                personaMap.put("nombre",nom);
                personaMap.put("sexo",mGenerOption);
                personaMap.put("pais",opciones);
                mDatabase.child("users").updateChildren(personaMap);
            }
        });*/

getDatos();

salir.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       regresar();
    }
});
    }

    private void regresar() {
        mAuth.signOut();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    private void getDatos(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        String correo = snapshot.child("correo").getValue().toString();
                        tvCorreo.setText(correo);
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}