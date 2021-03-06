package com.example.chashi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import iammert.com.expandablelib.ExpandableLayout;
import iammert.com.expandablelib.Section;

public class Product_category extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;

    private List<SubCatagory> subCatagories = new ArrayList<>();
    private List<Product_item> products = new ArrayList<>();
    private Product_Category_Adapter mAdapter;
    private String category;

    private ExpandableLayout expandableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

        expandableLayout = findViewById(R.id.expandable_layout);


        category = getIntent().getExtras().getString("type");

        Toast.makeText(this,category,Toast.LENGTH_SHORT).show();

        initializeRecyclerView();


        readDataFromFirebase();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void readDataFromFirebase() {
        Toast.makeText(Product_category.this,"hocche",Toast.LENGTH_SHORT).show();
        FirebaseUtilClass.getDatabaseReference().child("Products").child(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    SubCatagory subCatagory = new SubCatagory();
                    Product_item product_item;
                    subCatagory.setSub_cat_name(dsp.getKey());
                    if(dsp.getKey() == "ধান"){
                        subCatagory.setIcon(R.drawable.ic_wheat);
                    }
                    //Log.d("testing:1", "onDataChange: " + dsp.getKey());
                    products = new ArrayList<>();
                    for(DataSnapshot dsp1: dsp.getChildren()){

                        product_item = new Product_item();
                        product_item.setId(dsp1.getKey());
                        product_item.setName(dsp1.child("name").getValue().toString());
                        product_item.setDesc(dsp1.child("description").getValue().toString());
                        product_item.setId(dsp1.child("id").getValue().toString());
                        product_item.setPrice(dsp1.child("price").getValue().toString());
                        product_item.setQuantity(dsp1.child("quantity").getValue().toString());
                        product_item.setImage(dsp1.child("image").getValue().toString());
                        products.add(product_item);


                    }
                    //Log.d("testing:1", "onDataChange: " +dsp.getKey() +" "+ products.get(0).getName());
                    //Log.d("testing:1", "onDataChange: " +dsp.getKey() +" "+ products.get(1).getName());
                    subCatagory.setProduct_items(products);

                    subCatagories.add(subCatagory);

                }
                //Toast.makeText(Product_category.this,String.valueOf(subCatagories.get(0).getProduct_items().get(0).getName()),Toast.LENGTH_SHORT).show();

                for(SubCatagory s: subCatagories){
                    for(Product_item i:s.getProduct_items()){
                        Log.d("testing:1", "onDataChange: " +s.getSub_cat_name() +" "+ i.getName());
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initializeRecyclerView() {
        categoryRecyclerView = findViewById(R.id.catagory_rclrvw);

        mAdapter = new Product_Category_Adapter(subCatagories,this);
        RecyclerView.LayoutManager mLayoutmanager =new LinearLayoutManager(this);
        categoryRecyclerView.setLayoutManager(mLayoutmanager);
        categoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        categoryRecyclerView.setAdapter(mAdapter);
    }


}
