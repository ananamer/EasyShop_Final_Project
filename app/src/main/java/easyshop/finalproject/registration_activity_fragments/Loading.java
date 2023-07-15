package easyshop.finalproject.registration_activity_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import easyshop.finalproject.MenuActivity;
import easyshop.finalproject.R;

public class Loading extends Fragment {

    private View view;
    private Runnable runnable;
    private Handler handler;
    private Bundle csBundle;
    private Intent csIntent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_loading, container, false);
        csBundle = getArguments();

        csIntent = new Intent(view.getContext(), MenuActivity.class);
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                csIntent.putExtra("customer_bundle", csBundle);
                startActivity(csIntent);
                getActivity().finish();
            }
        };
        handler.postDelayed(runnable, 3500);

        return view;
    }
}