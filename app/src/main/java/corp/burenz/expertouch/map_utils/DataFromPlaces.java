package corp.burenz.expertouch.map_utils;

import java.util.ArrayList;

/**
 * Created by buren on 29/4/18.
 */

public class DataFromPlaces {

    ArrayList<String> placeId;
    ArrayList<String> placeTitle;
    ArrayList<String> placeAddress;


     public void addPlace(String placeId, String placeTitle, String placeAddress) {

         this.placeId.add(placeId);
         this.placeTitle.add(placeTitle);
         this.placeAddress.add(placeAddress);

     }


    public DataFromPlaces() {
        placeId = new ArrayList<>();
        placeTitle = new ArrayList<>();
        placeAddress    =   new ArrayList<>();

    }

    public ArrayList<String> getPlaceId() {
        return placeId;
    }

    public ArrayList<String> getPlaceTitle() {
        return placeTitle;
    }

    public ArrayList<String> getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceId(ArrayList<String> placeId) {
        this.placeId = placeId;
    }

    public void setPlaceTitle(ArrayList<String> placeTitle) {
        this.placeTitle = placeTitle;
    }

    public void setPlaceAddress(ArrayList<String> placeAddress) {
        this.placeAddress = placeAddress;
    }
}

