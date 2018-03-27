package corp.burenz.expertouch.service;

import android.app.usage.NetworkStats;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import corp.burenz.expertouch.activities.Buket;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;



import org.json.JSONException;
import org.json.JSONObject;

import corp.burenz.expertouch.activities.JobPostDetails;
import corp.burenz.expertouch.activities.Jobs;
import corp.burenz.expertouch.util.Config;
import corp.burenz.expertouch.util.NotificationUtils;

/**
 *
 *

 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        String NARRATION = "narrate";

        /* from user settings */
        SharedPreferences sharedPreferences = getSharedPreferences(NARRATION,0);
        if(!sharedPreferences.getBoolean("push",true)){
            return;
        }

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification


            Log.e("INFO","App alredy in background");

        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl     = data.getString("image");


            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e("payload_inside",payload.getString("from"));
            String storeIcon    = payload.getString("storeIcon");


            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e("storeIcon", "storeIcon: " + storeIcon);

            Log.e(TAG, "timestamp: " + timestamp);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                Intent resultIntent;

                if (payload.getString("from").contains("bucket")){
                    resultIntent = new Intent(getApplicationContext(), Buket.class);
                    resultIntent.putExtra("type", payload.getString("type"));
//                    resultIntent.putExtra("type", "education");

                }else{
                    resultIntent = new Intent(getApplicationContext(), JobPostDetails.class);
                    resultIntent.putExtra("postId", payload.getString("postId"));
//                    resultIntent.putExtra("postId", "12");

                }
                // app is in background, show the notification in notification tray

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    Log.e("imageURL","empty");

                    showNotificationMessage(storeIcon,getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    Log.e("imageURL","present " + imageUrl);
                    showNotificationMessageWithBigImage(storeIcon, getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(String storeIcon, Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(storeIcon,title, message, timeStamp, intent);
    }

    /*
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(String storeIcon, Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(storeIcon, title, message, timeStamp, intent, imageUrl);
    }
}
