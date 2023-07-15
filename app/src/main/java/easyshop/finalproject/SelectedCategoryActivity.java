package easyshop.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import easyshop.finalproject.selected_category_fragments.ChosenCategory;

public class SelectedCategoryActivity extends AppCompatActivity {

    Fragment chosenCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);

        chosenCategory = new ChosenCategory();

        Intent intent = getIntent();
        Bundle categoryInfo = intent.getBundleExtra("category_info");
        chosenCategory.setArguments(categoryInfo);
        getSupportFragmentManager().beginTransaction().replace(R.id.selected_category_frame, chosenCategory).commit();
    }
}