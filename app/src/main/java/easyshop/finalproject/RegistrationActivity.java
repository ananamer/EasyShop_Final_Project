package easyshop.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import easyshop.finalproject.registration_activity_fragments.LogIn;

public class RegistrationActivity extends AppCompatActivity {

    Fragment logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        logIn = new LogIn();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, logIn).commit();
    }
}
