package com.buildappswithdylan.androideatit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.buildappswithdylan.androideatit.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    MaterialEditText edtName,edtPassword,edtPhone;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName =(MaterialEditText)findViewById(R.id.edtName);
        edtPhone =(MaterialEditText)findViewById(R.id.edtPhone);
        edtPassword =(MaterialEditText)findViewById(R.id.edtPassword);

        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        //init firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("wait");
                mDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                     if (snapshot.child(edtPhone.getText().toString()).exists()){
                         mDialog.dismiss();
                         Toast.makeText(SignUp.this, "User already registered", Toast.LENGTH_SHORT).show();

                     }

                    else{
                       mDialog.dismiss();
                       User user = new User(edtName.getText().toString(),edtPassword.getText().toString());
                       table_user.child(edtPhone.getText().toString()).setValue(user);
                       Toast.makeText(SignUp.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                       finish();

                     }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });







            }
        });
















    }
}