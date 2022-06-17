package com.example.parstagram.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.parstagram.Models.Post;
import com.example.parstagram.Models.User;
import com.example.parstagram.R;
import com.example.parstagram.databinding.ItemPostBinding;
import com.example.parstagram.ui.PostDetailActivity;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemPostBinding itembinding;
        public ViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            itembinding = binding;
            itembinding.getRoot().setOnClickListener(this);

        }

        public void bind(Post post) {
            itembinding.tvDescription.setText(post.getDescription());
            itembinding.tvUsername.setText(post.getUser().getUsername());
            itembinding.tvDescriptionUsername.setText(post.getUser().getUsername());

            if (post.getUser().getImage() != null) {
                Glide.with(context).load(post.getUser().getImage().getUrl()).centerCrop().circleCrop().into(itembinding.ivProfile);

            }

            itembinding.ibLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> getLikedBy = post.getLike();
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    if (getLikedBy.contains(currentUser.getObjectId())) {
                        getLikedBy.remove(currentUser.getObjectId());
                        post.setUserLike(getLikedBy);
                        itembinding.ibLike.setBackgroundResource(R.drawable.ic_instagram_heart);
                        itembinding.tvLikeCount.setText(String.valueOf(post.getLike().size()));
                    } else{
                        getLikedBy.add(currentUser.getObjectId());
                        post.setUserLike(getLikedBy);
                        itembinding.ibLike.setBackgroundResource(R.drawable.ic_red_heart);
                        itembinding.tvLikeCount.setText(String.valueOf(post.getLike().size()));
                    }
                }
            });

            ParseFile image = post.getImage();
            if (image !=  null) {

                Glide.with(context)
                        .load(image.getUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                itembinding.progressBar.setVisibility(View.VISIBLE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                itembinding.progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(itembinding.ivPostImageHome);

//                Glide.with(context).load(image.getUrl())
//                        .transition(DrawableTransitionOptions.withCrossFade())
//                        .into(itembinding.ivPostImageHome);

            } else {

                itembinding.ivPostImageHome.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            Post post = posts.get(getAdapterPosition());
            Intent i = new Intent(context, PostDetailActivity.class);
            i.putExtra("USER_POST", Parcels.wrap(post));
            context.startActivity(i);

        }
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}
