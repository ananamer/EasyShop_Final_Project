package easyshop.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import easyshop.finalproject.selected_action_activity_fragments.Categories;
import easyshop.finalproject.selected_action_activity_fragments.MyAccount;
import easyshop.finalproject.selected_action_activity_fragments.MyCart;
import easyshop.finalproject.selected_action_activity_fragments.RateUs;

public class SelectedActionActivity extends AppCompatActivity {

    private Bundle csBundle;
    private Bundle clickedBtn;
    private String choosed;
    private Fragment categories, myAccount, myCart, rateUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_action);

        categories = new Categories();
        myAccount = new MyAccount();
        myCart = new MyCart();
        rateUs = new RateUs();

        clickedBtn = getIntent().getBundleExtra("clicked_btn_bundle");
        csBundle = getIntent().getBundleExtra("customer_bundle");

        choosed = clickedBtn.getString("chosen_activity");

        if(choosed.equals("MY_ACCOUNT")){
            myAccount.setArguments(csBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.selection_type_frame, myAccount).commit();
        }

        if(choosed.equals("SHOP")){
            categories.setArguments(csBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.selection_type_frame, categories).commit();
        }

        if(choosed.equals("MY_CART")){
            myCart.setArguments(csBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.selection_type_frame, myCart).commit();
        }

        if(choosed.equals("RATE_US")){
            rateUs.setArguments(csBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.selection_type_frame, rateUs).commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MenuActivity.class);
        intent.putExtra("customer_bundle",csBundle);
        startActivity(intent);
        finish();
    }
}