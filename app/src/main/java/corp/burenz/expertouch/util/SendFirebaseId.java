package corp.burenz.expertouch.util;

import android.os.AsyncTask;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by buren on 12/11/17.
 */

public class SendFirebaseId extends AsyncTask< String , String, String > {




    @Override
    protected String doInBackground(String... strings) {

        StringBuilder stringBuilder = new StringBuilder();
        URL url;
        HttpsURLConnection urlConnection;
        String urlToHit = strings[0];








        return null;

    }

}
