package corp.burenz.expertouch.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.activities.CompanyProfile;
import corp.burenz.expertouch.activities.JobPostDetails;
import corp.burenz.expertouch.fragments.post.flag.JobPost;


/**
 * Created by xperTouch on 7/29/2016.
 */


public class AddCounts extends RecyclerView.Adapter<AddCounts.CompanyAdds>{

    boolean FROM_JOBS = false;

    String[] textsIGotS;


    @Override
    public AddCounts.CompanyAdds onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_counts,parent,false);

        CompanyAdds companyAdds = new CompanyAdds(v);

        return companyAdds;


    }



    private ArrayList<String> postId;
    private Context mContext;
    String singlePostId;

    AddCounts(String[] textsIGot){
        this.textsIGotS = textsIGot;
    }



    public AddCounts(String[] textsIGot, boolean FROM_JOBS){

        this.textsIGotS = textsIGot;
        this.FROM_JOBS  = FROM_JOBS;

    }

    AddCounts(Context mContext, String[] textsIGot, boolean FROM_JOBS, ArrayList<String> postId){

        this.textsIGotS         = textsIGot;
        this.FROM_JOBS          = FROM_JOBS;
        this.mContext           = mContext;
        this.postId             = postId;

    }

    AddCounts(Context mContext, String[] textsIGot, boolean FROM_JOBS, ArrayList<String> postId, String singlePostId){

        this.textsIGotS         = textsIGot;
        this.FROM_JOBS          = FROM_JOBS;
        this.mContext           = mContext;
        this.postId             = postId;
        this.singlePostId       = singlePostId;
    }




    @Override
    public void onBindViewHolder(AddCounts.CompanyAdds holder, final int position) {

        holder.textsIGot.setText(Html.fromHtml(textsIGotS[position]));

        if(FROM_JOBS){

            /*setting blue color to predict links*/
//            if (textsIGotS[position].contains("...")){holder.textsIGot.setTextColor(Color.GRAY);}else{holder.textsIGot.setTextColor(Color.BLACK);}

            holder.textsIGot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.e("finale","im listening add counts adapter");
                    if (singlePostId!= null){
                        mContext.startActivity(new Intent(mContext, JobPostDetails.class).putExtra("postId",singlePostId));
                    }else {
                        mContext.startActivity(new Intent(mContext, JobPostDetails.class).putExtra("postId",postId.get(position)));
                    }


                }
            });

        }



    }

    @Override
    public int getItemCount() {
        return textsIGotS.length;
    }



    public  static class CompanyAdds extends RecyclerView.ViewHolder {

        TextView textsIGot;


        public CompanyAdds(View itemView) {
            super(itemView);
            textsIGot = (TextView) itemView.findViewById(R.id.textIGot);

        }
    }
}

