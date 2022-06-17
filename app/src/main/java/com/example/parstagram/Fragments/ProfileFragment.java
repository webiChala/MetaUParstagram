package com.example.parstagram.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.parstagram.Adapters.PostsAdapter;
import com.example.parstagram.Models.Post;
import com.example.parstagram.Models.User;
import com.example.parstagram.R;
import com.example.parstagram.databinding.FragmentPostBinding;
import com.example.parstagram.databinding.FragmentProfileBinding;
import com.example.parstagram.ui.HomeActivity;
import com.example.parstagram.ui.LoginActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    private PostsAdapter adapter;
    private List<Post> allPosts;
    FragmentProfileBinding binding;

    public ProfileFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);
        binding.rvUserPosts.setAdapter(adapter);
        binding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log user out
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();

                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        binding.tvProfileUsername.setText(ParseUser.getCurrentUser().getUsername().toString());
        binding.rvUserPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        User currentUser = (User) ParseUser.getCurrentUser();
        Log.i(TAG, "Url: " + currentUser);
        //Glide.with(getContext()).load(currentUser.getImage().getUrl()).into(binding.ivProfileImage);
        queryPosts();



    }

    //retrieve all post of current user
    private void queryPosts() {

        //instantiate a query variable that has methods to grab all posts
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while getting posts", e);
                    return;

                }
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription());
                }
                allPosts.clear();
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
                //binding.swipeContainer.setRefreshing(false);

            }
        });
    }
}