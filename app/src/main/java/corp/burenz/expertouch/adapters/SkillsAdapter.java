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



public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.SkillViewHolder> {



    String[] skillList;

    public SkillsAdapter(String[] skillList){
        this.skillList = skillList;

    }


    @Override
    public SkillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.skill_adapter,parent,false);
        SkillViewHolder skillViewHolder = new SkillViewHolder(v);
        return skillViewHolder;

    }



    @Override
    public void onBindViewHolder(SkillViewHolder holder, int position) {

        TextView skillTextView;
        skillTextView = holder.skillTextView;
        skillTextView.setText(skillList[position]);




    }


    @Override
    public int getItemCount() {
        return skillList.length;
    }




    public static class SkillViewHolder extends RecyclerView.ViewHolder {

        TextView skillTextView;
        public SkillViewHolder(View itemView) {
            super(itemView);

            skillTextView = (TextView)itemView.findViewById(R.id.skillText);

        }
    }





}
