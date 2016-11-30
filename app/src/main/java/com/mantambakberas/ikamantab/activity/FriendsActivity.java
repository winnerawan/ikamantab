package com.mantambakberas.ikamantab.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mantambakberas.ikamantab.R;
import com.mantambakberas.ikamantab.adapter.ListFriendsAdapter;
import com.mantambakberas.ikamantab.config.ApiInterface;
import com.mantambakberas.ikamantab.helper.ItemClickSupport;
import com.mantambakberas.ikamantab.helper.SQLiteHandler;
import com.mantambakberas.ikamantab.helper.SessionManager;
import com.mantambakberas.ikamantab.model.Friend;
import com.mantambakberas.ikamantab.request.RequestWithKey;
import com.mantambakberas.ikamantab.response.FriendsResponse;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FriendsActivity extends AppCompatActivity {

    private static final String TAG = FriendsActivity.class.getSimpleName();

    @Bind(R.id.menuToolbar) Toolbar toolbar;
    private SQLiteHandler db;
    private SessionManager session;
    @Bind(R.id.re_list_friends) RecyclerView recyclerView;
    List<Friend> friends;
    String api_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator =
                    VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(), android.R.color.white, getTheme()));
            //supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setTitle("");
            BitmapDrawable toolbar_bg = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.toolbar_bg));
            supportActionBar.setBackgroundDrawable(toolbar_bg);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        api_key = user.get("api_key");
        session = new SessionManager(getApplicationContext());
        getFriends(api_key);

    }

    private void getFriends(final String api_key) {
        RequestWithKey req = new RequestWithKey();
        ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
        api.getListAllFriends(new Callback<FriendsResponse>() {
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
                Intent i = new Intent(FriendsActivity.this, FriendProfileActivity.class);
                i.putExtra("name", f.getName());                            // 1
                i.putExtra("foto", f.getFoto());                            // 2
                i.putExtra("info", f.getJurusan()+" - "+f.getAngkatan());   // 3
                i.putExtra("email", f.getEmail());                          // 4
                i.putExtra("bio", f.getBio());                              // 5
                i.putExtra("asrama", f.getAsrama());                        // 6
                i.putExtra("profesi", f.getProfesi());                      // 7
                i.putExtra("keahlian", f.getKeahlian());                    // 8
                i.putExtra("jenis_kelamin", f.getJenisKelamin());           // 9
                i.putExtra("penghargaan", f.getPenghargaan());              // 10
                i.putExtra("minat", f.getMinatProfesi());                   // 11
                i.putExtra("ref", f.getReferensiRekomendasi());             // 12
                i.putExtra("telp", f.getTelp());                            // 13
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_friends, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.search:
                Intent i = new Intent(FriendsActivity.this, SearchDetailActivity.class);
                startActivity(i);
                break;
            case R.id.request:
                Intent ii = new Intent(FriendsActivity.this, FriendRequestActivity.class);
                startActivity(ii);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
