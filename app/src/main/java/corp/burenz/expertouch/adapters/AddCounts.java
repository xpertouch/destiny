package corp.burenz.expertouch.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import corp.burenz.expertouch.R;


/**
 * Created by xperTouch on 7/29/2016.
 */


public class AddCounts extends RecyclerView.Adapter<AddCounts.CompanyAdds>{



    String[] textsIGotS;


    @Override
    public AddCounts.CompanyAdds onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_counts,parent,false);

        CompanyAdds companyAdds = new CompanyAdds(v);

        return companyAdds;


    }




    public AddCounts(String[] textsIGot){
        this.textsIGotS = textsIGot;
    }

    @Override
    public void onBindViewHolder(AddCounts.CompanyAdds holder, int position) {

        TextView textsIGot;

        textsIGot = holder.textsIGot;
        textsIGot.setText(textsIGotS[position]);

    }

    @Override
    public int getItemCount() {
        return textsIGotS.length;
    }



    public  static class CompanyAdds extends RecyclerView.ViewHolder {

        TextView textsIGot;


        public CompanyAdds(View itemView) {
            super(itemView);
            textsIGot = (TextView) itemView.findViewById(R.id.textIGot);

        }
    }
}












//public class AddCounts extends ArrayAdapter<String> {
//
//    String[] textsiGot;
//    TextView textsIGotT;
//
//    public AddCounts(Context context, String[] textsIGot) {
//        super(context,R.layout.post_counts,textsIGot);
//
//        this.textsiGot = textsIGot;
//
//
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//
//        View v = LayoutInflater.from(getContext()).inflate(R.layout.post_counts,parent,false);
//        textsIGotT = (TextView) v.findViewById(R.id.textIGot);
//
//        textsIGotT.setText(textsiGot[position]);
//
//
//
//        return v;
//
//
//
//    }
//}
