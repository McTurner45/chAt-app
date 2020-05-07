package com.kotalishious.bwblackmarket.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kotalishious.bwblackmarket.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    MaterialEditText username,email,password,cnfPass, fullname;
    Button btn_register;


    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        username=findViewById(R.id.usernameReg);
        email=findViewById(R.id.email);
        fullname=findViewById(R.id.fullname);
        password=findViewById(R.id.password);
        btn_register=findViewById(R.id.regBtRS);
        cnfPass=findViewById(R.id.conPass);

        auth=FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username=username.getText().toString();
                String txt_email=email.getText().toString();
                String txt_password=password.getText().toString();
                String txt_conPass=cnfPass.getText().toString();
                String txt_fullname=fullname.getText().toString();

                if (TextUtils.isEmpty(txt_username)||TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password)){
                    Toast.makeText(RegistrationActivity.this,"All Fields Are Required",Toast.LENGTH_LONG).show();
                }else if (txt_password.length()<6){
                    Toast.makeText(RegistrationActivity.this,"Password must be at least 6 characters",Toast.LENGTH_LONG).show();

                }else if (!txt_password.equals(txt_conPass)){
                    Toast.makeText(RegistrationActivity.this,"Confirm Password must be the same as Password, confirm pass:"+ txt_conPass+" pass: "+txt_password,Toast.LENGTH_LONG).show();
                }else {
                    register(txt_username,txt_email,txt_password, txt_fullname);
                }


            }
        });

    }

    private void register(final String username, String email, String password, final String fullname)
    {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser= auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid= firebaseUser.getUid();

                            reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap=new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("Fullnames", fullname);
                            hashMap.put("username",username);
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "offline");
                            hashMap.put("search", username.toLowerCase());

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent=new Intent(RegistrationActivity.this, MassagesBtwUsers.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                            });
                        }else{
                            Toast.makeText(RegistrationActivity.this,"You can't register with this email or password",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
