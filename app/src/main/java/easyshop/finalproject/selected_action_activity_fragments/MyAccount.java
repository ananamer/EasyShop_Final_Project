package easyshop.finalproject.selected_action_activity_fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import easyshop.finalproject.Data_Center.Customer;
import easyshop.finalproject.MenuActivity;
import easyshop.finalproject.R;

public class MyAccount extends Fragment {

    private EditText firstNameET, lastNameET, emailET, phoneNumberET;
    private ImageView userImg;
    private View view;
    private Button saveBtn;
    private AppCompatImageButton uploadImg;
    private Runnable runnable;
    private Handler handler;
    private Bundle csBundle;
    private Intent csIntent;
    private FirebaseAuth auth;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private HashMap hashMap = new HashMap();
    private Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_account, container, false);

        csBundle = getArguments();
        csIntent = new Intent(getActivity(), MenuActivity.class);
        handler = new Handler();
        auth = FirebaseAuth.getInstance();

        initValues();
        fillData();
        createBtns();

        return view;
    }

    private void initValues() {
        firstNameET = view.findViewById(R.id.account_first_name);
        lastNameET = view.findViewById(R.id.account_last_name);
        emailET = view.findViewById(R.id.account_email);
        emailET.setEnabled(false);
        phoneNumberET = view.findViewById(R.id.account_phone_number);
        saveBtn = view.findViewById(R.id.save_btn);
        uploadImg = view.findViewById(R.id.edit_btn);
        userImg = view.findViewById(R.id.user_img);
    }

    private void fillData() {
        firstNameET.setText(csBundle.getString("first_name"));
        lastNameET.setText(csBundle.getString("last_name"));
        emailET.setText(csBundle.getString("email"));
        phoneNumberET.setText(csBundle.getString("phone_number"));

        FirebaseDatabase.getInstance().getReference("Customer").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer c = snapshot.getValue(Customer.class);
                if(getContext() == null)
                    userImg.setImageResource(R.drawable.user_image);
                else{
                    Glide.with(getContext())
                            .load(c.getImageUrl())
                            .placeholder(R.drawable.user_image)
                            .centerCrop()
                            .fitCenter()
                            .transform(new RoundedCornersTransformation(200, 0))
                            .into((userImg));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private HashMap getData() {
        hashMap.put("firstName", firstNameET.getText().toString());
        hashMap.put("lastName", lastNameET.getText().toString());
        hashMap.put("phoneNumber", phoneNumberET.getText().toString());
        csBundle.putString("first_name", firstNameET.getText().toString());
        csBundle.putString("last_name", lastNameET.getText().toString());
        csBundle.putString("phone_number", phoneNumberET.getText().toString());

        return hashMap;
    }

    private void createBtns() {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Customer")
                        .child(auth.getCurrentUser().getUid()).updateChildren(getData()).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(v.getContext(), "Your information is saved!", Toast.LENGTH_SHORT).show();
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                csIntent.putExtra("customer_bundle", csBundle);
                                startActivity(csIntent);
                                getActivity().finish();
                            }
                        };
                        handler.postDelayed(runnable, 2500);
                    }
                });
            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = emailET.getText().toString();
                storageReference = FirebaseStorage.getInstance().getReference("profile_pictures/" + fileName);

                /*Get Image from the phone*/
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                someActivityResultLauncher.launch(galleryIntent);

            }
        });
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        imageUri = result.getData().getData();
                        userImg.setImageURI(imageUri);

                        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        HashMap hashMap = new HashMap<>();
                                        hashMap.put("imageUrl", uri.toString());

                                        FirebaseDatabase.getInstance().getReference("Customer")
                                                .child(auth.getCurrentUser().getUid()).updateChildren(hashMap);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed to Upload", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
}