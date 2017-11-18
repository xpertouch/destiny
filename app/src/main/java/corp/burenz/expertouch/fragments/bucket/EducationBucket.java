package corp.burenz.expertouch.fragments.bucket;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.BucketAdapter;

/**
 * Created by xperTouch on 10/13/2016.
 */

public class EducationBucket extends Fragment {

    ArrayList<String> companyTitles;
    ArrayList<String> saleDate;
    ArrayList<String> saleTitle;
    ArrayList<String> companyCity;
    ArrayList<String> saleDiscription;
    ArrayList<String> saleBanner;
    ArrayList<String> saleID;
    ArrayList<String> myLikeIds;
    ArrayList<String> totalLikes;
    ArrayList<String> attachedBanner;
    SharedPreferences userData;

    String LOCAL_APP_DATA = "userInformation";

    LinearLayout noAdverts;
    RelativeLayout loadProgress;

    RecyclerView myBucketRV;
    RecyclerView.Adapter adapter;
    String userState;
    String USER_EMAIL;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v  = inflater.inflate(R.layout.education_bucket,container,false);

        myBucketRV = (RecyclerView) v.findViewById(R.id.educationBucketRV);
        myBucketRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        myBucketRV.clearOnScrollListeners();
        myBucketRV.hasFixedSize();

        loadProgress = (RelativeLayout) v.findViewById(R.id.loadProgressEducation);
        noAdverts  =  (LinearLayout) v.findViewById(R.id.noAdvertEducation);

        userData = getActivity().getSharedPreferences(LOCAL_APP_DATA,0);
        userState  = userData.getString("userState","Jammu and Kashmir");
        USER_EMAIL = userData.getString("userEmail","noEmail@example.com");
        new GetLikes().execute();





        return v;




    }


    private class GetLikes extends AsyncTask<String,String,String>{

        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;

        JSONObject jsonObject;
        JSONArray jsonArray;
        List<NameValuePair> basicNameValuePair = new ArrayList<>();


        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            myLikeIds = new ArrayList<String>();
            loadProgress.setVisibility(View.VISIBLE);
            myBucketRV.setVisibility(View.GONE);


        }

        @Override
        protected String doInBackground(String... params){


            basicNameValuePair.add(new BasicNameValuePair("userPhone",USER_EMAIL));

            try{

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/bucket/get_likes.php");
                httpPost.setEntity(new UrlEncodedFormEntity(basicNameValuePair));
                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);
                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();


                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));

                String str = "";
                while((str = bufferedReader.readLine())!= null){
                    builder.append(str);
                }


                jsonObject = new JSONObject();
                jsonArray = new JSONArray(builder.toString());


                int len = jsonArray.length();

                for (int i = 0; i < len; i ++){

                    jsonObject = jsonArray.getJSONObject(i);
                    myLikeIds.add(jsonObject.getString("ids"));

                }






            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){

            }


            return builder.toString();
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);


            new BucketLoader(getActivity(),myBucketRV).execute();

        }










    }

    public class BucketLoader extends AsyncTask<String, String, String> {


        Context context;
        JSONObject jsonObject;
        JSONArray jsonArray;


        StringBuilder line = new StringBuilder();
        BufferedReader bufferedReader;

        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;

        List<NameValuePair> nameValuePairs = new ArrayList<>();

        public BucketLoader(Context context, RecyclerView recyclerView) {
            this.context = context;
            this.recyclerView = recyclerView;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            companyTitles = new ArrayList<>();
            saleDate = new ArrayList<>();
            saleTitle = new ArrayList<>();
            companyCity = new ArrayList<>();
            saleDiscription = new ArrayList<>();
            saleBanner = new ArrayList<>();
            saleID  =new ArrayList<>();
            totalLikes  = new ArrayList<>();
            attachedBanner = new ArrayList<>();

        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("type","education"));
            nameValuePairs.add(new BasicNameValuePair("state",userState));




            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/bucket/get_sales.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";


                while ((str = bufferedReader.readLine()) != null) {
                    line.append(str);
                }


                jsonObject = new JSONObject();
                jsonArray = new JSONArray(line.toString());

                int length = jsonArray.length();


                for (int i = 0; i < length; i++) {

                    jsonObject = jsonArray.getJSONObject(i);

                    saleID.add(jsonObject.getString("saleID"));
                    companyTitles.add(jsonObject.getString("companyTitle"));
                    saleDate.add(jsonObject.getString("saleDate"));
                    saleTitle.add(jsonObject.getString("saleTitle"));
                    companyCity.add(jsonObject.getString("companyCity"));
                    saleDiscription.add(jsonObject.getString("saleDiscription"));
                    saleBanner.add(jsonObject.getString("companyBanner"));
                    totalLikes.add(jsonObject.getString("totalLikes"));
                    attachedBanner.add(jsonObject.getString("attachedBanner"));
                }

            } catch (HttpHostConnectException e) {
                //      Toast.makeText(context, "Error While connecting to the server", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                //     Toast.makeText(context, "Something Went Wrong"+e.toString(), Toast.LENGTH_SHORT).show();

            }
            return line.toString();
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



//            saleID.add("50");
//            companyTitles.add("Burenz Corporation");
//            saleDate.add("26 November 2016");
//            saleTitle.add("Welcome To Burenz Corp");
//            companyCity.add("Anantnag Jammu and Kashmir");
//            saleDiscription.add("Well fellows this is a company you would love to Join");
//            saleBanner.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
//            totalLikes.add("120");
//
//
//            saleID.add("51");
//            companyTitles.add("Burenz Corporation");
//            saleDate.add("26 November 2016");
//            saleTitle.add("Welcomw To Burenz Corp");
//            companyCity.add("Anantnag Jammu and Kashmir");
//            saleDiscription.add("Well fellows this is a company you would love to Join");
//            saleBanner.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
//            totalLikes.add("130");
//
//
//            saleID.add("52");
//            companyTitles.add("Burenz Corporation");
//            saleDate.add("26 November 2016");
//            saleTitle.add("Welcomw To Burenz Corp");
//            companyCity.add("Anantnag Jammu and Kashmir");
//            saleDiscription.add("Well fellows this is a company you would love to Join");
//            saleBanner.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
//            totalLikes.add("240");
//
//
//            saleID.add("53");
//            companyTitles.add("Burenz Corporation");
//            saleDate.add("26 November 2016");
//            saleTitle.add("Welcomw To Burenz Corp");
//            companyCity.add("Anantnag Jammu and Kashmir");
//            saleDiscription.add("Well fellows this is a company you would love to Join");
//            saleBanner.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
//            totalLikes.add("333");
//
//
//            saleID.add("54");
//            companyTitles.add("Burenz Corporation");
//            saleDate.add("26 November 2016");
//            saleTitle.add("Welcomw To Burenz Corp");
//            companyCity.add("Anantnag Jammu and Kashmir");
//            saleDiscription.add("Well fellows this is a company you would love to Join");
//            saleBanner.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
//            totalLikes.add("6");
//
//
//
//            saleID.add("55");
//            companyTitles.add("Burenz Corporation");
//            saleDate.add("26 November 2016");
//            saleTitle.add("Welcome To Burenz Corp");
//            companyCity.add("Anantnag Jammu and Kashmir");
//            saleDiscription.add("Well fellows this is a company you would love to Join");
//            saleBanner.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
//            totalLikes.add("98");
//
//
//            saleID.add("56");
//            companyTitles.add("Burenz Corporation");
//            saleDate.add("26 November 2016");
//            saleTitle.add("Welcomw To Burenz Corp");
//            companyCity.add("Anantnag Jammu and Kashmir");
//            saleDiscription.add("Well fellows this is a company you would love to Join");
//            saleBanner.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
//            totalLikes.add("1234");
//
//
//            saleID.add("57");
//            companyTitles.add("Burenz Corporation");
//            saleDate.add("26 November 2016");
//            saleTitle.add("Welcomw To Burenz Corp");
//            companyCity.add("Anantnag Jammu and Kashmir");
//            saleDiscription.add("Well fellows this is a company you would love to Join");
//            saleBanner.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
//            totalLikes.add("45");
//
//
//            saleID.add("58");
//            companyTitles.add("Burenz Corporation");
//            saleDate.add("26 November 2016");
//            saleTitle.add("Welcomw To Burenz Corp");
//            companyCity.add("Anantnag Jammu and Kashmir");
//            saleDiscription.add("Well fellows this is a company you would love to Join");
//            saleBanner.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
//            totalLikes.add("22");
//
//
//            saleID.add("59");
//            companyTitles.add("Burenz Corporation");
//            saleDate.add("26 November 2016");
//            saleTitle.add("Welcomw To Burenz Corp");
//            companyCity.add("Anantnag Jammu and Kashmir");
//            saleDiscription.add("Well fellows this is a company you would love to Join");
//            saleBanner.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
//            totalLikes.add("56");
//
//            saleID.add("60");
//            companyTitles.add("Burenz Corporation");
//            saleDate.add("26 November 2016");
//            saleTitle.add("Welcome To Burenz Corp");
//            companyCity.add("Anantnag Jammu and Kashmir");
//            saleDiscription.add("Well fellows this is a company you would love to Join");
//            saleBanner.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
//            totalLikes.add("98");
//
//
//            saleID.add("61");
//            companyTitles.add("Burenz Corporation");
//            saleDate.add("26 November 2016");
//            saleTitle.add("Welcomw To Burenz Corp");
//            companyCity.add("Anantnag Jammu and Kashmir");
//            saleDiscription.add("Well fellows this is a company you would love to Join");
//            saleBanner.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
//            totalLikes.add("75");
//
//
//            saleID.add("62");
//            companyTitles.add("Burenz Corporation");
//            saleDate.add("26 November 2016");
//            saleTitle.add("Welcomw To Burenz Corp");
//            companyCity.add("Anantnag Jammu and Kashmir");
//            saleDiscription.add("Well fellows this is a company you would love to Join");
//            saleBanner.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
//            totalLikes.add("45");
//
//
//            saleID.add("63");
//            companyTitles.add("Burenz Corporation");
//            saleDate.add("26 November 2016");
//            saleTitle.add("Welcomw To Burenz Corp");
//            companyCity.add("Anantnag Jammu and Kashmir");
//            saleDiscription.add("Well fellows this is a company you would love to Join");
//            saleBanner.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
//            totalLikes.add("22");
//
//
//            saleID.add("64");
//            companyTitles.add("Burenz Corporation");
//            saleDate.add("26 November 2016");
//            saleTitle.add("Welcomw To Burenz Corp");
//            companyCity.add("Anantnag Jammu and Kashmir");
//            saleDiscription.add("Well fellows this is a company you would love to Join");
//            saleBanner.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
//            totalLikes.add("56");
//


            if (companyTitles.size() == 0) {
                myBucketRV.setVisibility(View.GONE);
                noAdverts.setVisibility(View.VISIBLE);
                loadProgress.setVisibility(View.GONE);

            } else {
                myBucketRV.setVisibility(View.VISIBLE);
                noAdverts.setVisibility(View.GONE);
                loadProgress.setVisibility(View.GONE);

                adapter = new BucketAdapter(getActivity(),saleID ,companyTitles, companyCity, saleTitle, saleDiscription, saleDate, saleBanner,myLikeIds,totalLikes,attachedBanner);
                adapter.notifyDataSetChanged();
                myBucketRV.setAdapter(new RecyclerViewMaterialAdapter(adapter));
                MaterialViewPagerHelper.registerRecyclerView(getActivity(),myBucketRV);
            }


        }



    }



}
