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
import com.example.shawarmos.DAL.UserModel;
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
    private String reviewUid;

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

        binding.addReviewFragmentProgressBar.setVisibility(View.GONE);

        reviewUid = null;

        if (review != null) {
            reviewUid = review.getReviewId();
            binding.addReviewFragmentNameEt.setText(review.getTitle());
            binding.addReviewFragmentDescriptionEt.setText(review.getDescription());
            binding.addReviewFragmentRankRb.setRating((float) review.getRating());
            if (review.getImageUrl() != null && !review.getImageUrl().equals("")) {
                Picasso.get().load(review.getImageUrl()).placeholder(R.drawable.logo).into(binding.addReviewFragmentImg);
            } else {
                binding.addReviewFragmentImg.setImageResource(R.drawable.logo);
            }
        }

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle((reviewUid == null) ? "Add new review" : "Edit Review");

        binding.addReviewFragmentSaveBtn.setOnClickListener(view1 -> {
            binding.addReviewFragmentProgressBar.setVisibility(View.VISIBLE);

            String title = binding.addReviewFragmentNameEt.getText().toString();
            String description = binding.addReviewFragmentDescriptionEt.getText().toString();
            double rank = binding.addReviewFragmentRankRb.getRating();

            if (reviewUid == null) {
                reviewUid = UUID.randomUUID().toString();
            }

            UserModel.instance().getCurrentUser(user -> {
                String imgUrl = "";
                if (review != null) {
                    imgUrl = review.getImageUrl();
                }

                Review newReview = new Review(reviewUid, title, description, rank, user.getUserId(), imgUrl);
                if (isAvatarSelected) {
                    binding.addReviewFragmentImg.setDrawingCacheEnabled(true);
                    binding.addReviewFragmentImg.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) binding.addReviewFragmentImg.getDrawable()).getBitmap();

                    Model.instance().uploadImage(title, bitmap, url-> {
                        if (url != null) {
                            newReview.setImageUrl(url);
                        }
                        Model.instance().addReview(newReview, (unused) -> {
                            binding.addReviewFragmentProgressBar.setVisibility(View.GONE);
                            Navigation.findNavController(view1).navigate(AddReviewFragmentDirections.actionAddReviewFragmentToReviewPageFragment(newReview));
                        });
                    });
                } else {
                    Model.instance().addReview(newReview, (unused) -> {
                        binding.addReviewFragmentProgressBar.setVisibility(View.GONE);
                        Navigation.findNavController(view1).navigate(AddReviewFragmentDirections.actionAddReviewFragmentToReviewPageFragment(newReview));
                    });
                }
            });
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