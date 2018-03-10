package corp.burenz.expertouch.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.activities.SettingsActivity;
import corp.burenz.expertouch.util.MySingleton;

/**
 * Created by buren on 13/2/18.
 */

public class LinksLayoutAdapter extends RecyclerView.Adapter<LinksLayoutAdapter.LinksViewHolder> {
    @Override
    public LinksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new LinksViewHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.links_layout_adapter,parent, false) );
    }


    private ArrayList<String>      urlToHit;
    private Context                 mContext;


    public LinksLayoutAdapter(ArrayList<String> urlToHit, Context mContext) {
        this.urlToHit = urlToHit;
        this.mContext = mContext;
    }


    @Override
    public void onBindViewHolder(final LinksViewHolder holder, final int position) {


        holder.imageInsideNotification.setImageUrl(urlToHit.get(position), MySingleton.getInstance(mContext).getImageLoader());
        Log.e("channel","" + urlToHit.get(position));
        holder.linksText.setText(urlToHit.get(position));
        holder.linkCardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_VIEW);


                if(!urlToHit.get(holder.getAdapterPosition()).contains("http://") ){
                    urlToHit.set( holder.getAdapterPosition(), "http://" + urlToHit.get(holder.getAdapterPosition()) );
                }
                i.setData(Uri.parse(urlToHit.get(holder.getAdapterPosition())));

                try{
                    mContext.startActivity(i);
                }catch (Exception e){
                    Toast.makeText(mContext, "Please Connect To networks and try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return urlToHit.size();
    }

    public class LinksViewHolder extends RecyclerView.ViewHolder {

        TextView            linksText;
        CardView            linkCardHolder;
        NetworkImageView    imageInsideNotification;



        public LinksViewHolder(View v) {
            super(v);
            linksText               = (TextView) v.findViewById(R.id.linkToTheBrpwser);
            linkCardHolder          = (CardView) v.findViewById(R.id.linkCardHolder);
            imageInsideNotification = (NetworkImageView) v.findViewById(R.id.imageInsideNotification);

        }
    }
}
