package com.example.duan1_coffee.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.duan1_coffee.Customadapter;
import com.example.duan1_coffee.DocBao;
import com.example.duan1_coffee.LoginActivity;
import com.example.duan1_coffee.R;
import com.example.duan1_coffee.WebViewActivity;
import com.example.duan1_coffee.XMLDOMParser;
import com.example.duan1_coffee.adapter.ProductAdapter;
import com.example.duan1_coffee.adapter.ProductCategoryAdapter;
import com.example.duan1_coffee.model.ProductCategory;
import com.example.duan1_coffee.model.Products;
import com.example.duan1_coffee.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class New_Fragment extends androidx.fragment.app.Fragment {
    ImageSlider imageSlider;
    GoogleSignInClient mGoogleSignInClient;
    ImageView imgGG;
    TextView nameGG,gmailGG;
    FirebaseAuth mAuth;


    ProductCategoryAdapter productCategoryAdapter;
    RecyclerView productCatRecycler, prodItemRecycler;
    ProductAdapter productAdapter;

    DatabaseReference databaseReference;

    ListView lv;
    Customadapter customadapter;
    ArrayList<DocBao> mangdocbao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new, container, false);
//        //Initialise firebase
//        mAuth = FirebaseAuth.getInstance();
//        final FirebaseUser mUser=mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        //Anh xa
        imgGG=view.findViewById(R.id.imgGG);
        nameGG=view.findViewById(R.id.nameGG);
        gmailGG=view.findViewById(R.id.gmailGG);
        lv=view.findViewById(R.id.lv);
        mangdocbao=new ArrayList<DocBao>();

        //Link RSS
        new Readdata().execute("https://vnexpress.net/rss/tin-xem-nhieu.rss");
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("link",mangdocbao.get(position).link);
                startActivity(intent);
            }
        });



        //Slide show
        ImageSlider imageSlider=view.findViewById(R.id.slideshow);
        List<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel("https://media3.s-nbcnews.com/j/newscms/2019_33/2203981/171026-better-coffee-boost-se-329p_67dfb6820f7d3898b5486975903c2e51.fit-760w.jpg","Coffee PoLy"));
        slideModels.add(new SlideModel("https://curlytales.com/wp-content/uploads/2019/10/Coffee-National-Coffee-Day-FT-Blog0919.jpg","Dink some coffee!"));
        imageSlider.setImageList(slideModels,true);





//        productCatRecycler = view.findViewById(R.id.cat_recycler);
//        prodItemRecycler = view.findViewById(R.id.product_recycler);




//        List<ProductCategory> productCategoryList = new ArrayList<>();
//        productCategoryList.add(new ProductCategory(1, "Thịnh hành"));
//        productCategoryList.add(new ProductCategory(2, "Phổ biến nhất"));
//        productCategoryList.add(new ProductCategory(3, "Các sản phẩm của shop"));
//
//        setProductRecycler(productCategoryList);
//
//        List<Products> productsList = new ArrayList<>();
//        productsList.add(new Products(1, "Cafe Sua", R.drawable.prod_cafe_sua));
//        productsList.add(new Products(2, "Cappuccino", R.drawable.prod_cafe_capuchiano));
//        productsList.add(new Products(1, "Caramel Macchiato", R.drawable.prod_cafe_machiato));
//        productsList.add(new Products(2, "Cafe Sua", R.drawable.prod_cafe_sua));
//        productsList.add(new Products(1, "Cappuccino", R.drawable.prod_cafe_capuchiano));
//        productsList.add(new Products(2, "Caramel Macchiato", R.drawable.prod_cafe_machiato));
//
//        setProdItemRecycler(productsList);






















        //Xuat thong tin nguoi dung tu gmail
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            //get familyname sẽ xuất họ ; get GivenName sẽ xuất tên
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
//            Uri personPhoto = acct.getPhotoUrl();

            nameGG.setText("Welcome!!! " +personName);
            gmailGG.setText(personEmail);
//            Glide.with(this).load(String.valueOf(personPhoto)).into(imgGG);
            Glide.with(this).load(acct.getPhotoUrl()).into(imgGG);

        }else {
            showAllUserData();
        }


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);



        return view;
    }






    private void showAllUserData() {
        String id=mAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if(dataSnapshot.exists()){
                    //Check nhap dung pass cu
                    if (!user.getUsername().equals(nameGG)) {
                        nameGG.setText("Welcome! "+ user.getUsername());
                    }
                    if (!user.getEmail().equals(gmailGG)) {
                        gmailGG.setText(user.getEmail());
                    }
                    if (user.getImage().equals("default")){
                        imgGG.setImageResource(R.mipmap.ic_logoapp);
                    } else {
                        Glide.with(getContext()).load(user.getImage()).into(imgGG);
                    }


                }






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }





    //Class doc bao
    class Readdata extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser parser=new XMLDOMParser();
            Document document=parser.getDocument(s);
            NodeList nodeList=document.getElementsByTagName("item");
            NodeList nodeListdescription=document.getElementsByTagName("description");
            String hinhanh="";
            String title="";
            String link="";
            for( int i=0;i<nodeList.getLength();i++){
                String cdata=nodeListdescription.item(i + 1).getTextContent();
                Pattern p=Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher=p.matcher(cdata);
                if(matcher.find()){
                    hinhanh=matcher.group(1);
//                    Log.d("hinhanh",hinhanh +"........"+ i);
                }
                Element element=(Element) nodeList.item(i);
                title+=parser.getValue(element,"title");
                link=parser.getValue(element,"link");
                mangdocbao.add(new DocBao(title,link,hinhanh));

            }

            customadapter=new Customadapter(getActivity(),android.R.layout.simple_list_item_1,mangdocbao);
            lv.setAdapter(customadapter);
            super.onPostExecute(s);

        }
    }
    private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }

}
