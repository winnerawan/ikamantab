package com.mantambakberas.ikamantab.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.mantambakberas.ikamantab.R;
import com.mantambakberas.ikamantab.adapter.ListUsersAdapter;
import com.mantambakberas.ikamantab.config.ApiInterface;
import com.mantambakberas.ikamantab.helper.ItemClickSupport;
import com.mantambakberas.ikamantab.helper.SQLiteHandler;
import com.mantambakberas.ikamantab.model.User;
import com.mantambakberas.ikamantab.model.Users;
import com.mantambakberas.ikamantab.request.RequestWithKey;
import com.mantambakberas.ikamantab.request.RequestWithoutKey;
import com.mantambakberas.ikamantab.response.IsFriendResponse;
import com.mantambakberas.ikamantab.response.UsersResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchDetailActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = SearchDetailActivity.class.getSimpleName();

    @Bind(R.id.menuToolbar) Toolbar toolbar;
    SQLiteHandler db;
    @Bind(R.id.re_list_users) RecyclerView recyclerView;
    @Bind(R.id.loading) ProgressBar pBar;
    List<Users> users;
    String api_key, me;
    ListUsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.anim_pop_down, R.anim.anim_push_down);
        setContentView(R.layout.activity_search_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator =
                    VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(), android.R.color.white, getTheme()));
            //supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setTitle("");
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        api_key = user.get("api_key");
        me = user.get("user_id");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getAllUser(api_key);
        adapter = new ListUsersAdapter(users, R.layout.list_users_white, getApplicationContext());
        pBar.setVisibility(View.GONE);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_detail, menu);
        final  MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Users> filteredUser = filter(users, newText);
        adapter = new ListUsersAdapter(filteredUser, R.layout.list_users_white, getApplicationContext());
        recyclerView.setAdapter(adapter);

        return true;


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Users> filter(List<Users> models, String query) {
        query = query.toLowerCase();final List<Users> filteredModelList = new ArrayList<>();
        for (Users model : models) {
            final String name = model.getName().toLowerCase();
            final String angkatan = model.getAngkatan().toString();
            final String jurusan = model.getJurusan().toLowerCase();
            final String email = model.getEmail().toLowerCase();
            // TODO
            // edit return value user list
            if (name.contains(query) || angkatan.contains(query) || jurusan.contains(query)
                    || email.contains(query)) {
                filteredModelList.add(model);
            } else {
                //
            }

        }
        return filteredModelList;
    }


    private void getAllUser(final String api_key) {
        RequestWithKey req = new RequestWithKey();
        ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
        api.getlistusers(new Callback<UsersResponse>() {
            @Override
            public void success(UsersResponse usersResponse, Response response) {
                boolean error = usersResponse.getError();
                if (!error) {
                    users = usersResponse.getUsers();
                    adapter = new ListUsersAdapter(users, R.layout.list_users_white, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                // cek sudah berteman apa belum
                RequestWithoutKey req = new RequestWithoutKey();
                ApiInterface api = req.RequestWithoutKey().create(ApiInterface.class);
                // get user_id & friend_id
                //final Users u = users.get(position);
                final Users u = (Users) adapter.getItem(position);
                final String friend = u.getId().toString();
                api.isfriend(me, friend, new Callback<IsFriendResponse>() {
                    @Override
                    public void success(IsFriendResponse isFriendResponse, Response response) {
                        // Banyak job disini, konsentrasi!!
                        boolean error = isFriendResponse.getError();
                        if (!error) {
                            //final cek is friend ?
                            boolean isfriend = isFriendResponse.getIsFriend();
                            if (isfriend) {
                                Users f = users.get(position);
                                Intent i = new Intent(SearchDetailActivity.this, FriendProfileActivity.class);
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
                                Log.e(TAG, "sudah berteman");

                            } else {
                                Intent i = new Intent(SearchDetailActivity.this, UserProfileActivity.class);
                                i.putExtra("me", me);
                                i.putExtra("friend", u.getId().toString());
                                i.putExtra("name", u.getName());
                                i.putExtra("info", u.getJurusan()+" - "+u.getAngkatan());
                                i.putExtra("foto", u.getFoto());
                                i.putExtra("gcm", u.getGcm());
                                startActivity(i);
                                Log.e(TAG, "belum berteman");
                                Log.e(TAG, "is friend "+response.getUrl());
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
        });
    }

    private void finishAction() {
        finish();
        overridePendingTransition(R.anim.anim_pop_up, R.anim.anim_push_up);
    }

    public void onBackPressed() {
        finishAction();
    }

}
