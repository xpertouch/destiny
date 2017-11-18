package corp.burenz.expertouch.fragments.post.flag;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.activities.MyCompanyPosts;
import corp.burenz.expertouch.adapters.FilterListAdapter;

/**
 * Created by xperTouch on 10/12/2016.
 */
public class JobPost extends Fragment implements View.OnClickListener{

    ViewFlipper postBottomFliper;
    LinearLayout postAdd;
    LinearLayout flagAdd;
    CardView card0,card1,card2,card3,card4;
    Animation animation;
    TextView postFinally,cancelPost;

    ViewFlipper flagPostFlipper;

    ImageView close1,close2;
    ListAdapter typeAdapter,categoryAdapter;

    String COMPANY_ADD  = "companyAdd";
    SharedPreferences companyAdd;
    SharedPreferences.Editor editor;

    EditText postPosition0E,postPosition1E,postPosition2E;
    EditText noOfPosts0E,noOfPosts1E,noOfPosts2E;
    EditText skillsNeeded0E,skillsNeeded1E,skillsNeeded2E;
    EditText experience0E,experience1E,experience2E;
    TextView selectedCatagory;

    String addCatagory = "0",addType = "0";


    LinearLayout postCatagoryLL,postTypeLL;
    ListView catagoryList,typeList;
    ImageView cataArrow,typeArrow;



    String add;
    RadioButton male0,female0,any0,male1,female1,any1,male2,female2,any2;
    String gender0 = "individual";
    String gender1 = "individual";
    String gender2 = "individual";

    ViewFlipper sendingPostFlipper;

    LinearLayout flagAddlayout;


    final String[] catagoryArray= {
            "All Categories",
            "Government",
            "Corporate",

    };





    final String []corporateTypes = {
            "All Types",
            "Overseas",
            "Health",
            "Bank",
            "Software",
            "Teaching",
            "Non Teaching",
            "Insurance",
            "Hardware",
            "NGO",
            "Managers",
            "Administrators",
            "Clerks",
            "Statistical Assistant"

    };



    final String []govermentTypes = {

            "All Types",
            "Agriculture"
            ,"Animal Husbandry"
            ,"Fisheries"
            ,"Dairies"
            ,"Building Construction"
            ,"Cabinet Secretariat and Co-ordination"
            ,"Disaster Management"
            ,"Excise Department"
            ,"Finance"
            ,"Food & Consumer Protection"
            ,"Forest"
            ,"General Administration"
            ,"Industries Dept."
            ,"Handloom & Sericulture"
            ,"Human Resources Development"
            ,"Information & Public Relations"
            ,"Labour, Employment and Training"
            ,"Mines and Geology"
            ,"Minor Irrigation"
            ,"Minority Welfare"
            ,"National Savings"
            ,"Office of the Chief Election Officer"
            ,"Panchayat Raj"
            ,"Parliamentary Affairs"
            ,"Planning"
            ,"Public Health Engineering Dept."
            ,"Registration"
            ,"Revenue and Land Reforms"
            ,"Road Construction"
            ,"Rural Development"
            ,"Rural Engineering Organisation"
            ,"Rural Works"
            ,"State Board of Technical Education (SBTE)"
            ,"Science and Technology"
            ,"Social Welfare"
            ,"Sugarcane"
            ,"Tourism"
            ,"Transport"
            ,"Urban Development"
            ,"Water Resources"
            ,"Welfare"
            ,"Schedule Caste & Schedule Tribe"
            ,"Youth, Art & Culture"

    };




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.post_job,container,false);



        animation = AnimationUtils.loadAnimation(getActivity(),R.anim.card_animation);

        inItViews(v);
        onClickListners();
        visitEditTexts(v);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                postBottomFliper.showNext();

            }
        },500);

        postAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (card1.getVisibility() == View.GONE && card2.getVisibility() == View.GONE ){
                    card1.startAnimation(animation);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            card1.setVisibility(View.VISIBLE);
                        }
                    },500);
                    card1.setVisibility(View.VISIBLE);
                }else if (card1.getVisibility() == View.VISIBLE && card2.getVisibility() == View.GONE ){

                    card2.startAnimation(animation);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            card2.setVisibility(View.VISIBLE);

                        }
                    },500);
                }else if (card1.getVisibility() == View.GONE && card2.getVisibility() == View.VISIBLE){

                    card1.startAnimation(animation);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            card1.setVisibility(View.VISIBLE);

                        }
                    },500);

                }


            }
        });


        flagAdd.setOnClickListener(this);










        return  v;


    }



    void postBanner(){


        flagAddlayout.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.md_styled_slide_up_slow));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                flagAddlayout.setVisibility(View.VISIBLE);

            }
        },500);

        //        sendPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            new SendPost().execute();
//
//
//
//            }
//        });
//








    }

    public String oneCard(){

        // Toast.makeText(getActivity(), "Executing One Cards", Toast.LENGTH_SHORT).show();

        String noOfPosts = noOfPosts0E.getText().toString();
        String postPosition = postPosition0E.getText().toString();
        String experience = experience0E.getText().toString();
        String skillsNeeded = skillsNeeded0E.getText().toString();

        return "Vacancy Of " + noOfPosts + " " + gender0 +"(s)  for the Position of " + postPosition + " with "+ experience +" Experience Prescribed Qualification " + skillsNeeded + "";

    }

    public String twoCard(){


        String noOfPosts = noOfPosts0E.getText().toString();
        String postPosition = postPosition0E.getText().toString();
        String experience = experience0E.getText().toString();
        String skillsNeeded = skillsNeeded0E.getText().toString();

        String noOfPosts1 = noOfPosts1E.getText().toString();
        String postPosition1 = postPosition1E.getText().toString();
        String experience1 = experience1E.getText().toString();
        String skillsNeeded1 = skillsNeeded1E.getText().toString();

        // Toast.makeText(getActivity(), "Executing 2 Cards", Toast.LENGTH_SHORT).show();

        return "Vacancy Of " + noOfPosts +" "+ gender0 + "(s) for The Position Of " + postPosition + " with "+ experience +"  Experience Prescribed Qualification " + skillsNeeded + " 3xt3 " +
                "Vacancy Of " + noOfPosts1+ " " + gender1 + "(s) for The Position Of " + postPosition1 + " with "+ experience1 +"  Experience Prescribed Qualification " + skillsNeeded1;

    }

    public String twoCardIn(){


        String noOfPosts = noOfPosts0E.getText().toString();
        String postPosition = postPosition0E.getText().toString();
        String experience = experience0E.getText().toString();
        String skillsNeeded = skillsNeeded0E.getText().toString();

        String noOfPosts2 = noOfPosts2E.getText().toString();
        String postPosition2 = postPosition2E.getText().toString();
        String experience2 = experience2E.getText().toString();
        String skillsNeeded2 = skillsNeeded2E.getText().toString();

        // Toast.makeText(getActivity(), "Executing 2 CardsIn", //Toast.LENGTH_SHORT).show();


        return "Vacancy Of " + noOfPosts + " " + gender0 + "(s) for The Position Of " + postPosition + " with "+ experience +"  Experience Prescribed Qualification " + skillsNeeded + " 3xt3 " +
                "Vacancy Of " + noOfPosts2 + " " + gender2 + "(s) for The Position Of " + postPosition2 + " with "+ experience2 +"  Experience Prescribed Qualification " + skillsNeeded2;

    }

    public String threeCard(){


        String noOfPosts = noOfPosts0E.getText().toString();
        String postPosition = postPosition0E.getText().toString();
        String experience = experience0E.getText().toString();
        String skillsNeeded = skillsNeeded0E.getText().toString();

        String noOfPosts1 = noOfPosts1E.getText().toString();
        String postPosition1 = postPosition1E.getText().toString();
        String experience1 = experience1E.getText().toString();
        String skillsNeeded1 = skillsNeeded1E.getText().toString();

        String noOfPosts2 = noOfPosts2E.getText().toString();
        String postPosition2 = postPosition2E.getText().toString();
        String experience2 = experience2E.getText().toString();
        String skillsNeeded2 = skillsNeeded2E.getText().toString();
        //Toast.makeText(getActivity(), "Executing 3 Cards", //Toast.LENGTH_SHORT).show();


        return  "Vacancy Of "+ noOfPosts + " " + gender0 + "(s) for The Position Of " + postPosition +  " with "+ experience + " Experience Prescribed Qualification " + skillsNeeded + " 3xt3 " +
                "Vacancy Of "+ noOfPosts1+ " " + gender1 + "(s) for The Position Of " + postPosition1 + " with "+ experience1 +" Experience Prescribed Qualification " + skillsNeeded1 + " 3xt3 " +
                "Vacancy Of "+ noOfPosts2+ " " + gender2 + "(s) for The Position Of " + postPosition2 + " with "+ experience2 +" Experience Prescribed Qualification " + skillsNeeded2;


    }


    void inItViews(View v){
        flagAddlayout = (LinearLayout) v.findViewById(R.id.flagPostBanner);
        postBottomFliper = (ViewFlipper)v.findViewById(R.id.postBottomFlipper);
        flagPostFlipper = (ViewFlipper)v.findViewById(R.id.flagPostFlipper);

        postCatagoryLL = (LinearLayout) v.findViewById(R.id.postCatagoryLL);
        postTypeLL = (LinearLayout) v.findViewById(R.id.postTypeLL);

        selectedCatagory = (TextView) v.findViewById(R.id.selectedCatagory);

        cataArrow = (ImageView) v.findViewById(R.id.cataArrow);
        typeArrow = (ImageView) v.findViewById(R.id.typeArrow);

        catagoryList = (ListView) v.findViewById(R.id.catagoryListView);
        typeList = (ListView) v.findViewById(R.id.typeListView);


        postFinally = (TextView) v.findViewById(R.id.postFinally);
        cancelPost = (TextView) v.findViewById(R.id.cancelPost);
        postAdd = (LinearLayout)v.findViewById(R.id.addPost);
        flagAdd = (LinearLayout)v.findViewById(R.id.flagAdd);
        male0 = (RadioButton)v.findViewById(R.id.male0);
        female0 = (RadioButton)v.findViewById(R.id.female0);
        any0 = (RadioButton)v.findViewById(R.id.any0);
        male1 = (RadioButton)v.findViewById(R.id.male1);
        female1 = (RadioButton)v.findViewById(R.id.female1);
        any1 = (RadioButton)v.findViewById(R.id.any1);
        male2 = (RadioButton)v.findViewById(R.id.male2);
        female2 = (RadioButton)v.findViewById(R.id.female2);
        any2 = (RadioButton)v.findViewById(R.id.any2);
        card0 = (CardView)v.findViewById(R.id.card0);
        card1 = (CardView)v.findViewById(R.id.card1);
        card2 = (CardView)v.findViewById(R.id.card2);
        close1 = (ImageView)v.findViewById(R.id.close1);
        close2 = (ImageView) v.findViewById(R.id.close2);

        sendingPostFlipper = (ViewFlipper) v.findViewById(R.id.sendingPostFlipper);

        postCatagoryLL = (LinearLayout) v.findViewById(R.id.postCatagoryLL);
        postTypeLL = (LinearLayout) v.findViewById(R.id.postTypeLL);


        postCatagoryLL.setOnClickListener(this);
        postTypeLL.setOnClickListener(this);

    }


    void onClickListners(){

        male0.setOnClickListener(this);
        female0.setOnClickListener(this);
        any0.setOnClickListener(this);


        postFinally.setOnClickListener(this);
        cancelPost.setOnClickListener(this);

        male1.setOnClickListener(this);
        female1.setOnClickListener(this);
        any1.setOnClickListener(this);


        male2.setOnClickListener(this);
        female2.setOnClickListener(this);
        any2.setOnClickListener(this);

        close1.setOnClickListener(this);
        close2.setOnClickListener(this);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                card0.startAnimation(animation);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        card0.setVisibility(View.VISIBLE);
                    }
                },500);

            }

        },1000);



    }


    public void visitEditTexts(View v){


        noOfPosts0E = (EditText) v.findViewById(R.id.noOfPosts0);
        noOfPosts1E = (EditText) v.findViewById(R.id.noOfPosts1);
        noOfPosts2E = (EditText) v.findViewById(R.id.noOfPosts2);

        postPosition0E = (EditText) v.findViewById(R.id.postPosition0);
        postPosition1E = (EditText) v.findViewById(R.id.postPosition1);
        postPosition2E = (EditText) v.findViewById(R.id.postPosition2);

        experience0E  = (EditText) v.findViewById(R.id.experience0);
        experience1E  = (EditText) v.findViewById(R.id.experience1);
        experience2E  = (EditText) v.findViewById(R.id.experience2);

        skillsNeeded0E = (EditText) v.findViewById(R.id.skillsNeeded0);
        skillsNeeded1E = (EditText) v.findViewById(R.id.skillsNeeded1);
        skillsNeeded2E = (EditText) v.findViewById(R.id.skillsNeeded2);



    }



    @Override
    public void onClick(View v) {




        switch (v.getId()){



            case R.id.cancelPost:




                flagAddlayout.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.md_styled_slide_down_slow));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        flagAddlayout.setVisibility(View.GONE);

                    }
                },500);

                break;

            case R.id.postFinally:

                addCatagory = "Corporate";

                if (addCatagory.equals("0") && addType.equals("0")){
                    Snackbar.make(v,"Please Select Your Add Category and type",Snackbar.LENGTH_LONG).show();

                }else if ( addCatagory != "0" && addType.equals("0")){
                    Snackbar.make(v,"Please Select Your Add Type",Snackbar.LENGTH_LONG).show();
                }else if ( addCatagory != "0" && addType != "0" ){
                    new SendPost().execute();
                }


                break;


            case R.id.male0:

                male0.setChecked(true);
                female0.setChecked(false);
                any0.setChecked(false);

                gender0 = "male";

                break;

            case R.id.female0:

                female0.setChecked(true);
                male0.setChecked(false);
                any0.setChecked(false);

                gender0 = "female";
                break;


            case R.id.any0:
                any0.setChecked(true);
                female0.setChecked(false);
                male0.setChecked(false);

                gender0 = "individual";

                break;


            case R.id.male1:

                male1.setChecked(true);
                female1.setChecked(false);
                any1.setChecked(false);

                gender1 = "male";
                break;


            case R.id.female1:

                female1.setChecked(true);
                male1.setChecked(false);
                any1.setChecked(false);
                gender1 = "female";
                break;



            case R.id.any1:
                any1.setChecked(true);
                female1.setChecked(false);
                male1.setChecked(false);
                gender1 = "individual";

                break;


            case R.id.male2:

                male2.setChecked(true);
                female2.setChecked(false);
                any2.setChecked(false);
                gender2 = "male";
                break;


            case R.id.female2:

                female2.setChecked(true);
                male2.setChecked(false);
                any2.setChecked(false);

                gender2 = "female";
                break;


            case R.id.any2:
                any2.setChecked(true);
                female2.setChecked(false);
                male2.setChecked(false);

                gender2 = "individual";

                break;


            case R.id.close1:

                card1.setVisibility(View.GONE);
                break;

            case R.id.close2:
                card2.setVisibility(View.GONE);
                break;


            case R.id.postCatagoryLL:

                //postCatagoryLL.setBackgroundColor(R.color.vectorColor);
                cataArrow.setVisibility(View.VISIBLE);
                typeArrow.setVisibility(View.INVISIBLE);
                postTypeLL.setBackgroundColor(Color.WHITE);
                categoryAdapter = new FilterListAdapter(getActivity(),catagoryArray);
                catagoryList.setAdapter(categoryAdapter);
                catagoryList.setVisibility(View.VISIBLE);
                typeList.setVisibility(View.GONE);

                typeAdapter = new FilterListAdapter(getActivity(),catagoryArray);
                typeList.setAdapter(typeAdapter);


                catagoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        addCatagory = catagoryArray[position];
                        addType = govermentTypes[0];

                        if (addCatagory.equals("Government")){
                            typeAdapter = new FilterListAdapter(getActivity(),govermentTypes);
                        }else if (addCatagory.equals("Corporate")){
                            typeAdapter = new FilterListAdapter(getActivity(),corporateTypes);

                        }
                        typeList.setAdapter(typeAdapter);


                        Toast.makeText(getActivity(), "Catagory Selected :"+addCatagory, Toast.LENGTH_SHORT).show();
                        selectedCatagory.setText("Category Selected : "+addCatagory);


                    }
                });





                break;

            case R.id.postTypeLL:

                //setting Caatagory corporate
                addCatagory = "Corporate";


                if (addCatagory.equals("0")){
                    Snackbar.make(v,"Please Select Your Add Category First",Snackbar.LENGTH_LONG).show();
                    break;
                }else if (addCatagory.equals("Government")){
                    typeAdapter = new FilterListAdapter(getActivity(),govermentTypes);
                    typeList.setAdapter(typeAdapter);
                }else if (addCatagory.equals("Corporate")){
                    typeAdapter = new FilterListAdapter(getActivity(),corporateTypes);
                    typeList.setAdapter(typeAdapter);
                }else if (addCatagory.equals("All Categories")){
                    Snackbar.make(v,"Please Select Your Add Category First",Snackbar.LENGTH_LONG).show();
                    break;
                }


                postCatagoryLL.setBackgroundColor(Color.WHITE);
                cataArrow.setVisibility(View.INVISIBLE);
                typeArrow.setVisibility(View.VISIBLE);
                //postTypeLL.setBackgroundColor(R.color.line);

                catagoryList.setVisibility(View.GONE);
                typeList.setVisibility(View.VISIBLE);

                break;



            case R.id.flagAdd:

                InputMethodManager im;
                im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(postPosition0E.getWindowToken(),0);

                typeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        ///////////////////////////////////////////////

                        InputMethodManager im;
                        im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        im.hideSoftInputFromWindow(postPosition0E.getWindowToken(),0);


                        if (addCatagory.equals("All Categories")){


                            addType = govermentTypes[0];
                            selectedCatagory.setText("Category Selected :"+addCatagory+" Type Selected "+addType);
                            Toast.makeText(getActivity(), "Category Selected :"+addCatagory+" Type Selected "+addType, Toast.LENGTH_SHORT).show();

                        } else if (addCatagory.equals("Government")){

                            addType = govermentTypes[position];
                            selectedCatagory.setText("Category Selected :"+addCatagory+" Type Selected "+addType);
                            Toast.makeText(getActivity(), "Category Selected :"+addCatagory+" Type Selected "+addType, Toast.LENGTH_SHORT).show();


                        }else if (addCatagory.equals("Corporate")){

                            addType = corporateTypes[position];
                            selectedCatagory.setText("Category Selected :"+addCatagory+" Type Selected "+addType);
                            Toast.makeText(getActivity(), "Type Selected "+addType, Toast.LENGTH_SHORT).show();


                        }




                    }
                });



                if (card0.getVisibility() == View.VISIBLE && card1.getVisibility() == View.GONE && card2.getVisibility() == View.GONE ){


                    add = oneCard();
                    //Toast.makeText(PostAdd.this, ""+add, //Toast.LENGTH_SHORT).show();

                    companyAdd = getActivity().getSharedPreferences(COMPANY_ADD,0);
                    editor = companyAdd.edit();
                    editor.putString("add",add);
                    editor.commit();

                    //   new SendPost().execute();

                    postBanner();


                }


                else  if (card0.getVisibility() == View.VISIBLE && card1.getVisibility() == View.VISIBLE && card2.getVisibility() == View.GONE ){



                    add = twoCard();
                    //Toast.makeText(PostAdd.this, ""+add, //Toast.LENGTH_SHORT).show();

                    companyAdd = getActivity().getSharedPreferences(COMPANY_ADD,0);
                    editor = companyAdd.edit();
                    editor.putString("add",add);
                    editor.commit();

//                    new SendPost().execute();

                    postBanner();


                }


                else if (card0.getVisibility() == View.VISIBLE && card1.getVisibility() == View.VISIBLE && card2.getVisibility() == View.VISIBLE ){

                    add = threeCard();
                    //Toast.makeText(PostAdd.this, ""+add, //Toast.LENGTH_SHORT).show();

                    companyAdd = getActivity().getSharedPreferences(COMPANY_ADD,0);
                    editor = companyAdd.edit();
                    editor.putString("add",add);
                    editor.commit();

                    // new SendPost().execute();

                    postBanner();




                }

                else if (card0.getVisibility() == View.VISIBLE && card1.getVisibility() == View.GONE && card2.getVisibility() == View.VISIBLE ){

                    add = twoCardIn();
                    //Toast.makeText(PostAdd.this, ""+add, //Toast.LENGTH_SHORT).show();



                    postBanner();

                }







                break;








        }


    }




    class SendPost extends AsyncTask<String , String ,String> {


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairList = new ArrayList<>();

        String COMPANY_DETAILS = "myCompanyDetails";
        SharedPreferences myCompanyDetails;


        @Override
        protected String doInBackground(String... params) {

            myCompanyDetails = getActivity().getSharedPreferences(COMPANY_DETAILS, 0);
            companyAdd = getActivity().getSharedPreferences(COMPANY_ADD, 0);


            nameValuePairList.add(new BasicNameValuePair("companyTitle", myCompanyDetails.getString("companyName", "noTitle")));
            nameValuePairList.add(new BasicNameValuePair("jobInfo", companyAdd.getString("add", "Empty Add")));
            nameValuePairList.add(new BasicNameValuePair("addCatagory", addCatagory));
            nameValuePairList.add(new BasicNameValuePair("addType", addType));

            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host) + "/post_add/post_add.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();


                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";
                while ((str = bufferedReader.readLine()) != null) {

                    builder.append(str);

                }


            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }


            return builder.toString();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            flagPostFlipper.showNext();

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("BUGY",s);

            if (s.contains("0")) {
                Toast.makeText(getActivity(), "Network Failure, Please try Again", Toast.LENGTH_SHORT).show();
                flagPostFlipper.showNext();

            } else if (s.contains("1")) {
                flagAddlayout.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.md_styled_slide_down_slow));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        flagAddlayout.setVisibility(View.GONE);

                    }
                }, 500);

                Toast.makeText(getActivity(), "Advertisement Posted Successfully", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getActivity(), MyCompanyPosts.class));
                        getActivity().finish();

                    }
                }, 200);


            } else if (s.contains("exists")) {
                Toast.makeText(getActivity(), "You have already posted the same Advertisement", Toast.LENGTH_SHORT).show();
                flagPostFlipper.showNext();

            } else if (s.contains("crook")) {
                Toast.makeText(getActivity(), "Your Post Facility has been disabled, Please Contact our Customer Care ", Toast.LENGTH_SHORT).show();
                flagPostFlipper.showNext();

            } else {

                Toast.makeText(getActivity(), "We are having Trouble connecting to the Internet", Toast.LENGTH_SHORT).show();
                flagPostFlipper.showNext();


            }


        }

    }






}

