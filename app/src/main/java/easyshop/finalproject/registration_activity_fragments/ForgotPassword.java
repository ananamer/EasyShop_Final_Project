package easyshop.finalproject.registration_activity_fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import easyshop.finalproject.R;

public class ForgotPassword extends Fragment {

    private View view;
    private Button resetPasswordBtn;
    private EditText emailContainer;
    private String email;
    private Fragment logIn;
    private Runnable runnable;
    private Handler handler;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        auth = FirebaseAuth.getInstance();

        emailContainer = view.findViewById(R.id.email_address_input);
        resetPasswordBtn = view.findViewById(R.id.reset_pass_btn);

        logIn = new LogIn();
        handler = new Handler();

        initResetPassBtn();

        return view;
    }

    public void initResetPassBtn() {
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailContainer.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(v.getContext(), "insert your email first", Toast.LENGTH_SHORT).show();
                } else {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(view.getContext(), "the reset password mail has been sent\n check your email please", Toast.LENGTH_SHORT).show();
                                runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, logIn).commit();
                                    }
                                };
                                handler.postDelayed(runnable, 2500);
                            } else {
                                Toast.makeText(view.getContext(), "something went wrong, please try again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}