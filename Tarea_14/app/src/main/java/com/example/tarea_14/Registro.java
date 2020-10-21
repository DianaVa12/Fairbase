package com.example.tarea_14;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {
    private EditText correo, contraseña;
    private Button guardar;
    //VARIABLE DE FIREBASE
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    //VARIABLES DE LOS DATOS QUE SE VAN A REGISTRAR
    private String email = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        correo = (EditText) findViewById(R.id.etCorreo);
        contraseña = (EditText) findViewById(R.id.etContraseña);
        guardar = (Button) findViewById(R.id.btnRegistrarme);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = correo.getText().toString();
                password = contraseña.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){
                    if (password.length()>=6){
                        registerUser();
                    }else{
                        Toast.makeText(Registro.this, "La Contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Registro.this, "Debe Completar los Campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void registerUser(){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("correo",email);
                            String id = mAuth.getCurrentUser().getUid();

                            mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if (task2.isSuccessful()) {
                                        startActivity(new Intent(Registro.this, MainActivity.class));
                                        finish();
                                        Toast.makeText(Registro.this, "Te has registrado exitosamente", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Registro.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Registro.this, "No se pudo registrar este usuario",Toast.LENGTH_SHORT).show();
                        }
                    }
        });
    }
}