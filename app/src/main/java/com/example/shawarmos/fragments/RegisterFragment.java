package com.example.shawarmos.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shawarmos.DAL.AuthModel;
import com.example.shawarmos.DAL.Model;
import com.example.shawarmos.DAL.UserModel;
import com.example.shawarmos.R;
import com.example.shawarmos.activities.FeedActivity;
import com.example.shawarmos.databinding.FragmentRegisterBinding;
import com.example.shawarmos.models.User;

public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    private ActivityResultLauncher<Void> cameraLauncher;
    private ActivityResultLauncher<String> galleryLauncher;
    private boolean isAvatarSelected = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.registerFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        }, this, Lifecycle.State.RESUMED);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.registerFragmentAvatarImg.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    binding.registerFragmentAvatarImg.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.registerFragmentCameraBtn.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        binding.registerFragmentSearchImgBtn.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });

        binding.registerFragmentProgressBar.setVisibility(View.GONE);

        binding.registerFragmentRegisterBtn.setOnClickListener(view1 -> {

            String password = binding.registerFragmentPasswordEt.getText().toString();
            String userName = binding.registerFragmentUsernameEt.getText().toString();
            String email = binding.registerFragmentEmailEt.getText().toString();;
            String confirmPassword = binding.registerFragmentConfirmPasswordEt.getText().toString();

            if (!isFormValid(userName, email, password, confirmPassword)) {
                return;
            }

            binding.registerFragmentProgressBar.setVisibility(View.VISIBLE);

            AuthModel.instance().register(email, password, (userId, ex) -> {
                if (userId != null) {
                    User newUser = new User(userId, userName, email);
                    Bitmap bitmap = (isAvatarSelected) ? ((BitmapDrawable) binding.registerFragmentAvatarImg.getDrawable()).getBitmap(): null;
                    UserModel.instance().createUser(newUser, bitmap, ()-> {
                        redirectToFeedActivity();
                    });
                } else {
                    binding.registerFragmentProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Registration failed: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                }

            });
        });

        return view;
    }

    private boolean isFormValid(String username, String email, String password, String passwordConf) {
        boolean isValid = true;

        if (username.isEmpty()) {
            binding.registerFragmentEmailEt.setError("Enter user name");
            isValid = false;
        }

        if (email.isEmpty()) {
            binding.registerFragmentEmailEt.setError("Enter email address");
            isValid = false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.registerFragmentEmailEt.setError("Invalid email address");
            isValid = false;
        }

        if (password.isEmpty()) {
            binding.registerFragmentPasswordEt.setError("Enter password");
            isValid = false;
        }

        if (!passwordConf.equals(password)) {
            binding.registerFragmentConfirmPasswordEt.setError("Passwords do not match");
            isValid = false;
        }

        return isValid;
    }

    private void redirectToFeedActivity() {
        Intent intent = new Intent(getContext(), FeedActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}