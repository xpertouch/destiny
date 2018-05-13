package corp.burenz.expertouch.map_utils;

/**
 * Created by buren on 26/4/18.
 */

public class MyPannelModel {

    private String placeTitle,placeStars, placeQuery, placeLatLng;

    public MyPannelModel(String placeTitle, String placeStars, String placeQuery, String placeLatLng) {
        this.placeTitle = placeTitle;
        this.placeStars = placeStars;
        this.placeQuery = placeQuery;
        this.placeLatLng = placeLatLng;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public void setPlaceTitle(String placeTitle) {
        this.placeTitle = placeTitle;
    }

    public String getPlaceStars() {
        return placeStars;
    }

    public void setPlaceStars(String placeStars) {
        this.placeStars = placeStars;
    }

    public String getPlaceQuery() {
        return placeQuery;
    }

    public void setPlaceQuery(String placeQuery) {
        this.placeQuery = placeQuery;
    }

    public String getPlaceLatLng() {
        return placeLatLng;
    }

    public void setPlaceLatLng(String placeLatLng) {
        this.placeLatLng = placeLatLng;
    }


}
