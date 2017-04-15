package ru.ca.time;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RVadapter extends RecyclerView.Adapter<RVadapter.ViewHolder>  {

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, time,desc;

        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvMain);
            time = (TextView) itemView.findViewById(R.id.tvTime);
            desc = (TextView) itemView.findViewById(R.id.tvDesc);
        }
    }


    List<Excersice> excersices;
    public RVadapter(List<Excersice> excersices) {
        this.excersices = excersices;

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder ViewHolder, int i) {
        ViewHolder.name.setText(excersices.get(i).getName());
        ViewHolder.time.setText(excersices.get(i).getStringTime());
        ViewHolder.desc.setText(excersices.get(i).getDesc());
    }

    @Override
    public int getItemCount() {
        return excersices.size();
    }






}