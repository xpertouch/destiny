package corp.burenz.expertouch.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.butter.NotificationModel;

/**
 * Created by buren on 2/4/18.
 */

public class MyNotificationAdapter extends RecyclerView.Adapter<MyNotificationAdapter.MyInnerHolder> {

    private NotificationModel notificationModel;

    public MyNotificationAdapter(NotificationModel notificationModel){
        this.notificationModel = notificationModel;
    }

    @Override
    public MyInnerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyInnerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_adapter,parent, false));
    }

    @Override
    public void onBindViewHolder(MyInnerHolder holder, int position) {
        holder.nTitleTV.setText(notificationModel.getNotificationTitle().get(position));
        holder.nDescription.setText(notificationModel.getNotificationBody().get(position));
        holder.nDate.setText(notificationModel.getNotificationDate().get(position));
    }

    @Override
    public int getItemCount() {
        return notificationModel.getNotificationId().size();
    }

    public class MyInnerHolder extends RecyclerView.ViewHolder {

        TextView nTitleTV, nDescription, nDate;

        public MyInnerHolder(View itemView) {
            super(itemView);

            nTitleTV        = (TextView) itemView.findViewById(R.id.nTitleTV);
            nDescription    = (TextView) itemView.findViewById(R.id.nDescriptionTV);
            nDate           = (TextView) itemView.findViewById(R.id.notificationDateTV);

        }

    }
}
