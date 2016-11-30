package com.mantambakberas.ikamantab.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mantambakberas.ikamantab.R;
import com.mantambakberas.ikamantab.config.ApiInterface;
import com.mantambakberas.ikamantab.config.AppController;
import com.mantambakberas.ikamantab.helper.CheckConnection;
import com.mantambakberas.ikamantab.helper.ConnectivityReceiver;
import com.mantambakberas.ikamantab.helper.SQLiteHandler;
import com.mantambakberas.ikamantab.helper.SessionManager;
import com.mantambakberas.ikamantab.helper.Validation;
import com.mantambakberas.ikamantab.model.ID;
import com.mantambakberas.ikamantab.request.RequestWithoutKey;
import com.mantambakberas.ikamantab.response.LoginResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {


    private static final String TAG = LoginActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private SessionManager session;
    private Validation validation;
    private SQLiteHandler db;
    @Bind(R.id.txtEmail) EditText txtEmail;
    @Bind(R.id.txtPassword) EditText txtPassword;
    @Bind(R.id.forgot) TextView bForgot;
    @Bind(R.id.register) TextView link_register;
    @Bind(R.id.bLogin) Button bSignIn;
    @Bind(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //set dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(true);

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());

        if (session.isLoggedIn()) {
            // User sudah login, langsung ke main activity
            //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            //LoginActivity.this.startActivity(intent);
            LoginActivity.this.finish();
        }
        checkConnection();

        // ke activity forgot password
        bForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
            }
        });

        // ke activity register
        link_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });


        //tombol login
        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation validation = new Validation();
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                //jika email dan password tidak kosong, format email valid, dan internet up maka login
                if (!email.isEmpty() && !password.isEmpty() && validation.checkEmail(email) && ConnectivityReceiver.isConnected()) {
                    checkLogin(email, password);
                    //jika email dan password kosong maka tampilkan dialog password & email tidak boleh kosong
                } else if (email.isEmpty() && password.isEmpty()){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginnull), Toast.LENGTH_SHORT).show();
                    //jika format email tidak valid maka tampilkan dialog format email tidak valid
                } else if(!validation.checkEmail(email)) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.emailnotvalid), Toast.LENGTH_SHORT).show();
                    //jika tidak internet down makan tampilkan snackbar dan action ke setting
                } else if (!ConnectivityReceiver.isConnected()) {
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.checkconnection), Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
                            startActivity(intent);
                        }
                    });
                    snackbar.show();
                }
            }
        });
    }

    private void checkLogin(final String email, final String password) {
        RequestWithoutKey req = new RequestWithoutKey();
        ApiInterface api = req.RequestWithoutKey().create(ApiInterface.class);
        //tampilkan dialog (menunggu response server)
        pDialog.setMessage(getResources().getString(R.string.authenticating) + "...");
        pDialog.show();
            api.login(email, password, new Callback<LoginResponse>() {
                @Override
                public void success(final LoginResponse loginResponse, Response response) {
                    //server merespon, sembunyikan dialog
                    pDialog.dismiss();
                    //mendapatkan response error
                    boolean error = loginResponse.getError();
                    //jika tidak error
                    if (!error) {
                        //ambil user id dari server, digunakan untuk chatting nanti
                        //request tanpa api_key karena cuma proses login
                        RequestWithoutKey req = new RequestWithoutKey();
                        ApiInterface api = req.RequestWithoutKey().create(ApiInterface.class);
                        api.getidbyemail(email, new Callback<ID>() {
                            @Override
                            public void success(ID id, Response response) {
                                user_id = id.getId();
                                //move on ke main activity
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                                Log.e(TAG, "USER ID = "+user_id);
                                db.addUser(loginResponse.getName(), loginResponse.getEmail(), loginResponse.getApiKey(),
                                        id.getId(), loginResponse.getCreatedAt());
                                session.setLogin(true);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                error.printStackTrace();
                            }
                        });
                        // insert ke database response dari server
                        //db.addUser(loginResponse.getName(), loginResponse.getEmail(), loginResponse.getApiKey(), user_id, loginResponse.getCreatedAt());
                        //modifikasi session, jika user sudah login maka tidak akan balikan dg login actity
                        //session.setLogin(true);
                        //jika response error
                    } else {
                        pDialog.dismiss();
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.notmatch), Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        //Toast.makeText(getApplicationContext(), getResources().getString(R.string.notmatch), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(TAG, error.toString());
                }
            });
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.checkconnection), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        AppController.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }
}

