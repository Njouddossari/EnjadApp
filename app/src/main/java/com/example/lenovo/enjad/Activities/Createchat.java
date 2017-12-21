package com.example.lenovo.enjad.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.lenovo.enjad.R;
import com.example.lenovo.enjad.data.StaticConfig;
import com.example.lenovo.enjad.FirebaseFiles.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Createchat extends AppCompatActivity {
    private Set<String> listIDChoose;
    private static final String TAG = "Createchat";
    private boolean isEditGroup = false;
    DatabaseReference nearbyRefrence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_chat);

        listIDChoose = new HashSet<>();

        nearbyRefrence = FirebaseDatabase.getInstance().getReference("nearest");
        nearbyRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //add nearby users using their ids

                if (dataSnapshot.getValue() != null) {
                    HashMap mapListNearby = (HashMap) dataSnapshot.getValue();
                    Iterator iterator = mapListNearby.keySet().iterator();
                    while (iterator.hasNext()) {
                        String idGroup = (String) mapListNearby.get(iterator.next().toString());
                        listIDChoose.add(idGroup);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*List of users ids
        listIDChoose.add(StaticConfig.UID);
        listIDChoose.add("SwukPrlWdugxjhIP9eLe3qcSX0n2");
        listIDChoose.add("che8VDbSgrR2AR015agXfWNmXNk1");*/

        createGroup();
    }

    private void createGroup() {
        //Show dialog wait


        final String idGroup = (StaticConfig.UID + System.currentTimeMillis()).hashCode() + "";
        Room room = new Room();
        room.member.addAll(listIDChoose);
        room.groupInfo.put("name", "group0");
        room.groupInfo.put("admin", StaticConfig.UID);
        FirebaseDatabase.getInstance().getReference().child("group/" + idGroup).setValue(room).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                addRoomForUser(idGroup, 0);
                //   Log.w(TAG, "onComplete: " + task.getException().getMessage());
            }

        });
    }

    private void addRoomForUser(final String roomId, final int userIndex) {

        if (userIndex == listIDChoose.size()) {
            if (!isEditGroup) {
                //dialogWait.dismiss();
                Toast.makeText(this, "Create group success", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, null);
                Createchat.this.finish();
            } else {
              //  deleteRoomForUser(roomId, 0);
            }
        } else {
            FirebaseDatabase.getInstance().getReference().child("user/" + listIDChoose.toArray()[userIndex] + "/group/" + roomId).setValue(roomId).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    addRoomForUser(roomId, userIndex + 1);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                   // dialogWait.dismiss();

                    //if group faild to be build -over 24 hours-
                }
            });
        }

    }
}

