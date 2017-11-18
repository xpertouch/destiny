package corp.burenz.expertouch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.activities.SkilledExperts;

/**
 * Created by xperTouch on 7/11/2016.
 */
public class CataAdapter extends RecyclerView.Adapter<CataAdapter.CataViewHolder> implements View.OnClickListener{

    Context context;


    public  CataAdapter(Context context){

        this.context = context;


    }


    @Override
    public CataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  v = LayoutInflater.from(parent.getContext()).inflate(R.layout.catagories_adapter,parent,false);

        Iconics.init(context);

        CataViewHolder cataViewHolder = new CataViewHolder(v);
        return cataViewHolder;


    }



    @Override
    public void onBindViewHolder(CataViewHolder holder, int position) {
        LinearLayout takeDoctor;
        takeDoctor = holder.takeDoctor;

        takeDoctor.setOnClickListener(this);
        holder.takeTransporters.setOnClickListener(this);
        holder.takeLocalTransport.setOnClickListener(this);
        holder.takeConsultants.setOnClickListener(this);
        holder.takeRepairing.setOnClickListener(this);
        holder.takeArtsans.setOnClickListener(this);
        holder.takeBotiques.setOnClickListener(this);
        holder.takeLaywer.setOnClickListener(this);
        holder.takeOrderSupplies.setOnClickListener(this);
        holder.takeAgents.setOnClickListener(this);
        holder.takeOthers.setOnClickListener(this);
        holder.takeHealth.setOnClickListener(this);
        holder.takeContractors.setOnClickListener(this);
        holder.takeMedia.setOnClickListener(this);
        holder.takeSoftware.setOnClickListener(this);
        holder.takeLabours.setOnClickListener(this);
        holder.takeTeachers.setOnClickListener(this);
        holder.takeEngineer.setOnClickListener(this);

    }



    @Override
    public int getItemCount() {
        return 1;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){


            case R.id.takeDoctor:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Doctors"));
                break;


            case R.id.takeEngineer:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Engineers"));
                break;


            case R.id.takeLocalTransport:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Cabs"));
                break;


            case R.id.takeTransporters:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Transporters"));
                break;

            case R.id.takeConsultants:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Accountants"));
                break;

            case R.id.takeRepairing:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Mechanics"));
                break;


            case R.id.takeBotiques:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Fashion"));
                break;

            // complete
            case R.id.takeLaywer:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Lawyers"));
                break;


            // complete
            case R.id.takeArtsans:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Artisans"));
                break;



            // teachers
            case R.id.takeTeachers:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Teachers"));
                break;

            // labours
            case R.id.takeLabours:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Labours"));
                break;

            // software and services
            case R.id.takeSoftware:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Software and Services"));
                break;

            //media
            case R.id.takeMedia:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Media"));
                break;


            //contractors
            case R.id.takeContractors:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Contractors"));
                break;

            //health
            case R.id.takeHealth:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Health"));
                break;

            //others
            case R.id.takeOthers:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Others"));
                break;


            //agents
            case R.id.takeAgents:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","Agents"));
                break;



            case R.id.takeOrderSupplies:
                context.startActivity(new Intent(context,SkilledExperts.class).putExtra("Context","General Order Suppliers"));
                break;


        }


    }

    public static class CataViewHolder extends RecyclerView.ViewHolder {

        LinearLayout takeDoctor,takeArtsans,takeLaywer,takeBotiques,takeRepairing,takeConsultants,takeLocalTransport,takeTransporters,takeOrderSupplies,takeAgents,takeOthers,takeHealth,takeContractors,takeMedia,takeSoftware,takeLabours,takeTeachers,takeEngineer;





        public CataViewHolder(View v) {
            super(v);
            takeDoctor = (LinearLayout) v.findViewById(R.id.takeDoctor);
            takeArtsans = (LinearLayout) v.findViewById(R.id.takeArtsans);
            takeLaywer= (LinearLayout) v.findViewById(R.id.takeLaywer);
            takeBotiques= (LinearLayout) v.findViewById(R.id.takeBotiques);
            takeRepairing = (LinearLayout) v.findViewById(R.id.takeRepairing);
            takeConsultants = (LinearLayout) v.findViewById(R.id.takeConsultants);
            takeLocalTransport = (LinearLayout) v.findViewById(R.id.takeLocalTransport);
            takeTransporters = (LinearLayout) v.findViewById(R.id.takeTransporters);


            takeOrderSupplies = (LinearLayout) v.findViewById(R.id.takeOrderSupplies);
            takeAgents = (LinearLayout) v.findViewById(R.id.takeAgents);
            takeOthers= (LinearLayout) v.findViewById(R.id.takeOthers);
            takeHealth= (LinearLayout) v.findViewById(R.id.takeHealth);
            takeContractors= (LinearLayout) v.findViewById(R.id.takeContractors);
            takeMedia= (LinearLayout) v.findViewById(R.id.takeMedia);
            takeSoftware= (LinearLayout) v.findViewById(R.id.takeSoftware);
            takeLabours = (LinearLayout) v.findViewById(R.id.takeLabours);
            takeTeachers = (LinearLayout) v.findViewById(R.id.takeTeachers);

            takeEngineer = (LinearLayout) v.findViewById(R.id.takeEngineer);



        }
    }
}
