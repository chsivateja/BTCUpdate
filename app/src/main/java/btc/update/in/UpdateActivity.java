package btc.update.in;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RemoteViews;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UpdateActivity extends AppCompatActivity implements ValueEventListener {
    private static FirebaseDatabase database;
    Intent mServiceIntent;

    private RecyclerView recyclerView;
    private BTCAdapter adapter;
    private List<BTC> btcList;
    private RecyclerView.LayoutManager mLayoutManager;
    private HashMap<String, Integer> thumbnail = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        thumbnail.put("bitlio", R.drawable.bitlio_logo);
        thumbnail.put("buyucoin", R.drawable.buyucoin_logo);
        thumbnail.put("coinsecure", R.drawable.coinsecure_logo);
        thumbnail.put("koinex", R.drawable.koinex_logo);
        thumbnail.put("localbitcoins", R.drawable.localbitcoins_logo);
        thumbnail.put("pocketbits", R.drawable.pocketbits_logo);
        thumbnail.put("throughbit", R.drawable.throughbit_logo);
        thumbnail.put("unocoin", R.drawable.unocoin_logo);
        thumbnail.put("zebpay", R.drawable.zebpay_logo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        btcList = new ArrayList<>();
        adapter = new BTCAdapter(this, btcList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        //mLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(mLayoutManager);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        final Account[] accounts = AccountManager.get(this).getAccounts();
        //Log.e("accounts","->"+accounts.length);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("BTCPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        List<String> emails = new ArrayList<String>();

        for (Account account : accounts) {
            if (Patterns.EMAIL_ADDRESS.matcher(account.name).matches()) {
                emails.add(account.name);
            }
        }
        Set<String> set = new HashSet<String>();
        set.addAll(emails);
        editor.putStringSet("emails", set);
        editor.commit();
        if (database == null) {
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }

        DatabaseReference myRef = database.getReference("btc");
        myRef.addValueEventListener(this);
        prepareBTC();

        try {
            //Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                return true;
            case R.id.action_signout:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                startActivity(new Intent(getApplicationContext(), AuthActivity.class));
                                finish();
                            }
                        });
                return true;
            case R.id.action_exit:
                finish();
                System.exit(0);

            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }

    /**
     * Adding few BTC for testing
     */
    private void prepareBTC() {
//        HashMap<String,Integer> thumbnail = new HashMap<String,Integer>();
//        thumbnail.put("bitlio",R.drawable.bitlio_logo);
//        thumbnail.put("buyucoin",R.drawable.buyucoin_logo);
//        thumbnail.put("coinsecure",R.drawable.coinsecure_logo);
//        thumbnail.put("koinex",R.drawable.koinex_logo);
//        thumbnail.put("localbitcoins",R.drawable.localbitcoins_logo);
//        thumbnail.put("pocketbits",R.drawable.pocketbits_logo);
//        thumbnail.put("throughbit",R.drawable.throughbit_logo);
//        thumbnail.put("unocoin",R.drawable.unocoin_logo);
//        thumbnail.put("zebpay",R.drawable.zebpay_logo);
//        Integer tn = 0;
//        tn=thumbnail.get("bitlio");
//        BTC a = new BTC("zebpay","11111","222ff22","http://www.google.com",tn);
//        btcList.add(a);
//        tn=thumbnail.get("buyucoin");
//        a = new BTC("zebpay","11111","222ff22","http://www.google.com",tn);
//        btcList.add(a);
//        tn=thumbnail.get("coinsecure");
//        a = new BTC("zebpay","11111","222ff22","http://www.google.com",tn);
//        btcList.add(a);
//        tn=thumbnail.get("koinex");
//        a = new BTC("zebpay","11111","22222","http://www.google.com",tn);
//        btcList.add(a);


        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        btcList.clear();
        BTC h = new BTC();
        h.setSell("-1");
        BTC l = new BTC();
        l.setBuy("99999999999");
        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            BTC b = ds.getValue(BTC.class);
            b.setThumbnail(thumbnail.get(ds.getKey()));
            if (b.getRef().isEmpty())
                b.setRef("http://www.google.com");
            if (Double.parseDouble(b.getSell()) > Double.parseDouble(h.getSell()))
                h = b;
            if (Double.parseDouble(b.getBuy()) < Double.parseDouble(l.getBuy()))
                l = b;
            btcList.add(b);
            Log.d("H", h.getName() + "  " + h.getBuy() + " " + h.getSell());
            Log.d("L", l.getName() + "  " + l.getBuy() + " " + l.getSell());
        }
        btcList.remove(h);
        btcList.add(0, h);
        btcList.remove(l);
        btcList.add(0, l);
//        BTC min=null;
//        for(BTC x:btcList){
//            min=(min==null||Double.parseDouble(x.getBuy())<Double.parseDouble(min.getBuy()))?x:min;
//        }
        //String minimumDistance=min.getBuy();
        //createNoti(l,h);
        prepareBTC();


    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    protected void onDestroy() {
        Log.d("UpdateActivity", "Destroyed");


        super.onDestroy();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }

    @Override
    protected void onStop() {
        Log.d("UpdateActivity", "Stop");
        super.onStop();
    }

    public void createNoti(BTC l, BTC h) {

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.noti);
        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher_round);
        contentView.setTextViewText(R.id.title, "BUY: " + l.getBuy() + " | " + l.getName().toUpperCase());
        contentView.setTextViewText(R.id.text, "SELL: " + h.getBuy() + " | " + h.getName().toUpperCase());

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContent(contentView)
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, AuthActivity.class), 0));

        Notification notification = mBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notification);


    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}