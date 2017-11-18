package corp.burenz.expertouch.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import corp.burenz.expertouch.R;

/**
 * Created by xperTouch on 9/19/2016.
 */
public class HelpCenterAdapter extends RecyclerView.Adapter<HelpCenterAdapter.ConfusionHolder> {

    String []questions,solutions;
    Context  context;

    @Override
    public ConfusionHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_adapter,parent,false);
        ConfusionHolder confusionHolder = new ConfusionHolder(v);
        return  confusionHolder;

    }


    public HelpCenterAdapter(Context context ,String[] questions,String[] solutions){

        this.context = context;
        this.questions = questions;
        this.solutions = solutions;

    }



    @Override
    public void onBindViewHolder(ConfusionHolder holder, int position) {
        final LinearLayout quesionBox;
        final LinearLayout solutionBox;
        TextView questionTitle,solutionToQuest;
        final ViewFlipper helpCenterFlipper;
        solutionBox = holder.solutionBox;
        questionTitle = holder.questionTitle;
        solutionToQuest = holder.solutionToQuest;
        helpCenterFlipper = holder.helpCenterFlipper;
        quesionBox = holder.quesionBox;


        questionTitle.setText(questions[position]);
        solutionToQuest.setText(solutions[position]);



        quesionBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (solutionBox.getVisibility() == View.GONE){
                    helpCenterFlipper.showNext();
                    solutionBox.startAnimation(AnimationUtils.loadAnimation(context,R.anim.abc_fade_in));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           solutionBox.setVisibility(View.VISIBLE);
                        }
                    },400);
                }else if (solutionBox.getVisibility() == View.VISIBLE){
                    helpCenterFlipper.showNext();
                    solutionBox.startAnimation(AnimationUtils.loadAnimation(context,R.anim.abc_fade_out));

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            solutionBox.setVisibility(View.GONE);
                        }
                    },400);



                }


            }
        });



    }

    @Override
    public int getItemCount() {
        return questions.length;
    }

    public static class ConfusionHolder extends RecyclerView.ViewHolder {


        LinearLayout solutionBox,quesionBox;
        TextView questionTitle,solutionToQuest;
        ViewFlipper helpCenterFlipper;


        public ConfusionHolder(View v) {
            super(v);
            solutionBox = (LinearLayout) v.findViewById(R.id.solutionBox);
            questionTitle = (TextView) v.findViewById(R.id.questionTitle);
            solutionToQuest = (TextView)   v.findViewById(R.id.solutionToQuest);
            helpCenterFlipper = (ViewFlipper) v.findViewById(R.id.helpCenterFlipper);
            quesionBox = (LinearLayout) v.findViewById(R.id.quesionBox);
        }
    }
}
