package com.hamza.app.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hamza.app.R;

public class LoginActivity extends AppCompatActivity {

    EditText Email,passlogin;
    Button loginbtn ;
    TextView forgetpass;

    private FirebaseAuth mAuth;
    private String email,password ;
    private final String TAG ="LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Email = findViewById(R.id.emaillogin);
        passlogin = findViewById(R.id.passlogin);
        loginbtn = findViewById(R.id.loginbtn);
        forgetpass = findViewById(R.id.forgetpass);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();




        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                if (TextUtils.isEmpty(Email.getText())||TextUtils.isEmpty(passlogin.getText())){
                    Toast.makeText(LoginActivity.this, "Enter Email and password !", Toast.LENGTH_LONG).show();
                    return;
                }

            signinUser(email,password);


            }
        }); }

    private void updateUI(FirebaseUser currentUser) {


            Intent afterlogin = new Intent(getBaseContext(), AfterLogin.class);
            afterlogin.putExtra("email",currentUser.getEmail());
            startActivity(afterlogin);



    }

    public  void signinUser(String email,String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            updateUI(currentUser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getBaseContext(), "Wrong password or Email !", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }





    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser!=null){
            updateUI(currentUser);
        }


    }


    public void goback(View view) {

        Intent intent=new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }



    public void Gosignup(View view) {
        goback(view);
    }



}
