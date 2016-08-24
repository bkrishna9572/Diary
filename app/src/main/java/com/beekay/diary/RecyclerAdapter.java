package com.beekay.diary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krishna on 11/15/2014.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private DataClass[] dataList;

    public RecyclerAdapter(DataClass[] data) {
            this.dataList=data;
    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle,viewGroup,false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, int i) {

//        TextView text=(TextView)viewHolder.view.findViewById(R.id.recycle_text);
//        text.setText(dataList.get(i).getText());
        viewHolder.head.setText(dataList[i].getText());
        viewHolder.body.setText(dataList[i].getBody());
        viewHolder.date.setText(""+dataList[i].getDate());
    }

    @Override
    public int getItemCount() {
        return dataList.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView head,body,date;

        public ViewHolder(View itemView)
        {
            super(itemView);
            head = (TextView) itemView.findViewById(R.id.head);
            body = (TextView) itemView.findViewById(R.id.body);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
