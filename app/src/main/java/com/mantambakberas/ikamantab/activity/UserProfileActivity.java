package com.mantambakberas.ikamantab.activity;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.mantambakberas.ikamantab.R;
import com.mantambakberas.ikamantab.config.ApiInterface;
import com.mantambakberas.ikamantab.config.AppController;
import com.mantambakberas.ikamantab.helper.CircleTransform;
import com.mantambakberas.ikamantab.helper.CircledNetworkImageView;
import com.mantambakberas.ikamantab.helper.SQLiteHandler;
import com.mantambakberas.ikamantab.request.RequestWithKey;
import com.mantambakberas.ikamantab.request.RequestWithoutKey;
import com.mantambakberas.ikamantab.response.BaseResponse;
import com.mantambakberas.ikamantab.response.HasAddedResponse;
import com.mantambakberas.ikamantab.response.NotifResponse;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserProfileActivity extends AppCompatActivity {

    private static final String TAG = UserProfileActivity.class.getSimpleName();

    String api_key, foto, name, info, friend, me, gcm, user_id;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    SQLiteHandler db;
    @Bind(R.id.toolbarFriend) Toolbar toolbar;
    @Bind(R.id.foto) CircleImageView fotoView;
    @Bind(R.id.name) TextView nameView;
    @Bind(R.id.info) TextView infoView;
    @Bind(R.id.bAdd) Button bAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.anim_pop_up, R.anim.anim_push_up);
        setContentView(R.layout.activity_user_profile);
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
        Bundle bundle = getIntent().getExtras();
        me = bundle.getString("me");
        friend = bundle.getString("friend");
        foto = bundle.getString("foto");
        name = bundle.getString("name");
        info = bundle.getString("info");
        gcm = bundle.getString("gcm");

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        api_key = user.get("api_key");
        user_id = user.get("user_id");

        //fotoView.setImageUrl(foto, imageLoader);
        String foto_url = foto + "?time=" + System.currentTimeMillis();
        Picasso.with(getApplicationContext()).load(Uri.parse(foto_url)).transform(new CircleTransform()).into(fotoView);
        nameView.setText(name);
        infoView.setText(info);

        checkHasAdded();

        if (bAdd.getText().equals(getResources().getString(R.string.addfriend))) {
            bAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addFriend(api_key);
                    Log.e(TAG, "me "+me);
                    Log.e(TAG, "friend "+friend);
                }
            });
        } else if (bAdd.getText().equals(getResources().getString(R.string.cancel))) {
            bAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO - CANCEL METHOD
                    Log.e(TAG, "me "+me);
                    Log.e(TAG, "friend "+friend);
                }
            });
        }


    }


    private void addFriend(final String api_key) {
        RequestWithKey req = new RequestWithKey();
        ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
        api.addFriend(user_id, friend, new Callback<BaseResponse>() {
            @Override
            public void success(BaseResponse baseResponse, Response response) {
                Log.e(TAG, "succ "+response.getBody());
                boolean error = baseResponse.getError();
                if (!error) {
                    // ubah tombol
                    changeButton();
                    sendNotifToFriend();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "err "+error.getUrl());
                Log.e(TAG, "err "+error.getBody());
                Log.e(TAG, "err "+error.getResponse());
                Log.e(TAG, "err "+error.getMessage());
            }
        });
    }

    private void changeButton() {
        bAdd.setText(getResources().getString(R.string.cancel));
    }

    private void sendNotifToFriend() {
        String body = name + " " + getResources().getString(R.string.requestfriend);
        String title = getResources().getString(R.string.requestfriend).toUpperCase();
        RequestWithoutKey req = new RequestWithoutKey();
        ApiInterface api = req.RequestWithoutKey().create(ApiInterface.class);
        api.sendNotif(gcm, title, body, new Callback<NotifResponse>() {
            @Override
            public void success(NotifResponse notifResponse, Response response) {
                int succ = notifResponse.getSuccess();
                if (succ==1) {
                    Toast.makeText(getApplicationContext(), "Friend Request Send", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    private void checkHasAdded() {
        RequestWithoutKey req = new RequestWithoutKey();
        ApiInterface api = req.RequestWithoutKey().create(ApiInterface.class);
        api.hasadded(me, friend, new Callback<HasAddedResponse>() {
            @Override
            public void success(HasAddedResponse hasAddedResponse, Response response) {
                boolean added = hasAddedResponse.getIsAdded();
                if (added) {
                    bAdd.setText(getResources().getString(R.string.cancel));
                } else {
                    bAdd.setText(getResources().getString(R.string.addfriend));
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void finishAction() {
        finish();
        overridePendingTransition(R.anim.anim_pop_down, R.anim.anim_push_down);
    }

    public void onBackPressed() {
        finishAction();
    }
 }
