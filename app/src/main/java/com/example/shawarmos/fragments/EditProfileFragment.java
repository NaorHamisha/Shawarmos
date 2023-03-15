package com.example.shawarmos.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.shawarmos.DAL.UserModel;
import com.example.shawarmos.R;
import com.example.shawarmos.databinding.FragmentEditProfileBinding;
import com.example.shawarmos.models.User;
import com.squareup.picasso.Picasso;


public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;
    private ActivityResultLauncher<Void> cameraLauncher;
    private ActivityResultLauncher<String> galleryLauncher;
    private boolean isAvatarSelected = false;
    private User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.addReviewFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        },this, Lifecycle.State.RESUMED);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.editProfileAvatarImg.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    binding.editProfileAvatarImg.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         binding = FragmentEditProfileBinding.inflate(inflater, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit profile");

        user = EditProfileFragmentArgs.fromBundle(getArguments()).getUser();

        binding.editProfileFragmentProgressBar.setVisibility(View.GONE);

        binding.editProfileFragmentCancelBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.editProfileFragmentSaveBtn.setOnClickListener(view -> {
            binding.editProfileFragmentProgressBar.setVisibility(View.VISIBLE);
            updateUserDetails();
        });

        binding.editProfileFragmentCameraBtn.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        binding.editProfileFragmentSearchImgBtn.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });

        initializeProfileDetails();

         return binding.getRoot();
    }

    private void updateUserDetails() {
        String userName = binding.editProfileFragmentUsernameEt.getText().toString();
        //String email = binding.editProfileFragmentEmailEt.getText().toString();
        String email = user.getEmail();
        String imgUrl = user.getAvatarUrl();
        String id = user.getUserId();

        User updatedUser = new User(id, userName, email, imgUrl);

        Bitmap bitmap = (isAvatarSelected) ? ((BitmapDrawable) binding.editProfileAvatarImg.getDrawable()).getBitmap(): null;

        UserModel.instance().updateUser(updatedUser, bitmap,() -> {
            binding.editProfileFragmentProgressBar.setVisibility(View.GONE);
            Navigation.findNavController(binding.getRoot()).popBackStack();
        });
    }

    private void initializeProfileDetails() {
       // binding.editProfileFragmentEmailEt.setText(user.getEmail());
        binding.editProfileFragmentUsernameEt.setText(user.getUserName());

        String imgUrl = user.getAvatarUrl();

        if (imgUrl != null && imgUrl != "") {
            Picasso.get().load(imgUrl).into(binding.editProfileAvatarImg);
        }
    }
}