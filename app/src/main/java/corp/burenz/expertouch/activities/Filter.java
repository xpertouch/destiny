package corp.burenz.expertouch.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.FilterListAdapter;

public class Filter extends AppCompatActivity {
    ListView stateList,catagoryList,typeList;
    LinearLayout selectState,selectCatagory,selectType;
    ListAdapter adapterType;
    SharedPreferences feedsFilter;
    String CURRENT_FILTER = "feedsFilter";
    SharedPreferences.Editor editor;
    String selectedState,selectedCatagory,selectedType;
    Button applyFilter,cancelFilter;

    TextView filteredState,filteredCatagory,filteredType;
    TextView stateTV,catagoryTV,typeTV;
    LinearLayout stateActive,catagoryActive,typesActive;
    LinearLayout selectAFilter;





    final String []statesArray = {
            "All States"
            ,"Andhra Pradesh",
            "Arunachal Pradesh",
            "Assam",
            "Bihar",
            "Chandigarh",
            "Chhattisgarh",
            "Dadra and Nagar Haveli",
            "Daman and Diu",
            "Delhi",
            "Goa",
            "Gujarat",
            "Haryana",
            "Himachal Pradesh",
            "Jammu and Kashmir",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Lakshadweep",
            "Madhya Pradesh",
            "Maharashtra",
            "Manipur",
            "Meghalaya",
            "Mizoram",
            "Nagaland",
            "Odisha",
            "Puducherry",
            "Punjab",
            "Rajasthan",
            "Sikkim",
            "Tamil Nadu",
            "Telengana",
            "Tripura",
            "Uttar Pradesh",
            "Uttarakhand",
            "West Bengal"


    };


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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);



        feedsFilter = getSharedPreferences(CURRENT_FILTER,0);

        initViews();

        selectedState = feedsFilter.getString("currentState","All States");
        selectedCatagory = feedsFilter.getString("currentCatagory","All Categories");
        selectedType = feedsFilter.getString("currentType","All Types");
        selectAFilter  = (LinearLayout) findViewById(R.id.selectAFilter);







        stateActive.setVisibility(View.INVISIBLE);
        catagoryActive.setVisibility(View.INVISIBLE);
        typesActive.setVisibility(View.INVISIBLE);











//        final String doctorType[] = {
//
//                "All Categories",
//                "OrthoPadic Doctors",
//                "Dermatologist",
//                "Radiologist",
//                "Gynoclologist",
//                "General Physician",
//                "Surgon"
//
//
//        };
//
//        final String governmentType[] = {
//                "All Categories",
//                "Revenue",
//                "Education",
//                "Police",
//                "Advocate",
//                "Engineering",
//                "Others"
//        };
//
//        final String engineersType[] = {
//                "All Categories",
//                "Computer Scinece",
//                "Mechanical",
//                "Civil",
//                "Electronics",
//                "Electrical",
//                "Others"
//        };
//
//
//        final String[] typeArray= {
//                "All Types",
//                "Software",
//                "Civil",
//                "Mechanical",
//                "Electronics",
//                "Electrical",
//                "IT",
//                "Ahamdabad",
//                "Dehli",
//                "jaipur",
//                "Jammu and Kasmir",
//                "Ahamdabad",
//                "Dehli",
//                "jaipur",
//                "Jammu and Kasmir",
//                "Ahamdabad",
//                "Dehli",
//                "jaipur",
//                "Jammu and Kasmir",
//                "Ahamdabad",
//                "Dehli",
//                "jaipur",
//                "Jammu and Kasmir",
//                "Ahamdabad",
//                "Dehli",
//                "jaipur",
//                "Jammu and Kasmir",
//
//
//
//        };






        filteredState.setText(feedsFilter.getString("currentState","All States"));
        filteredCatagory.setText(feedsFilter.getString("currentCatagory","All Categories"));
        filteredType.setText(feedsFilter.getString("currentType","All Types"));




        ListAdapter adapterState = new FilterListAdapter(Filter.this,statesArray);
        stateList.setAdapter(adapterState);

        ListAdapter adapterCatagory = new FilterListAdapter(Filter.this,catagoryArray);
        catagoryList.setAdapter(adapterCatagory);




        stateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedState = statesArray[position];
                filteredState.setText(selectedState);
                filteredState.setTextColor(Color.argb(255,0,153,204));


                // taking user to category selection by changing the filter level
                /////////////////////////////////////////////////////////////////////////////
                selectAFilter.setVisibility(View.GONE);
                catagoryList.setVisibility(View.VISIBLE);
                stateList.setVisibility(View.GONE);
                typeList.setVisibility(View.GONE);


                // Color ProperTies

                selectState.setBackgroundColor(Color.TRANSPARENT);
                selectType.setBackgroundColor(Color.TRANSPARENT);
                selectCatagory.setBackgroundColor(Color.WHITE);

                stateTV.setTextColor(Color.BLACK);
                catagoryTV.setTextColor(Color.argb(255,0,153,204));
                typeTV.setTextColor(Color.BLACK);

                // active ICon

                stateActive.setVisibility(View.INVISIBLE);
                catagoryActive.setVisibility(View.VISIBLE);
                typesActive.setVisibility(View.INVISIBLE);

                ///////////////////////////////////////// selection code ends here




            }
        });

        catagoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedCatagory = catagoryArray[position];
                filteredCatagory.setText(selectedCatagory);
                filteredCatagory.setTextColor(Color.argb(255,0,153,204));

                selectedType = govermentTypes[0];
                filteredType.setText("All Types");
                filteredType.setTextColor(Color.argb(255,0,153,204));

                if (selectedCatagory.equals("All Categories")){
                    selectedType = govermentTypes[0];
                    filteredType.setText("All Types");
                    filteredType.setTextColor(Color.argb(255,0,153,204));
                }else if (selectedCatagory.equals("Government")){
                    adapterType = new FilterListAdapter(Filter.this,govermentTypes);
                    typeList.setAdapter(adapterType);

                }else if (selectedCatagory.equals("Corporate")){
                    adapterType = new FilterListAdapter(Filter.this,corporateTypes);
                    typeList.setAdapter(adapterType);

                }

                if (selectedCatagory.equals("All Categories")){

                    // mention the filter is selected here

                } else {

                    if (filteredCatagory.getText().equals("Government")) {
                        adapterType = new FilterListAdapter(Filter.this, govermentTypes);
                        typeList.setAdapter(adapterType);

                    } else if (filteredCatagory.getText().equals("Corporate")) {
                        adapterType = new FilterListAdapter(Filter.this, corporateTypes);
                        typeList.setAdapter(adapterType);

                    }


                    typeList.setVisibility(View.VISIBLE);
                    stateList.setVisibility(View.GONE);
                    catagoryList.setVisibility(View.GONE);

                    // Color properties
                    selectState.setBackgroundColor(Color.TRANSPARENT);
                    selectType.setBackgroundColor(Color.WHITE);
                    selectCatagory.setBackgroundColor(Color.TRANSPARENT);

                    stateTV.setTextColor(Color.BLACK);
                    catagoryTV.setTextColor(Color.BLACK);
                    typeTV.setTextColor(Color.argb(255, 0, 153, 204));


                    // active ICon

                    stateActive.setVisibility(View.INVISIBLE);
                    catagoryActive.setVisibility(View.INVISIBLE);
                    typesActive.setVisibility(View.VISIBLE);


                }











            }
        });





        typeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (selectedCatagory.equals("Government")){

                    selectedType = govermentTypes[position];
                    filteredType.setText(selectedType);
                    filteredType.setTextColor(Color.argb(255,0,153,204));

                }else if (selectedCatagory.equals("Corporate")){


                    selectedType = corporateTypes[position];
                    filteredType.setText(selectedType);
                    filteredType.setTextColor(Color.argb(255,0,153,204));
                }

            }
        });










        selectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stateList.setVisibility(View.VISIBLE);
                catagoryList.setVisibility(View.GONE);
                typeList.setVisibility(View.GONE);


                // Color Properties


                selectState.setBackgroundColor(Color.WHITE);
                selectType.setBackgroundColor(Color.TRANSPARENT);
                selectCatagory.setBackgroundColor(Color.TRANSPARENT);

                stateTV.setTextColor(Color.argb(255,0,153,204));
                catagoryTV.setTextColor(Color.BLACK);
                typeTV.setTextColor(Color.BLACK);

                // active Icon
                stateActive.setVisibility(View.VISIBLE);
                catagoryActive.setVisibility(View.INVISIBLE);
                typesActive.setVisibility(View.INVISIBLE);


            }
        });



        selectCatagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectAFilter.setVisibility(View.GONE);
                catagoryList.setVisibility(View.VISIBLE);
                stateList.setVisibility(View.GONE);
                typeList.setVisibility(View.GONE);


                // Color ProperTies

                selectState.setBackgroundColor(Color.TRANSPARENT);
                selectType.setBackgroundColor(Color.TRANSPARENT);
                selectCatagory.setBackgroundColor(Color.WHITE);

                stateTV.setTextColor(Color.BLACK);
                catagoryTV.setTextColor(Color.argb(255,0,153,204));
                typeTV.setTextColor(Color.BLACK);

                // active ICon

                stateActive.setVisibility(View.INVISIBLE);
                catagoryActive.setVisibility(View.VISIBLE);
                typesActive.setVisibility(View.INVISIBLE);




            }
        });



        selectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedCatagory.equals("All Categories")){
                    Snackbar.make(v,"Please Select a Category",Snackbar.LENGTH_SHORT).show();
                } else{

                    if (filteredCatagory.getText().equals("Government")){
                        adapterType = new FilterListAdapter(Filter.this,govermentTypes);
                        typeList.setAdapter(adapterType);

                    }else if (filteredCatagory.getText().equals("Corporate")){
                        adapterType = new FilterListAdapter(Filter.this,corporateTypes);
                        typeList.setAdapter(adapterType);

                    }


                    typeList.setVisibility(View.VISIBLE);
                    stateList.setVisibility(View.GONE);
                    catagoryList.setVisibility(View.GONE);

                    // Color properties
                    selectState.setBackgroundColor(Color.TRANSPARENT);
                    selectType.setBackgroundColor(Color.WHITE);
                    selectCatagory.setBackgroundColor(Color.TRANSPARENT);

                    stateTV.setTextColor(Color.BLACK);
                    catagoryTV.setTextColor(Color.BLACK);
                    typeTV.setTextColor(Color.argb(255,0,153,204));


                    // active ICon

                    stateActive.setVisibility(View.INVISIBLE);
                    catagoryActive.setVisibility(View.INVISIBLE);
                    typesActive.setVisibility(View.VISIBLE);

                }



            }
        });



        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = feedsFilter.edit();
                editor.putString("currentState",selectedState);
                editor.putString("currentCatagory",selectedCatagory);
                editor.putString("currentType",selectedType);
                editor.commit();
                Filter.this.finish();
            }
        });

        cancelFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filter.this.finish();
            }
        });






    }

    void initViews(){


        filteredState = (TextView)findViewById(R.id.filteredState);
        filteredCatagory  = (TextView)findViewById(R.id.filteredCatagory);
        filteredType = (TextView)findViewById(R.id.filteredType);


        stateTV = (TextView)findViewById(R.id.stateTV);
        catagoryTV = (TextView)findViewById(R.id.catagoryTV);
        typeTV = (TextView)findViewById(R.id.typesTV);



        stateList = (ListView) findViewById(R.id.stateListV);
        catagoryList = (ListView)findViewById(R.id.catagoryList);
        typeList = (ListView)findViewById(R.id.typeList);

        applyFilter = (Button)findViewById(R.id.applyFilter);
        cancelFilter = (Button)findViewById(R.id.cancelFilter);

        stateActive = (LinearLayout)findViewById(R.id.stateActive);
        catagoryActive = (LinearLayout)findViewById(R.id.catagoryActive);
        typesActive = (LinearLayout)findViewById(R.id.typesActive);

        selectState = (LinearLayout)findViewById(R.id.selectStateLayout);
        selectType = (LinearLayout)findViewById(R.id.selectTypeLayout);
        selectCatagory = (LinearLayout)findViewById(R.id.selectCatagoryLayout);






    }

}
