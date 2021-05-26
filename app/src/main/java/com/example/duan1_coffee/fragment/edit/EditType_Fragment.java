package com.example.duan1_coffee.fragment.edit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.example.duan1_coffee.model.ProductType;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class EditType_Fragment extends Fragment {
    private static final String ARGUMENT_TYPE_ID = "ARGUMENT_TYPE_ID";
    private static final String ARGUMENT_TYPE_NAME = "ARGUMENT_TYPE_NAME";
    private static final String ARGUMENT_TYPE_IMAGE = "ARGUMENT_TYPE_IMAGE";

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button btnChooseImage;
    private Button btnUpload;
    private TextView tvShowTypes;
    private EditText edtProductTypeName;
    private ImageView imgProductType;
    private ProgressBar mProgressBar;
    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    String uniqueId;

    public static EditType_Fragment newInstance(String typeId, String typeName, String typeImage) {
        Bundle args = new Bundle();
        // Save data here
        args.putString(ARGUMENT_TYPE_ID, typeId);
        args.putString(ARGUMENT_TYPE_NAME, typeName);
        args.putString(ARGUMENT_TYPE_IMAGE, typeImage);
        EditType_Fragment fragment = new EditType_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_edit_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        final String typeId = getArguments().getString(ARGUMENT_TYPE_ID);
        String typeName = getArguments().getString(ARGUMENT_TYPE_NAME);
        final String typeImage = getArguments().getString(ARGUMENT_TYPE_IMAGE);

        Log.d("TADFFF", typeId + ", " + typeName + ", " + typeImage);

        btnChooseImage = view.findViewById(R.id.btnChooseImage);
        btnUpload = view.findViewById(R.id.btnUpload);
        tvShowTypes = view.findViewById(R.id.tvShowTypes);
        edtProductTypeName = view.findViewById(R.id.edtProductTypeName);
        imgProductType = view.findViewById(R.id.imgProductType);
        mProgressBar = view.findViewById(R.id.progress_bar);


        edtProductTypeName.setText(typeName);
        Picasso.get()
                .load(typeImage)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imgProductType);

        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Types");

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
                    String typeName = edtProductTypeName.getText().toString().trim();
                    updateProduct(typeId, typeName, typeImage);
                }
            }
        });
        tvShowTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Show All Products click!", Toast.LENGTH_SHORT).show();
                new Function().loadFragment(new ProductType_Fragment(), R.id.fragment_edit_type_change, view);
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void updateProduct(final String typeId, final String typeName, final String imgType) {

        if (mImageUri != null) {
            uniqueId = UUID.randomUUID().toString();
            StorageReference fileReference = mStorageRef.child("typeImages/" + uniqueId);
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

                            ProductType type = new ProductType(typeName, imageUrl);
                            mDatabaseRef.child(typeId).setValue(type).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "Update Item Success", Toast.LENGTH_LONG).show();
                                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                                    StorageReference imageRef = firebaseStorage.getReferenceFromUrl(imgType);
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

    

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(imgProductType);
        }
    }
}
