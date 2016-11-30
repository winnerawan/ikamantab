package com.mantambakberas.ikamantab.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.mantambakberas.ikamantab.R;
import com.mantambakberas.ikamantab.config.ApiInterface;
import com.mantambakberas.ikamantab.config.AppController;
import com.mantambakberas.ikamantab.helper.CircledNetworkImageView;
import com.mantambakberas.ikamantab.helper.SQLiteHandler;
import com.mantambakberas.ikamantab.request.RequestWithKey;
import com.mantambakberas.ikamantab.response.BaseResponse;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AcceptFriendActivity extends AppCompatActivity {

    private static final String TAG = AcceptFriendActivity.class.getSimpleName();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    SQLiteHandler db;
    String foto, name, info, friend, me, gcm, api_key;
    @Bind(R.id.toolbarFriend) Toolbar toolbar;
    @Bind(R.id.foto) CircledNetworkImageView fotoView;
    @Bind(R.id.name) TextView nameView;
    @Bind(R.id.info) TextView infoView;
    @Bind(R.id.bConfirm) Button bConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_friend);
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

        Bundle bundle = getIntent().getExtras();
        me = user.get("user_id");
        friend = bundle.getString("friend");
        foto = bundle.getString("foto");
        name = bundle.getString("name");
        info = bundle.getString("info");
        gcm = bundle.getString("gcm");

        nameView.setText(name);
        fotoView.setImageUrl(foto, imageLoader);
        infoView.setText(info);


        bConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptFriend(api_key);
            }
        });
    }

    private void acceptFriend(final String api_key) {
        RequestWithKey req = new RequestWithKey();
        ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
        api.acceptFriend(me, friend, new Callback<BaseResponse>() {
            @Override
            public void success(BaseResponse baseResponse, Response response) {
                boolean error = baseResponse.getError();
                if (!error) {
                    Log.e(TAG, "me "+me+" friend "+friend);
                    Intent i = new Intent(AcceptFriendActivity.this, FriendsActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
