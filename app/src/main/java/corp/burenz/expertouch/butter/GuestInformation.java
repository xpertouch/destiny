package corp.burenz.expertouch.butter;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by buren on 19/11/17.
 */

public class GuestInformation {

    Context mContext;

    public GuestInformation(Context mContext){
        this.mContext  = mContext;
    }







    public String getGuestNumber(){
        return  mContext.getSharedPreferences(MySharedConfig.GuestPrefs.LOCAL_APP_DATA, 0).getString("userEmail", "null");

    }

    public String getGuestName(){
        return  mContext.getSharedPreferences(MySharedConfig.GuestPrefs.LOCAL_APP_DATA, 0).getString("userName", "null");
    }


}
