package corp.burenz.expertouch.map_utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import corp.burenz.expertouch.R;

/**
 * Created by buren on 29/4/18.
 */

public class SearchLocationAdapter extends RecyclerView.Adapter<SearchLocationAdapter.SearchHolder> {

    private DataFromPlaces dataFromPlaces;
    private Context mContext;

    public SearchLocationAdapter(DataFromPlaces dataFromPlaces, Context mContext) {
        this.dataFromPlaces = dataFromPlaces;
        this.mContext       = mContext;
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final SearchHolder holder, final int position) {

        holder.listViewInsideAC.setVisibility(View.VISIBLE);

        if (dataFromPlaces.getPlaceTitle().get(position).length() > 30){
            holder.cityTitleLV.setText(dataFromPlaces.getPlaceTitle().get(position).substring(0,30).concat(".."));

        }else {
            holder.cityTitleLV.setText(dataFromPlaces.getPlaceTitle().get(position));
        }


        if (dataFromPlaces.getPlaceAddress().get(position).length() > 50){
            holder.cityAddressTV.setText(dataFromPlaces.getPlaceAddress().get(position).substring(0,45).concat("..."));

        }else {
            holder.cityAddressTV.setText(dataFromPlaces.getPlaceAddress().get(position));
        }

        holder.listViewInsideAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext.getSharedPreferences("maps",0).edit().putString("place_id",dataFromPlaces.getPlaceId().get(position)).commit();
                ((Activity)mContext).finish();
                ((Activity)mContext).overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);

            }
        });


    }

    @Override
    public int getItemCount() {
        return dataFromPlaces.getPlaceId().size();
    }

    public class SearchHolder extends RecyclerView.ViewHolder {
        TextView cityTitleLV, cityAddressTV;
        LinearLayout listViewInsideAC;

        public SearchHolder(View itemView) {
            super(itemView);
            cityTitleLV = (TextView) itemView.findViewById(R.id.cityTitleLV);
            listViewInsideAC = (LinearLayout) itemView.findViewById(R.id.listViewInsideAC);
            cityAddressTV   =   (TextView) itemView.findViewById(R.id.cityAddressTV);
        }
    }
}
