package corp.burenz.expertouch.adapters;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import corp.burenz.expertouch.R;

/**
 * Created by xperTouch on 7/24/2016.
 */
public class MySpinnerAdapter  implements android.widget.SpinnerAdapter {

    String []passedArray;
    TextView spinnerText;
    TextView dropMeDownV;
    public MySpinnerAdapter(String[] passedArray) {
        this.passedArray = passedArray;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_drop_down,parent,false);

        dropMeDownV = (TextView)convertView.findViewById(R.id.dropMeDownV);
        dropMeDownV.setText(passedArray[position]);

        return convertView;



    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return passedArray.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_adapter,parent,false);

        spinnerText = (TextView)convertView.findViewById(R.id.spinnerText);
        spinnerText.setText(passedArray[position]);

        return convertView;

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
