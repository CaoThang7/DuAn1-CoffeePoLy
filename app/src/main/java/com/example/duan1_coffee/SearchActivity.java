package com.example.duan1_coffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_coffee.adapter.product.ProductListOnSearchAdapter;
import com.example.duan1_coffee.fragment.Home_Fragment;
import com.example.duan1_coffee.fragment.chat_Fragment;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    public static List<Product> products =new ArrayList<>();
    ProductListOnSearchAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products");
        EditText edtKeyWork = (EditText) findViewById(R.id.edtKeyWork);
        adapter = new ProductListOnSearchAdapter(SearchActivity.this, products);
        recyclerView = findViewById(R.id.rcv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setAdapter(adapter);

        findViewById(R.id.tvSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = edtKeyWork.getText().toString();
                Toast.makeText(SearchActivity.this, "" + key, Toast.LENGTH_SHORT).show();
                if(key != null && key.length()>0){
                    char[] letters=key.toCharArray();
                    String firstLetter = String.valueOf(letters[0]).toUpperCase();
                    String remainingLetters = key.substring(1);
                    key=firstLetter+remainingLetters;

                    Query firebaseSearchQuery = reference.orderByChild("productName").startAt(key)
                            .endAt(key + "uf8ff");
                    firebaseSearchQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            products.clear();
                            if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Product product = ds.getValue(Product.class);
                                    product.setProductId(ds.getKey());
                                    products.add(product);
                                }
                                adapter.notifyDataSetChanged();
                            }else {
                                Toast.makeText(SearchActivity.this, "Product not exist!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("FIREBASE CRUD", databaseError.getMessage());
                        }
                    });
                }

            }
        });


//        findViewById(R.id.tvBackToShop).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Function().loadFragment(new Home_Fragment(), R.id.frameLayout, v);
//            }
//        });

//        findViewById(R.id.tvChatWithUs).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Function().loadFragment(new chat_Fragment(), R.id.frameLayout, v);
//            }
//        });
    }
}