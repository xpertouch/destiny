package corp.burenz.expertouch.fragments.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.FavouriteCompaniesAdapter;
import corp.burenz.expertouch.databases.Favourites;

/**
 * Created by xperTouch on 8/13/2016.
 */
public class CompanyFavFragment extends Fragment {


    RecyclerView companyFav;
    RecyclerView.Adapter companyFavAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.favourite_company_adds,container,false);

        Favourites favourites = new Favourites(getActivity());

        favourites.writer();
        String []companyBanner = favourites.getCompanyBanner().split("\n");
        String [] companyTitles = favourites.getCompanyTitle().split("\n");
        String [] postDate = favourites.getPostDate().split("\n");
        String [] jobInfo = favourites.getJobInfo().split("\n");
        String [] companyCall = favourites.getcompanyCall().split("\n");
        String [] companyMail = favourites.getmailCompany().split("\n");
        String [] visitCompany = favourites.getvisitCompany().split("\n");
        favourites.close();



        companyFav = (RecyclerView) v.findViewById(R.id.companyFav);

        companyFav.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Describe Adapter First


        companyFavAdapter = new FavouriteCompaniesAdapter(getActivity(),companyTitles,postDate,jobInfo,companyCall,visitCompany,companyMail,companyBanner);

        companyFav.setAdapter(companyFavAdapter);

        favourites.close();









        return v;






    }
}
