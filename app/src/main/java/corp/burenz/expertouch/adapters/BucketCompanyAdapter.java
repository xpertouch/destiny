package corp.burenz.expertouch.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import corp.burenz.expertouch.R;

/**
 * Created by xperTouch on 10/14/2016.
 */
public class BucketCompanyAdapter extends RecyclerView.Adapter<BucketCompanyAdapter.BucketPostHolder> {



    ArrayList<String> saleTitleArray,saleDiscriptionArray,postDateArray,saleID;


    @Override
    public BucketPostHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.bucket_company,parent,false);
        return new BucketPostHolder(v);



    }

    public BucketCompanyAdapter(ArrayList<String> saleTitleArray, ArrayList<String> saleDiscriptionArray, ArrayList<String> postDateArray, ArrayList<String> saleID) {
        this.saleTitleArray = saleTitleArray;
        this.saleDiscriptionArray = saleDiscriptionArray;
        this.postDateArray = postDateArray;
        this.saleID = saleID;
    }

    @Override
    public void onBindViewHolder(BucketPostHolder holder, int position) {

        TextView bucketSaleTitle,bucketSaleDiscription,bucketPostDate;

        bucketSaleTitle = holder.bucketSaleTitle;
        bucketSaleDiscription = holder.bucketSaleDiscription;
        bucketPostDate = holder.bucketPostDate;


        bucketSaleTitle.setText(saleTitleArray.get(position).toString());
        bucketSaleDiscription.setText(saleDiscriptionArray.get(position).toString());
        bucketPostDate.setText(postDateArray.get(position).toString());


    }

    @Override
    public int getItemCount() {
        return saleTitleArray.size();
    }

    public static class BucketPostHolder extends RecyclerView.ViewHolder {
        TextView bucketSaleTitle,bucketSaleDiscription,bucketPostDate;

        public BucketPostHolder(View itemView) {
            super(itemView);

            bucketSaleTitle = (TextView)itemView.findViewById(R.id.bucketSaleTitle);
            bucketSaleDiscription = (TextView) itemView.findViewById(R.id.bucketSaleDiscription);
            bucketPostDate = (TextView) itemView.findViewById(R.id.bucketSalePostDAte);

        }
    }

}
