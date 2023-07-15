package easyshop.finalproject.selected_action_activity_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import easyshop.finalproject.MenuActivity;
import easyshop.finalproject.R;

public class RateUs extends Fragment {

    private View view;
    private Button rateBtn;
    private RatingBar ratingStar[];
    private Runnable runnable;
    private Handler handler;
    private DatabaseReference database;
    private float rating;
    private Bundle csBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rate_us, container, false);
        database = FirebaseDatabase.getInstance().getReference("Ratings");

        ratingStar = new RatingBar[5];
        handler = new Handler();

        csBundle = getArguments();

        for(int i=0; i<5; i++)
            ratingStar[i] = (RatingBar)view.findViewById(getResources().getIdentifier("star"+ (i+1) +"_rating_bar", "id", getContext().getPackageName()));
        rateBtn = view.findViewById(R.id.rate_btn);
        defineBtn();

        return view;
    }

    public void defineBtn(){
        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Thank you for your rating!", Toast.LENGTH_SHORT).show();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        rating = 0;
                        for(int i=0; i<5; i++){
                            rating+= ratingStar[i].getRating();
                        }
                        database.push().setValue(rating);
                        Intent intent = new Intent(getActivity(), MenuActivity.class);
                        intent.putExtra("customer_bundle",csBundle);
                        startActivity(intent);
                        getActivity().finish();
                    }
                };
                handler.postDelayed(runnable,2500);
            }
        });
    }
}