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

public class MainActivity extends AppCompatActivity {

    ImageView add;

    ArrayList<Model> list=new ArrayList<>();
    RecyclerView recyclerview;
    My_Database db;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new My_Database(MainActivity.this);

        add = findViewById(R.id.add);
        recyclerview = findViewById(R.id.recyclerview);

        getData();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog);
                ImageView close = dialog.findViewById(R.id.close);
                TextView textView = dialog.findViewById(R.id.textview);
                Button addnewcategory = dialog.findViewById(R.id.addnewcategory);
                EditText addCategory = dialog.findViewById(R.id.addcategory);

                addnewcategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (getIntent().getExtras() == null) {
                            db.addData(addCategory.getText().toString());
                            addCategory.setText("");
                            refreshRecyclerView();

                        } else {

                            textView.setText("This field is required");
                        }
                        dialog.dismiss();
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

    }
        private void getData()
        {
            Cursor cursor=db.viewData();
            Log.d("TAG", "getData: view"+db.viewData());
            while (cursor.moveToNext())//1
            {
                Integer id=cursor.getInt(0);
                String name=cursor.getString(1);


                Model model=new Model(id,name);
                list.add(model);
            }

            adapter=new Adapter(MainActivity.this,list,db);
            LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerview.setLayoutManager(manager);
            recyclerview.setAdapter(adapter);
        }

    private void refreshRecyclerView() {
        list.clear(); // Clear the existing data
        getData();     // Fetch the updated data from the database
        adapter.notifyDataSetChanged(); // Notify the adapter about the changes
    }

}