package corp.burenz.expertouch.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import corp.burenz.expertouch.R;

/*** Created by xperTouch on 7/19/2016.***/




public class PostsCompany extends RecyclerView.Adapter<PostsCompany.MyCompanyPosts> {


    ArrayList<String> postDateArray,jobDiscriptionArray;
    Context context;

    @Override
    public MyCompanyPosts onCreateViewHolder(ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_company,parent,false);
        MyCompanyPosts myCompanyPosts = new MyCompanyPosts(v);
        return myCompanyPosts;


    }


    public PostsCompany(Context context,ArrayList<String> postDateArray, ArrayList<String> jobDiscriptionArray) {
        this.postDateArray = postDateArray;
        this.jobDiscriptionArray = jobDiscriptionArray;
        this.context = context;

    }



    @Override
    public void onBindViewHolder(MyCompanyPosts holder, int position) {

        TextView postDate;
        RecyclerView jobDiscriptionRV;
        RecyclerView.Adapter jobAdapter;

        postDate = holder.postDate;
        jobDiscriptionRV = holder.jobDiscriptionRV;
        jobDiscriptionRV.setLayoutManager(new LinearLayoutManager(context));

        postDate.setText(postDateArray.get(position).toString());
        jobAdapter = new AddCounts(jobDiscriptionArray.get(holder.getAdapterPosition()).toString().split("3xt3"));
        jobDiscriptionRV.setAdapter(jobAdapter);

    }

    @Override
    public int getItemCount() {
        return jobDiscriptionArray.size();
    }


    public class MyCompanyPosts extends RecyclerView.ViewHolder {
        TextView postDate;
        RecyclerView jobDiscriptionRV;
        public MyCompanyPosts(View itemView) {
                super(itemView);
                jobDiscriptionRV = (RecyclerView) itemView.findViewById(R.id.jobDiscritionRV);
                postDate = (TextView)itemView.findViewById(R.id.postDate);

        }
    }

}
