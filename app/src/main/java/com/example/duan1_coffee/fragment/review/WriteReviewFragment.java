package com.example.duan1_coffee.fragment.review;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.fragment.Home_Fragment;
import com.example.duan1_coffee.fragment.chat_Fragment;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.Review;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WriteReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WriteReviewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    public WriteReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WriteReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WriteReviewFragment newInstance(String param1, String param2, String param3) {

        RatingBar simpleRatingBar;

        WriteReviewFragment fragment = new WriteReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_write_review, container, false);

        String uid = FirebaseAuth.getInstance().getUid();
        ImageView productImage = (ImageView) view.findViewById(R.id.productImage);
        TextView tvProductName = (TextView) view.findViewById(R.id.tvProductName);
        tvProductName.setText(mParam2);
        final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Review");
        final TextView tvReviewText = (TextView) view.findViewById(R.id.tvReviewText);

        final RatingBar simpleRatingBar = (RatingBar) view.findViewById(R.id.ratingBar); // initiate a rating bar
        LayerDrawable stars = (LayerDrawable) simpleRatingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        Picasso.get()
                .load(mParam3)
                .placeholder(R.drawable.ic_product_iamge)
                .fit()
                .centerCrop()
                .into(productImage);


        view.findViewById(R.id.tvBackToShop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Move to Shop!", Toast.LENGTH_SHORT).show();
                new Function().loadFragment(new Home_Fragment(), R.id.frameLayout, view);
            }
        });

        view.findViewById(R.id.tvChatWithUs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Move to Chat!", Toast.LENGTH_SHORT).show();
                new Function().loadFragment(new chat_Fragment(), R.id.frameLayout, view);
            }
        });

        view.findViewById(R.id.tvAddReview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Float ratingNumber = simpleRatingBar.getRating();
                final String reviewText = tvReviewText.getText().toString();
                if(!TextUtils.isEmpty(reviewText)){
                    String id = reference.push().getKey();
                    Review review = new Review(id, mParam1, mParam2, mParam3, ratingNumber, reviewText, email);
                    reference.child(id).setValue(review).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Add Review Success!", Toast.LENGTH_SHORT).show();
                            new Function().loadFragment(new Home_Fragment(), R.id.frameLayout, view);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Write Review Fail!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(getActivity(), "Input Review not invalid!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}