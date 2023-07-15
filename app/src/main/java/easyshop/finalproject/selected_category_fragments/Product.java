package easyshop.finalproject.selected_category_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import easyshop.finalproject.Data_Center.Customer;
import easyshop.finalproject.Data_Center.Item;
import easyshop.finalproject.R;
import easyshop.finalproject.SelectedCategoryActivity;


public class Product extends Fragment {

    private View view;
    private TextView productNameView, productPriceView, productDescriptionView;
    private Button addToCartBtn;
    private ImageView productImageView;
    private Runnable runnable;
    private Handler handler;
    private Bundle productInfoBundle;
    private FirebaseAuth auth;
    private DatabaseReference customer, customerCart;
    private HashMap updateCustomer, updateCustomerCart;
    int numItemsInCart = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product, container, false);
        auth = FirebaseAuth.getInstance();

        customer = FirebaseDatabase.getInstance().getReference("Customer").child(auth.getCurrentUser().getUid());
        customerCart = customer.child("myCart");

        handler = new Handler();
        updateCustomer = new HashMap();
        updateCustomerCart = new HashMap();

        initViews();
        getData();
        defineBtn();

        productInfoBundle = getArguments();
        productNameView.setText(productInfoBundle.getString("item_name"));
        productPriceView.setText("$" + productInfoBundle.getInt("item_price") );
        productDescriptionView.setText(productInfoBundle.getString("item_description"));
        Glide.with(getContext()).load(productInfoBundle.getString("item_url")).fitCenter().into((productImageView));

        return view;
    }

    private void initViews() {
        productNameView = view.findViewById(R.id.product_name_title);
        productPriceView = view.findViewById(R.id.product_price_holder);
        productDescriptionView = view.findViewById(R.id.description);
        addToCartBtn = view.findViewById(R.id.add_to_cart);
        productImageView = view.findViewById(R.id.product_img);
    }

    public void update(){
        numItemsInCart+=1;
        updateCustomer.put("numItemsInCart", numItemsInCart);
        customer.updateChildren(updateCustomer);
    }

    public void getData(){
        customer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer c = snapshot.getValue(Customer.class);
                numItemsInCart = c.getNumItemsInCart();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void defineBtn(){
        addToCartBtn.setOnClickListener(e -> {
            FirebaseDatabase.getInstance().getReference("Shop")
                    .child(productInfoBundle.getString("item_type")).child(productInfoBundle.getString("item_identifier")).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Item item = snapshot.getValue(Item.class);
                    updateCustomerCart.put(String.valueOf(numItemsInCart) + "_key", item);
                    customerCart.updateChildren(updateCustomerCart).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(view.getContext(), "the item has been added to your cart", Toast.LENGTH_SHORT).show();
                            update();
                            runnable = new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getActivity(), SelectedCategoryActivity.class);
                                    Bundle categoryInfo = new Bundle();
                                    categoryInfo.putString("category_name", productInfoBundle.getString("item_type"));
                                    intent.putExtra("category_info", categoryInfo);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            };
                            handler.postDelayed(runnable, 2500);
                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        });
    }
}