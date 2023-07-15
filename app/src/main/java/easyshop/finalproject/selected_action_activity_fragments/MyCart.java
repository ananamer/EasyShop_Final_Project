package easyshop.finalproject.selected_action_activity_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import easyshop.finalproject.Data_Center.Customer;
import easyshop.finalproject.Data_Center.Item;
import easyshop.finalproject.R;


public class MyCart extends Fragment {

    private View view;
    private ArrayList<Item> items;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CartAdapter adapter;
    private TextView totalPriceView;
    private Button purchaseBtn;
    private FirebaseAuth auth;
    private DatabaseReference customerDataBase;
    private final String sendingMail = "kenda.app.20@gmail.com";
    private String order;
    private String customerName = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        auth = FirebaseAuth.getInstance();

        items = new ArrayList<>();

        totalPriceView = view.findViewById(R.id.total_price);
        purchaseBtn = view.findViewById(R.id.purchase_btn);
        recyclerView = view.findViewById(R.id.my_cart_items);

        getItemsList();
        buy();

        linearLayoutManager = new LinearLayoutManager(view.getContext());

        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    public void getItemsList() {
        customerDataBase = FirebaseDatabase.getInstance().getReference("Customer").child(auth.getCurrentUser().getUid());
        customerDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                Customer c = snapshot.getValue(Customer.class);
                customerName = c.getFirstName();
                if (c.getMyCart() != null) {
                    HashMap itemsHashMap = c.getMyCart();
                    items = new ArrayList<>(itemsHashMap.values());
                }
                adapter = new CartAdapter(getContext(), items);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        customerDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalPriceView.setText("$"+String.valueOf(calculate(items)));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void buy() {
        createOrder();
        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"ameranan412@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "order for " + customerName);
                email.putExtra(Intent.EXTRA_TEXT, order);
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Send Mail Using :"));
            }
        });
    }

    public void createOrder(){
        order = "";
        for (Item item : items) {
            order += item.getName() + "( " + item.getType() + " ), " + item.getQuantity() + "\n";
        }
    }

    public int calculate(ArrayList<Item> products) {
        int totalPrice = 0;
        for (Item item : products) {
            totalPrice += item.getQuantity() * item.getPrice();
        }
        return totalPrice;
    }
}