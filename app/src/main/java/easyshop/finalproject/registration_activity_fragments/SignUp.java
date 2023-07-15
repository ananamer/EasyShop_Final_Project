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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import easyshop.finalproject.Data_Center.Customer;
import easyshop.finalproject.R;

public class SignUp extends Fragment {

    private Button createAccountBtn;
    private EditText firstNameLayout, lastNameLayout, emailLayout, passwordLayout, confirmPasswordLayout, phoneNumberLayout;
    private String firstName, lastName, email, password, confirmPassword, phoneNumber;
    private View view;
    private Fragment logIn;
    private Runnable runnable;
    private Handler handler;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mAuth = FirebaseAuth.getInstance();

        defineLayouts();
        createAccount();

        logIn = new LogIn();
        handler = new Handler();

        return view;
    }

    private void defineLayouts() {
        createAccountBtn = view.findViewById(R.id.create_account);
        firstNameLayout = view.findViewById(R.id.first_name_input);
        lastNameLayout = view.findViewById(R.id.last_name_input);
        emailLayout = view.findViewById(R.id.email_input);
        passwordLayout = view.findViewById(R.id.password_input);
        confirmPasswordLayout = view.findViewById(R.id.password_confirm_input);
        phoneNumberLayout = view.findViewById(R.id.phone_number_input);
    }

    public void getValues() {
        firstName = firstNameLayout.getText().toString();
        lastName = lastNameLayout.getText().toString();
        email = emailLayout.getText().toString().trim();
        password = passwordLayout.getText().toString().trim();
        confirmPassword = confirmPasswordLayout.getText().toString();
        phoneNumber = phoneNumberLayout.getText().toString();
    }

    private void createAccount() {
        createAccountBtn.setOnClickListener(e -> {
            getValues();

            if (firstName.length() < 3 || firstName.length() > 10)
                Toast.makeText(view.getContext(), "Your first name should be at least 3 characters and maximum 10 characters", Toast.LENGTH_SHORT).show();

            else if (lastName.length() < 3 || lastName.length() > 10)
                Toast.makeText(view.getContext(), "Your last name should be at least 3 characters and maximum 10 characters", Toast.LENGTH_SHORT).show();

            else if (email.indexOf('@') == -1)
                Toast.makeText(view.getContext(), "Your email isn't valid", Toast.LENGTH_SHORT).show();

            else if (password.length() < 6 || password.length() > 15)
                Toast.makeText(view.getContext(), "Your password should be at least 6 characters and maximum 10 characters", Toast.LENGTH_SHORT).show();

            else if (!password.equals(confirmPassword))
                Toast.makeText(view.getContext(), "confirm your password again!", Toast.LENGTH_SHORT).show();

            else {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Customer customer = new Customer(firstName, lastName, email, phoneNumber);
                            FirebaseDatabase.getInstance().getReference("Customer")
                                    .child(mAuth.getCurrentUser().getUid())
                                    .setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(view.getContext(), "Your information is saved", Toast.LENGTH_SHORT).show();
                                        runnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, logIn).commit();
                                            }
                                        };
                                        handler.postDelayed(runnable, 2500);
                                    } else {
                                        Toast.makeText(view.getContext(), "Failed to save your information, try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(view.getContext(), "this email is already used", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}