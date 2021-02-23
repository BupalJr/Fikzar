package bupaljr.com.fikzar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView profileImage;
    public ImageView postImage, postLike, postComment;
    public TextView username;
    public TextView postTime;
    public TextView postDes;
    public TextView likeCounter;
    public TextView commentsCounter;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        profileImage = itemView.findViewById(R.id.profile_image);
        postImage = itemView.findViewById(R.id.post_image);
        username = itemView.findViewById(R.id.post_username);
        postTime = itemView.findViewById(R.id.post_time);
        postDes = itemView.findViewById(R.id.post_des);


        postLike = itemView.findViewById(R.id.post_like);
        postComment = itemView.findViewById(R.id.post_comments);
        likeCounter = itemView.findViewById(R.id.like_counter);
        commentsCounter = itemView.findViewById(R.id.comments_counter);

    }
}
























