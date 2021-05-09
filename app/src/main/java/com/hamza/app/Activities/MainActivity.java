package com.hamza.app.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hamza.app.Model.User;
import com.hamza.app.R;
import com.hamza.app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {



    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String USER="user";
    private static final String TAG="MainActivity";
    private User user ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);





    database = FirebaseDatabase.getInstance();
    mDatabase = database.getReference(USER);
    mAuth = FirebaseAuth.getInstance();

    mainBinding.signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = mainBinding.email.getText().toString();
            String pass = mainBinding.passward.getText().toString();
            if (TextUtils.isEmpty(email)|| TextUtils.isEmpty(pass)){
                Toast.makeText(MainActivity.this, "Enter email and passward !", Toast.LENGTH_LONG).show();
                return;
            }
            String fullname = mainBinding.name.getText().toString();
            user = new User (email,pass,fullname);
            registerUser(email,pass);

        }
    }); }

    public  void registerUser(String email,String password){

        mAuth.createUserWithEmailAndPassword(email ,  password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getBaseContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });}

        public  void updateUI(FirebaseUser currentuser){

        String keyId = mDatabase.push().getKey();
        mDatabase.child(keyId).setValue(user);

        Intent login = new Intent(getBaseContext(), AfterLogin.class);
        startActivity(login);
        finish(); }

        public void loginbtn(View view) {

            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
            finish(); }}