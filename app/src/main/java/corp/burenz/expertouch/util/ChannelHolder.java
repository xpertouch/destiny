package corp.burenz.expertouch.util;

import java.util.ArrayList;

/**
 * Created by buren on 30/1/18.
 */

public class ChannelHolder {

    private ArrayList<String> companyBannerURL, companyTitle, companyAddress, companyAbout,  isSubscribedByMe ,companyID;

    public ChannelHolder(ArrayList<String> companyBannerURL, ArrayList<String> companyTitle, ArrayList<String> companyAddress, ArrayList<String> companyAbout, ArrayList<String> isSubscribedByMe, ArrayList<String> companyID) {
        this.companyBannerURL       = companyBannerURL;
        this.companyTitle           = companyTitle;
        this.companyAddress         = companyAddress;
        this.companyAbout           = companyAbout;
        this.isSubscribedByMe       = isSubscribedByMe;
        this.companyID              = companyID;
    }

    public ArrayList<String> getCompanyBannerURL() {
        return companyBannerURL;
    }

    public ArrayList<String> getCompanyTitle() {
        return companyTitle;
    }

    public ArrayList<String> getCompanyAddress() {
        return companyAddress;
    }

    public ArrayList<String> getCompanyAbout() {
        return companyAbout;
    }

    public ArrayList<String> getIsSubscribedByMe() {
        return isSubscribedByMe;
    }

    public ArrayList<String> getCompanyID() {
        return companyID;
    }

    public void setCompanyBannerURL(ArrayList<String> companyBannerURL) {
        this.companyBannerURL = companyBannerURL;
    }

    public void setCompanyTitle(ArrayList<String> companyTitle) {
        this.companyTitle = companyTitle;
    }

    public void setCompanyAddress(ArrayList<String> companyAddress) {
        this.companyAddress = companyAddress;
    }

    public void setCompanyAbout(ArrayList<String> companyAbout) {
        this.companyAbout = companyAbout;
    }

    public void setIsSubscribedByMe(int position, String updateValue) {
        this.isSubscribedByMe.set(position,updateValue);

    }

    public void setCompanyID(ArrayList<String> companyID) {
        this.companyID = companyID;
    }
}
