package com.example.chatapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.models.Message;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class RecyclerMessageListAdapter extends RecyclerView.Adapter<RecyclerMessageListAdapter.MessageViewHolder> {

    private final LayoutInflater inflater;
    private List<Message> messages;
    private static final int SENT_MESSAGE = 1;
    private static final int RECEIVED_MESSAGE = 0;


    public RecyclerMessageListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View messageView;
        if (viewType == SENT_MESSAGE) {
            messageView = inflater.inflate(R.layout.sent_message_layout, parent, false);
            return new MessageViewHolder(messageView);
        }
        messageView = inflater.inflate(R.layout.received_message_layout, parent, false);
        return new MessageViewHolder(messageView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        if (messages != null) {
            final Message current = messages.get(position);
            holder.content.setText(current.getContent());
            String strDateTime;
            DateFormat from = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
            DateFormat to = new SimpleDateFormat("yyyy.MM.dd, HH:mm");
            try {
                strDateTime = to.format(from.parse(current.getCreated()));
                holder.date.setText(strDateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView content;
        private TextView date;

        private MessageViewHolder(View messageView) {
            super(messageView);
            content = messageView.findViewById(R.id.messageContent);
            date = messageView.findViewById(R.id.messageDate);
        }
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (messages != null)
            return messages.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).isSent())
            return SENT_MESSAGE;
        return RECEIVED_MESSAGE;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
