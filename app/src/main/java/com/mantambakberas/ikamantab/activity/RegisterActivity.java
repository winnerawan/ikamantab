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
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mantambakberas.ikamantab.R;
import com.mantambakberas.ikamantab.config.ApiInterface;
import com.mantambakberas.ikamantab.config.AppConfig;
import com.mantambakberas.ikamantab.helper.NothingSelectedSpinnerAdapter;
import com.mantambakberas.ikamantab.helper.Validation;
import com.mantambakberas.ikamantab.model.Asrama;
import com.mantambakberas.ikamantab.model.Jurusan;
import com.mantambakberas.ikamantab.response.AsramaResponse;
import com.mantambakberas.ikamantab.response.JurusanResponse;
import com.mantambakberas.ikamantab.response.RegisterResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Bind(R.id.menuToolbar) Toolbar toolbar;
    private List<Jurusan> listJurusan;
    private List<Asrama> listAsrama;
    private List<String> jenisKelamin= new ArrayList<String>();
    private Validation validation;

    @Bind(R.id.bRegister) Button register;
    @Bind(R.id.txtName) EditText txtName;
    @Bind(R.id.txtEmail) EditText txtEmail;
    @Bind(R.id.txtPassword) EditText txtPassword;
    @Bind(R.id.txtJenis) Spinner txtJenis;
    @Bind(R.id.txtAngkatan) Spinner txtAngkatan;
    @Bind(R.id.txtJurusan) Spinner txtJurusan;
    @Bind(R.id.txtAsrama) Spinner txtAsrama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
            getListJurusan();
            getListAsrama();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String pass = txtPassword.getText().toString().trim();
                validation = new Validation();
                if(!name.isEmpty() && !email.isEmpty() && !pass.isEmpty() && validation.checkEmail(email)) {
                    registerUser(name, email, pass);


                    //validation
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.formnull), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //register logic
    private void registerUser(final String name, final String email, final String password) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AppConfig.BASE_URL).build();
        final ApiInterface apiInterface = restAdapter.create(ApiInterface.class);
        apiInterface.register(name, email, password, new Callback<RegisterResponse>() {
            @Override
            public void success(RegisterResponse apiResponse, Response response) {
                // ambil nilai error dari api
                boolean err = apiResponse.getError();
                // jika tidak error
                if (!err) {
                    // log || ngga penting
                    String angkatan_lulus = txtAngkatan.getSelectedItem().toString();
                    // register info || insert ke user_detail --> go to method registerInfo @ line 152
                    registerInfo(txtJenis.getSelectedItem().toString(), angkatan_lulus, String.valueOf(txtJurusan.getSelectedItemId()+1), String.valueOf(txtAsrama.getSelectedItemId()+1));
                    // log || ngga penting
                    Log.e(TAG, "reginfo :"+angkatan_lulus);
                    // start activity
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                    // done, jika back button pressed , jangan balik ke login aktiviti
                    finish();
                } else {
                    // jika error || ambil error dari api
                    String message_error = apiResponse.getMessage();
                    // tampilkan toast error
                    Toast.makeText(getApplicationContext(), message_error, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.toString());
                //Log.e(TAG, "xxx" +jurusan_id);
            }
        });
    }


    private void registerInfo(final String jenis_kelamin, final String angkatan_lulus, final String jurusan_id
            ,final String asrama_id) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AppConfig.BASE_URL).build();
        ApiInterface apiInterface = restAdapter.create(ApiInterface.class);
        apiInterface.registerinfo(jenis_kelamin, angkatan_lulus, jurusan_id, asrama_id, new Callback<RegisterResponse>() {
            @Override
            public void success(RegisterResponse registerResponse, Response response) {
                boolean err = registerResponse.getError();
                if (!err) {
                    Toast.makeText(getApplicationContext(), "Registrasi berhasil", Toast.LENGTH_LONG).show();
                    //Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    // startActivity(i);
                    //finish();
                } else {
                    String message_error = registerResponse.getMessage();
                    Toast.makeText(getApplicationContext(), message_error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.toString());
            }
        });
    }

    private void getListJurusan() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AppConfig.BASE_URL).build();
        ApiInterface apiInterface = restAdapter.create(ApiInterface.class);

        apiInterface.listjurusan(new Callback<JurusanResponse>() {
            @Override
            public void success(JurusanResponse jurusanResponse, Response response) {
                listJurusan = jurusanResponse.getJurusan();
                populateJurusan();
                populateJenisKelamin();
                populateAngkatan();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "sp error :" +error.toString());
            }
        });
    }

    private void getListAsrama() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AppConfig.BASE_URL).build();
        ApiInterface apiInterface = restAdapter.create(ApiInterface.class);
        apiInterface.listasrama(new Callback<AsramaResponse>() {
            @Override
            public void success(AsramaResponse asramaResponse, Response response) {
                listAsrama = asramaResponse.getAsrama();
                populateAsrama();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void populateJurusan() {
        String[] jurusan = new String[listJurusan.size()];
        for (int i=0; i<listJurusan.size(); i++) {
            jurusan[i] = listJurusan.get(i).getDeskripsi();
            //jurusan_id = String.valueOf(listJurusan.get(i).getJurusanId());
        }
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_jurusan, jurusan);
        txtJurusan.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.spinner_item_jurusan, this));
    }

    // set asrama
    private void populateAsrama() {
        String[] asrama = new String[listAsrama.size()];
        for (int i=0; i<listAsrama.size(); i++) {
            asrama[i] = listAsrama.get(i).getDeskripsi();
        }
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_asrama, asrama);
        txtAsrama.setAdapter( new NothingSelectedSpinnerAdapter(adapter, R.layout.spinner_item_asrama, this));
    }

    //set jenis kelamin
    private void populateJenisKelamin() {
        jenisKelamin.add("Pria");
        jenisKelamin.add("Wanita");
        ArrayAdapter adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_jenis_kelamin, jenisKelamin);
        txtJenis.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.spinner_jenis_kelamin, this));

    }

    // ubah angkatan lulus jadi spinner
    // set spinner
    private void populateAngkatan() {
        ArrayList<String> years = new ArrayList<String>();
        int lastYear = 2015;
        for (int i = 1980; i <= lastYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_angkatan, years);
        txtAngkatan.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.spinner_item_angkatan, this));
    }
}
