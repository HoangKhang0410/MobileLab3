package com.example.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private ImageView sampleID;
        private TextView className;
        private TextView textName;
        private LinearLayout linearLayout;
        private ItemClickListener itemClickListener;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            sampleID = itemView.findViewById(R.id.sampleID);
            textName = itemView.findViewById(R.id.studentName);
            className = itemView.findViewById(R.id.className);
            linearLayout = itemView.findViewById(R.id.recyclerView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        //Tạo setter cho biến itemClickListenenr
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false); // Gọi interface , false là vì đây là onClick
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true); // Gọi interface , true là vì đây là onLongClick
            return true;
        }
    }

    private Context context;
    private List<Student> listStudent;
    private  StudentDbHandler studentDB;
    public RecyclerAdapter(Context context, List<Student> listStudent){
        this.context = context;
        this.listStudent = listStudent;
        studentDB = new StudentDbHandler(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View recyclerView = inflater.inflate(R.layout.recycler_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(recyclerView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder , int position){
        Student student = listStudent.get(position);
        holder.textName.setText(student.getName());
        holder.className.setText(student.getClassName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick) {
                    studentDB.deleteStudent(listStudent.get(position));
                    listStudent.remove(position);
                    notifyDataSetChanged();
                }
                else {
                    MainActivity.getmInstanceActivity().openEditStudentDialog(listStudent.get(position), position);

                }
            }
        });


    }
    @Override
    public int getItemCount() {
        return listStudent.size();
    }

}
