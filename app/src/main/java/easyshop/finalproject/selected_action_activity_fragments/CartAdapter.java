package easyshop.finalproject.selected_action_activity_fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import easyshop.finalproject.Data_Center.Item;
import easyshop.finalproject.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{
    private ArrayList<Item> items;
    private HashMap cartItems, itemsInCartHashmap;
    private Context context;
    FirebaseAuth auth;
    DatabaseReference databaseReference;


    public CartAdapter(Context context, ArrayList<Item> items) {
        this.items = items;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private Button deleteBtn, addcartItemsBtn, decreasecartItemsBtn;
        private ImageView itemImage;
        private TextView productName, productPrice, itemQuantity;
        private CartAdapter adapter;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            deleteBtn = itemView.findViewById(R.id.delete_item);
            addcartItemsBtn = itemView.findViewById(R.id.add_items);
            decreasecartItemsBtn = itemView.findViewById(R.id.decrease_items);
            itemImage = itemView.findViewById(R.id.item_img);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            itemQuantity = itemView.findViewById(R.id.item_quantity);

            auth = FirebaseAuth.getInstance();
            databaseReference = FirebaseDatabase.getInstance().getReference("Customer").child(auth.getCurrentUser().getUid());

            cartItems = new HashMap();
            itemsInCartHashmap = new HashMap();
            for (int i = 0; i < items.size(); i++)
                cartItems.put(String.valueOf(i) + "_key", items.get(i));

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartItems.clear();
                    adapter.items.remove(getAdapterPosition());
                    itemsInCartHashmap.put("numItemsInCart", items.size());
                    for (int i = 0; i < items.size(); i++)
                        cartItems.put(String.valueOf(i) + "_key", items.get(i));
                    databaseReference.child("myCart").setValue(cartItems);
                    databaseReference.updateChildren(itemsInCartHashmap);
                    adapter.notifyItemRemoved(getAdapterPosition());
                }
            });


            addcartItemsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.items.get(getAdapterPosition()).setQuantity(Integer.parseInt(itemQuantity.getText().toString()) + 1);
                    cartItems.put(String.valueOf(getAdapterPosition()) + "_key", items.get(getAdapterPosition()));
                    databaseReference.child("myCart").setValue(cartItems);
                    adapter.notifyItemChanged(getAdapterPosition());
                }
            });

            decreasecartItemsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapter.items.get(getAdapterPosition()).getQuantity() != 1) {
                        adapter.items.get(getAdapterPosition()).setQuantity(Integer.parseInt(itemQuantity.getText().toString()) - 1);
                        cartItems.put(String.valueOf(getAdapterPosition()) + "_key", items.get(getAdapterPosition()));
                        databaseReference.child("myCart").setValue(cartItems);
                        adapter.notifyItemChanged(getAdapterPosition());
                    } else {
                        cartItems.clear();
                        adapter.items.remove(getAdapterPosition());
                        itemsInCartHashmap.put("numItemsInCart", items.size());
                        for (int i = 0; i < items.size(); i++)
                            cartItems.put(String.valueOf(i) + "_key", items.get(i));
                        databaseReference.child("myCart").setValue(cartItems);
                        databaseReference.updateChildren(itemsInCartHashmap);
                        adapter.notifyItemRemoved(getAdapterPosition());
                    }
                }
            });
        }

        public MyViewHolder linkAdapter(CartAdapter adapter) {
            this.adapter = adapter;
            return this;
        }


        public void setData(String name, int price, int quantity, String itemUrl) {
            productName.setText(name);
            productPrice.setText(Integer.toString(price));
            Glide.with(context).load(itemUrl).fitCenter().transforms(new CenterCrop(), new RoundedCorners(20)).into((itemImage));
            itemQuantity.setText(Integer.toString(quantity));
        }
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new MyViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        int productPrice, productQuantity, totalPrice;
        String productName, itemUrl;

        itemUrl = items.get(position).getItemUrl();
        productPrice = items.get(position).getPrice();
        productQuantity = items.get(position).getQuantity();
        productName = items.get(position).getName();

        holder.setData(productName, productPrice, productQuantity, itemUrl);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
