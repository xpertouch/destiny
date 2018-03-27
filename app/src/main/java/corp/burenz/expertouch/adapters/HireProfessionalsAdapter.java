package corp.burenz.expertouch.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.iconics.view.IconicsImageView;



import corp.burenz.expertouch.R;
import corp.burenz.expertouch.activities.SkilledExperts;

import static com.mikepenz.fontawesome_typeface_library.FontAwesome.Icon.faw_gavel;
import static com.mikepenz.fontawesome_typeface_library.FontAwesome.Icon.faw_graduation_cap;
import static com.mikepenz.fontawesome_typeface_library.FontAwesome.Icon.faw_truck;
import static com.mikepenz.fontawesome_typeface_library.FontAwesome.Icon.faw_user;
import static com.mikepenz.fontawesome_typeface_library.FontAwesome.Icon.faw_user_md;
import static com.mikepenz.google_material_typeface_library.GoogleMaterial.Icon.gmd_ac_unit;
import static com.mikepenz.google_material_typeface_library.GoogleMaterial.Icon.gmd_build;
import static com.mikepenz.google_material_typeface_library.GoogleMaterial.Icon.gmd_group;
import static com.mikepenz.google_material_typeface_library.GoogleMaterial.Icon.gmd_local_atm;
import static com.mikepenz.google_material_typeface_library.GoogleMaterial.Icon.gmd_local_hospital;
import static com.mikepenz.google_material_typeface_library.GoogleMaterial.Icon.gmd_local_library;
import static com.mikepenz.google_material_typeface_library.GoogleMaterial.Icon.gmd_person_pin_circle;
import static com.mikepenz.google_material_typeface_library.GoogleMaterial.Icon.gmd_phonelink;
import static com.mikepenz.google_material_typeface_library.GoogleMaterial.Icon.gmd_spa;
import static com.mikepenz.google_material_typeface_library.GoogleMaterial.Icon.gmd_switch_video;
import static com.mikepenz.google_material_typeface_library.GoogleMaterial.Icon.gmd_time_to_leave;
import static com.mikepenz.google_material_typeface_library.GoogleMaterial.Icon.gmd_visibility_off;
import static com.mikepenz.google_material_typeface_library.GoogleMaterial.Icon.gmd_work;

/**
 * Created by buren on 22/3/18.
 */

public class HireProfessionalsAdapter extends RecyclerView.Adapter<HireProfessionalsAdapter.GridHolder> {

    /*removed health, labour, General Oder supplies, */

    Context mContext;

    public HireProfessionalsAdapter(Context mContext) {
        this.mContext = mContext;
    }


    private String []displayExpertCategories = {

            "Artisan",
            "Cab",
            "Stylist",
            "Engineer",
            "Agent",
            "Lawyer",
            "Tutor",
            "Mechanic",
            "Accountant",
            "Transporter",
            "Doctor",
            "Contractor",
            "Media",
            "Other",

    };

    private String []expertCatagories ={

            "Artisans",
            "Cabs",
            "Fashion",
            "Engineers",
            "Agents",
            "Lawyers",
            "Teachers",
            "Mechanics",
            "Accountants",
            "Transporters",
            "Doctors",
            "Contractors",
            "Media",
            "Others",


    };




    private IIcon drawableCataRes[] = {

            gmd_group,
            gmd_time_to_leave,
            gmd_spa,
            faw_graduation_cap,
            faw_user,
            faw_gavel,
            gmd_local_library,
            gmd_build,
            gmd_local_atm,
            faw_truck,
            faw_user_md,
            gmd_work,
            gmd_switch_video,
            gmd_group,

    };




    @Override
    public HireProfessionalsAdapter.GridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HireProfessionalsAdapter.GridHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hire_professionals_adapter,parent, false));
    }

    @Override
    public void onBindViewHolder(final HireProfessionalsAdapter.GridHolder holder, int position) {




    holder.catagoryIMageEQ.setIcon(new IconicsDrawable(mContext,drawableCataRes[position]).color(Color.WHITE).sizeDp(22));
    holder.adapterBottomDescription.setText(displayExpertCategories[position]);

    holder.expertHolderLL.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Activity  activity = (Activity) mContext;

            activity.startActivity(new Intent(mContext, SkilledExperts.class).putExtra("Context",expertCatagories[holder.getAdapterPosition()]));
            activity.overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);

        }
    });


    }

    @Override
    public int getItemCount() {
        return displayExpertCategories.length;
    }

    public class GridHolder extends RecyclerView.ViewHolder {

        TextView            adapterBottomDescription;
        IconicsImageView    catagoryIMageEQ;
        LinearLayout expertHolderLL;



        public GridHolder(View itemView) {
            super(itemView);
            adapterBottomDescription =  (TextView)  itemView.findViewById(R.id.adapterBottomDescription);
            catagoryIMageEQ          =  (IconicsImageView) itemView.findViewById(R.id.catagoryIMageEQ);
            expertHolderLL           =  (LinearLayout) itemView.findViewById(R.id.expertHolderLL);

        }
    }
}
