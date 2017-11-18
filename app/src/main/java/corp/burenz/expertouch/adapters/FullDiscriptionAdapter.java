package corp.burenz.expertouch.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import corp.burenz.expertouch.R;

/**
 * Created by xperTouch on 7/12/2016.
 */
public class FullDiscriptionAdapter extends RecyclerView.Adapter<FullDiscriptionAdapter.FullViewHolder> {

    String[] keysForMore,valueForMore;

    public FullDiscriptionAdapter(String[] keysForMore,String[] valueForMore){

        this.keysForMore = keysForMore;
        this.valueForMore = valueForMore;

    }
    @Override
    public FullViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.full_description_layout,parent,false);

        FullViewHolder fullViewHolder = new FullViewHolder(v);

        return fullViewHolder;


    }

    @Override
    public void onBindViewHolder(FullViewHolder holder, int position) {

        TextView keysForMoreTextView,valuesForMoreTextView;

        keysForMoreTextView = holder.keysForMoreTextView;
        valuesForMoreTextView = holder.valuesForMoreTextView;

        keysForMoreTextView.setText(keysForMore[position]);
        valuesForMoreTextView.setText(valueForMore[position]);


    }

    @Override
    public int getItemCount() {
        return keysForMore.length;
    }

    public static  class FullViewHolder extends RecyclerView.ViewHolder {

        TextView keysForMoreTextView,valuesForMoreTextView;

        public FullViewHolder(View itemView) {
            super(itemView);

            keysForMoreTextView   = (TextView)itemView.findViewById(R.id.keyForMore);
            valuesForMoreTextView = (TextView)itemView.findViewById(R.id.valueForMore);

        }

    }

}
