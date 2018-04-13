package corp.burenz.expertouch.util;

import android.content.Context;
import android.widget.Toast;

import org.cryse.widget.persistentsearch.SearchItem;
import org.cryse.widget.persistentsearch.SearchSuggestionsBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import corp.burenz.expertouch.databases.SearchHistory;

public class SampleSuggestionsBuilder implements SearchSuggestionsBuilder {
    private Context mContext;
    private ArrayList<String> companyTitles;
    private ArrayList<String> companyTitlesS;


    private List<SearchItem> mHistorySuggestions = new ArrayList<SearchItem>();;


    public SampleSuggestionsBuilder(Context context, ArrayList<String> companyTitles) {

        companyTitlesS = new ArrayList<>();
        this.mContext       = context;
        this.companyTitles  = companyTitles;

        createHistory();

    }


    public SampleSuggestionsBuilder(Context context) {

        this.mContext       = context;
        createHistory();

    }


    private void createHistorys() {
        SearchItem item1 = new SearchItem(
                "1clickAway Official",
                "1clickAway",
                SearchItem.TYPE_SEARCH_ITEM_HISTORY
        );
        mHistorySuggestions.add(item1);
        SearchItem item2 = new SearchItem(
                "Southtech Solutions",
                "southtech solutions",
                SearchItem.TYPE_SEARCH_ITEM_HISTORY
        );
        mHistorySuggestions.add(item2);
        SearchItem item3 = new SearchItem(
                "Fashion Folks",
                "fashion folks",
                SearchItem.TYPE_SEARCH_ITEM_HISTORY
        );
        mHistorySuggestions.add(item3);
    }



    private void createHistory() {

        SearchHistory searchHistory = new SearchHistory(mContext);

       try {
           searchHistory.writer();
           String  []searchHistoryArray =  searchHistory.getHistoryStringArray().split("\n");

//           Toast.makeText(mContext, searchHistory.getHistoryStringArray(), Toast.LENGTH_SHORT).show();
           if( searchHistoryArray.length == 0 ){return;}

           for (String aSearchhistoryArray : searchHistoryArray) {
                if (aSearchhistoryArray.length() > 0){
                    mHistorySuggestions.add(new SearchItem(aSearchhistoryArray, aSearchhistoryArray, SearchItem.TYPE_SEARCH_ITEM_HISTORY));
                }


           }

           createHistorys();
           searchHistory.close();
       }catch (Exception e){
           Toast.makeText(mContext, "Bug here " + e.toString(), Toast.LENGTH_SHORT).show();
       }



    }

    @Override
    public Collection<SearchItem> buildEmptySearchSuggestion(int maxCount) {
        List<SearchItem> items = new ArrayList<SearchItem>();
        items.addAll(mHistorySuggestions);
        return items;
    }

    @Override
    public Collection<SearchItem> buildSearchSuggestion(int maxCount, String query) {
        List<SearchItem> items = new ArrayList<SearchItem>();
        SearchHistory searchHistory = new SearchHistory(mContext);

        searchHistory.writer();
        String  []searchHistoryArray =  searchHistory.getHistoryStringArray().split("\n");
//           Toast.makeText(mContext, searchHistory.getHistoryStringArray(), Toast.LENGTH_SHORT).show();


        searchHistory.close();


        for (String aSearchHistoryArray : searchHistoryArray) {

            if (aSearchHistoryArray.toLowerCase().contains(query)) {

                SearchItem peopleSuggestion = new SearchItem(
                        aSearchHistoryArray, aSearchHistoryArray, SearchItem.TYPE_SEARCH_ITEM_HISTORY


                );
                items.add(peopleSuggestion);


            }


        }


        if (companyTitles != null) {

            for (int i = 0; i < companyTitles.size(); i++) {

                if (companyTitles.get(i).toLowerCase().contains(query)) {

                    SearchItem peopleSuggestion = new SearchItem(
                            companyTitles.get(i), companyTitles.get(i), SearchItem.TYPE_SEARCH_ITEM_SUGGESTION


                    );
                    items.add(peopleSuggestion);

                }
            }


        }


/*

*/








/*
        if(query.startsWith("@")) {
            SearchItem peopleSuggestion = new SearchItem(
                    "Search channel: " + query.substring(1),
                    query,
                    SearchItem.TYPE_SEARCH_ITEM_SUGGESTION
            );
            items.add(peopleSuggestion);


        } else if(query.startsWith("#")) {
            SearchItem toppicSuggestion = new SearchItem(
                    "Search Topic: " + query.substring(1),
                    query,
                    SearchItem.TYPE_SEARCH_ITEM_SUGGESTION
            );
            items.add(toppicSuggestion);
        } else {
            SearchItem peopleSuggestion = new SearchItem(
                    "Search People: " + query,
                    "@" + query,
                    SearchItem.TYPE_SEARCH_ITEM_SUGGESTION
            );
            items.add(peopleSuggestion);
            SearchItem toppicSuggestion = new SearchItem(
                    "Search Topic: " + query,
                    "#" + query,
                    SearchItem.TYPE_SEARCH_ITEM_SUGGESTION
            );
            items.add(toppicSuggestion);
        }
        for(SearchItem item : mHistorySuggestions) {
            if(item.getValue().startsWith(query)) {
                items.add(item);
            }
        }
*/

        return items;
    }

}
