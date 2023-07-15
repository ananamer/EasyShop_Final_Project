package easyshop.finalproject.selected_category_fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import easyshop.finalproject.Data_Center.Item;
import easyshop.finalproject.R;

public class ChosenCategory extends Fragment {

    private View view;
    private GridView gridView;
    private ArrayList<Item> items;
    private ItemsAdapter adapter;
    private TextView categoryName;
    private Bundle categoryBundle;
    private DatabaseReference database;
    private Fragment product;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chosen_category, container, false);

        categoryName = view.findViewById(R.id.category_name);
        categoryBundle = getArguments();
        categoryName.setText(categoryBundle.getString("category_name"));
        database = FirebaseDatabase.getInstance().getReference("Shop").child(categoryName.getText().toString());

        product = new Product();
        items = new ArrayList<>();
        adapter = new ItemsAdapter(getActivity(), items);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Item item = dataSnapshot.getValue(Item.class);
                    items.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        gridView = view.findViewById(R.id.products);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle productInfo = new Bundle();
                productInfo.putString("item_name", items.get(position).getName());
                productInfo.putInt("item_price", items.get(position).getPrice());
                productInfo.putString("item_url", items.get(position).getItemUrl());
                productInfo.putString("item_description", items.get(position).getDescription());
                productInfo.putString("item_type", categoryName.getText().toString());
                productInfo.putString("item_identifier",String.valueOf(position+1));
                product.setArguments(productInfo);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.selected_category_frame, product).addToBackStack(null).commit();
            }
        });

        return view;
    }
}