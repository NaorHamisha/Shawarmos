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

import com.example.shawarmos.DAL.Model;
import com.example.shawarmos.R;
import com.example.shawarmos.databinding.FragmentAddReviewBinding;
import com.example.shawarmos.models.ReviewModel;

public class AddReviewFragment extends Fragment {

    private FragmentAddReviewBinding binding;
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
                    binding.addReviewFragmentImg.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    binding.addReviewFragmentImg.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddReviewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.addReviewFragmentSaveBtn.setOnClickListener(view1 -> {
            // TODO: Check if the variables have values
            String name = binding.addReviewFragmentNameEt.getText().toString();
            String description = binding.addReviewFragmentDescriptionEt.getText().toString();
            float rank = binding.addReviewFragmentRankRb.getRating();

            ReviewModel review  = new ReviewModel(name, description, rank);

            if (isAvatarSelected) {
                binding.addReviewFragmentImg.setDrawingCacheEnabled(true);
                binding.addReviewFragmentImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.addReviewFragmentImg.getDrawable()).getBitmap();

                Model.instance().uploadImage(name, bitmap, url->{
                    if (url != null){
                        review.setImageUrl(url);
                    }
                    Model.instance().addReview(review, (unused) -> {
                        Navigation.findNavController(view1).popBackStack();
                    });
                });
            }
//            else {
//                Model.instance().addReview(review, (unused) -> {
//                    Navigation.findNavController(view1).popBackStack();
//                });
//            }
        });

        binding.addReviewFragmentCancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack(R.id.shawarmaListFragment,false));

        binding.addReviewFragmentCameraBtn.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        binding.addReviewFragmentSearchImgBtn.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });

        return view;
    }
}