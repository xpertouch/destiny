package corp.burenz.expertouch.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.butter.GuestInformation;
import corp.burenz.expertouch.util.ChannelHolder;

/**
 * Created by buren on 30/1/18.
 */

public class ChannelAdaper extends RecyclerView.Adapter<ChannelAdaper.ChannelAdaperViewHolder> {

    private Context                 mContext;
    private ChannelHolder           channelHolderList;
    ChannelAdaperViewHolder         holder;

    public ChannelAdaper(Context mContext, ChannelHolder channelHolderlist){
        this.channelHolderList  = channelHolderlist;
        this.mContext           = mContext;
    }

    @Override
    public ChannelAdaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ChannelAdaperViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(final ChannelAdaperViewHolder holder, final int position) {

//        holder.companyTitleTV.setText();
            this.holder = holder;

            holder.companyTitleTV.setText(channelHolderList.getCompanyTitle().get(position));
            holder.companyLocationTV.setText(channelHolderList.getCompanyAddress().get(position));
            holder.companyAboutTV.setText(channelHolderList.getCompanyAbout().get(position));

            if(channelHolderList.getIsSubscribedByMe().get(position).equals("0")){
                holder.subsscribeNow.setVisibility(View.VISIBLE);
                holder.unSubscribeNow.setVisibility(View.GONE);

            }else{

                holder.subsscribeNow.setVisibility(View.GONE);
                holder.unSubscribeNow.setVisibility(View.VISIBLE);


            }


            holder.subsscribeNow.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){


                    /*subscribe now  pass subscribe in string s[0]*/

                    new SubscriptionUtils(position,holder,1,channelHolderList.getCompanyTitle().get(holder.getAdapterPosition())).execute("subscribe",channelHolderList.getCompanyTitle().get(holder.getAdapterPosition()));

                }



            });


        holder.unSubscribeNow.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                    /*subscribe now  pass unsubscribe in string s[0]*/
                    new SubscriptionUtils(position,holder,0,channelHolderList.getCompanyTitle().get(holder.getAdapterPosition())).execute("unsubscribe",channelHolderList.getCompanyTitle().get(holder.getAdapterPosition()));

            }



        });





    }

    @Override
    public int getItemCount() {
        return channelHolderList.getCompanyAbout().size();
    }

    static  class ChannelAdaperViewHolder   extends RecyclerView.ViewHolder {

      /* NetworkImageView companyBannerView;
      */
        TextView    companyTitleTV, companyLocationTV, companyAboutTV;
        Button      subsscribeNow, unSubscribeNow;





        ChannelAdaperViewHolder(View v) {
            super(v);

            companyTitleTV          =   (TextView) v.findViewById(R.id.companyTitleTVC);
            companyLocationTV       =   (TextView) v.findViewById(R.id.companyLocationTVC);
            companyAboutTV          =   (TextView) v.findViewById(R.id.companyAboutTVC);
            subsscribeNow           =   (Button)   v.findViewById(R.id.subsCribeChannelBtnC);
            unSubscribeNow          =   (Button)   v.findViewById(R.id.unsubscribefromChannelButton);
        }
    }

    private class SubscriptionUtils         extends AsyncTask<String, String, String> {

        private int FLAG;
        private String channelName;
        private ChannelAdaperViewHolder channelAdaperViewHolder;
        private int position;

        SubscriptionUtils(int position, ChannelAdaperViewHolder channelAdaperViewHolder, int FLAG, String channelName) {
            this.FLAG                       = FLAG;
            this.channelName                = channelName;
            this.channelAdaperViewHolder    = channelAdaperViewHolder;
            this.position                   = position;
        }

        @Override
        protected void onPreExecute()  {
            super.onPreExecute();

//            holder.subsscribeNow.setVisibility(View.GONE);
//            holder.unSubscribeNow.setVisibility(View.VISIBILE);


            if (FLAG == 1){

                channelAdaperViewHolder.subsscribeNow.setVisibility(View.GONE);
                channelAdaperViewHolder.unSubscribeNow.setVisibility(View.VISIBLE);


            }else {

                channelAdaperViewHolder.subsscribeNow.setVisibility(View.VISIBLE);
                channelAdaperViewHolder.unSubscribeNow.setVisibility(View.GONE);
            }


            /*set the subscriber button to subscribed*/



        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            switch(s.trim()){

                case "success":
                        /*Dont do anything*/

                        /*subscribe to firebase and register a class here*/
                        if (FLAG == 0){
                            try{ FirebaseMessaging.getInstance().subscribeToTopic(channelName.replace(" ","").toLowerCase()); }catch (Exception e){e.printStackTrace();}
                        }else {
                            try{ FirebaseMessaging.getInstance().unsubscribeFromTopic(channelName.replace(" ","").toLowerCase()); }catch (Exception e){e.printStackTrace();}
                        }
                        Log.e("fire",channelName.replace(" ","").toLowerCase());
                        setIsSubscribedByMe(channelHolderList.getIsSubscribedByMe(),position,String.valueOf(FLAG));


                    break;


                case "failure":
                        /* toast poor network
                        * reverse teh process pof turning the button from subscribed to subscribe*/


                        Toast.makeText(mContext, "Connection Slow please try again later", Toast.LENGTH_SHORT).show();

                        if (FLAG == 1){
                            channelAdaperViewHolder.subsscribeNow.setVisibility(View.VISIBLE);
                            channelAdaperViewHolder.unSubscribeNow.setVisibility(View.GONE);
                        }else {
                            channelAdaperViewHolder.subsscribeNow.setVisibility(View.GONE);
                            channelAdaperViewHolder.unSubscribeNow.setVisibility(View.VISIBLE);
                          }



                    break;

                case "exists":
                        /*dont do anything*/
                    break;

            }





        }


        void setIsSubscribedByMe(ArrayList<String> isSubscribedByMe, int position, String updateValue) {
            isSubscribedByMe.set(position,updateValue);

        }

        @Override
        protected String doInBackground(String... strings) {

          /*  strings[0]  = whether subscribe or unsubscribe
            strings[1]  = companys id      */

            strings[0] = "sub";
            strings[1] = "channel_id";

            String          urlToHit            =   "";
            StringBuilder   stringBuilder       =   new StringBuilder();


            HttpURLConnection httpURLConnection ;
            URL url;


            try {


                switch (strings[0]){

                    case "sub":
                        url = new URL(urlToHit + "?functionname=" + URLEncoder.encode(strings[0], "UTF-8") + "&user_phone="+ URLEncoder.encode(new GuestInformation(mContext).getGuestNumber(), "UTF-8") +"&channel_id=" + URLEncoder.encode(strings[1],"UTF-8") );
                        break;

                    case "unsub":
                        url = new URL(urlToHit + "?functionname=" + URLEncoder.encode(strings[0], "UTF-8") + "&user_phone="+ URLEncoder.encode(new GuestInformation(mContext).getGuestNumber(), "UTF-8") +"&channel_id=" + URLEncoder.encode(strings[1],"UTF-8") );
                        break;

                    default:
                        url = new URL(urlToHit + "?functionname=" + URLEncoder.encode("sub", "UTF-8") + "&user_phone="+ URLEncoder.encode(strings[1], "UTF-8") +"&channel_id=" + URLEncoder.encode(strings[2],"UTF-8") );

                }






                httpURLConnection =  (HttpURLConnection)  url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";

                while(  (line  =    bufferedReader.readLine()) != null   ){
                    stringBuilder.append(line).append("\n");
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return stringBuilder.toString();


        }

    }




}
