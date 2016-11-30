package com.mantambakberas.ikamantab.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mantambakberas.ikamantab.R;
import com.mantambakberas.ikamantab.config.ApiInterface;
import com.mantambakberas.ikamantab.helper.SQLiteHandler;
import com.mantambakberas.ikamantab.model.ShareInfo;
import com.mantambakberas.ikamantab.request.RequestWithKey;
import com.mantambakberas.ikamantab.response.IsFriendResponse;

import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SharedActivity extends AppCompatActivity {

    SQLiteHandler db;
    String api_key, user_id;
    List<String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        api_key = user.get("api_key");
        user_id = user.get("user_id");

        Uri data = getIntent().getData();
        String scheme = data.getScheme(); // "http"
        String host = data.getHost(); // "ip"
        params = data.getPathSegments();
        params.get(0);
        params.get(1);

        check(api_key);

    }

    private void check(final String api_key) {
        RequestWithKey req = new RequestWithKey();
        final ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
        api.isfriend(user_id, params.get(3), new Callback<IsFriendResponse>() {
            @Override
            public void success(IsFriendResponse isFriendResponse, Response response) {
                boolean isfriend = isFriendResponse.getIsFriend();
                if (isfriend) {
                    api.getshareinfo(params.get(3), new Callback<ShareInfo>() {
                        @Override
                        public void success(ShareInfo shareInfo, Response response) {
                            Intent i = new Intent(SharedActivity.this, FriendProfileActivity.class);
                            i.putExtra("name", shareInfo.getName());                            // 1
                            i.putExtra("foto", shareInfo.getFoto());                            // 2
                            i.putExtra("info", shareInfo.getJurusan()+" - "+shareInfo.getAngkatan());   // 3
                            i.putExtra("email", shareInfo.getEmail());                          // 4
                            i.putExtra("bio", shareInfo.getBio());                              // 5
                            i.putExtra("asrama", shareInfo.getAsrama());                        // 6
                            i.putExtra("profesi", shareInfo.getProfesi());                      // 7
                            i.putExtra("keahlian", shareInfo.getKeahlian());                    // 8
                            i.putExtra("jenis_kelamin", shareInfo.getJenisKelamin());           // 9
                            i.putExtra("penghargaan", shareInfo.getPenghargaan());              // 10
                            i.putExtra("minat", shareInfo.getMinatProfesi());                   // 11
                            i.putExtra("ref", shareInfo.getReferensiRekomendasi());             // 12
                            i.putExtra("telp", shareInfo.getTelp());                            // 13
                            startActivity(i);
                            startActivity(i);
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                } else {
                    RequestWithKey req = new RequestWithKey();
                    ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
                    api.getshareinfo(params.get(3), new Callback<ShareInfo>() {
                        @Override
                        public void success(ShareInfo shareInfo, Response response) {
                            Intent i  = new Intent(SharedActivity.this, UserProfileActivity.class);
                            i.putExtra("me", user_id);
                            i.putExtra("friend", params.get(3));
                            i.putExtra("name", shareInfo.getName());
                            i.putExtra("info", shareInfo.getJurusan()+" - "+shareInfo.getAngkatan());
                            i.putExtra("foto", shareInfo.getFoto());
                            i.putExtra("gcm", shareInfo.getGcm());
                            startActivity(i);
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
