package corp.burenz.expertouch.butter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by buren on 21/11/17.
 */

public class MySharedConfig extends AppCompatActivity{


    public  static class AutoDetect {
        public static String AUTO_DETECH_SHARED_PREF = "autoDetect";
        public static String OTP_INSIDE = "autoOTP";





    }

    public static class CompanyPrefs{



    }


    class VersionPrefs{

        public static final  String APP_VERSION         = "appVersion";
        public static final  String currentAppVersion   = "v1.0.3";


    }

    public static class GuestPrefs{

        public static final  String LOCAL_APP_DATA      = "userInformation";

    }



    public static class BannerPrefs{


        public static final String BANNER_PREF           = "customBanner";
        public static final String IS_CUSTOM_BOOLEAN     = "isCustom";

        public static final String BANNER_URL_1           = "banner1";
        public static final String BANNER_URL_2           = "banner2";
        public static final String BANNER_URL_3           = "banner3";
        public static final String BANNER_URL_4           = "banner4";
        public static final String BANNER_URL_5           = "banner5";
        public static final String BANNER_URL_6           = "banner6";


        public static final String BANNER_URL_1_DEF       = "http://192.168.43.21/banners/default/banner1.jpg";
        public static final String BANNER_URL_2_DEF       = "http://192.168.43.21/banners/default/banner2.jpg";
        public static final String BANNER_URL_3_DEF       = "http://192.168.43.21/banners/default/banner3.jpg";
        public static final String BANNER_URL_4_DEF       = "http://192.168.43.21/banners/default/banner4.jpg";
        public static final String BANNER_URL_5_DEF       = "http://192.168.43.21/banners/default/banner5.jpg";
        public static final String BANNER_URL_6_DEF       = "http://192.168.43.21/banners/default/banner6.jpg";






    }

    public static class IntroPrefs{

        public static final  String INTRODUCE_ME        = "introduction";


    }



    public static class FilterPrefs{

        public static final  String CURRENT_FILTER      = "feedsFilter";



    }










}
