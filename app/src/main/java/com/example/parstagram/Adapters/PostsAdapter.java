package com.example.parstagram.Adapters;

import android.content.Context;
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
import com.example.parstagram.R;
import com.example.parstagram.databinding.ItemPostBinding;
import com.parse.ParseFile;

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemPostBinding itembinding;
        public ViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            itembinding = binding;
        }

        public void bind(Post post) {
            itembinding.tvDescription.setText(post.getDescription());
            itembinding.tvUsername.setText(post.getUser().getUsername());
            itembinding.tvDescriptionUsername.setText(post.getUser().getUsername());
            Glide.with(context).load(post.getUser().getImage().getUrl()).centerCrop().circleCrop().into(itembinding.ivProfile);

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
