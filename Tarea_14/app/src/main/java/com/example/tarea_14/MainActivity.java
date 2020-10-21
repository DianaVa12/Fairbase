package com.example.tarea_14;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText correo,contraseña;
    private Button ingresar;
    private Spinner spinner1;
    private FirebaseAuth mAuth;


    private String email = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        correo = (EditText)findViewById(R.id.etUser);
        contraseña = (EditText)findViewById(R.id.etPass);
        ingresar = (Button) findViewById(R.id.btnIngresar);
        spinner1 = (Spinner)findViewById(R.id.spinner);
        String []opciones={"Opciones","Persona","Producto","Inventario"};
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item, opciones);
        spinner1.setAdapter(adapter);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = correo.getText().toString();
                password = contraseña.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){
                    loginUser();
                }else{
                    Toast.makeText(MainActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private  void loginUser(){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String select =spinner1.getSelectedItem().toString();
                if (task.isSuccessful()){
                    if (select.equals("Opciones")) {
                        Toast.makeText(MainActivity.this, "Debe Selecionar una opcion", Toast.LENGTH_SHORT).show();
                    } else
                    if (select.equals("Persona")) {
                        startActivity(new Intent(MainActivity.this, Persona.class));
                        finish();
                    }else
                    if (select.equals("Producto")) {
                        startActivity(new Intent(MainActivity.this, Producto.class));
                        finish();
                    }else
                    if (select.equals("Inventario")) {
                        startActivity(new Intent(MainActivity.this, Inventario.class));
                        finish();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "No se pudo Iniciar Sesion compruebe los datos ingresados ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registrarse(View view){
        Intent i = new Intent(this,Registro.class);
        startActivity(i);
    }
}