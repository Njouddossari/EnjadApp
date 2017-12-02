package com.example.lenovo.enjad.Utilities;

/**
 * Created by Njoud on 12/2/2017.
 */

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.enjad.Activities.MainActivity;
import com.example.lenovo.enjad.Activities.chatActivity;
import com.example.lenovo.enjad.JavaClasses.ChatMessage;
import com.example.lenovo.enjad.JavaClasses.User;
import com.example.lenovo.enjad.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MessageAdapter extends FirebaseListAdapter<ChatMessage> {

    private Class<chatActivity> activity;

    public MessageAdapter(Activity activity, Class<ChatMessage> modelClass, int modelLayout, DatabaseReference ref) {
        super(activity, modelClass, modelLayout, ref);
    }


    @Override
    protected void populateView(View v, ChatMessage model, int position) {
        TextView messageText = (TextView) v.findViewById(R.id.message_text);
        TextView messageUser = (TextView) v.findViewById(R.id.message_user);
        TextView messageTime = (TextView) v.findViewById(R.id.message_time);

        messageText.setText(model.getMessageText());
        messageUser.setText(model.getMessageUser());

        // Format the date before showing it
        messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ChatMessage chatMessage = getItem(position);
        if (chatMessage.getMessageUserId().equals(chatActivity.getLoggedInUserName())) {
            view = new chatActivity().getLayoutInflater().inflate(R.layout.item_out_message, viewGroup, false);
        } else
            view = new chatActivity().getLayoutInflater().inflate(R.layout.item_in_message, viewGroup, false);

        //generating view
        populateView(view, chatMessage, position);

        return view;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }
}