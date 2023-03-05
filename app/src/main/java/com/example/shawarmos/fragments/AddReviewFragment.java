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

import com.example.shawarmos.DAL.Model;
import com.example.shawarmos.R;
import com.example.shawarmos.databinding.FragmentAddReviewBinding;
import com.example.shawarmos.models.Review;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class AddReviewFragment extends Fragment {

    private FragmentAddReviewBinding binding;
    private ActivityResultLauncher<Void> cameraLauncher;
    private ActivityResultLauncher<String> galleryLauncher;
    private Review review;
    private String reviewUuid;

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

        review = AddReviewFragmentArgs.fromBundle(getArguments()).getReview();

        reviewUuid = null;

        if (review != null) {
            reviewUuid = review.getReviewId();
            binding.addReviewFragmentNameEt.setText(review.getTitle());
            binding.addReviewFragmentDescriptionEt.setText(review.getDescription());
            binding.addReviewFragmentRankRb.setRating((float) review.getRating());
            if (review.getImageUrl() != null && !review.getImageUrl().equals("")) {
                Picasso.get().load(review.getImageUrl()).placeholder(R.drawable.avatar).into(binding.addReviewFragmentImg);
            }else{
                binding.addReviewFragmentImg.setImageResource(R.drawable.avatar);
            }
            // TODO serialize here an image to the view
        }

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle((reviewUuid == null) ? "Add new review" : "Edit Review");


        binding.addReviewFragmentSaveBtn.setOnClickListener(view1 -> {

            String title = binding.addReviewFragmentNameEt.getText().toString();
            String description = binding.addReviewFragmentDescriptionEt.getText().toString();
            double rank = binding.addReviewFragmentRankRb.getRating();

            if (reviewUuid == null) {
                reviewUuid = UUID.randomUUID().toString();
            }

            Review review = new Review(reviewUuid, title, description, rank, "Naor Hamisha", "");

            if (isAvatarSelected) {
                binding.addReviewFragmentImg.setDrawingCacheEnabled(true);
                binding.addReviewFragmentImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.addReviewFragmentImg.getDrawable()).getBitmap();

                Model.instance().uploadImage(title, bitmap, url->{
                    if (url != null){
                        review.setImageUrl(url);
                    }
                    Model.instance().addReview(review, (unused) -> {
                        Navigation.findNavController(view1).popBackStack();
                    });
                });
            } else {
                Model.instance().addReview(review, (unused) -> {
                    Navigation.findNavController(view1).popBackStack();
                });
            }
        });

        binding.addReviewFragmentCancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack(R.id.menu_mainFeedFragment,false));

        binding.addReviewFragmentCameraBtn.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        binding.addReviewFragmentSearchImgBtn.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });

        return binding.getRoot();
    }
}