package com.example.to_do_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemSelectionChangeListener{

    ImageView add;
    TextView btndelete;
    ArrayList<Model> list = new ArrayList<>();
    RecyclerView recyclerview;
    My_Database db;
    Adapter adapter;
    ImageView close ;
    TextView textView;
    Button addnewcategory ;
    EditText addCategory ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new My_Database(MainActivity.this);

        add = findViewById(R.id.add);
        recyclerview = findViewById(R.id.recyclerview);
        btndelete = findViewById(R.id.btndelete);

        getData();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog);
                close = dialog.findViewById(R.id.close);
                textView = dialog.findViewById(R.id.textview);
                addnewcategory = dialog.findViewById(R.id.addnewcategory);
                addCategory = dialog.findViewById(R.id.addcategory);

                addnewcategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String category = addCategory.getText().toString().trim();
                        if (!category.isEmpty()) {
                            db.addData(category);
                            addCategory.setText("");
                            refreshRecyclerView();
                            dialog.dismiss();
                        }
                        else {
                            textView.setText("This field is required");
                        }
                    }
                });


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = list.size() - 1; i >= 0; i--) {
                    if (list.get(i).isSelected()) {
                        db.deleteData(list.get(i).getId());
                        list.remove(i);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify adapter after removing items
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteButtonClick(view);
            }
        });
    }

    private void getData() {
        Cursor cursor = db.viewData();
        Log.d("TAG", "getData: view" + db.viewData());
        while (cursor.moveToNext())//1
        {
            Integer id = cursor.getInt(0);
            String name = cursor.getString(1);

            Model model = new Model(id, name);
            list.add(model);
        }

        adapter = new Adapter(MainActivity.this, list, db, new EditItemClickListener() {
            @Override
            public void onEditItemClicked(String name, Integer id, int position) {

                CreateEdit(name,id,position);
            }

        },this);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
    }

    private void CreateEdit(String name,Integer id,int position) {
        BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog);

        EditText editCategory = dialog.findViewById(R.id.addcategory);
        Button btnAdd = dialog.findViewById(R.id.addnewcategory);

        editCategory.setText(name); // Set the current category name in the EditText

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editCategory.getText().toString().trim();
                if (!newName.isEmpty()) {
                    // Update the category name in the database
                    //int position = adapter.i(); // Assuming you have a method to get the selected position in your adapter
                    Model selectedModel = list.get(position);
                    db.updateData(selectedModel.getId(), newName);
                    refreshRecyclerView(); // Refresh the RecyclerView to reflect changes
                    dialog.dismiss();
                } else {
                    // Handle empty name case if needed
                }
            }
        });

        dialog.show();
    }

    private void refreshRecyclerView() {
        list.clear();
        getData();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelectionChanged() {
        boolean anyItemSelected = false;
        for (Model item : list) {
            if (item.isSelected()) {
                anyItemSelected = true;
                break;
            }
        }
        // Set visibility of btndelete based on whether any item is selected
        btndelete.setVisibility(anyItemSelected ? View.VISIBLE : View.GONE);
    }

    // Method to handle deletion when btndelete is clicked
    public void onDeleteButtonClick(View view) {
        // Iterate through the list to find selected items and delete them
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).isSelected()) {
                db.deleteData(list.get(i).getId());
                list.remove(i);
            }
        }
        // Notify the adapter that the dataset has changed
        adapter.notifyDataSetChanged();
        // Update the visibility of btndelete
        btndelete.setVisibility(View.GONE);
    }


}