package corp.burenz.expertouch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import corp.burenz.expertouch.R;

/**
 * Created by xperTouch on 7/18/2016.
 */
public class FilterListAdapter extends ArrayAdapter<String> {

    String[] arrayList;
    TextView itemName;
    LinearLayout selectedItem;

    public FilterListAdapter(Context context, String[] arrayList) {
        super(context, R.layout.filter_list_layout ,arrayList);

        this.arrayList  = arrayList;





    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View v  = LayoutInflater.from(getContext()).inflate(R.layout.filter_list_layout,parent,false);

        itemName = (TextView)v.findViewById(R.id.itemName);
        itemName.setText(arrayList[position]);

        selectedItem = (LinearLayout)v.findViewById(R.id.selectLayout);




        return v;



    }
}
