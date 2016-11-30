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
import android.view.View;

import com.mantambakberas.ikamantab.R;
import com.mantambakberas.ikamantab.adapter.ListFriendsAdapter;
import com.mantambakberas.ikamantab.config.ApiInterface;
import com.mantambakberas.ikamantab.helper.ItemClickSupport;
import com.mantambakberas.ikamantab.helper.SQLiteHandler;
import com.mantambakberas.ikamantab.model.Friend;
import com.mantambakberas.ikamantab.request.RequestWithKey;
import com.mantambakberas.ikamantab.response.FriendsResponse;
import com.mantambakberas.ikamantab.response.PendingFriendResponse;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FriendRequestActivity extends AppCompatActivity {

    private static final String TAG = FriendRequestActivity.class.getSimpleName();

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.re_list_friends_req) RecyclerView recyclerView;
    List<Friend> friends;
    private SQLiteHandler db;
    String api_key, me, friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);
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
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        api_key = user.get("api_key");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getAllFriendRequest(api_key);

    }

    private void getAllFriendRequest(final String api_key) {
        RequestWithKey req = new RequestWithKey();
        ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
        api.getFriendRequest(new Callback<FriendsResponse>() {
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
                Intent i = new Intent(FriendRequestActivity.this, AcceptFriendActivity.class);
                i.putExtra("friend", f.getId().toString());
                i.putExtra("gcm", f.getGcm());
                i.putExtra("name", f.getName());
                i.putExtra("info", f.getJurusan()+" - "+f.getAngkatan());
                i.putExtra("foto", f.getFoto());
                startActivity(i);
            }
        });
    }

}
