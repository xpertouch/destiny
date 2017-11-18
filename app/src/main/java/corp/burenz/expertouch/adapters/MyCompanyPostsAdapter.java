package corp.burenz.expertouch.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import corp.burenz.expertouch.R;



/**
 * Created by xperTouch on 9/10/2016.
 */
public class MyCompanyPostsAdapter extends RecyclerView.Adapter<MyCompanyPostsAdapter.MyPostsHolder>{


    Context context;
    ArrayList<String> postDates;
    ArrayList<String> posts;
    ArrayList<String> postId;
    String result = "0";





    int cardCount = 0;


    int TIME_OUT = 5000 ;

    @Override
    public MyPostsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_company_posts_adapter,parent,false);
        return new MyPostsHolder(v);


    }




    public MyCompanyPostsAdapter(Context context,ArrayList<String> postDates, ArrayList<String> posts,ArrayList<String> postId){

        this.postDates = postDates;
        this.posts= posts;
        this.context = context;
        this.postId = postId;

    }





    @Override
    public void onBindViewHolder(final MyPostsHolder holder, int position) {


        final ViewFlipper deletionProgressFlipper;
        TextView myPostDateV;
        final ImageButton deleteMyPostsLL;
        RecyclerView myPostsInMyPosts;
        RecyclerView.Adapter adapter;
        final CardView deleteMyPostsCardView;
        LinearLayout emptyCompany;
        Button yesMSure,noDont;

        myPostsInMyPosts = holder.myPostsInMyPosts;
        myPostDateV = holder.myPostDateV;
        deleteMyPostsLL = holder.deleteMyPostsLL;
        deleteMyPostsCardView = holder.deleteMyPostsCardView;
        deletionProgressFlipper = holder.deletionProgressFlipper;
        yesMSure = holder.yesMSure;
        noDont = holder.noDont;

        cardCount = postId.size();

        myPostDateV.setText(postDates.get(position).toString());
        adapter = new AddCounts(posts.get(position).toString().split("3xt3"));

        myPostsInMyPosts.setLayoutManager(new LinearLayoutManager(context));
        myPostsInMyPosts.setAdapter(adapter);

        final String removePostId = postId.get(position).toString();

        yesMSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                new DeleteMyPost().execute(removePostId);


                deletionProgressFlipper.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        deletionProgressFlipper.setClickable(true);
                    }
                },200);


                deletionProgressFlipper.showNext();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (result.equals("deleted")){

                            deleteMyPostsCardView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fab_close));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    deleteMyPostsCardView.setVisibility(View.GONE);


                                    if (cardCount == 0){

                                        holder.emptyCompany.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fab_open));
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                holder.emptyCompany.setVisibility(View.VISIBLE);
                                            }
                                        },300);
                                    }




                                }
                            },300);

                        }else{
                            Toast.makeText(context, "Operation Timed out", Toast.LENGTH_SHORT).show();

                            deletionProgressFlipper.showNext();
                            deletionProgressFlipper.setClickable(true);
                            deletionProgressFlipper.setEnabled(true);
                            deleteMyPostsLL.setClickable(true);
                            deleteMyPostsLL.setEnabled(true);
                        }


                    }
                },TIME_OUT);







            }
        });



        noDont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletionProgressFlipper.showPrevious();
            }
        });

        deleteMyPostsLL.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                deletionProgressFlipper.showNext();


            }
        });



    }





    @Override
    public int getItemCount() {

        return postDates.size();
    }

    public static class MyPostsHolder extends RecyclerView.ViewHolder {

        TextView myPostDateV;
        ImageButton deleteMyPostsLL;
        RecyclerView myPostsInMyPosts;
        CardView deleteMyPostsCardView;
        LinearLayout emptyCompany;
        Button yesMSure,noDont;

        ViewFlipper deletionProgressFlipper;
        public MyPostsHolder(View itemView) {
            super(itemView);

            myPostDateV = (TextView) itemView.findViewById(R.id.myPostDateV);
            deleteMyPostsLL  = (ImageButton) itemView.findViewById(R.id.deleteMyJobPostLL);
            myPostsInMyPosts = (RecyclerView) itemView.findViewById(R.id.myPostInMyPostsRV);
            deleteMyPostsCardView = (CardView) itemView.findViewById(R.id.deleteMypostCardView);
            deletionProgressFlipper = (ViewFlipper) itemView.findViewById(R.id.deletionProgerssFlipper);
            emptyCompany = (LinearLayout) itemView.findViewById(R.id.emptyJobs);
            yesMSure = (Button)itemView.findViewById(R.id.yesMSure);
            noDont = (Button)itemView.findViewById(R.id.noDont);

        }




    }




    private class DeleteMyPost extends AsyncTask<String,String,String>{


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairList = new ArrayList<>();

        @Override
        protected String doInBackground(String... params) {

            nameValuePairList.add(new BasicNameValuePair("postId",params[0]));




            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(context.getString(R.string.host)+ "/post_add/delete_post.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();


                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";
                while ( (str = bufferedReader.readLine()  ) != null ){

                    builder.append(str);

                }



            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }


            return builder.toString();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("1")){

                result = "deleted";
                cardCount--;


            }else {
                result = "error";
            }


        }
    }

}
