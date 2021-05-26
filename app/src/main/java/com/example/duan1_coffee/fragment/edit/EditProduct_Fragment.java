package com.example.duan1_coffee.fragment.edit;

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

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.fragment.product.ProductList_Fragment;
import com.example.duan1_coffee.fragment.product.ProductType_Fragment;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.UUID;

public class EditProduct_Fragment extends Fragment {
    // KHAI BAO
    private static final String ARGUMENT_PRODUCT_ID = "ARGUMENT_PRODUCT_ID";
    private static final String ARGUMENT_PRODUCT_NAME = "ARGUMENT_PRODUCT_NAME";
    private static final String ARGUMENT_PRODUCT_PRICE = "ARGUMENT_PRODUCT_PRICE";
    private static final String ARGUMENT_PRODUCT_TYPE = "ARGUMENT_PRODUCT_TYPE";
    private static final String ARGUMENT_PRODUCT_IMAGE = "ARGUMENT_PRODUCT_IMAGE";

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button btnChooseImage;
    private Button btnUpload;
    private TextView tvShowProducts;
    private EditText edtProductName, edtProductPrice;
    private ImageView imgProduct;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    private Spinner spinner;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef, mDatabaseRefType;
    private StorageTask mUploadTask;
    String uniqueId;

    // TAO INSTANCE DE CHUYEN DU LIEU QUA CAC FRAGMENT
    public static EditProduct_Fragment newInstance(String productId, String productName, String productType, float productPrice, String productImage) {
        Bundle args = new Bundle();
        // Save data here
        args.putString(ARGUMENT_PRODUCT_ID, productId);
        args.putString(ARGUMENT_PRODUCT_NAME, productName);
        args.putString(ARGUMENT_PRODUCT_TYPE, productType);
        args.putFloat(ARGUMENT_PRODUCT_PRICE, productPrice);
        args.putString(ARGUMENT_PRODUCT_IMAGE, productImage);
        EditProduct_Fragment fragment = new EditProduct_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        // GET DATA TU PRODUCT LIST
        final String productId = getArguments().getString(ARGUMENT_PRODUCT_ID);
        String productName = getArguments().getString(ARGUMENT_PRODUCT_NAME);
        final String productType = getArguments().getString(ARGUMENT_PRODUCT_TYPE);
        final String productImage = getArguments().getString(ARGUMENT_PRODUCT_IMAGE);
        float productPrice = getArguments().getFloat(ARGUMENT_PRODUCT_PRICE);

        Log.d("TADFFF", productId + ", " + productName + ", " + productType + ", " + productImage + ", " + productPrice);
        // ANH XA
        btnChooseImage = view.findViewById(R.id.btnChooseImage);
        btnUpload = view.findViewById(R.id.btnUpload);
        tvShowProducts = view.findViewById(R.id.tvShowProducts);
        edtProductName = view.findViewById(R.id.edtProductName);
        edtProductPrice = view.findViewById(R.id.edtProductPrice);
        imgProduct = view.findViewById(R.id.imgProduct);
        mProgressBar = view.findViewById(R.id.progress_bar);
        spinner = view.findViewById(R.id.spinner);

        edtProductName.setText(productName);
        edtProductPrice.setText("" + productPrice);
        Picasso.get()
                .load(productImage)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imgProduct);

        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Products");
        mDatabaseRefType = FirebaseDatabase.getInstance().getReference("Types");

        // SPINNER
        mDatabaseRefType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // SET SPINNER  ADAPTER FOR EDIT PRODUCT
                final List<String> listSpinner = new ArrayList<String>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String item = dataSnapshot.child("typeName").getValue(String.class);
                    listSpinner.add(item);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listSpinner);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);

                // SET SPINNER CORRECT
                for (int position = 0; position < spinner.getCount(); position++) {
                    if(spinner.getItemAtPosition(position).equals(productType)) {
                        spinner.setSelection(position);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // BUTTON CHOOSE IMAGE
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        // BUTTON UPDATE LEN FIREBASE
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getActivity(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    String productName = edtProductName.getText().toString().trim();
                    String productPrice = edtProductPrice.getText().toString().trim();

                    updateProduct(productId, productName, productImage ,productPrice);
//                    updateProductNoImage(productId, productName, productImage, productPrice);
                }
            }
        });

        // TRO VE ALL PRODUCTS
        tvShowProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Show All Products click!", Toast.LENGTH_SHORT).show();
                new Function().loadFragment(new ProductList_Fragment(), R.id.fragment_edit_product_change, view);
            }
        });

    }

    // HAM CHON HINH
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // HAM UPDATE PRODUCT
    private void updateProduct(final String productId, final String productName, final String imgProduct, final String productPrice) {
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

                            Product product = new Product(productName, spinner.getSelectedItem().toString(), imageUrl, Float.parseFloat(productPrice));
                            mDatabaseRef.child(productId).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "Update Item Success", Toast.LENGTH_LONG).show();
                                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                                    StorageReference imageRef = firebaseStorage.getReferenceFromUrl(imgProduct);
                                    imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getActivity(), "Delete Old Image Success", Toast.LENGTH_SHORT).show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getActivity(), "Delete Old Image Fail", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Update Item Fail", Toast.LENGTH_LONG).show();
                                }
                            });;
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

    private void updateProductNoImage(final String productId, final String productName, final String imgProduct, final String productPrice) {
        Product product = new Product(productName, spinner.getSelectedItem().toString(), imgProduct,  Float.parseFloat(productPrice));
        mDatabaseRef.child(productId).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(), "Update Item Success", Toast.LENGTH_LONG).show();
                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Update Item Fail", Toast.LENGTH_LONG).show();
            }
        });;

    }



    // CHECK CODE
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
