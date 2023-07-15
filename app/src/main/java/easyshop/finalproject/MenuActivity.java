package easyshop.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private CardView myAccountBtn, shopBtn, myCartBtn, rateUsBtn;
    private Bundle clickedBtn;
    private Bundle csBundle;
    private Intent csIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        clickedBtn = new Bundle();
        csBundle = getIntent().getBundleExtra("customer_bundle");
        csIntent = new Intent(this,SelectedActionActivity.class);

        myAccountBtn = findViewById(R.id.my_account_btn);
        shopBtn = findViewById(R.id.shop_btn);
        myCartBtn = findViewById(R.id.my_cart_btn);
        rateUsBtn = findViewById(R.id.rate_us_btn);

        pressedBtn();
    }

    private void pressedBtn() {

        myAccountBtn.setOnClickListener(e-> {
            clickedBtn.putString("chosen_activity","MY_ACCOUNT");
            csIntent.putExtra("customer_bundle", csBundle);
            csIntent.putExtra("clicked_btn_bundle",clickedBtn);
            startActivity(csIntent);
            finish();
        });

        shopBtn.setOnClickListener(e-> {
            clickedBtn.putString("chosen_activity","SHOP");
            csIntent.putExtra("customer_bundle", csBundle);
            csIntent.putExtra("clicked_btn_bundle",clickedBtn);
            startActivity(csIntent);
            finish();
        });

        myCartBtn.setOnClickListener(e-> {
            clickedBtn.putString("chosen_activity","MY_CART");
            csIntent.putExtra("customer_bundle", csBundle);
            csIntent.putExtra("clicked_btn_bundle",clickedBtn);
            startActivity(csIntent);
            finish();
        });

        rateUsBtn.setOnClickListener(e-> {
            clickedBtn.putString("chosen_activity","RATE_US");
            csIntent.putExtra("customer_bundle", csBundle);
            csIntent.putExtra("clicked_btn_bundle",clickedBtn);
            startActivity(csIntent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,RegistrationActivity.class);
        startActivity(intent);
        finish();
    }
}