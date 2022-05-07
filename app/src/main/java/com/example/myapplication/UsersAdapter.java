package com.example.myapplication;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> implements Filterable {

    Context context;
    List<UserListResponse> userListResponseData;
    List<UserListResponse> userListfiltered;

    public void setUserdata(Context context, List<UserListResponse> userListResponseData) {
        this.userListResponseData = userListResponseData;
        this.context = context;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.users_list_items, null);
        UsersViewHolder usersViewHolder = new UsersViewHolder(view);
        return usersViewHolder;
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // set the data
        holder.tvname.setText("Repository Name: " + userListResponseData.get(position).getName());
        if (userListResponseData.get(position).getHas_wiki() != null) {
            try {
                holder.tvLoginName.setText("Login Name: " + userListResponseData.get(position).getOwner().getString("login"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        holder.tvSize.setText("Size: " + userListResponseData.get(position).getSize());
        // set the view to different color if the has_wiki value is true
        if (userListResponseData.get(position).getHas_wiki() != null){
            if (userListResponseData.get(position).getHas_wiki().equals(true)) {
                holder.llBackground.setBackgroundColor(Color.parseColor("#f09595"));
            }
        }
        // implement setONCLickListtener on itemView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with user name
                Toast.makeText(context, userListResponseData.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userListResponseData.size(); // size of the list items
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView tvname, tvLoginName, tvSize;
        LinearLayout llBackground;

        public UsersViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            tvname = (TextView) itemView.findViewById(R.id.tvname);
            tvLoginName = (TextView) itemView.findViewById(R.id.tvLoginName);
            tvSize = (TextView) itemView.findViewById(R.id.tvSize);
            llBackground = (LinearLayout) itemView.findViewById(R.id.llBackground);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    userListfiltered = userListResponseData;
                } else {
                    List<UserListResponse> filteredList = new ArrayList<>();
                    for (UserListResponse userListResponse : userListResponseData) {
                        if (userListResponse.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(userListResponse);
                        }
                    }
                    userListfiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = userListfiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userListResponseData = (ArrayList<UserListResponse>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }
}
