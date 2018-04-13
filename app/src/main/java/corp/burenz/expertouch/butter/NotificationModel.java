package corp.burenz.expertouch.butter;

import java.util.ArrayList;

/**
 * Created by buren on 2/4/18.
 */

public class NotificationModel {

    private ArrayList<String> notificationTitle, notificationBody, notificationId, notificationDate;

    public NotificationModel() {
        this.notificationTitle  = new ArrayList<>();
        this.notificationBody   = new ArrayList<>();
        this.notificationId     = new ArrayList<>();
        this.notificationDate   = new ArrayList<>();
    }



    public void setNotificationModel(String  nId, String nTitle, String nBody, String nDate){
        notificationId.add(nId);
        notificationBody.add(nBody);
        notificationTitle.add(nTitle);
        notificationDate.add(nDate);
    }

    public ArrayList<String> getNotificationTitle() {
        return notificationTitle;
    }

    public ArrayList<String> getNotificationBody() {
        return notificationBody;
    }

    public ArrayList<String> getNotificationId() {
        return notificationId;
    }

    public ArrayList<String> getNotificationDate() {
        return notificationDate;
    }
}
