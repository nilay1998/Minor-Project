package com.example.attendancemanagementfaculty;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.attendancemanagementfaculty.Reterofit.AllItems;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    ArrayList<AllItems> arrayList=new ArrayList<>();

    public RecyclerViewAdapter(ArrayList<AllItems> marrayList) {
        arrayList=marrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.rollNumber.setText(arrayList.get(i).getRollNumber());
        viewHolder.name.setText(arrayList.get(i).getName());
        Log.e("YPYPY", "onBindViewHolder: "+arrayList.get(i).getAttendance().length);
        viewHolder.attendance.setText(String.valueOf(arrayList.get(i).getAttendance().length));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView rollNumber;
        TextView name;
        TextView attendance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rollNumber=itemView.findViewById(R.id.rollNumber);
            name=itemView.findViewById(R.id.namee);
            attendance=itemView.findViewById(R.id.attendance);
        }
    }
}
