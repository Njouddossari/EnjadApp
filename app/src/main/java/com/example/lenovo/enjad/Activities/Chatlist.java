package com.example.lenovo.enjad.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.enjad.Activities.Createchat;
import com.example.lenovo.enjad.FirebaseFiles.Room;
import com.example.lenovo.enjad.R;
import com.example.lenovo.enjad.data.FriendDB;
import com.example.lenovo.enjad.data.GroupDB;
import com.example.lenovo.enjad.data.StaticConfig;
import com.example.lenovo.enjad.FirebaseFiles.Group;
import com.example.lenovo.enjad.FirebaseFiles.ListFriend;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Chatlist extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerListGroups;
    public FragGroupClickFloatButton onClickFloatButton;
    private ArrayList<Group> listGroup;
    private ListGroupsAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static final int CONTEXT_MENU_DELETE = 1;
    public static final int CONTEXT_MENU_EDIT = 2;
    public static final int CONTEXT_MENU_LEAVE = 3;
    public static final int REQUEST_EDIT_GROUP = 0;
    public static final String CONTEXT_MENU_KEY_INTENT_DATA_POS = "pos";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_group);

        listGroup = GroupDB.getInstance(this).getListGroups();
        recyclerListGroups = (RecyclerView) findViewById(R.id.recycleListGroup);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerListGroups.setLayoutManager(layoutManager);
        adapter = new ListGroupsAdapter(this, listGroup);
        recyclerListGroups.setAdapter(adapter);
        onClickFloatButton = new FragGroupClickFloatButton();


        if (listGroup.size() == 0) {
            //Ket noi server hien thi group
            mSwipeRefreshLayout.setRefreshing(true);
            getListGroup();
        }

    }

    ////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////////
    private void getListGroup() {
        FirebaseDatabase.getInstance().getReference().child("user/" + StaticConfig.UID + "/group").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    HashMap mapListGroup = (HashMap) dataSnapshot.getValue();
                    Iterator iterator = mapListGroup.keySet().iterator();
                    while (iterator.hasNext()) {
                        String idGroup = (String) mapListGroup.get(iterator.next().toString());
                        Group newGroup = new Group();
                        newGroup.id = idGroup;
                        listGroup.add(newGroup);
                    }
                    getGroupInfo(0);
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_GROUP && resultCode == Activity.RESULT_OK) {
            listGroup.clear();
            ListGroupsAdapter.listFriend = null;
            GroupDB.getInstance(this).dropDB();
            getListGroup();
        }
    }

    private void getGroupInfo(final int indexGroup) {
        if (indexGroup == listGroup.size()) {
            adapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            FirebaseDatabase.getInstance().getReference().child("group/" + listGroup.get(indexGroup).id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        HashMap mapGroup = (HashMap) dataSnapshot.getValue();
                        ArrayList<String> member = (ArrayList<String>) mapGroup.get("member");
                        HashMap mapGroupInfo = (HashMap) mapGroup.get("groupInfo");
                        for (String idMember : member) {
                            listGroup.get(indexGroup).member.add(idMember);
                        }
                        listGroup.get(indexGroup).groupInfo.put("name", (String) mapGroupInfo.get("name"));
                        listGroup.get(indexGroup).groupInfo.put("admin", (String) mapGroupInfo.get("admin"));
                    }
                    GroupDB.getInstance(Chatlist.this).addGroup(listGroup.get(indexGroup));
                    Log.d("Chatlist", listGroup.get(indexGroup).id + ": " + dataSnapshot.toString());
                    getGroupInfo(indexGroup + 1);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onRefresh() {
        listGroup.clear();
        ListGroupsAdapter.listFriend = null;
        GroupDB.getInstance(Chatlist.this).dropDB();
        adapter.notifyDataSetChanged();
        getListGroup();
    }


    public class FragGroupClickFloatButton implements View.OnClickListener {

        Context context;

        public FragGroupClickFloatButton getInstance(Context context) {
            this.context = context;
            return this;
        }

        @Override
        public void onClick(View view) {
            startActivity(new Intent(Chatlist.this, MainActivity.class));
        }
    }


    static class ListGroupsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<Group> listGroup;
        public static ListFriend listFriend = null;
        private Context context;

        public ListGroupsAdapter(Context context, ArrayList<Group> listGroup) {
            this.context = context;
            this.listGroup = listGroup;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.rc_item_group, parent, false);
            return new ItemGroupViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final String groupName = listGroup.get(position).groupInfo.get("name");
            if (groupName != null && groupName.length() > 0) {
                ((ItemGroupViewHolder) holder).txtGroupName.setText(groupName);
                ((ItemGroupViewHolder) holder).iconGroup.setText((groupName.charAt(0) + "").toUpperCase());
            }
            ((ItemGroupViewHolder) holder).btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setTag(new Object[]{groupName, position});
                    view.getParent().showContextMenuForChild(view);
                }
            });
            ((RelativeLayout) ((ItemGroupViewHolder) holder).txtGroupName.getParent()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listFriend == null) {
                        listFriend = FriendDB.getInstance(context).getListFriend();
                    }
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra(StaticConfig.INTENT_KEY_CHAT_FRIEND, groupName);
                    ArrayList<CharSequence> idFriend = new ArrayList<>();
//                ChatActivity.bitmapAvataFriend = new HashMap<>();
//                for(String id : listGroup.get(position).member) {
//                    idFriend.add(id);
//                    String avata = listFriend.getAvataById(id);
//                    if(!avata.equals(StaticConfig.STR_DEFAULT_BASE64)) {
//                        byte[] decodedString = Base64.decode(avata, Base64.DEFAULT);
//                        ChatActivity.bitmapAvataFriend.put(id, BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
//                    }else if(avata.equals(StaticConfig.STR_DEFAULT_BASE64)) {
//                        ChatActivity.bitmapAvataFriend.put(id, BitmapFactory.decodeResource(context.getResources(), R.drawable.default_avata));
//                    }else {
//                        ChatActivity.bitmapAvataFriend.put(id, null);
//                    }
//                }
                    intent.putCharSequenceArrayListExtra(StaticConfig.INTENT_KEY_CHAT_ID, idFriend);
                    intent.putExtra(StaticConfig.INTENT_KEY_CHAT_ROOM_ID, listGroup.get(position).id);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return listGroup.size();
        }
    }

    static class ItemGroupViewHolder extends RecyclerView.ViewHolder {
        public TextView iconGroup, txtGroupName;
        public ImageButton btnMore;

        public ItemGroupViewHolder(View itemView) {
            super(itemView);

            iconGroup = (TextView) itemView.findViewById(R.id.icon_group);
            txtGroupName = (TextView) itemView.findViewById(R.id.txtName);
            btnMore = (ImageButton) itemView.findViewById(R.id.btnMoreAction);
        }


    }
}