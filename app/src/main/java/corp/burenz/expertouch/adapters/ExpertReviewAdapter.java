package corp.burenz.expertouch.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.util.MySingleton;

import static corp.burenz.expertouch.R.color.green;
import static corp.burenz.expertouch.R.color.red;

/**
 * Created by buren on 20/1/18.
 */

public class ExpertReviewAdapter extends RecyclerView.Adapter<ExpertReviewAdapter.ReviewHolder> {

    private ArrayList<String> review_user_pic, review_user_name,review_date , review_stars , review_body,review_user_title;
    private Context mContext;
    public  ExpertReviewAdapter (Context mContext, ArrayList<String> review_user_pic, ArrayList<String> review_user_name, ArrayList<String> review_date , ArrayList<String> review_stars , ArrayList<String> review_body ,ArrayList<String> review_user_title){

        this.mContext           =   mContext;
        this.review_user_pic    =   review_user_pic;
        this.review_user_name   =   review_user_name;
        this.review_date        =   review_date;
        this.review_stars       =   review_stars;
        this.review_body        =   review_body;
        this.review_user_title  =   review_user_title;





    }


    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.expert_review_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {

        holder.tv_review_user_name  .setText(review_user_name.get(position));
        holder.tv_review_date       .setText(review_date.get(position));

        if( review_body.get(position).length() == 0 ){ holder.tv_review_body.setVisibility(View.GONE); }else { holder.tv_review_body.setVisibility(View.VISIBLE); }
        holder.tv_review_body       .setText(review_body.get(position));
        holder.iv_review_user_pic   .setImageUrl(review_user_pic.get(position), MySingleton.getInstance(mContext).getImageLoader());
        holder.rb_review_stars      .setText(review_stars.get(position));
        holder.review_user_title    .setText(review_user_title.get(position));

        if( Float.valueOf(review_stars.get(position)) == 1){

            holder.ratings_holder.setCardBackgroundColor(Color.RED);
            holder.line_divider_layout.setBackgroundColor(Color.RED);

        }else{
            holder.ratings_holder.setCardBackgroundColor(mContext.getResources().getColor(R.color.green));
            holder.line_divider_layout.setBackgroundColor( mContext.getResources().getColor(R.color.green));

        }

        if(review_body.get(position).length() == 0){
            holder.tv_review_body.setVisibility(View.GONE);

        }else{
            holder.tv_review_body.setVisibility(View.VISIBLE);

        }


    }

    @Override
    public int getItemCount() {
        return review_user_name.size();
    }

    public static class ReviewHolder extends RecyclerView.ViewHolder {

        TextView  tv_review_user_name, tv_review_date , tv_review_body;
        TextView rb_review_stars,review_user_title;
        NetworkImageView iv_review_user_pic;

        CardView ratings_holder;
        LinearLayout line_divider_layout;

        public ReviewHolder(View itemView) {
            super(itemView);

            tv_review_user_name     = (TextView) itemView.findViewById(R.id.review_user_name);
            tv_review_date          = (TextView) itemView.findViewById(R.id.review_date);
            tv_review_body          = (TextView) itemView.findViewById(R.id.review_body);

            ratings_holder          = (CardView) itemView.findViewById(R.id.ratings_holder_card);
            line_divider_layout     = (LinearLayout) itemView.findViewById(R.id.divider_line_layout);

            rb_review_stars         = (TextView)itemView.findViewById(R.id.review_stars);
            iv_review_user_pic      = (NetworkImageView) itemView.findViewById(R.id.review_user_pic);

            review_user_title       = (TextView) itemView.findViewById(R.id.review_user_title);


        }
    }
}
