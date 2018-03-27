package corp.burenz.expertouch.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.LinksLayoutAdapter;
import corp.burenz.expertouch.util.MySingleton;

/**
 * Created by buren on 11/2/18.
 */

public class JobPostDetails  extends AppCompatActivity{

    ImageButton         finishJobPostIB;
    Bundle              fromJobsActivity;
    TextView            postDetailsCompanyTitleTV,posDetailsPostDetailsTV,postDetailsTextView;
    ProgressBar         postDetailsProgress;
    NetworkImageView    companyBannerImage;
    String              informationToSearch;
    String              completeJobInformationFromInternet;
    RecyclerView        listOfUrlsRV;
    TextView            noLinksIfNot, jobInTheDescriptionTV;

    String              companyTitleFromServer, postDate, companyBannerURL;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_post_details);




        inflateViews();


        finishJobPostIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobPostDetails.this.finish();
            }
        });


        if (completeJobInformationFromInternet == null){ return;}

    }

    void inflateViews(){

        postDetailsProgress             = (ProgressBar )        findViewById(R.id.postDetailsProgress);
        finishJobPostIB                 = (ImageButton)         findViewById(R.id.finishJobPost);
        postDetailsCompanyTitleTV       = (TextView)            findViewById(R.id.postDetailsCompanyTitleTV);
        posDetailsPostDetailsTV         = (TextView)            findViewById(R.id.posDetailsPostDetailsTV);
        postDetailsTextView             = (TextView)            findViewById(R.id.postDetailsTextView);
        companyBannerImage              = (NetworkImageView)    findViewById(R.id.companyBannerImage);
        noLinksIfNot                    = (TextView)            findViewById(R.id.noLinksIfNot);
        listOfUrlsRV                    = (RecyclerView)        findViewById(R.id.listOfUrlsRV);
        jobInTheDescriptionTV           = (TextView)            findViewById(R.id.jobsInTheDescriptionTV);


        fromJobsActivity        = getIntent().getExtras();

        if (fromJobsActivity != null){

            new GetJobInformation(JobPostDetails.this).execute(fromJobsActivity.getString("postId","none"));


        }










    }


    ArrayList<String> retrieveLinks(String text) {
        ArrayList<String> links = new ArrayList<>();

        String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        while(m.find()) {
            String urlStr = m.group();
            char[] stringArray1 = urlStr.toCharArray();

            if (urlStr.startsWith("(") && urlStr.endsWith(")"))
            {

                char[] stringArray = urlStr.toCharArray();

                char[] newArray = new char[stringArray.length-2];
                System.arraycopy(stringArray, 1, newArray, 0, stringArray.length-2);
                urlStr = new String(newArray);
                System.out.println("Finally Url ="+newArray.toString());

            }
            System.out.println("...Url..."+urlStr);
            links.add(urlStr);
        }
        return links;
    }


    private class GetJobInformation extends AsyncTask < String, String, String >{

        private Context mContext;

        GetJobInformation(Context mContext) {
            this.mContext = mContext;
        }
        ArrayList<String> retrieveLinks(String text) {
            ArrayList<String> links = new ArrayList<>();

            String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(text);
            while(m.find()) {
                String urlStr = m.group();
                char[] stringArray1 = urlStr.toCharArray();

                if (urlStr.startsWith("(") && urlStr.endsWith(")"))
                {

                    char[] stringArray = urlStr.toCharArray();

                    char[] newArray = new char[stringArray.length-2];
                    System.arraycopy(stringArray, 1, newArray, 0, stringArray.length-2);
                    urlStr = new String(newArray);
                    System.out.println("Finally Url ="+newArray.toString());

                }
                System.out.println("...Url..."+urlStr);
                links.add(urlStr);
            }
            return links;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            postDetailsProgress.setVisibility(View.VISIBLE);
            postDetailsTextView.setVisibility(View.GONE);
            jobInTheDescriptionTV.setVisibility(View.GONE);



        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            jobInTheDescriptionTV.setVisibility(View.VISIBLE);

            Log.e("post_new","from the serevr  = " +s);
            postDetailsProgress.setVisibility(View.GONE);

            /*for testing*/

            if (completeJobInformationFromInternet != null){
                informationToSearch = completeJobInformationFromInternet;
                postDetailsTextView.setText(completeJobInformationFromInternet);

                postDetailsCompanyTitleTV.setText(companyTitleFromServer);
                posDetailsPostDetailsTV.setText(postDate);
                companyBannerImage.setImageUrl(companyBannerURL,MySingleton.getInstance(mContext).getImageLoader());


                postDetailsTextView.setVisibility(View.VISIBLE);
                jobInTheDescriptionTV.setVisibility(View.VISIBLE);



                ArrayList<String> listOfUrls = retrieveLinks(completeJobInformationFromInternet);
                if(listOfUrls.size() == 0){
                    /* no links inside the text*/
                    noLinksIfNot.setVisibility(View.VISIBLE);
                    noLinksIfNot.setText("No Links detected in the description...");

                }else {
                    noLinksIfNot.setVisibility(View.GONE);
                    LinksLayoutAdapter linksLayoutAdapter = new LinksLayoutAdapter(listOfUrls, JobPostDetails.this);
                    listOfUrlsRV.setLayoutManager(new LinearLayoutManager(JobPostDetails.this));
                    listOfUrlsRV.setAdapter(linksLayoutAdapter);

                }


            }else{
                Toast.makeText(JobPostDetails.this, "We are having trouble connecting to the internet, Please make sure your have an active internet connection", Toast.LENGTH_SHORT).show();
                JobPostDetails.this.finish();
            }


        }

        @Override
        protected String doInBackground(String... strings) {


            String urlTohit                 = getString(R.string.host) + "/workshop/post_details_with_id.php";
            StringBuilder stringBuilder     = new StringBuilder();

            HttpURLConnection httpURLConnection;
            URL               url;


            try {
                url                     = new URL(urlTohit + "?post_id=" + URLEncoder.encode(strings[0],"UTF-8"));
//                Log.e("post_new",urlTohit + "?like=" + URLEncoder.encode(informationToSearch.substring(0,20),"UTF-8") + "&c_t=" + URLEncoder.encode(fromJobsActivity.getString("companyTitle","1clickAway Official"),"UTF-8"));
                httpURLConnection       = (HttpURLConnection) url.openConnection();

                BufferedReader bufferedReader  = new BufferedReader( new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";

                while ( (line = bufferedReader.readLine() ) != null){
                    stringBuilder.append(line).append("\n");
                }


                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                for (int i = 0; i <= jsonArray.length(); i++ ){


                    completeJobInformationFromInternet = jsonArray.getJSONObject(0).getString("jobInfo");
                    companyTitleFromServer  =            jsonArray.getJSONObject(0).getString("companyTitle");
                    companyBannerURL        =    jsonArray.getJSONObject(0).getString("companyBanner");
                    postDate                = jsonArray.getJSONObject(0).getString("postDate");

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return stringBuilder.toString();
        }




    }





}
