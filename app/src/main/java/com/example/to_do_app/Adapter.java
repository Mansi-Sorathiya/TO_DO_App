package com.example.to_do_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder>{
    MainActivity mainActivity;
    ArrayList<Model> list;
    My_Database db;
    EditItemClickListener editItemClickListener;
    OnItemSelectionChangeListener selectionChangeListener;

    public Adapter(MainActivity mainActivity, ArrayList<Model> list, My_Database db, EditItemClickListener editItemClickListener, OnItemSelectionChangeListener selectionChangeListener) {
        this.mainActivity = mainActivity;
        this.list = list;
        this.db = db;
        this.editItemClickListener = editItemClickListener;
        this.selectionChangeListener = selectionChangeListener;
    }

    @NonNull
    @Override
    public Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mainActivity).inflate(R.layout.item,parent,false);
        Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.Holder holder, int position) {
        Model currentItem = list.get(position);
        holder.name.setText(currentItem.getName());
        holder.checkBox.setChecked(currentItem.isSelected());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editItemClickListener.onEditItemClicked(list.get(position).getName(),list.get(position).getId(),position);
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItem.setSelected(holder.checkBox.isChecked());
                selectionChangeListener.onItemSelectionChanged();
            }
        });
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                db.deleteData(list.get(position).getId());
////                list.remove(position);
////                notifyDataSetChanged();
//
//                for (int i = list.size() - 1; i >= 0; i--) {
//                    if (list.get(i).isSelected()) {
//                        db.deleteData(list.get(i).getId());
//                        list.remove(i);
//                    }
//                }
//                notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView edit,delete;
        CheckBox checkBox;
        public Holder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.textview);
            checkBox=itemView.findViewById(R.id.checkbox);
            edit=itemView.findViewById(R.id.edit);
//           delete=itemView.findViewById(R.id.delete);

        }
    }
}
