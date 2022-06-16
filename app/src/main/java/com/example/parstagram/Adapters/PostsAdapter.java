package com.example.parstagram.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
                Glide.with(context).load(image.getUrl()).into(itembinding.ivPostImageHome);

            } else {
                itembinding.ivPostImageHome.setVisibility(View.GONE);
            }
        }
    }
}
