package com.mantambakberas.ikamantab.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mantambakberas.ikamantab.R;
import com.mantambakberas.ikamantab.adapter.ListFriendsAdapter;
import com.mantambakberas.ikamantab.adapter.ListUsersAdapter;
import com.mantambakberas.ikamantab.config.ApiInterface;
import com.mantambakberas.ikamantab.config.AppConfig;
import com.mantambakberas.ikamantab.config.AppController;
import com.mantambakberas.ikamantab.gcm.GcmIntentService;
import com.mantambakberas.ikamantab.helper.CircleTransform;
import com.mantambakberas.ikamantab.helper.CircledNetworkImageView;
import com.mantambakberas.ikamantab.helper.ItemClickSupport;
import com.mantambakberas.ikamantab.helper.NotificationUtils;
import com.mantambakberas.ikamantab.helper.SQLiteHandler;
import com.mantambakberas.ikamantab.helper.SessionManager;
import com.mantambakberas.ikamantab.model.Me;
import com.mantambakberas.ikamantab.model.Users;
import com.mantambakberas.ikamantab.model.Friend;
import com.mantambakberas.ikamantab.request.RequestWithKey;
import com.mantambakberas.ikamantab.response.FriendsResponse;
import com.mantambakberas.ikamantab.response.UsersResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.mantambakberas.ikamantab.R.id.coordinatorLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private SessionManager session;
    private SQLiteHandler db;
    List<Friend> friends = new ArrayList<Friend>();

    @Bind(R.id.menuToolbar) Toolbar toolbar;

    DrawerLayout mDrawerLayout;
    public Me my;
    String api_key,name,email, user_id;
    List<Users> users;
    RecyclerView recyclerView;
    // update & share
    @Bind(R.id.share) TextView bShare;
    @Bind(R.id.updateProfile) TextView bProfile;

    //Navigation info
    @Bind(R.id.name) TextView nameView;
    @Bind(R.id.email) TextView emailView;
    @Bind(R.id.foto) ImageView fotoView;

    //navigation action
    @Bind(R.id.beranda) TextView act_home;
    @Bind(R.id.logout) TextView act_logout;
    @Bind(R.id.teman) TextView act_teman;
    @Bind(R.id.agenda) TextView act_agenda;
    @Bind(R.id.publikasi) TextView act_publikasi;
    @Bind(R.id.iamantarab) TextView act_iamantarab;
    @Bind(R.id.pengaturan) TextView act_pengaturan;
    @Bind(R.id.forum) TextView act_forum;

    @Bind(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.anim_pop_down, R.anim.anim_push_down);
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        api_key = user.get("api_key");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        name = user.get("name");
        email = user.get("email");
        user_id = user.get("user_id");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator =
                    VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(), android.R.color.white, getTheme()));
            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setTitle("");
            BitmapDrawable toolbar_bg = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.toolbar_bg));
            supportActionBar.setBackgroundDrawable(toolbar_bg);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        recyclerView = (RecyclerView) findViewById(R.id.re_list_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Log.e(TAG, "api_key = "+api_key);
        nameView.setText(name);
        emailView.setText(email);
        
        ///getListAllUsers();
        getMyInformation(api_key);
        statusbarTranslucent();
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        showAllOrSugges(api_key);

        /**
         * Always check for google play services availability before
         * proceeding further with GCM
         * */
        if (checkPlayServices()) {
            registerGCM();
        }

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(AppConfig.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    //FirebaseMessaging.getInstance().subscribeToTopic(AppConfig.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(AppConfig.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        // share & update
        bProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                Intent i = new Intent(MainActivity.this, MyProfileActivity.class);
                startActivity(i);
            }
        });

        bShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                Intent iShare = new Intent(Intent.ACTION_SEND);
                iShare.setType("text/plain");
                iShare.putExtra(Intent.EXTRA_SUBJECT, name);
                String share = AppConfig.BASE_URL+"/api/v1/user/"+user_id;
                iShare.putExtra(Intent.EXTRA_TEXT, share);
                startActivity(Intent.createChooser(iShare, "Share..."));
            }
        });

        //navigation

        act_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
            }
        });
        fotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                Intent i = new Intent(MainActivity.this, MyProfileActivity.class);
                startActivity(i);
            }
        });

        act_teman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                setLayouFriends(api_key);
            }
        });

        act_agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                underDev();
            }
        });
        act_forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                Intent i = new Intent(MainActivity.this, ForumActivity.class);
                startActivity(i);
            }
        });
        act_publikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                underDev();
            }
        });
        act_iamantarab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                underDev();
            }
        });

        act_pengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                underDev();
            }
        });

        act_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                logoutUser();
            }
        });
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(AppConfig.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        //if (!TextUtils.isEmpty(regId))
        //    txtRegId.setText("Firebase Reg Id: " + regId);
        //else
        //    txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(AppConfig.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(AppConfig.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        // kembali ke login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void statusbarTranslucent() {
        if(Build.VERSION.SDK_INT>21) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void getListAllUsers() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestInterceptor.RequestFacade request) {
                        request.addHeader("Authorization", api_key);
                        Log.e(TAG, request.toString());
                    }
                })
                .setEndpoint(AppConfig.BASE_URL)
                .build();
        ApiInterface apiInterface = restAdapter.create(ApiInterface.class);
        apiInterface.getlistusers(new Callback<UsersResponse>() {
            @Override
            public void success(UsersResponse usersResponse, Response response) {
                users = usersResponse.getUsers();
                recyclerView.setAdapter(new ListUsersAdapter(users, R.layout.list_users, getApplicationContext()));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.toString());
            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                Users u = users.get(position);
                Intent i = new Intent(MainActivity.this, UserProfileActivity.class);
                i.putExtra("me", user_id);
                i.putExtra("foto", u.getFoto());
                i.putExtra("name", u.getName());
                i.putExtra("friend", u.getId().toString());
                Log.e(TAG, "friend_id::"+u.getId().toString());
                i.putExtra("info", u.getJurusan() + " - " + u.getAngkatan());
                i.putExtra("gcm", u.getGcm());
                startActivity(i);
            }
        });
    }

        private void getFriendSuggestion(final String api_key) {
            RequestWithKey req = new RequestWithKey();
            ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
            api.getFriendSuggestion(new Callback<FriendsResponse>() {
                @Override
                public void success(FriendsResponse friendsResponse, Response response) {
                    boolean error = friendsResponse.getError();
                    if (!error) {
                        friends = friendsResponse.getFriends();
                        recyclerView.setAdapter(new ListFriendsAdapter(friends, R.layout.list_users, getApplicationContext()));
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

            ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Friend f = friends.get(position);
                    Intent i = new Intent(MainActivity.this, UserProfileActivity.class);
                    i.putExtra("foto", f.getFoto());
                    i.putExtra("name", f.getName());
                    i.putExtra("friend", f.getId());
                    i.putExtra("info", f.getJurusan() + " - " + f.getAngkatan());
                    i.putExtra("gcm", f.getGcm());
                    startActivity(i);
                }
            });
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_disclaimer) {
        //    Intent i = new Intent(MainActivity.this, AboutActivity.class);
        //    startActivity(i);
        if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    // starting the service to register with GCM
    private void registerGCM() {
        Intent intent = new Intent(this, GcmIntentService.class);
        intent.putExtra("key", "register");
        startService(intent);
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported. Google Play Services not installed!");
                Toast.makeText(getApplicationContext(), "This device is not supported. Google Play Services not installed!", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    private void setLayouFriends(final String api_key) {
        RequestWithKey req = new RequestWithKey();
        ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
        api.getListAllFriends(new Callback<FriendsResponse>() {
            @Override
            public void success(FriendsResponse friendsResponse, Response response) {
                boolean error = friendsResponse.getError();
                if (!error) {
                    if (friendsResponse.getFriends().size()==0) {
                        Intent i = new Intent(MainActivity.this, SearchFriendsActivity.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(MainActivity.this, FriendsActivity.class);
                        startActivity(i);
                    }
                } 
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }



    private void showAllOrSugges(final String api_key) {
        RequestWithKey req = new RequestWithKey();
        ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
        api.getListAllFriends(new Callback<FriendsResponse>() {
            @Override
            public void success(FriendsResponse friendsResponse, Response response) {
                // cek status error || tidak error
                boolean error = friendsResponse.getError();
                // jika tidak error
                if (!error) {
                    // jika belum punya teman, ( awal register)
                    if (friendsResponse.getFriends().size()==0) {
                        // tampilkan semua list pengguna
                        getListAllUsers();
                        // jika sudah punya teman, (lebih dari 1) (login)
                    } else if (friendsResponse.getFriends().size()>=1) {
                        // tampilkan sugesti teman
                        getFriendSuggestion(api_key);
                    }
                }
            }
            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void getMyInformation(final String api_key) {
        RequestWithKey req = new RequestWithKey();
        ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
        api.getmyinfo(new Callback<Me>() {
            @Override
            public void success(Me me, Response response) {
                my = me;
                //fotoView.setImageUrl(me.getFoto(), imageLoader);
                String foto = me.getFoto();
                String foto_url = foto + "?time=" + System.currentTimeMillis();
                Picasso.with(getApplicationContext()).load(Uri.parse(foto_url)).transform(new CircleTransform()).into(fotoView);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void underDev() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Maaf fitur ini sedang dalam pengembangan. Mohon menunggu", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
