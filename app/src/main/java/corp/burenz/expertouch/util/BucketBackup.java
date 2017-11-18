package corp.burenz.expertouch.util;

/**
 * Created by xperTouch on 11/9/2016.
 */
public class BucketBackup {
//public class BucketAdapter extends RecyclerView.Adapter<BucketAdapter.MyBucketHolder> {
//
//
//
//
//    ArrayList<String> companyTitleArray,companyCityArray,saleTitleArray,saleDiscriptionArray,saleDateArray,saleBannerArray,saleIds,myLikeIds,totalLikes;
//    Context context;
//    Integer newCount;
//    String RESULT = "free",USER_EMAIL = "noemail@example.com";
//    int TIME_OUT = 1000;
//    int ADAPTER_POSITION  = 0;
//
//    public BucketAdapter(Context context,ArrayList<String> saleIds,ArrayList<String> companyTitleB,ArrayList<String> companyCity,ArrayList<String> saleTitle,ArrayList<String> saleDiscription,ArrayList<String> saleDate,ArrayList<String> saleBanner,ArrayList<String> myLikeIds,ArrayList<String> totalLikes){
//        this.context = context;
//        this.saleIds = saleIds;
//        this.companyTitleArray = companyTitleB;
//        this.companyCityArray = companyCity;
//        this.saleTitleArray = saleTitle;
//        this.saleDateArray = saleDate;
//        this.saleDiscriptionArray = saleDiscription;
//        this.saleBannerArray = saleBanner;
//        this.myLikeIds = myLikeIds;
//        this.totalLikes = totalLikes;
//    }
//
//
//    @Override
//    public MyBucketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bucket_adapter,parent,false);
//        SharedPreferences userData;
//        String LOCAL_APP_DATA = "userInformation";
//        userData = context.getSharedPreferences(LOCAL_APP_DATA,0);
//        USER_EMAIL = userData.getString("userEmail","noemail@example.com");
//        return new MyBucketHolder(v);
//
//    }
//
//
//    @Override
//    public void onBindViewHolder(MyBucketHolder oriHolder, final int position) {
//
//        final MyBucketHolder holder = (MyBucketHolder) oriHolder;
//        ViewFlipper thumbsFlipper;
//        final ImageButton thumbsUpAdd,thumbsDownAdd;
//
//        TextView companyTitleB;
//        TextView companyCity;
//        TextView saleTitle;
//        final TextView totalLikesTV;
//        final TextView saleDiscription;
//        final TextView saleDate;
//        final NetworkImageView saleBanner;
//        final ImageView companyProfileB;
//        final ImageView shareSaleB;
//        LinearLayout styleSheet;
//
//
//        companyTitleB = holder.companyTitleB;
//        companyCity = holder.companyCity;
//        saleTitle = holder.saleTitle;
//        saleDiscription = holder.saleDiscription;
//        saleDate = holder.saleDate;
//        saleBanner = holder.saleBanner;
//        companyProfileB = holder.companyProfileB;
//        shareSaleB = holder.shareSaleB;
//
//        styleSheet = holder.styleSheet;
//
//        thumbsUpAdd = holder.thumbsUpAdd;
//        thumbsFlipper = holder.thumbsFlipper;
//        thumbsDownAdd = holder.thumbsDownAdd;
//        totalLikesTV = holder.totalLikes;
//
//
//        totalLikesTV.setText(totalLikes.get(position));
//
//        ImageLoader imageLoader = MySingleton.getInstance(context).getImageLoader();
//        Log.e("ADAPt",""+position);
//        int FAV_FLAG = 0;
//
//        //  styleSheet.setBackgroundColor(0xFF000000+new Random().nextInt(0xFFFFFF));
//
//        for (int i = 0; i < myLikeIds.size();i++){
//            if (saleIds.get(position).equals(myLikeIds.get(i))){
//                thumbsDownAdd.setVisibility(View.VISIBLE);
//                thumbsUpAdd.setVisibility(View.GONE);
//                FAV_FLAG = 1;
//                break;
//            }
//
//        }
//
//
//
//        if (FAV_FLAG == 0){
//            thumbsUpAdd.setVisibility(View.VISIBLE);
//            thumbsDownAdd.setVisibility(View.GONE);
//        }
//
//
//
//
//        saleBanner.setImageUrl((String) saleBannerArray.get(position), imageLoader);
//
//        companyTitleB.setText(companyTitleArray.get(position));
//        companyCity.setText(companyCityArray.get(position));
//        saleTitle.setText(saleTitleArray.get(position));
//        saleDiscription.setText(saleDiscriptionArray.get(position));
//        saleDate.setText(saleDateArray.get(position));
//
//        shareSaleB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "In Progress", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        companyProfileB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(new Intent(context, CompanyProfile.class));
//            }
//        });
//
//
//
//
//        final MediaPlayer ourSplasSound =  MediaPlayer.create(context,R.raw.pop);
//
//        final ViewFlipper otherFipper = thumbsFlipper;
//
//        thumbsUpAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                new SortLikes().execute("/bucket/like_mech.php",saleIds.get(position));
//
//                ADAPTER_POSITION = holder.getAdapterPosition();
//
//                thumbsUpAdd.setVisibility(View.GONE);
//                thumbsDownAdd.setVisibility(View.VISIBLE);
//                ourSplasSound.start();
//                newCount = Integer.parseInt(totalLikesTV.getText().toString());
//                newCount++;
//                totalLikesTV.startAnimation(AnimationUtils.loadAnimation(context,R.anim.card_animation));
//                totalLikesTV.setText(String.valueOf(newCount));
//
//
//                thumbsDownAdd.setEnabled(false);
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (RESULT.contains("1")){
//                            try {
//                                Log.e("NEWCOUNT", saleIds.get(position));
//                                myLikeIds.add(saleIds.get(position));
//                                totalLikes.remove(position);
//                                totalLikes.add(position,totalLikesTV.getText().toString());
//                                Log.e("NEWCOUNT","liked new Count = " + myLikeIds.size());
//
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
//
//
//                        }else {
//
//
//                            thumbsUpAdd.setVisibility(View.VISIBLE);
//                            thumbsDownAdd.setVisibility(View.GONE);
//                            newCount = Integer.parseInt(totalLikesTV.getText().toString());
//                            newCount--;
//                            totalLikesTV.startAnimation(AnimationUtils.loadAnimation(context,R.anim.card_animation));
//                            totalLikesTV.setText(String.valueOf(newCount));
//
//                            Toast.makeText(context, "Connection Slow", Toast.LENGTH_SHORT).show();
//
//
//                        }
//
//                        thumbsDownAdd.setEnabled(true);
//
//                    }
//                },TIME_OUT);
//
//
//
//
//            }
//        });
//
//        thumbsDownAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                new SortLikes().execute("/bucket/unlike_mech.php",saleIds.get(position));
//
//                thumbsUpAdd.setVisibility(View.VISIBLE);
//                thumbsDownAdd.setVisibility(View.GONE);
//                newCount = Integer.parseInt(totalLikesTV.getText().toString());
//                newCount--;
//                totalLikesTV.startAnimation(AnimationUtils.loadAnimation(context,R.anim.card_animation));
//                totalLikesTV.setText(String.valueOf(newCount));
//
//                thumbsUpAdd.setEnabled(false);
//
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (RESULT.contains("1")){
//
//                            try {
//                                myLikeIds.remove(saleIds.get(position));
//                                totalLikes.remove(position);
//                                totalLikes.add(position,totalLikesTV.getText().toString());
//
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
//
//                            Log.e("NEWCOUNT","Unliked new Count = " + myLikeIds.size());
//
//                        }else {
//
//                            thumbsUpAdd.setVisibility(View.GONE);
//                            thumbsDownAdd.setVisibility(View.VISIBLE);
//                            newCount = Integer.parseInt(totalLikesTV.getText().toString());
//                            newCount++;
//                            totalLikesTV.startAnimation(AnimationUtils.loadAnimation(context,R.anim.card_animation));
//                            totalLikesTV.setText(String.valueOf(newCount));
//
//                            Toast.makeText(context, "Connection Slow", Toast.LENGTH_SHORT).show();
//
//
//
//                        }
//                        thumbsUpAdd.setEnabled(true);
//                    }
//                },TIME_OUT);
//
//            }
//        });
//
//
//
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return companyTitleArray.size();
//    }
//
//
//
//    static class MyBucketHolder extends RecyclerView.ViewHolder {
//
//        TextView companyTitleB,companyCity,saleTitle,saleDiscription,saleDate;
//        NetworkImageView saleBanner;
//        LinearLayout styleSheet;
//        ImageView companyProfileB,shareSaleB;
//        final private ViewFlipper thumbsFlipper;
//        final private ImageButton thumbsUpAdd,thumbsDownAdd;
//        TextView totalLikes;
//
//        public MyBucketHolder(View itemView) {
//            super(itemView);
//
//            companyTitleB = (TextView) itemView.findViewById(R.id.companyTitleB);
//            companyCity  = (TextView) itemView.findViewById(R.id.companyCityB);
//            saleTitle = (TextView) itemView.findViewById(R.id.saleTitle);
//            saleDate = (TextView) itemView.findViewById(R.id.saleDate);
//            saleDiscription  = (TextView) itemView.findViewById(R.id.saleDiscription);
//            saleBanner = (NetworkImageView) itemView.findViewById(R.id.saleBanner);
//            companyProfileB = (ImageView) itemView.findViewById(R.id.companyProfileB);
//            shareSaleB = (ImageView) itemView.findViewById(R.id.shareSalesB);
//            thumbsFlipper = (ViewFlipper) itemView.findViewById(R.id.thumbsFlipper);
//            thumbsUpAdd = (ImageButton) itemView.findViewById(R.id.thumbsUpAdd);
//            thumbsDownAdd = (ImageButton) itemView.findViewById(R.id.thumbsDownAdd);
//            totalLikes = (TextView) itemView.findViewById(R.id.totalLikesTV);
//            styleSheet = (LinearLayout) itemView.findViewById(R.id.styleSheet);
//        }
//    }
//
//    class SortLikes extends AsyncTask<String,String,String> {
//
//        StringBuilder builder = new StringBuilder();
//        BufferedReader bufferedReader;
//        List<NameValuePair> nameValuePairList = new ArrayList<>();
//
//
//        @Override
//        protected String doInBackground(String... params) {
//
//
//            // params[0] is like or unlike params[1] is saleId
//            nameValuePairList.add(new BasicNameValuePair("userEmail",USER_EMAIL));
//            nameValuePairList.add(new BasicNameValuePair("salesId",params[1] ));
//            Log.e("CATACOMB","userEmail "+USER_EMAIL);
//            Log.e("CATACOMB","hit "+params[0]);
//            Log.e("CATACOMB","salesId "+params[1]);
//            try {
//
//
//                HttpClient httpClient = new DefaultHttpClient();
//                HttpPost httpPost = new HttpPost(context.getString(R.string.host)+ params[0]);
//                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
//                HttpResponse httpResponse = httpClient.execute(httpPost);
//                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();
//
//
//                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
//                String str = "";
//                while ( (str = bufferedReader.readLine()  ) != null ){
//
//                    builder.append(str);
//
//                }
//
//
//
//            } catch (ClientProtocolException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//
//            return builder.toString();
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            RESULT = "busy";
//
//
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            s = "1";
//            if (s.contains("1")){
//                RESULT = "1";
//
//
//
//
//
//            }else if (s.contains("0")){
//
//                RESULT = "0";
//
//            }else{
//                RESULT = "failed";
//            }
//
//
//        }
//
//
//
//    }
//
//
//}
}
