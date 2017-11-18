package corp.burenz.expertouch.fragments.more;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Created by xperTouch on 7/19/2016.
 */
public class NewsFeeds extends AsyncTask<String,String,String> {

    // Context and JSON
    Context context;
    JSONObject jsonObject;

    //ARRAYLIST

    ArrayList<String> personsNames = new ArrayList<>();
    ArrayList<String> personsStates = new ArrayList<>();


    //RecyclerView

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    // String
    StringBuilder line = new StringBuilder();
    BufferedReader rd;






    public NewsFeeds(Context context, RecyclerView recyclerView){
        this.context = context;
        this.recyclerView = recyclerView;

    }








    @Override
    protected String doInBackground(String... params) {
        return null;


    }




}
