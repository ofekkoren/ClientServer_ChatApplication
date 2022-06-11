package com.example.chatapp.conversation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.chatapp.MyApp;
import com.example.chatapp.R;
import com.example.chatapp.adapters.RecyclerMessageListAdapter;
import com.example.chatapp.models.Contact;
import com.example.chatapp.models.Conversation;
import com.example.chatapp.models.Message;

import java.util.ArrayList;

public class ConversationScreen extends AppCompatActivity {

    private static Conversation currentConversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversationscreen);
        Contact con = new Contact(0, "Tomer Eligayev", "Tomer-77", MyApp.getContext().getString(R.string.BaseUrl), "Have " +
                "a nice day", "2022-05-26T03:53:23.8120000");
        currentConversation = new Conversation("Ofek KorenTomer Eligayev", new ArrayList<Message>(), con);
        Message m1 = new Message(1, "Hello, how are you?", "2022-05-26T03:53:23.8120000", true);
        Message m2 = new Message(2, "I'm fine, thanks", "2022-05-26T03:53:23.8120000", false);
        Message m3 = new Message(3, "Have a nice day.", "2022-05-26T03:53:23.8120000", false);
        Message m4 = new Message(4, "Have a nice day.", "2022-05-26T03:53:23.8120000", false);
        Message m5 = new Message(5, "Have a nice day.ggggggg ggggg gggggggggggg gggggggggggggggggggggggggggggg",
                "2022-05-26T03:53:23.8120000", false);
        Message m6 = new Message(6, "Have a nice day.", "2022-05-26T03:53:23.8120000", false);
        Message m7 = new Message(7, "Have", "2022-05-26T03:53:23.8120000", true);
        Message m8 = new Message(6, "Have", "2022-05-26T03:53:23.8120000", false);
        Message m9 = new Message(9, "Have a nice day.ggggggg ggggg gggggggggggg gggggggggggggggggggggggggggggg",
                "2022-05-26T03:53:23.8120000", true);

        currentConversation.getMessages().add(m1);
        currentConversation.getMessages().add(m2);
        currentConversation.getMessages().add(m3);
        currentConversation.getMessages().add(m4);
        currentConversation.getMessages().add(m5);
        currentConversation.getMessages().add(m6);
        currentConversation.getMessages().add(m7);
        currentConversation.getMessages().add(m8);
        currentConversation.getMessages().add(m9);

        TextView header = findViewById(R.id.conversationHeader);
        header.setText(currentConversation.getContact().getName());
        RecyclerView chatBody = findViewById(R.id.chatBody);
        final RecyclerMessageListAdapter adapter = new RecyclerMessageListAdapter(this);
        chatBody.setAdapter(adapter);
        chatBody.setLayoutManager(new LinearLayoutManager(this));
        adapter.setMessages(currentConversation.getMessages());
        chatBody.scrollToPosition(currentConversation.getMessages().size()-1);

    }
}