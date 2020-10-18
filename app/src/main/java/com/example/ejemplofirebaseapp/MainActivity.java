package com.example.ejemplofirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passEditText;
    private EditText rePassEditText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.emailEditText);
        passEditText = findViewById(R.id.passEditText);
        rePassEditText = findViewById(R.id.rePassEditText);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        Log.i("Users", "" + currentUser);
    }

    public void createUserWithEmailAndPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        emailEditText = findViewById(R.id.emailEditText);
                        passEditText = findViewById(R.id.passEditText);
                        rePassEditText = findViewById(R.id.rePassEditText);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            Toast.makeText(MainActivity.this, "Su cuenta ha sido registrado correctamente.", Toast.LENGTH_SHORT).show();
                            emailEditText.setText("");
                            passEditText.setText("");
                            rePassEditText.setText("");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void buttonSendData(View view) {
        String email = emailEditText.getText().toString();
        String password = passEditText.getText().toString();
        String repassword = rePassEditText.getText().toString();

        if (!email.isEmpty() && !password.isEmpty() && !repassword.isEmpty()) {
            if (password.equals(repassword)) {
                if (password.length() > 5) {
                    createUserWithEmailAndPassword(email, password);
                } else {
                    Toast.makeText(this, "La Contraseña debe tener minimo 5 caracteres.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Las contraseñas no son iguales.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Complete todos los campos para continuar.", Toast.LENGTH_SHORT).show();
        }
    }
};

