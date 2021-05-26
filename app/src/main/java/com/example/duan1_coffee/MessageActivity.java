package com.example.duan1_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_coffee.adapter.MessageAdapter;
import com.example.duan1_coffee.fragment.chat_Fragment;
import com.example.duan1_coffee.model.Chat;
import com.example.duan1_coffee.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    ImageButton btnsend;
    EditText text_send;
    TextView username2;
    Intent intent;
    FirebaseUser fUser;
    DatabaseReference reference;
    Toolbar toolbar;

    MessageAdapter messageAdapter;
    List<Chat> mchat;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        //Anh xa
        toolbar =findViewById(R.id.toolbarMess);
        username2=findViewById(R.id.username2);
        btnsend=findViewById(R.id.btn_send);
        text_send=findViewById(R.id.text_send);
        recyclerView=findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager((getApplicationContext()));
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);






        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        intent=getIntent();
        final String userid=intent.getStringExtra("userid");
        fUser=FirebaseAuth.getInstance().getCurrentUser();


        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=text_send.getText().toString();
                if(!msg.equals("")){
                    sendMessage(fUser.getUid(), userid, msg);
                }else {
                    Toast.makeText(MessageActivity.this, "You can send message",Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });



        fUser=FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                username2.setText(user.getUsername());
                readMessage(fUser.getUid(), userid, user.getImage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void sendMessage(String sender,String receiver,String message){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap =new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);

        reference.child("Chats").push().setValue(hashMap);
    }


    private void readMessage(final String myid, final String userid, final String imageurl){
        mchat=new ArrayList<>();

        reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             mchat.clear();
             for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                 Chat chat=snapshot.getValue(Chat.class);
                 if(chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                 chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                     mchat.add(chat);
                 }
                 messageAdapter=new MessageAdapter(MessageActivity.this, mchat, imageurl);
                 recyclerView.setAdapter(messageAdapter);
             }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
