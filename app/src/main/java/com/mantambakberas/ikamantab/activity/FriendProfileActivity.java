/*
 * Copyright (c) 2016. Ikamantab (Ikatan Keluarga Alumni Man Tambakberas).
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file   except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License..
 */

package com.mantambakberas.ikamantab.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.mantambakberas.ikamantab.R;
import com.mantambakberas.ikamantab.config.AppController;
import com.mantambakberas.ikamantab.helper.CircledNetworkImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FriendProfileActivity extends AppCompatActivity {

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    @Bind(R.id.menuToolbar) Toolbar toolbar;
    @Bind(R.id.foto) CircledNetworkImageView fotoView;
    @Bind(R.id.name) TextView nameView;
    @Bind(R.id.info) TextView infoView;
    @Bind(R.id.telp) TextView telpView;
    @Bind(R.id.biotext) TextView bioView;
    @Bind(R.id.profesi) TextView profesiView;
    @Bind(R.id.pelatihan) TextView pelatihanView;
    @Bind(R.id.penghargaan) TextView penghargaanView;
    @Bind(R.id.referensi) TextView referensiView;
    @Bind(R.id.minat) TextView minatView;
    @Bind(R.id.email) TextView emailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
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
        Bundle bundle =  getIntent().getExtras();
        String name = bundle.getString("name");                 // 1
        String foto = bundle.getString("foto");                 // 2
        final String email = bundle.getString("email");         // 3
        String info = bundle.getString("info");                 // 4
        String bio = bundle.getString("bio");                   // 5
        String profesi = bundle.getString("profesi");           // 6
        String keahlian = bundle.getString("keahlian");         // 7
        String ref = bundle.getString("ref");                   // 8
        String minat = bundle.getString("minat");               // 9
        String telp = bundle.getString("telp");                 // 10
        String penghargaan = bundle.getString("penghargaan");   // 11

        nameView.setText(name);
        fotoView.setImageUrl(foto, imageLoader);
        emailView.setText(email);
        infoView.setText(info);
        profesiView.setText(profesi);
        penghargaanView.setText(penghargaan);
        referensiView.setText(ref);
        minatView.setText(minat);
        telpView.setText(telp);
        pelatihanView.setText(keahlian);
        bioView.setText(bio);

        emailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mail = new Intent(Intent.ACTION_VIEW);
                mail.setData(Uri.parse("mailto:"+email));
                startActivity(mail);
            }
        });


    }
}
