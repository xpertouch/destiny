package corp.burenz.expertouch.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

//import com.android.volley.toolbox.ImageLoader;
import com.mikepenz.iconics.view.IconicsImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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
import corp.burenz.expertouch.activities.CompanyProfile;
import corp.burenz.expertouch.fragments.post.flagged.MyBucket;
import corp.burenz.expertouch.util.MySingleton;

/**
 * Created by xperTouch on 10/11/2016.
 */
public class BucketAdapter extends RecyclerView.Adapter<BucketAdapter.MyBucketHolder> {




    ArrayList<String> companyTitleArray,companyCityArray,saleTitleArray,saleDiscriptionArray,saleDateArray,saleBannerArray,saleIds,myLikeIds,totalLikes,attachedBanner;
    Context context;
    Integer newCount;
    String USER_EMAIL = "noemail@example.com";
    String USER_STATE;
    int TIME_OUT = 4000;
    SharedPreferences userData;
    String LOCAL_APP_DATA = "userInformation";



    private void setLikesSupport(MyBucketHolder  holder, int position){


        if( totalLikes.get(position).equals("0")){
            holder.textViewSupportTV.setText("");
            holder.totalLikes.setVisibility(View.GONE);
        }else{

            holder.totalLikes.setText(totalLikes.get(position));
            if(totalLikes.get(position).equals("1")){
                holder.textViewSupportTV.setText("Like");
            }else{
                holder.textViewSupportTV.setText("users liked this");

            }

            holder.totalLikes.setVisibility(View.VISIBLE);
        }

    }



    public BucketAdapter(Context context,ArrayList<String> saleIds,ArrayList<String> companyTitleB,ArrayList<String> companyCity,ArrayList<String> saleTitle,ArrayList<String> saleDiscription,ArrayList<String> saleDate,ArrayList<String> saleBanner,ArrayList<String> myLikeIds,ArrayList<String> totalLikes,ArrayList<String> attachedBanner){

        this.context                = context;
        this.saleIds                = saleIds;
        this.companyTitleArray      = companyTitleB;
        this.companyCityArray       = companyCity;
        this.saleTitleArray         = saleTitle;
        this.saleDateArray          = saleDate;
        this.saleDiscriptionArray   = saleDiscription;
        this.saleBannerArray        = saleBanner;
        this.myLikeIds              = myLikeIds;
        this.totalLikes             = totalLikes;
        this.attachedBanner         = attachedBanner;
    }


    @Override
    public MyBucketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyBucketHolder(LayoutInflater.from(context).inflate(R.layout.bucket_adapter,parent,false));
    }




    private void likeThePost(MyBucketHolder holder, int position){

        MediaPlayer ourSplasSound =  MediaPlayer.create(context,R.raw.pop);

        holder.thumbsUpAdd     .setVisibility(View.GONE);
        holder.thumbsDownAdd   .startAnimation(AnimationUtils.loadAnimation(context,R.anim.card_animation));
        holder.thumbsDownAdd   .setVisibility(View.VISIBLE);
        ourSplasSound.start();
        holder.totalLikes.startAnimation(AnimationUtils.loadAnimation(context,R.anim.card_animation));
        holder.totalLikes.setText(String.valueOf(  Integer.parseInt(holder.totalLikes.getText().toString()) + 1));

        try {
            myLikeIds.add(saleIds.get(position));
            totalLikes.set(position,holder.totalLikes.getText().toString());

            setLikesSupport(holder,position);

        }catch (Exception e){
            e.printStackTrace();
            Log.e("NEWCOUNT","UNFORTUNATE SLAP ");

        }

        new SortLikes("like",position,holder, holder.totalLikes.getText().toString()).execute("/bucket/like_mech.php",saleIds.get(position));


    }


    @Override
    public void onBindViewHolder(MyBucketHolder oriHolder, final int position) {

        SharedPreferences   userData;
        String LOCAL_APP_DATA = "userInformation";
        userData = context.getSharedPreferences(LOCAL_APP_DATA,0);
        USER_EMAIL = userData.getString("userEmail","noemail@example.com");
        USER_STATE = userData.getString("userState","Jammu and Kashmir");

        final MyBucketHolder holder = (MyBucketHolder) oriHolder;
        ViewFlipper thumbsFlipper;
        final ImageButton thumbsUpAdd,thumbsDownAdd;

         final TextView companyTitleB;
         TextView companyCity;
         TextView saleTitle;
        final TextView totalLikesTV;
        final TextView saleDiscription;
        final TextView saleDate;
        final ImageView saleBanner,attachedBannerView;
        final ImageView companyProfileB;
        final IconicsImageView shareSaleB;
        LinearLayout styleSheet;


        companyTitleB       = holder.companyTitleB;
        companyCity         = holder.companyCity;
        saleTitle           = holder.saleTitle;
        saleDiscription     = holder.saleDiscription;
        saleDate            = holder.saleDate;
        saleBanner          = holder.saleBanner;
        companyProfileB     = holder.companyProfileB;
        shareSaleB          = holder.shareSaleB;
        attachedBannerView  = holder.attachedBannerView;
        styleSheet          = holder.styleSheet;
        thumbsUpAdd         = holder.thumbsUpAdd;
        thumbsFlipper       = holder.thumbsFlipper;
        thumbsDownAdd       = holder.thumbsDownAdd;
        totalLikesTV        = holder.totalLikes;


        Log.e("ADAPt",""+position);
        int FAV_FLAG = 0;



        for (int i = 0; i < myLikeIds.size();i++){
            if (saleIds.get(position).equals(myLikeIds.get(i))){
                thumbsDownAdd.setVisibility(View.VISIBLE);
                thumbsUpAdd.setVisibility(View.GONE);
                FAV_FLAG = 1;
                break;
            }

        }
        if (FAV_FLAG == 0){
            thumbsUpAdd.setVisibility(View.VISIBLE);
            thumbsDownAdd.setVisibility(View.GONE);
        }


        holder.imageLoaderForBanner.displayImage(saleBannerArray.get(position),holder.saleBanner);

        /* setting the company image*/
//        holder.saleBanner.setImageUrl((String) saleBannerArray.get(position), holder.imageLoaderForBanner);



        /*checking the banner and setting visibility accordingly*/
        if (attachedBanner.get(position).contains("banners")){

            holder.attachedeBannerVH.setVisibility(View.VISIBLE);
//            attachedBannerView.setImageUrl((String)         attachedBanner.get(position), holder.imageLoader);
            holder.imageLoader.displayImage(attachedBanner.get(position),holder.attachedBannerView);


            Log.e("sale_banner","Position of sale banner "+position + " "+ saleBannerArray.get(position));
            Log.e("sale_banner","Position attached banner "+position + " "+ attachedBanner.get(position));


        }else {
            holder.attachedeBannerVH.setVisibility(View.GONE);
        }



        setLikesSupport(holder,position);



            companyTitleB.setText(companyTitleArray.get(position));
            companyCity.setText(companyCityArray.get(position));
            saleTitle.setText(saleTitleArray.get(position));
            saleDiscription.setText(saleDiscriptionArray.get(position));
            saleDate.setText(saleDateArray.get(position));

            shareSaleB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String advert       = "Shared via 1clickAway, Find Best Jobs, Experts and Offers from your City and State. Click on the below link to Download Now\nhttps://play.google.com/store/apps/details?id=corp.burenz.expertouch";
                    String firstString  = "Hey i am sharing with you an advertisement from" +
                            " \n"+ companyTitleB.getText().toString() +" posted "+saleDateArray.get(position)+"" +
                            " where they mentioned "+saleTitleArray.get(position)+" \n "+saleDiscriptionArray.get(position) ;
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "" + firstString + "\n\n" + advert);
                    context.startActivity(Intent.createChooser(shareIntent, "Share via..."));

                }
            });

            companyTitleB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent companyProfileIntent= new Intent(context,CompanyProfile.class);
                    companyProfileIntent.putExtra("companyName",companyTitleB.getText().toString());
                    companyProfileIntent.putExtra("companyState",USER_STATE);
                    companyProfileIntent.putExtra("companyBanner",saleBannerArray.get(position));
                    context.startActivity(companyProfileIntent);
                }
            });




        MediaPlayer ourSplasSound =  MediaPlayer.create(context,R.raw.pop);

        final ViewFlipper otherFipper = thumbsFlipper;

        final boolean[] isDoubleClick = {false};


        holder.doubleClickArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isDoubleClick[0]){holder.awesomeHeartLikes.startAnimation(AnimationUtils.loadAnimation(context,R.anim.card_animation));

                    if(holder.thumbsDownAdd.getVisibility() != View.VISIBLE)
                    likeThePost(holder, position);

                    return;}
                isDoubleClick[0] = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isDoubleClick[0] = false;
                    }
                },200);

            }
        });



        thumbsUpAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               likeThePost(holder, position);

            }
        });

        thumbsDownAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                thumbsUpAdd.setVisibility(View.VISIBLE);
                thumbsDownAdd.setVisibility(View.GONE);

                newCount = Integer.parseInt(totalLikesTV.getText().toString());
                newCount--;


                if (newCount != 0){
                    totalLikesTV.startAnimation(AnimationUtils.loadAnimation(context,R.anim.card_animation));
                }

                totalLikesTV.setText(String.valueOf(newCount));
                if( newCount == 0 ){
                    holder.textViewSupportTV.setText("Be the first to like this post");
                    holder.totalLikes.setVisibility(View.GONE);

                }

                try {

                                myLikeIds   .remove(saleIds.get(position));
                                totalLikes  .set(position,totalLikesTV.getText().toString());
                                setLikesSupport(holder,position);

                }catch (Exception e){
                                e.printStackTrace();
                            }

                            Log.e("NEWCOUNT","Unliked new Count = " + myLikeIds.size());


                new SortLikes("unlike",position,holder,totalLikesTV.getText().toString()).execute("/bucket/unlike_mech.php",saleIds.get(position));



            }
        });





    }

    @Override
    public int getItemCount() {
        return companyTitleArray.size();
    }



     class MyBucketHolder extends RecyclerView.ViewHolder {

            ImageLoader imageLoader,imageLoaderForBanner;

            TextView            companyTitleB,companyCity,saleTitle,saleDiscription,saleDate;
            ImageView           saleBanner,attachedBannerView;
            LinearLayout        styleSheet, centralContainer, doubleClickArea ;
            RelativeLayout      attachedeBannerVH;
            ImageView           companyProfileB;
            IconicsImageView    shareSaleB;
            final private ViewFlipper thumbsFlipper;
            final private ImageButton thumbsUpAdd,thumbsDownAdd;
            ImageButton         awesomeHeartLikes;
            TextView            totalLikes, textViewSupportTV;

        public MyBucketHolder(View itemView) {
            super(itemView);

            companyTitleB       = (TextView)            itemView.findViewById(R.id.companyTitleB);
            companyCity         = (TextView)            itemView.findViewById(R.id.companyCityB);
            saleTitle           = (TextView)            itemView.findViewById(R.id.saleTitle);
            saleDate            = (TextView)            itemView.findViewById(R.id.saleDate);
            saleDiscription     = (TextView)            itemView.findViewById(R.id.saleDiscription);
            saleBanner          = (ImageView)           itemView.findViewById(R.id.saleBanner);
            companyProfileB     = (ImageView)           itemView.findViewById(R.id.companyProfileB);
            shareSaleB          = (IconicsImageView)    itemView.findViewById(R.id.shareSalesB);
            thumbsFlipper       = (ViewFlipper)         itemView.findViewById(R.id.thumbsFlipper);
            thumbsUpAdd         = (ImageButton)         itemView.findViewById(R.id.thumbsUpAdd);
            thumbsDownAdd       = (ImageButton)         itemView.findViewById(R.id.thumbsDownAdd);
            totalLikes          = (TextView)            itemView.findViewById(R.id.totalLikesTV);
            styleSheet          = (LinearLayout)        itemView.findViewById(R.id.styleSheet);
            attachedBannerView  = (ImageView)           itemView.findViewById(R.id.attachedBannerView);
            textViewSupportTV   = (TextView)            itemView.findViewById(R.id.likes_index_support_tv);
            centralContainer    = (LinearLayout)        itemView.findViewById(R.id.central_container_bucket_posts);
            awesomeHeartLikes   = (ImageButton)         itemView.findViewById(R.id.awesome_heart_on_double_click);
            doubleClickArea     = (LinearLayout)        itemView.findViewById(R.id.double_click_area);
            attachedeBannerVH   = (RelativeLayout)      itemView.findViewById(R.id.attachedBannerHolder);

            // Create global configuration and initialize ImageLoader with this config
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
            ImageLoader.getInstance().init(config);
            imageLoader          = ImageLoader.getInstance(); // Get singleton instance
            imageLoaderForBanner = ImageLoader.getInstance(); // Get singleton instance



        }
    }
    class SortLikes extends AsyncTask<String,String,String>{


        String          path;
        int             position;
        MyBucketHolder  myBucketHolder;
        String             likes;


        public SortLikes(String path, int position, MyBucketHolder myBucketHolder, String likes) {

            this.path           = path;
            this.position       = position;
            this.myBucketHolder = myBucketHolder;
            this.likes          = likes;



        }

        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairList = new ArrayList<>();


        @Override
        protected String doInBackground(String... params) {


            // params[0] is like or unlike params[1] is saleId
            nameValuePairList.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePairList.add(new BasicNameValuePair("salesId",params[1] ));
            Log.e("CATACOMB","userEmail "+USER_EMAIL);
            Log.e("CATACOMB","hit "+params[0]);
            Log.e("CATACOMB","salesId "+params[1]);
            try {


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(context.getString(R.string.host)+ params[0]);
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("NEWCOUNT","INSIDE ON POST");


            if (!s.contains("1")) {
                if (!s.contains("0")) {
                    int localLikes;
                    if (path.equals("like")){

                        try {

                                myLikeIds.remove(saleIds.get(position));
                                totalLikes.set(position,String.valueOf(Integer.parseInt(likes) - 1));

                                myBucketHolder.thumbsUpAdd     .setVisibility(View.VISIBLE);
                                myBucketHolder.thumbsDownAdd   .setVisibility(View.GONE);
                                newCount        = Integer.parseInt(myBucketHolder.totalLikes.getText().toString());
                                newCount--;

                                myBucketHolder.totalLikes.startAnimation(AnimationUtils.loadAnimation(context,R.anim.card_animation));
                                myBucketHolder.totalLikes.setText(String.valueOf(newCount));

                                setLikesSupport(myBucketHolder,position);





                        }catch (Exception e){
                            e.printStackTrace();
                        }



                    }else if (path.equals("unlike")) {

                        try {
                            myLikeIds.add(saleIds.get(position));
                            totalLikes.set(position, String.valueOf(Integer.parseInt(likes) + 1));

                            Log.e("NEWCOUNT", "UNLIKE FAILED saleID" + saleIds.get(position));




                            myBucketHolder.thumbsUpAdd.setVisibility(View.GONE);
                            myBucketHolder.thumbsDownAdd.setVisibility(View.VISIBLE);
                            newCount = Integer.parseInt(myBucketHolder.totalLikes.getText().toString());
                            newCount++;
                            myBucketHolder.totalLikes.startAnimation(AnimationUtils.loadAnimation(context,R.anim.card_animation));
                            myBucketHolder.totalLikes.setText(String.valueOf(newCount));

                            setLikesSupport(myBucketHolder,position);





                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    Toast.makeText(context,"Connection Slow Please Try Again",Toast.LENGTH_LONG).show();
                    }
            }


        }



    }


}



