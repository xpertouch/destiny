package corp.burenz.expertouch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.mikepenz.iconics.Iconics;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.ExpertCard;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

import static corp.burenz.expertouch.R.anim;
import static corp.burenz.expertouch.R.id;
import static corp.burenz.expertouch.R.layout;
import static corp.burenz.expertouch.R.string;

public class SkilledExperts extends AppCompatActivity {


    String LOCAL_APP_DATA = "userInformation";
    SharedPreferences userData;
    Button retrySkilledButton;
    RecyclerView skilledRV;
    RecyclerView.Adapter adapter;

    ViewFlipper filterStateFlipper,filterTypeFlipper;
    ImageButton setStateFilter,setTypeFilter;

    TextView noSkillTitle,noSkillSubtitle;
    LinearLayout noConnectionSkilled;
    // Majour Filter Layouts
    LinearLayout filterStateSwitcher,filterStateLayout;

    //switcher Layouts
    LinearLayout filterTypeLayout,filterTypeSwitcher,noExpertLL;
    Spinner spinStates,spinTypes;
    String selectedState,selectedType;
    TextView typeLine,stateLine,currentFilterState,currentFilterType;
    String selectedSkill;
    TextView skillTitle,skillSubtitle;
    static String filterResult;
    String visibilty;

    RelativeLayout skileldProgress;

    ArrayList<String> expertNames;
    ArrayList<String> expertMainExpertise;
    ArrayList<String> exeprtCityArray;
    ArrayList<String> expertCallStatus;
    ArrayList<String> expertSkills;
    ArrayList<String> expertPic;
    ArrayList<String> expertId;
    SharedPreferences introduceMe;
    String INTRODUCE_ME = "introduction";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_skilled);

        Bundle bundle = getIntent().getExtras();
        noSkillTitle = (TextView) findViewById(id.noSkillTitile);
        noSkillSubtitle = (TextView) findViewById(id.noSkillSubtitle);
        retrySkilledButton = (Button) findViewById(id.retrySkilledBtnn);
        noConnectionSkilled = (LinearLayout) findViewById(id.noConnectionSkill);
        if (bundle == null) {
            Toast.makeText(SkilledExperts.this, "Please Refresh the page", Toast.LENGTH_SHORT).show();
            return;
        }

        Iconics.init(SkilledExperts.this);
        selectedSkill = bundle.getString("Context");
        expertNames = new ArrayList<>();
        expertMainExpertise = new ArrayList<>();
        exeprtCityArray = new ArrayList<>();
        expertCallStatus = new ArrayList<>();
        expertSkills = new ArrayList<>();
        expertId = new ArrayList<>();
        expertPic = new ArrayList<String>();

        introduceMe = getSharedPreferences(INTRODUCE_ME, 0);
        if (!introduceMe.getBoolean("tappedType", false)) {
            new MaterialTapTargetPrompt.Builder(SkilledExperts.this)
                    .setTarget(findViewById(id.filterTypeSwitcher))
                    .setAutoDismiss(false)
                    .setPrimaryText("Surf various type of " + selectedSkill)
                    .setSecondaryText("Tap the icon to start exploring Various types of " + selectedSkill)
                    .setOnHidePromptListener(new MaterialTapTargetPrompt.OnHidePromptListener() {
                        @Override
                        public void onHidePrompt(MotionEvent event, boolean tappedTarget) {
                            //Do something such as storing a value so that this prompt is never shown again

                        }

                        @Override
                        public void onHidePromptComplete() {

                        }
                    })
                    .show();

        }

                retrySkilledButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) SkilledExperts.this.getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnected()) {
                        noConnectionSkilled.startAnimation(AnimationUtils.loadAnimation(SkilledExperts.this, R.anim.fab_close));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                noConnectionSkilled.setVisibility(View.GONE);
                                noExpertLL.setVisibility(View.GONE);
                                new GetExperts(SkilledExperts.this, skilledRV).execute();
                            }
                        }, 300);

                    } else {

                        skilledRV.setVisibility(View.GONE);
                        noExpertLL.setVisibility(View.GONE);
                        noConnectionSkilled.startAnimation(AnimationUtils.loadAnimation(SkilledExperts.this, anim.shakeanim));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                noConnectionSkilled.setVisibility(View.VISIBLE);
                            }
                        }, 300);


                    }


                }
            });



            userData = getSharedPreferences(LOCAL_APP_DATA, 0);
            selectedState = userData.getString("userState", "All States");
            selectedType = "All Categories";
            if (userData.getBoolean("COMPANY", false)) {

                noSkillTitle.setText("No Experts Found");
                noSkillSubtitle.setText("Looks like there are no expert Profiles in this category");

            }
            setupVariables();

            skilledRV = (RecyclerView) findViewById(id.skilledRV);

            //setting Action bar Title and Subtitle
            setupFrameVariables();

            skillTitle.setText(selectedSkill);
            skillSubtitle.setText(selectedSkill + " from India");

            //TextViews

            skilledRV.setLayoutManager(new LinearLayoutManager(SkilledExperts.this));

            ConnectivityManager connectivityManager = (ConnectivityManager) SkilledExperts.this.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                noConnectionSkilled.setVisibility(View.GONE);
                noExpertLL.setVisibility(View.GONE);
                new GetExperts(SkilledExperts.this, skilledRV).execute();
            } else {
                skilledRV.setVisibility(View.GONE);
                noExpertLL.setVisibility(View.GONE);
                noConnectionSkilled.startAnimation(AnimationUtils.loadAnimation(SkilledExperts.this, anim.shakeanim));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noConnectionSkilled.setVisibility(View.VISIBLE);
                    }
                }, 300);


            }

            currentFilterState.setText(selectedState);
            currentFilterType.setText(selectedType);


            // Filter enabling Layouts


            setType(bundle.getString("Context"));

            filterStateSwitcher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    introduceMe = getSharedPreferences(INTRODUCE_ME,0);
                    SharedPreferences.Editor editor;
                    editor = introduceMe.edit();
                    editor.putBoolean("tappedState",true);
                    editor.commit();


                    if (filterTypeLayout.getVisibility() == View.VISIBLE) {

                        filterTypeLayout.setVisibility(View.GONE);
                        stateLine.setVisibility(View.VISIBLE);

                    } else {
                        filterTypeLayout.setVisibility(View.VISIBLE);
                    }


                    filterStateFlipper.showNext();
                }
            });


            filterTypeSwitcher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    introduceMe = getSharedPreferences(INTRODUCE_ME,0);
                    SharedPreferences.Editor editor;
                    editor = introduceMe.edit();
                    editor.putBoolean("tappedType",true);
                    editor.apply();


                    if (filterStateLayout.getVisibility() == View.VISIBLE) {

                        filterStateLayout.setVisibility(View.GONE);
                        typeLine.setVisibility(View.VISIBLE);

                    } else {
                        filterStateLayout.setVisibility(View.VISIBLE);
                    }


                    filterTypeFlipper.showNext();
                }
            });


            // these are visibility setting ImageButtons


            setStateFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // set State here
                    filterTypeLayout.setVisibility(View.VISIBLE);

                    typeLine.setVisibility(View.INVISIBLE);
                    stateLine.setVisibility(View.INVISIBLE);

                    filterStateFlipper.showNext();
                    selectedState = spinStates.getSelectedItem().toString();

                    currentFilterState.setText(selectedState);
                    // Get New List

                    ConnectivityManager connectivityManager = (ConnectivityManager) SkilledExperts.this.getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnected()) {
                        noConnectionSkilled.setVisibility(View.GONE);
                        noExpertLL.setVisibility(View.GONE);
                        new GetExperts(SkilledExperts.this, skilledRV).execute();
                    } else {
                        skilledRV.setVisibility(View.GONE);
                        noExpertLL.setVisibility(View.GONE);
                        noConnectionSkilled.startAnimation(AnimationUtils.loadAnimation(SkilledExperts.this, anim.shakeanim));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                noConnectionSkilled.setVisibility(View.VISIBLE);
                            }
                        }, 300);


                    }


                }
            });

            setTypeFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    if (!introduceMe.getBoolean("tappedState", false)) {
                        new MaterialTapTargetPrompt.Builder(SkilledExperts.this)
                                .setTarget(findViewById(id.filterSwitch))
                                .setPrimaryText("Surf Experts from Other States as well")
                                .setSecondaryText("Tap the icon to See experts from other States")
                                .setAutoDismiss(false)
                                .setOnHidePromptListener(new MaterialTapTargetPrompt.OnHidePromptListener() {
                                    @Override
                                    public void onHidePrompt(MotionEvent event, boolean tappedTarget) {
                                        //Do something such as storing a value so that this prompt is never shown again

                                    }

                                    @Override
                                    public void onHidePromptComplete() {

                                    }
                                })
                                .show();
                    }

                        filterStateLayout.setVisibility(View.VISIBLE);
                    typeLine.setVisibility(View.INVISIBLE);
                    stateLine.setVisibility(View.INVISIBLE);

                    filterTypeFlipper.showNext();
                    selectedType = spinTypes.getSelectedItem().toString();

                    currentFilterType.setText(selectedType);
                    //Get The New List
                    ConnectivityManager connectivityManager = (ConnectivityManager) SkilledExperts.this.getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnected()) {
                        noConnectionSkilled.setVisibility(View.GONE);
                        noExpertLL.setVisibility(View.GONE);
                        new GetExperts(SkilledExperts.this, skilledRV).execute();
                    } else {
                        skilledRV.setVisibility(View.GONE);
                        noExpertLL.setVisibility(View.GONE);
                        noConnectionSkilled.startAnimation(AnimationUtils.loadAnimation(SkilledExperts.this, anim.shakeanim));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                noConnectionSkilled.setVisibility(View.VISIBLE);
                            }
                        }, 300);


                    }


                }
            });




    }




    void setType(String type){

        ArrayAdapter<String> typeAdapter;



        String []lawyerTypes = {

                "All Categories"
                ,"Employment Lawyer"
                ,"Family Lawyer"
                ,"General Practice Lawyer"
                ,"Bankruptcy Lawyer"
                ,"Commercial Bankruptcy Lawyer"
                ,"Corporate Lawyer"
                ,"Criminal Lawyer"
                ,"Deportation Lawyer"
                ,"DUI Lawyer"
                ,"Inheritance Lawyer"
                ,"Spousal Support Lawyer"
                ,"Discrimination Lawyer"
                ,"Wrongful Death Lawyer"
                ,"Birth Injury Lawyer"
                ,"Health Insurance Lawyer"
                ,"Contract Lawyer"
                ,"Accident Lawyer"
                ,"Traffic Lawyer"
                ,"Others"



        };

        String []artizensType = {
                "All Categories"
                , "Welders",
                "Electricians",
                "Fitters",
                "Turners",
                "Millwrights",
                "Sheetmetal Workers",
                "Boilermakers",
                "Mechatronics",
                "Mechanics",
                "Woodworks",
                "Penlers",
                "Toolmakers",
                "Patternmakers",
                "Bricklayers",
                "Plumbers",
                "Maison",
                "Carpenters",
                "Joiners",
                "Shutterhands",
                "Steel fixers",
                "Glaziers",
                "Plasterers",
                "Tilers",
                "Sound technicians"
                ,"Painters",
                "Instrumentation and electronics technicians",
                "Others"

        };


        String []teacherTypes  = {
                "All Categories"
                , "Science",
                "Arts",
                "Commerce",
                "Physical Education",
                "Islamic Studies"
                ,"Others"
        };



        String []doctorsType = {
                "All Categories"
                ,"Audiologist"
                ,"Allergist"
                ,"Anesthesiologist"
                ,"Cardiologist"
                ,"Dentist"
                ,"Dermatologist"
                ,"Endocrinologist"
                ,"Gynecologist"
                ,"Immunologist"
                ,"Infectious Disease Specialist"
                ,"Internal Medicine Specialist"
                ,"Medical Geneticist"
                ,"Microbiologist"
                ,"Neonatologist"
                ,"Neurologist"
                ,"Neurosurgeon"
                ,"Obstetrician"
                ,"Oncologist"
                ,"Orthopedic Surgeon"
                ,"ENT Specialist"
                ,"Pediatrician"
                ,"Physiologist"
                ,"Plastic Surgeon"
                ,"Podiatrist"
                ,"Psychiatrist"
                ,"Radiologist"
                ,"Rheumatologist"
                ,"Surgeon"
                ,"Urologist"
                ,"Others"

        };




        String []mediaTypes  = {
                "All Categories",
                "Journalists",
                "Print Media",
                "Electronic Media",
                "News",
                "Agents"
                ,"Others"

        };


        String []healthTypes = {
                "All Categories",
                "Diagnostics",
                "Clinics",
                "Stores"
                ,"Others"

        };


        String []agentTypes = {
                "All Categories"
                ,"Insurance",
                "Travel",
                "Automobile",
                "Overseas",
                "Placement",
                "Haj and Umrah",
                "Pilgrimage"
                ,"Others"
        };

        String []contractorTypes = {
                "All Categories",
                "Government",
                "Private"
                ,"Others"
        };





        String []otherTypes = {
                "All Categories",
                "Wedding Planners",
                "Cooks",
                "Advisers",
                "Tourist Guides",
                "Others"

        };
        String []engineersType = {





                "All Categories",
                "Architectural & Building "
                ,"Chemical Engineering"
                ,"Civil Engineering"
                ,"Electrical Engineering"
                ,"Industrial Engineering"
                ,"Mechatronics Engineering"
                ,"Mechanical Engineering"
                ,"Metallurgical Engineering"
                ,"Computer Engineering"
                ,"Software Engineering"
                ,"Mathematical Engineering"
                ,"Others"

        };

        String []fashion = {
                  "All Categories"
                ,"Boutiques"
                , "Bridal Make Up"
                , "Mehandi expert"
                , "Hair Stylist"
                ,"Others"



        };

        String []allCategories = {
                "All Categories"
        };






        String []labours = {
                "All Categories"

        };

        String []mechanics= {

                "All Categories"
                ,"Mobile Phone",
                "Laptops",
                "Refrigerators",
                "Television",
                "Automobile"
                ,"Others"

        };

        String []accountants= {

                "All Categories"
                ,"Chartered Accountants",
                "Tax Consultants",
                "Accountants"
                ,"Others"

        };


        String []transporters = {

                "All Categories"
                ,"JCB's",
                "Trucks",
                "Load carriers",
                "Tippers",
                "Tractors",
                "Buses",
                "Matadors"
                ,"Others"

        };

        String []localTransport = {

                "All Categories"
                ,"Rikshaws",
                "Cabs"
                ,"Others"
        };


        String []orderSuppliesTypes= {

                "All Categories"
                ,"Stores",
                "Malls",
                "Agencies"
                ,"Others"
        };



        switch (type){


            case "Doctors":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,doctorsType);
                spinTypes.setAdapter(typeAdapter);
                break;

            case "Engineers":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,engineersType);
                spinTypes.setAdapter(typeAdapter);
                break;




            case "Cabs":
                    typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,localTransport);
                    spinTypes.setAdapter(typeAdapter);

                    break;

            case "Transporters":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,transporters);
                spinTypes.setAdapter(typeAdapter);

                break;
            case "Accountants":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,accountants);
                spinTypes.setAdapter(typeAdapter);

                break;
            case "Mechanics":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,mechanics);
                spinTypes.setAdapter(typeAdapter);

                break;
            case "Fashion":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,fashion);
                spinTypes.setAdapter(typeAdapter);

                break;
            case "Teachers":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,teacherTypes);
                spinTypes.setAdapter(typeAdapter);

                break;

            case "Labours":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,allCategories);
                spinTypes.setAdapter(typeAdapter);

                break;


            case "Software and Services":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,allCategories);
                spinTypes.setAdapter(typeAdapter);

                break;
            case "Lawyers":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,lawyerTypes);
                spinTypes.setAdapter(typeAdapter);

                break;

            case "Artisans":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,artizensType);
                spinTypes.setAdapter(typeAdapter);

                break;



            case "Media":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,mediaTypes);
                spinTypes.setAdapter(typeAdapter);

                break;
            case "Contractors":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,contractorTypes);
                spinTypes.setAdapter(typeAdapter);

                break;

            case "Health":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,healthTypes);
                spinTypes.setAdapter(typeAdapter);

                break;


            case "Agents":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,agentTypes);
                spinTypes.setAdapter(typeAdapter);

                break;

            case "Others":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,otherTypes);
                spinTypes.setAdapter(typeAdapter);

                break;

            case "General Order Suppliers":
                typeAdapter = new ArrayAdapter<String>(SkilledExperts.this, layout.spinner_adapter_black, id.spinnerTextBlack,orderSuppliesTypes);
                spinTypes.setAdapter(typeAdapter);

                break;








        }





    }



    void setupFrameVariables(){

        skillTitle = (TextView)findViewById(id.skillTitle);
        skillSubtitle = (TextView)findViewById(id.skillSubtitle);



    }
    void  setupVariables(){


        typeLine = (TextView)findViewById(id.typeLine);
        stateLine = (TextView)findViewById(id.stateLine);



        // Progress View
        skileldProgress = (RelativeLayout)findViewById(id.skilledProgress);

        currentFilterState = (TextView)findViewById(id.currentFilterState);
        currentFilterType = (TextView)findViewById(id.currentFilterType);


        //ImgaeButtons
        setStateFilter = (ImageButton)findViewById(id.setFilter);
        setTypeFilter = (ImageButton)findViewById(id.flipTypeButton);

        // Majour Layouts
        filterStateLayout = (LinearLayout)findViewById(id.filterState);
        filterTypeLayout = (LinearLayout)findViewById(id.filterType);

        //Switching Layouts
        filterStateSwitcher = (LinearLayout)findViewById(id.filterSwitch);
        filterTypeSwitcher = (LinearLayout)findViewById(id.filterTypeSwitcher);
        noExpertLL = (LinearLayout) findViewById(id.noExpertsLL);

        //Spinners
        spinStates = (Spinner)findViewById(id.spinStates);
        spinTypes = (Spinner)findViewById(id.typeSpinner);



        //Flippers
        filterStateFlipper = (ViewFlipper)findViewById(id.filterViewFlipper);
        filterTypeFlipper = (ViewFlipper)findViewById(id.filterTypeFlipper);



    }

    public class GetExperts extends AsyncTask< String, String, String >{


        Context context;
        JSONObject jsonObject;
        JSONArray jsonArray;


        RecyclerView recyclerView;

        StringBuilder line = new StringBuilder();
        BufferedReader bufferedReader;

        List<NameValuePair> param = new ArrayList<>();



        public GetExperts(Context context, RecyclerView recyclerView){

            this.context      = context;
            this.recyclerView = recyclerView;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();




            noConnectionSkilled.setVisibility(View.GONE);
            noExpertLL.setVisibility(View.GONE);
            noExpertLL.setVisibility(View.GONE);
            skileldProgress.setVisibility(View.VISIBLE);
            expertNames.clear();
            expertMainExpertise.clear();
            exeprtCityArray.clear();
            expertCallStatus.clear();
            expertSkills.clear();
            expertId.clear();
            expertPic.clear();

            skilledRV.setVisibility(View.GONE);
            skileldProgress.setVisibility(View.VISIBLE);


        }



        @Override
        protected String doInBackground(String... params) {

            userData = getSharedPreferences(LOCAL_APP_DATA,0);


                if(userData.getBoolean("EXPERT",false)){

                    visibilty = "public";

                }else if (userData.getBoolean("COMPANY",false)){

                    visibilty = "companies";
                }else{

                    visibilty = "public";
                }





            param.add(new BasicNameValuePair("userState",selectedState));
            param.add(new BasicNameValuePair("expertCategory",selectedSkill));
            param.add(new BasicNameValuePair("expertType",selectedType));
            param.add(new BasicNameValuePair("visibility",visibilty));

            //setupArrayLists();




            try{
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(getString(string.host)+"/hire/get_skilled.php");
                httpPost.setEntity(new UrlEncodedFormEntity(param));
                HttpResponse httpResponse = (HttpResponse)httpClient.execute(httpPost);
                HttpEntity httpEntity = (HttpEntity)httpResponse.getEntity();


                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while ((str = bufferedReader.readLine()) != null){

                    line.append(str);
                }


                jsonObject = new JSONObject();
                jsonArray = new JSONArray(line.toString());



                int length = jsonArray.length();


                for (int i = 0; i < length; i++){

                    jsonObject = jsonArray.getJSONObject(i);

                    expertNames.add(jsonObject.getString("expertsName"));
                    expertMainExpertise.add(jsonObject.getString("expertsMainExpertise"));
                    exeprtCityArray.add(jsonObject.getString("expertCity"));
                    expertCallStatus.add(jsonObject.getString("expertStatus"));
                    expertSkills.add(jsonObject.getString("expertSkills"));
                    expertPic.add(jsonObject.getString("expertPic"));
                    expertId.add(jsonObject.getString("expertId"));


                }













            }catch (JSONException e) {
                Log.e("Json Exception", "Some Issues" + e.toString());
            }
                catch (Exception e){

            }







        return line.toString();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ConnectivityManager connectivityManager = (ConnectivityManager) SkilledExperts.this.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()){

                skilledRV.setVisibility(View.VISIBLE);
                skileldProgress.setVisibility(View.GONE);

                adapter = new ExpertCard(SkilledExperts.this,expertId,expertPic,expertNames,expertMainExpertise,exeprtCityArray,expertCallStatus,expertSkills);


                if (expertId.size() == 0){
                    noExpertLL.startAnimation(AnimationUtils.loadAnimation(SkilledExperts.this, anim.shakeanim));
                    skilledRV.setVisibility(View.GONE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            noExpertLL.setVisibility(View.VISIBLE);
                        }
                    },300);

                }else{
                    // Setting stuff
                    skilledRV.setAdapter(adapter);

                }



            }else {
                skileldProgress.setVisibility(View.GONE);
                noExpertLL.setVisibility(View.GONE);
                skilledRV.setVisibility(View.GONE);
                noConnectionSkilled.startAnimation(AnimationUtils.loadAnimation(SkilledExperts.this, anim.shakeanim));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noConnectionSkilled.setVisibility(View.VISIBLE);
                    }
                },300);


            }




        }


    }
    void setupArrayLists(){


        expertId.add("12");
        expertId.add("13");
        expertId.add("14");
        expertId.add("15");
        expertId.add("16");
        expertId.add("17");
        expertId.add("18");


        expertPic.add("http://www.sample.com");
        expertPic.add("http://www.sample.com");
        expertPic.add("http://www.sample.com");
        expertPic.add("http://www.sample.com");
        expertPic.add("http://www.sample.com");
        expertPic.add("http://www.sample.com");

        expertNames.add("Expert #1");
        expertNames.add("Expert #2");
        expertNames.add("Expert #3");
        expertNames.add("Expert #4");
        expertNames.add("Expert #5");
        expertNames.add("Expert #6");


        expertMainExpertise.add("Front End Web Developer");
        expertMainExpertise.add("Back End CMS Developer");
        expertMainExpertise.add("Android Developer");
        expertMainExpertise.add("PHP Programmer");
        expertMainExpertise.add("jack of everything");
        expertMainExpertise.add("All Rounder");


        exeprtCityArray.add("Srinagar");
        exeprtCityArray.add("Anantnag");
        exeprtCityArray.add("Amsterdam");
        exeprtCityArray.add("Aukland");
        exeprtCityArray.add("Anantnag");
        exeprtCityArray.add("Amsterdam");
        exeprtCityArray.add("Aukland");



        expertCallStatus.add("Hello Folks, Call me only if you Really need an Expert or else please dont waste my time");
        expertCallStatus.add("Well i got plenty of time guys but just wait for three more days");
        expertCallStatus.add("Time and Tide waits for None");
        expertCallStatus.add("Sorry guys not available right Now");
        expertCallStatus.add("hear it Now i can work but not less than an hour");
        expertCallStatus.add("Give me an offer i cant refuse :)");

        expertSkills.add("Skill #1,Skill #2,Skill #3,Skill #4,Skill #6,Skill #7");
        expertSkills.add("Skill #1,Skill #2,Skill #3,Skill #4,Skill #6,Skill #7");
        expertSkills.add("Skill #1,Skill #2,Skill #3,Skill #4,Skill #6,Skill #7");
        expertSkills.add("Skill #1,Skill #2,Skill #3,Skill #4,Skill #6,Skill #7");
        expertSkills.add("Skill #1,Skill #2,Skill #3,Skill #4,Skill #6,Skill #7");
        expertSkills.add("Skill #1,Skill #2,Skill #3,Skill #4,Skill #6,Skill #7");


    }


    public void finishThis(View v){
        finish();
    }

}
