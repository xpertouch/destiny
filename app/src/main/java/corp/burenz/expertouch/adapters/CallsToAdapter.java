package corp.burenz.expertouch.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.util.CallPermissions;

/**
 * Created by xperTouch on 8/16/2016.
 */

public class CallsToAdapter extends RecyclerView.Adapter<CallsToAdapter.MyCallsMade> {


    Context context;



    @Override
    public MyCallsMade onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.my_calls_adapter,parent,false);
        MyCallsMade myCallsMade = new MyCallsMade(v);
        return myCallsMade;
    }

    String[] callTitle;
    String[] callDate;
    String callType;
    String[] callPhone;




    public CallsToAdapter(Context context, String callType ,String[] callTitle,String[] callDate, String[] callPhone){
            this.callTitle = callTitle;
            this.callDate = callDate;
            this.callType = callType;
            this.callPhone = callPhone;
            this.context = context;
    }


    @Override
    public void onBindViewHolder(MyCallsMade holder, final int position) {


        TextView callTitleV,callDateV;
        ImageView  callTypeI,callButtonI;


        callButtonI = holder.callButtonI;
        callTypeI = holder.callTypeI;
        callTitleV = holder.callTitleV;
        callDateV = holder.callDateV;



        callTitleV.setText(callTitle[position]);
        callDateV.setText(callDate[position]);

        if(callType.equals("company")){
            callTypeI.setImageResource(R.drawable.office);
        }


        callButtonI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + callPhone[position]));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    context.startActivity(new Intent(context,CallPermissions.class).putExtra("callIt",callPhone[position]));
                    return;
                }
                context.startActivity(intent);


            }
        });




    }

    @Override
    public int getItemCount() {
        return callTitle.length;
    }

    public class MyCallsMade extends RecyclerView.ViewHolder {

        TextView callTitleV,callDateV;
        ImageView  callTypeI,callButtonI;


        public MyCallsMade(View itemView) {
            super(itemView);

            callTitleV = (TextView)itemView.findViewById(R.id.callTitileV);
            callDateV = (TextView) itemView.findViewById(R.id.callDateV);
            callTypeI = (ImageView) itemView.findViewById(R.id.callTypeI);
            callButtonI = (ImageView)itemView.findViewById(R.id.callButtonI);

        }
    }






}
