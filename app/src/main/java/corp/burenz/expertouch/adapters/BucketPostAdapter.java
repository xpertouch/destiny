package corp.burenz.expertouch.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
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
public class BucketPostAdapter extends RecyclerView.Adapter<BucketPostAdapter.MyPostsHolder>{


    Context context;
    ArrayList<String> postDates;
    ArrayList<String> posts;
    ArrayList<String> postTitle;
    ArrayList<String> postId;
    ArrayList<String> totalLikes;
    ArrayList<String> bucketBanner;

    ArrayList<String> sizeVisibililty;

    String result = "0";


    String saleIndex;


    int postsLength = 0;

    int TIME_OUT = 5000 ;

    @Override
    public MyPostsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bucket_post_adapter,parent,false);
        return new MyPostsHolder(v);

    }




    public BucketPostAdapter(Context context, ArrayList<String> postTitle ,ArrayList<String> postDates, ArrayList<String> posts,ArrayList<String> postId,ArrayList<String> totalLikes){

        this.postDates = postDates;
        this.posts= posts;
        this.context = context;
        this.postTitle = postTitle;
        this.postId = postId;
        this.totalLikes = totalLikes;
        this.bucketBanner = bucketBanner;
    }





    @Override
    public void onBindViewHolder(final MyPostsHolder holder, int position) {


        TextView bucketPostDate,bucketPostTitle,bucketPostSubtitle,totalLikesV;
        ImageView deleteBucketPost;
        final ViewFlipper bucketDeletionFlipper;
        final CardView bucketPostCard;
        final LinearLayout loonelyCompanyBucket;
        Button sureDelete;
        Button noDontDelete;


        bucketPostDate = holder.bucketPostDate;
        bucketPostTitle = holder.bucketPostTitle;
        bucketPostSubtitle = holder.bucketPostSubtitle;
        deleteBucketPost = holder.deleteBucketPost;
        bucketDeletionFlipper = holder.bucketDeletionFlipper;
        bucketPostCard = holder.bucketPostCard;
        totalLikesV = holder.totalLikesV;
        sureDelete = holder.sureDelete;
        noDontDelete = holder.noDontDelete;


        loonelyCompanyBucket = holder.loonelyCompanyBucket;

        bucketPostDate.setText(postDates.get(position).toString());
        bucketPostTitle.setText(postTitle.get(position).toString());
        bucketPostSubtitle.setText(posts.get(position).toString());







        if (totalLikes.get(position).equals("0")){
            totalLikesV.setText("No Likes till Now");

        }else{

            totalLikesV.setText(totalLikes.get(position)+" people like this");

        }



        sureDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bucketDeletionFlipper.showNext();

                new DeleteMyPost().execute(postId.get(holder.getAdapterPosition()).toString());




                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (result.equals("deleted")){

                            bucketPostCard.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fab_close));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

//                                    if (postsLength == 1){
//
//                                        loonelyCompanyBucket.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fab_open));
//                                        new Handler().postDelayed(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                loonelyCompanyBucket.setVisibility(View.VISIBLE);
//                                            }
//                                        },300);
//                                    }
                                    bucketPostCard.setVisibility(View.GONE);


                                }
                            },300);

                        }else{
                            Toast.makeText(context, "Operation Timed out", Toast.LENGTH_SHORT).show();
                            bucketDeletionFlipper.setClickable(true);
                            bucketDeletionFlipper.showNext();

                        }


                    }
                },TIME_OUT);




            }
        });


        noDontDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            bucketDeletionFlipper.showPrevious();

            }
        });


        deleteBucketPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bucketDeletionFlipper.showNext();

            }
        });
        
        
        
        
        
        




    }

    @Override
    public int getItemCount() {

        postsLength = postId.size();


        return postDates.size();

    }

    public static class MyPostsHolder extends RecyclerView.ViewHolder {

        TextView bucketPostDate,bucketPostTitle,bucketPostSubtitle,totalLikesV;
        ImageView deleteBucketPost;
        ViewFlipper bucketDeletionFlipper;
        CardView bucketPostCard;
        LinearLayout loonelyCompanyBucket;
        Button sureDelete;
        Button noDontDelete;






        public MyPostsHolder(View itemView) {
            super(itemView);
        
            bucketPostSubtitle = (TextView) itemView.findViewById(R.id.bucketPostSubtitle); 
            bucketPostTitle = (TextView) itemView.findViewById(R.id.bucketPostTitle);
            deleteBucketPost = (ImageView) itemView.findViewById(R.id.deleteBucketPost);
            bucketPostDate = (TextView) itemView.findViewById(R.id.bucketPostDate);
            bucketPostCard = (CardView) itemView.findViewById(R.id.bucketPostCard);
            bucketDeletionFlipper = (ViewFlipper) itemView.findViewById(R.id.bucketDeletionFlipper);
            totalLikesV = (TextView) itemView.findViewById(R.id.totalLikesV);
            loonelyCompanyBucket = (LinearLayout) itemView.findViewById(R.id.lonelyCompanyBucket);
            sureDelete = (Button) itemView.findViewById(R.id.sureDelete);
            noDontDelete = (Button) itemView.findViewById(R.id.noDontDelete);
        }
        

    }




    class DeleteMyPost extends AsyncTask<String,String,String>{


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairList = new ArrayList<>();

        @Override
        protected String doInBackground(String... params) {


             saleIndex = params[0];
            nameValuePairList.add(new BasicNameValuePair("saleId",params[0]));





            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(context.getString(R.string.host)+ "/bucket/delete_sale.php");
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

            }else {
                result = "error";

            }


        }
    }

}
