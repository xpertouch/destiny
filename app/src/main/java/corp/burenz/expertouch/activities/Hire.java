package corp.burenz.expertouch.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.CataAdapter;

public class Hire extends AppCompatActivity {



    RecyclerView cataRecyclerView;
    RecyclerView.Adapter cataRecyclerViewAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire);
        Iconics.init(Hire.this);


        String[] titles = {
                "Doctors"
        };

        String[] subTitles = {
                "Hire Doctors for your Family"
        };

    setUpVariables();
        cataRecyclerViewAdapter = new CataAdapter(Hire.this);
        cataRecyclerView.setAdapter(cataRecyclerViewAdapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }


    void setUpVariables(){
        cataRecyclerView = (RecyclerView) findViewById(R.id.hireRV);
        cataRecyclerView.setLayoutManager(new LinearLayoutManager(Hire.this));
    }


}
