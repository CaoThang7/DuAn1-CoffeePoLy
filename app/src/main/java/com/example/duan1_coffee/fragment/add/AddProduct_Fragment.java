package com.example.duan1_coffee.fragment.add;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.fragment.Analytic_Fragment;
import com.example.duan1_coffee.fragment.Product_Fragment;
import com.example.duan1_coffee.fragment.product.ProductList_Fragment;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AddProduct_Fragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button btnChooseImage;
    private Button btnUpload;
    private TextView tvShowProducts;
    private EditText edtProductName, edtProductPrice;
    private Spinner spinner;
    private ImageView imgProduct;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    private List<String> list;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef, mDatabaseRefType;
    private StorageTask mUploadTask;
    String uniqueId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        btnChooseImage = view.findViewById(R.id.btnChooseImage);
        btnUpload = view.findViewById(R.id.btnUpload);
        tvShowProducts = view.findViewById(R.id.tvShowProducts);
        edtProductName = view.findViewById(R.id.edtProductName);
        edtProductPrice = view.findViewById(R.id.edtProductPrice);
        imgProduct = view.findViewById(R.id.imgProduct);
        spinner = view.findViewById(R.id.spinner);

        mProgressBar = view.findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Products");
        mDatabaseRefType = FirebaseDatabase.getInstance().getReference("Types");

        mDatabaseRefType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<String> listSpinner = new ArrayList<String>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String item = dataSnapshot.child("typeName").getValue(String.class);
                    listSpinner.add(item);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listSpinner);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getActivity(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadProduct();
                }
            }
        });
        tvShowProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Function().loadFragment(new ProductList_Fragment(), R.id.fragment_add_product_change, view);
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void uploadProduct() {
        if (mImageUri != null) {
            uniqueId = UUID.randomUUID().toString();
            StorageReference fileReference = mStorageRef.child("productImages/" + uniqueId);
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                            while(!uri.isComplete());
                            Uri url = uri.getResult();
                            Toast.makeText(getActivity(), "Upload Success", Toast.LENGTH_LONG).show();
                            String imageUrl = url.toString();

                            Product product = new Product(
                                    edtProductName.getText().toString().trim(),
                                    spinner.getSelectedItem().toString(),
                                    imageUrl,
                                    Float.parseFloat(edtProductPrice.getText().toString().trim()));
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(product);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(imgProduct);
        }
    }


}
