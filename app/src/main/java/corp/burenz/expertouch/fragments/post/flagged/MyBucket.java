package corp.burenz.expertouch.fragments.post.flagged;

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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.BucketPostAdapter;

/**
 * Created by xperTouch on 10/13/2016.
 */
public class MyBucket extends Fragment {



    String COMPANY_DETAILS = "myCompanyDetails";
    SharedPreferences myCompanyDetails;

    RelativeLayout bucketLoader;
    RecyclerView bucketPostsRV;
    RecyclerView.Adapter adapter;


    ArrayList<String> postTitle;
    ArrayList<String> postId;
    ArrayList<String> postDates;
    ArrayList<String> posts;
    ArrayList<String> totalLikes;


    LinearLayout noBucketPosts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.my_bucket,container,false);

        bucketPostsRV = (RecyclerView) getActivity().findViewById(R.id.bucketPostsRV);
        setViews(v);
        bucketPostsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        new GetMyCompanyPosts().execute();


        return v;





    }

    public void setViews(View v){

        bucketPostsRV = (RecyclerView) v.findViewById(R.id.bucketPostsRV);
        bucketLoader = (RelativeLayout) v.findViewById(R.id.bucketLoader);
        noBucketPosts = (LinearLayout) v.findViewById(R.id.noBucketPosts);
        myCompanyDetails = getActivity().getSharedPreferences(COMPANY_DETAILS,0);


    }
    class GetMyCompanyPosts extends AsyncTask<String,String,String> {



        StringBuilder builder  = new StringBuilder();
        BufferedReader bufferedReader;
        JSONObject jsonObject;
        JSONArray jsonArray;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();





        @Override
        protected String doInBackground(String... params) {


            nameValuePairs.add(new BasicNameValuePair("companyTitle",myCompanyDetails.getString("companyName","Company Name")));


            try{

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/bucket/get_company_sales.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);

                HttpEntity httpEntity = httpResponse.getEntity();

                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";


                while ( (str = bufferedReader.readLine()) != null ){
                    builder.append(str);
                }

                jsonArray = new JSONArray(builder.toString());

                int length = jsonArray.length();




                for (int  i = 0; i < length; i++){

                    jsonObject = jsonArray.getJSONObject(i);

                    postId.add(jsonObject.getString("saleId"));
                    postDates.add(jsonObject.getString("saleDate"));
                    postTitle.add(jsonObject.getString("saleTitle"));
                    posts.add(jsonObject.getString("saleDiscription"));
                    totalLikes.add(jsonObject.getString("totalLikes"));


                }







            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){

            }


            return builder.toString();
        }




        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            postDates = new ArrayList<String>();
            posts = new ArrayList<String>();
            postId = new ArrayList<String>();
            postTitle = new ArrayList<String>();
            totalLikes = new ArrayList<String>();

            bucketLoader.setVisibility(View.VISIBLE);
            bucketPostsRV.setVisibility(View.GONE);




        }






        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            bucketLoader.setVisibility(View.GONE);

            if (postDates.size() == 0){
                noBucketPosts.setVisibility(View.VISIBLE);
                bucketPostsRV.setVisibility(View.GONE);

            }else {

                adapter = new BucketPostAdapter(getActivity(),postTitle,postDates,posts,postId,totalLikes);
                bucketPostsRV.setVisibility(View.VISIBLE);
                bucketPostsRV.setAdapter(adapter);

            }




        }



    }


}
