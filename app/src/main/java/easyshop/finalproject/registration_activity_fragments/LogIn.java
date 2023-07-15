package easyshop.finalproject.registration_activity_fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import easyshop.finalproject.Data_Center.Customer;
import easyshop.finalproject.R;


public class LogIn extends Fragment {

    private View view;
    private Button logInBtn, forgotPasswordBtn, signUpBtn;
    private EditText emailLayout, passwordLayout;
    private String email, password;
    private Fragment forgotPassword, signUp, loading;
    private FirebaseAuth mAuth;
    private Bundle csBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_log_in, container, false);
        mAuth = FirebaseAuth.getInstance();

        initValues();
        updateBtns();

        signUp = new SignUp();
        forgotPassword = new ForgotPassword();
        loading = new Loading();

        return view;
    }

    private void updateBtns() {
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailLayout.getText().toString().trim();
                password = passwordLayout.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(getActivity(), "insert your email first", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(getActivity(), "insert password", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseDatabase.getInstance().getReference().child("Customer").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Customer c = snapshot.getValue(Customer.class);

                                        csBundle.putString("first_name", c.getFirstName());
                                        csBundle.putString("last_name", c.getLastName());
                                        csBundle.putString("email", c.getEmail());
                                        csBundle.putString("phone_number", c.getPhoneNumber());

                                        loading.setArguments(csBundle);
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, loading).commit();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if(e.getMessage().equals("The password is invalid or the user does not have a password."))
                                Toast.makeText(getActivity(), "The password is invalid, please try again", Toast.LENGTH_SHORT).show();
                            else if(e.getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted."))
                                Toast.makeText(getActivity(), "There is no user record corresponding, please sign up first", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, forgotPassword).addToBackStack(null).commit();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, signUp).addToBackStack(null).commit();
            }
        });
    }

    public void initValues() {
        logInBtn = view.findViewById(R.id.log_in_btn);
        forgotPasswordBtn = view.findViewById(R.id.forgot_pass_btn);
        signUpBtn = view.findViewById(R.id.new_sign_up_btn);
        emailLayout = view.findViewById(R.id.email_login_input);
        passwordLayout = view.findViewById(R.id.password_login_input);
        csBundle = new Bundle();
    }
}