package com.example.chatapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.models.Contact;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactsViewHolder> {
    class ContactsViewHolder extends RecyclerView.ViewHolder {
        private final TextView username;
        private final TextView lastMessage;
        private final TextView laseTime;
        private final ImageView contactProfile;

        private ContactsViewHolder(View contactItemView) {
            super(contactItemView);
            username = contactItemView.findViewById(R.id.contact_username);
            lastMessage = contactItemView.findViewById(R.id.contact_last_massage);
            laseTime = contactItemView.findViewById(R.id.contact_time);
            contactProfile = contactItemView.findViewById(R.id.contact_profile_image);
        }
    }
    private final LayoutInflater mInflater;
    private List<Contact> contacts;

    public ContactsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contactItemView = mInflater.inflate(R.layout.contact_list_item, parent, false);
        return new ContactsViewHolder(contactItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        if(contacts != null) {
            final Contact current = contacts.get(position);
            holder.username.setText(current.getName());
            holder.lastMessage.setText(current.getLast());
            holder.laseTime.setText(current.getLastdate());
            String strDateTime;
            DateFormat from = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
            DateFormat to = new SimpleDateFormat("yyyy.MM.dd, HH:mm");
            try {
                strDateTime = to.format(from.parse(current.getLastdate()));
                holder.laseTime.setText(strDateTime);

            } catch (ParseException e) {
                e.printStackTrace();
            }
//            holder.contactProfile.setImageResource(current.getContactProfile());
        }
    }

    public void setContacts(List<Contact> contactList) {
        contacts = contactList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(contacts != null) {
            return contacts.size();
        }
        return 0;
    }

    public List<Contact> getContacts() { return contacts; }
}