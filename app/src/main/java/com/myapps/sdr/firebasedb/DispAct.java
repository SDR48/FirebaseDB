package com.myapps.sdr.firebasedb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DispAct extends AppCompatActivity {


    private ListView listView;
    private ArrayList<String> arrayList = new ArrayList<>();
    DatabaseReference references;
    TextView logout;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp);
        firebaseAuth = FirebaseAuth.getInstance();
        final  String mUid = firebaseAuth.getCurrentUser().getUid();
        logout = findViewById(R.id.textView);
        listView = findViewById(R.id.listdet);
        references = FirebaseDatabase.getInstance().getReference();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(DispAct.this,LoginAct.class));
            }
        });
        references.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                String name = dataSnapshot.child("Users").child(mUid).child("Name").getValue(String.class);
                String email = dataSnapshot.child("Users").child(mUid).child("Email").getValue(String.class);
                String phone = dataSnapshot.child("Users").child(mUid).child("Phone").getValue(String.class);
                arrayList.add(name);
                arrayList.add(email);
                arrayList.add(phone);
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DispAct.this,android.R.layout.simple_list_item_1,arrayList);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DispAct.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
