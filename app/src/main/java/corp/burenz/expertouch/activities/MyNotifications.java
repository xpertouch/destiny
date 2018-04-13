package corp.burenz.expertouch.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.MyNotificationAdapter;
import corp.burenz.expertouch.butter.GuestInformation;
import corp.burenz.expertouch.butter.NotificationModel;

/**
 * Created by buren on 2/4/18.
 */

public class MyNotifications extends AppCompatActivity {

    private NotificationModel notificationModel;
    private RecyclerView nRecyclerView;
    private MyNotificationAdapter notificationAdapter;
    private LinearLayout nNetworkInInNotificationsLL, nNotificationsYetLL;
    private SwipeRefreshLayout pullDownNotification;
    private Button relaodMyNotifications;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein_scan, R.anim.fadeout_scan);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notifications);

          nRecyclerView                 = (RecyclerView) findViewById(R.id.nRecyclerView);
          nNetworkInInNotificationsLL   = (LinearLayout) findViewById(R.id.nNetworkInInNotificationsLL);
          nNotificationsYetLL           = (LinearLayout) findViewById(R.id.nNotificationsYetLL);
          pullDownNotification          = (SwipeRefreshLayout) findViewById(R.id.pullDownNotification);
          relaodMyNotifications         = (Button) findViewById(R.id.reloadMyNotications);

          pullDownNotification.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
              @Override
              public void onRefresh() {
                  new GetMyNotifications().execute();
              }
          });

          findViewById(R.id.onBackPressNotifications).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  finish();
                  overridePendingTransition(R.anim.fadein_scan, R.anim.fadeout_scan);
              }
          });

          relaodMyNotifications.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  new GetMyNotifications().execute();
              }
          });

          nRecyclerView.setLayoutManager(new LinearLayoutManager(this));

          new GetMyNotifications().execute();

    }


    private class GetMyNotifications extends AsyncTask< String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            /*http://1clickaway.in/ver1.1/workshop/create_notifications.php?functionname=get&user_phone=9797080059*/
            String          urlToHit        = "http://1clickaway.in/ver1.1/workshop/create_notifications.php";
            StringBuilder   stringBuilder   = new StringBuilder();


            HttpURLConnection httpURLConnection;
            URL url;

            try {

                url = new URL(urlToHit + "?functionname=get&user_phone=" + URLEncoder.encode(new GuestInformation(MyNotifications.this).getGuestNumber()));
                Log.e("urlToHit",urlToHit + "?functionname=get&user_phone=" + URLEncoder.encode(new GuestInformation(MyNotifications.this).getGuestNumber()));
                httpURLConnection  = (HttpURLConnection) url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";

                while ( (line = bufferedReader.readLine()) != null ){
                    stringBuilder.append(line).append("\n");
                }

                JSONArray jsonArray = new JSONArray(stringBuilder.toString());

                JSONObject jsonObject ;

                for ( int i = 0; i <= jsonArray.length(); i++ ){
                    jsonObject = jsonArray.getJSONObject(i);
                    notificationModel.setNotificationModel(jsonObject.getString("n_id"), jsonObject.getString("n_title"), jsonObject.getString("n_body"), jsonObject.getString("n_date"));
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


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pullDownNotification.setRefreshing(false);
            if (notificationModel.getNotificationId().size() == 0){
                nNotificationsYetLL.startAnimation(AnimationUtils.loadAnimation(MyNotifications.this, R.anim.fadein_scan));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nNotificationsYetLL.setVisibility(View.VISIBLE);
                    }
                },500);

            }else {
                nNotificationsYetLL.setVisibility(View.GONE);
            }

            notificationAdapter = new MyNotificationAdapter(notificationModel);
            nRecyclerView.setAdapter(notificationAdapter);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pullDownNotification.setRefreshing(true);
            notificationModel = new NotificationModel();
        }
    }
}
