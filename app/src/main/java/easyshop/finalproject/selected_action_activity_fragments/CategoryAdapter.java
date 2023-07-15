package easyshop.finalproject.selected_action_activity_fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import easyshop.finalproject.Data_Center.Category;
import easyshop.finalproject.R;

public class CategoryAdapter extends BaseAdapter {
    private ArrayList<Category> categories;
    private Context context;
    private LayoutInflater inflater;
    private LinearLayout categoryLayout;
    private ImageView categoryImageView;
    private TextView categoryNameView, categoryItemsNumView;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.categories = categories;
        this.context = context;
    }

    @Override
    public int getCount() {
        return categories.size();
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
            convertView = inflater.inflate(R.layout.category, null);
        }
        initViews(convertView);
        categoryImageView.setImageResource(categories.get(position).getImgResource());
        categoryNameView.setText(categories.get(position).getName());
        categoryItemsNumView.setText(categories.get(position).getItemsNum() + " Items");
        categoryLayout.setBackgroundResource(R.drawable.slightly_rounded);
        categoryLayout.setBackgroundTintList(context.getResources().getColorStateList(categories.get(position).getBackground()));
        return convertView;
    }

    private void initViews(View convertView) {
        categoryLayout = convertView.findViewById(R.id.category);
        categoryImageView = convertView.findViewById(R.id.category_img);
        categoryNameView = convertView.findViewById(R.id.category_name);
        categoryItemsNumView = convertView.findViewById(R.id.category_number_items);
    }
}
