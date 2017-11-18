package corp.burenz.expertouch.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

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
import corp.burenz.expertouch.util.MySingleton;

/**
 * Created by xperTouch on 10/11/2016.
 */
public class BucketAdapter extends RecyclerView.Adapter<BucketAdapter.MyBucketHolder> {




    ArrayList<String> companyTitleArray,companyCityArray,saleTitleArray,saleDiscriptionArray,saleDateArray,saleBannerArray,saleIds,myLikeIds,totalLikes,attachedBanner;
    Context context;
    Integer newCount;
    String RESULT = "free",USER_EMAIL = "noemail@example.com";
    String USER_STATE;
    int TIME_OUT = 4000;
    int ADAPTER_POSITION  = 0;
    SharedPreferences userData;
    String LOCAL_APP_DATA = "userInformation";
    String LIKES;
    String PATH;
    public BucketAdapter(Context context,ArrayList<String> saleIds,ArrayList<String> companyTitleB,ArrayList<String> companyCity,ArrayList<String> saleTitle,ArrayList<String> saleDiscription,ArrayList<String> saleDate,ArrayList<String> saleBanner,ArrayList<String> myLikeIds,ArrayList<String> totalLikes,ArrayList<String> attachedBanner){
        this.context = context;
        this.saleIds = saleIds;
        this.companyTitleArray = companyTitleB;
        this.companyCityArray = companyCity;
        this.saleTitleArray = saleTitle;
        this.saleDateArray = saleDate;
        this.saleDiscriptionArray = saleDiscription;
        this.saleBannerArray = saleBanner;
        this.myLikeIds = myLikeIds;
        this.totalLikes = totalLikes;
        this.attachedBanner = attachedBanner;
    }


    @Override
    public MyBucketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bucket_adapter,parent,false);
        SharedPreferences userData;
        String LOCAL_APP_DATA = "userInformation";
        userData = context.getSharedPreferences(LOCAL_APP_DATA,0);
        USER_EMAIL = userData.getString("userEmail","noemail@example.com");
        USER_STATE = userData.getString("userState","Jammu and Kashmir");
        return new MyBucketHolder(v);

    }


    @Override
    public void onBindViewHolder(MyBucketHolder oriHolder, final int position) {

        final MyBucketHolder holder = (MyBucketHolder) oriHolder;
        ViewFlipper thumbsFlipper;
        final ImageButton thumbsUpAdd,thumbsDownAdd;

         final TextView companyTitleB;
         TextView companyCity;
         TextView saleTitle;
        final TextView totalLikesTV;
        final TextView saleDiscription;
        final TextView saleDate;
        final NetworkImageView saleBanner,attachedBannerView;
        final ImageView companyProfileB;
        final Button shareSaleB;
        LinearLayout styleSheet;


        companyTitleB = holder.companyTitleB;
        companyCity = holder.companyCity;
        saleTitle = holder.saleTitle;
        saleDiscription = holder.saleDiscription;
        saleDate = holder.saleDate;
        saleBanner = holder.saleBanner;
        companyProfileB = holder.companyProfileB;
        shareSaleB = holder.shareSaleB;
        attachedBannerView = holder.attachedBannerView;
        styleSheet = holder.styleSheet;

        thumbsUpAdd = holder.thumbsUpAdd;
        thumbsFlipper = holder.thumbsFlipper;
        thumbsDownAdd = holder.thumbsDownAdd;
        totalLikesTV = holder.totalLikes;




        totalLikesTV.setText(totalLikes.get(position));

        ImageLoader imageLoader = MySingleton.getInstance(context).getImageLoader();
        Log.e("ADAPt",""+position);
        int FAV_FLAG = 0;

      //  styleSheet.setBackgroundColor(0xFF000000+new Random().nextInt(0xFFFFFF));

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
        if (attachedBanner.get(position).contains("banners")){
            attachedBannerView.setImageUrl((String) attachedBanner.get(position), imageLoader);
            attachedBannerView.setVisibility(View.VISIBLE);
        }else {
            attachedBannerView.setVisibility(View.GONE);
        }
        saleBanner.setImageUrl((String) saleBannerArray.get(position), imageLoader);

            companyTitleB.setText(companyTitleArray.get(position));
            companyCity.setText(companyCityArray.get(position));
            saleTitle.setText(saleTitleArray.get(position));
            saleDiscription.setText(saleDiscriptionArray.get(position));
            saleDate.setText(saleDateArray.get(position));

            shareSaleB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String advert = "Shared via 1clickAway, Find Best Jobs, Experts and Offers from your City and State. Click on the below link to Download Now\nhttps://play.google.com/store/apps/details?id=corp.burenz.expertouch";
                    String firstString = "Hey i am sharing with you an advertisement from" +
                            " \n"+ companyTitleB.getText().toString() +" posted on "+saleDateArray.get(position).toString()+"" +
                            " where they mentioned "+saleTitleArray.get(position).toString()+" \n "+saleDiscriptionArray.get(position).toString() ;
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
                    companyProfileIntent.putExtra("companyBanner",saleBannerArray.get(position).toString());
                    context.startActivity(companyProfileIntent);
                }
            });




        final MediaPlayer ourSplasSound =  MediaPlayer.create(context,R.raw.pop);

        final ViewFlipper otherFipper = thumbsFlipper;

        thumbsUpAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PATH = "like";
                ADAPTER_POSITION = holder.getAdapterPosition();

                thumbsUpAdd.setVisibility(View.GONE);
                thumbsDownAdd.setVisibility(View.VISIBLE);

                ourSplasSound.start();
                newCount = Integer.parseInt(totalLikesTV.getText().toString());
                newCount++;
                totalLikesTV.startAnimation(AnimationUtils.loadAnimation(context,R.anim.card_animation));
                totalLikesTV.setText(String.valueOf(newCount));
                thumbsDownAdd.setEnabled(false);


                        try {
                            Log.e("NEWCOUNT", saleIds.get(position));
                            myLikeIds.add(saleIds.get(position));
                            totalLikes.remove(position);
                            totalLikes.add(position,totalLikesTV.getText().toString());
                            LIKES = totalLikesTV.getText().toString();
                            Log.e("NEWCOUNT","liked new Count = " + myLikeIds.size());

                    }catch (Exception e){
                    e.printStackTrace();
                            Log.e("NEWCOUNT","UNFORTUNATE SLAP ");

                        }




                thumbsDownAdd.setEnabled(false);

                new SortLikes().execute("/bucket/like_mech.php",saleIds.get(position));


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (RESULT.contains("failed")){

                            thumbsUpAdd.setVisibility(View.VISIBLE);
                            thumbsDownAdd.setVisibility(View.GONE);
                            newCount = Integer.parseInt(totalLikesTV.getText().toString());
                            newCount--;
                            totalLikesTV.startAnimation(AnimationUtils.loadAnimation(context,R.anim.card_animation));
                            totalLikesTV.setText(String.valueOf(newCount));
                        }
                        thumbsDownAdd.setEnabled(true);

                    }
                },TIME_OUT);


















            }
        });

        thumbsDownAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                thumbsUpAdd.setVisibility(View.VISIBLE);
                thumbsDownAdd.setVisibility(View.GONE);
                newCount = Integer.parseInt(totalLikesTV.getText().toString());
                newCount--;
                totalLikesTV.startAnimation(AnimationUtils.loadAnimation(context,R.anim.card_animation));
                totalLikesTV.setText(String.valueOf(newCount));

                thumbsUpAdd.setEnabled(false);


                PATH = "unlike";
                ADAPTER_POSITION = holder.getAdapterPosition();

                            try {
                                myLikeIds.remove(saleIds.get(position));
                                totalLikes.remove(position);
                                totalLikes.add(position,totalLikesTV.getText().toString());
                                LIKES = totalLikesTV.getText().toString();
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            Log.e("NEWCOUNT","Unliked new Count = " + myLikeIds.size());

                new SortLikes().execute("/bucket/unlike_mech.php",saleIds.get(position));



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (RESULT.contains("failed")){

                            thumbsUpAdd.setVisibility(View.GONE);
                            thumbsDownAdd.setVisibility(View.VISIBLE);
                            newCount = Integer.parseInt(totalLikesTV.getText().toString());
                            newCount++;
                            totalLikesTV.startAnimation(AnimationUtils.loadAnimation(context,R.anim.card_animation));
                            totalLikesTV.setText(String.valueOf(newCount));

                        }
                        thumbsUpAdd.setEnabled(true);

                    }
                },TIME_OUT);






            }
        });





    }

    @Override
    public int getItemCount() {
        return companyTitleArray.size();
    }



     static class MyBucketHolder extends RecyclerView.ViewHolder {

        TextView companyTitleB,companyCity,saleTitle,saleDiscription,saleDate;
        NetworkImageView saleBanner,attachedBannerView;
        LinearLayout styleSheet;
        ImageView companyProfileB;
         Button shareSaleB;
        final private ViewFlipper thumbsFlipper;
        final private ImageButton thumbsUpAdd,thumbsDownAdd;
        TextView totalLikes;

        public MyBucketHolder(View itemView) {
            super(itemView);
            companyTitleB = (TextView) itemView.findViewById(R.id.companyTitleB);
            companyCity  = (TextView) itemView.findViewById(R.id.companyCityB);
            saleTitle = (TextView) itemView.findViewById(R.id.saleTitle);
            saleDate = (TextView) itemView.findViewById(R.id.saleDate);
            saleDiscription  = (TextView) itemView.findViewById(R.id.saleDiscription);
            saleBanner = (NetworkImageView) itemView.findViewById(R.id.saleBanner);
            companyProfileB = (ImageView) itemView.findViewById(R.id.companyProfileB);
            shareSaleB = (Button) itemView.findViewById(R.id.shareSalesB);
            thumbsFlipper = (ViewFlipper) itemView.findViewById(R.id.thumbsFlipper);
            thumbsUpAdd = (ImageButton) itemView.findViewById(R.id.thumbsUpAdd);
            thumbsDownAdd = (ImageButton) itemView.findViewById(R.id.thumbsDownAdd);
            totalLikes = (TextView) itemView.findViewById(R.id.totalLikesTV);
            styleSheet = (LinearLayout) itemView.findViewById(R.id.styleSheet);
            attachedBannerView = (NetworkImageView) itemView.findViewById(R.id.attachedBannerView);
        }
    }

    class SortLikes extends AsyncTask<String,String,String>{

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
        protected void onPreExecute() {
            super.onPreExecute();
            RESULT = "busy";


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("NEWCOUNT","INSIDE ON POST");


            if (s.contains("1")){
                RESULT = "1";

            }else if (s.contains("0")){






            } else{
                RESULT = "failed";



                int localLikes;

                if (PATH.equals("like")){
                    try {

                        localLikes = Integer.parseInt(LIKES);
                        localLikes--;
                        myLikeIds.remove(saleIds.get(ADAPTER_POSITION));
                        totalLikes.remove(ADAPTER_POSITION);
                        totalLikes.add(ADAPTER_POSITION,String.valueOf(localLikes));
                        Log.e("NEWCOUNT","LIKE FAILED saleID" +saleIds.get(ADAPTER_POSITION));

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else if (PATH.equals("unlike")) {

                    try {
                        localLikes = Integer.parseInt(LIKES);
                        localLikes++;

                        myLikeIds.add(saleIds.get(ADAPTER_POSITION));
                        totalLikes.remove(ADAPTER_POSITION);
                        totalLikes.add(ADAPTER_POSITION, String.valueOf(localLikes));
                        Log.e("NEWCOUNT", "UNLIKE FAILED saleID" + saleIds.get(ADAPTER_POSITION));


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(context,"Connection Slow Please Try Again",Toast.LENGTH_LONG).show();





                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                    }
                },TIME_OUT);

                }


        }



    }


}


