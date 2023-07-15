package easyshop.finalproject.selected_category_fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import easyshop.finalproject.Data_Center.Item;
import easyshop.finalproject.R;

public class ItemsAdapter extends BaseAdapter {
    private ArrayList<Item> items;
    private Context context;
    private LayoutInflater inflater;
    private TextView itemNameView, itemPriceView;
    private ImageView itemImage;
    private LinearLayout itemLinearLayout;

    public ItemsAdapter(Context context, ArrayList<Item> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item, null);
        }
        initViews(convertView);
        Glide.with(context).load(items.get(position).getItemUrl()).fitCenter().placeholder(R.color.gray).into(itemImage);
        itemNameView.setText(items.get(position).getName());
        itemPriceView.setText("$"+items.get(position).getPrice() );

        return convertView;
    }

    private void initViews(View convertView) {
        itemLinearLayout = convertView.findViewById(R.id.item);
        itemImage = convertView.findViewById(R.id.item_img);
        itemNameView = convertView.findViewById(R.id.item_name);
        itemPriceView = convertView.findViewById(R.id.item_price);
    }
}
