package com.in22labs.tneaapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.in22labs.tneaapp.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    Context context;
    private List<Notify> moviesList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView date;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_list);
            date = (TextView) view.findViewById(R.id.tv_date);

        }
    }


    public RecyclerAdapter(List<Notify> moviesList) {

        this.moviesList = moviesList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notify notify = moviesList.get(position);

        holder.title.setText(notify.getTitle());

        holder.date.setText(notify.getDate());

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    public void remove(int position) {
        moviesList.remove(position);
        notifyItemRemoved(position);
    }
}
