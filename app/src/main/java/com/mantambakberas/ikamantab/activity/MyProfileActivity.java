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

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mantambakberas.ikamantab.Manifest;
import com.mantambakberas.ikamantab.R;
import com.mantambakberas.ikamantab.config.ApiInterface;
import com.mantambakberas.ikamantab.config.AppConfig;
import com.mantambakberas.ikamantab.config.AppController;
import com.mantambakberas.ikamantab.helper.CircleTransform;
import com.mantambakberas.ikamantab.helper.CircledNetworkImageView;
import com.mantambakberas.ikamantab.helper.CropOption;
import com.mantambakberas.ikamantab.helper.CropOptionAdapter;
import com.mantambakberas.ikamantab.helper.SQLiteHandler;
import com.mantambakberas.ikamantab.model.Me;
import com.mantambakberas.ikamantab.request.RequestWithKey;
import com.mantambakberas.ikamantab.request.RequestWithoutKey;
import com.mantambakberas.ikamantab.response.BaseResponse;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyProfileActivity extends AppCompatActivity {

    private static final String TAG = MyProfileActivity.class.getSimpleName();
    private Uri mImageCaptureUri;
    // private ImageView mImageView;
    ImageView banar1;
    Bitmap photo;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    Me me;
    private SQLiteHandler db;
    String name,email,foto,info,profesi,penghargaan,pelatihan,minat,rekomendasi,api_key;

    @Bind(R.id.menuToolbar) Toolbar toolbar;
    @Bind(R.id.foto) CircleImageView fotoView;
    @Bind(R.id.name) TextView nameView;
    @Bind(R.id.info) TextView infoView;
    @Bind(R.id.telp) TextView telpView;
    @Bind(R.id.email) TextView emailView;
    @Bind(R.id.biotext) TextView bioView;
    @Bind(R.id.profesi) TextView profesiView;
    @Bind(R.id.pelatihan) TextView pelatihanView;
    @Bind(R.id.penghargaan) TextView penghargaanView;
    @Bind(R.id.referensi) TextView referensiView;
    @Bind(R.id.minat) TextView minatView;
    @Bind(R.id.bBio) TextView bBio;
    @Bind(R.id.bProfesi) TextView bProfesi;
    @Bind(R.id.bPelatihan) TextView bPelatihan;
    @Bind(R.id.bPenghargaan) TextView bPenghargaan;
    @Bind(R.id.bReferensi) TextView bReferensi;
    @Bind(R.id.bMinat) TextView bMinat;
    @Bind(R.id.ic_addProfesi) ImageView ic_addProfesi;
    @Bind(R.id.ic_addPelatihan) ImageView ic_addPelatihan;
    @Bind(R.id.ic_addPenghargaan) ImageView ic_addPenghargaan;
    @Bind(R.id.ic_addReferensi) ImageView ic_addReferensi;
    @Bind(R.id.ic_addMinat) ImageView ic_addMinat;
    EditText txtBio,txtProfesi,txtPelatihan,txtPenghargaan,txtReferensi,txtMinat;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.anim_pop_left, R.anim.anim_push_left);
        setContentView(R.layout.activity_my_profile);
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
        name = user.get("name");
        email = user.get("email");
        api_key = user.get("api_key");

        alert = new AlertDialog.Builder(this);

        getMyInformation(api_key);


        bBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update Biography
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                txtBio = new EditText(getApplicationContext());
                txtBio.setTextColor(getResources().getColor(android.R.color.black));
                alert.setTitle(getResources().getString(R.string.bio));
                alert.setView(txtBio);

                alert.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RequestWithKey req = new RequestWithKey();
                        final ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
                        api.updateBio(txtBio.getText().toString().trim(), new Callback<BaseResponse>() {
                            @Override
                            public void success(BaseResponse baseResponse, Response response) {
                                getMyInformation(api_key);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                            }
                        });
                    }
                });

                alert.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                alert.show();
            }
        });

        // End Update Bio

        bProfesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update Profesi
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                txtProfesi = new EditText(getApplicationContext());
                txtProfesi.setTextColor(getResources().getColor(android.R.color.black));
                alert.setTitle(getResources().getString(R.string.profesi));
                alert.setView(txtProfesi);

                alert.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RequestWithKey req = new RequestWithKey();
                        final ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
                        api.updateProfesi(txtProfesi.getText().toString().trim(), new Callback<BaseResponse>() {
                            @Override
                            public void success(BaseResponse baseResponse, Response response) {
                                getMyInformation(api_key);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                            }
                        });
                    }
                });

                alert.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.show();
            }
        });

        // End Update Profesi

        // Update Pelatihan

        bPelatihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                txtPelatihan = new EditText(getApplicationContext());
                txtPelatihan.setTextColor(getResources().getColor(android.R.color.black));
                alert.setTitle(getResources().getString(R.string.pelatihan));
                alert.setView(txtPelatihan);

                alert.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RequestWithKey req = new RequestWithKey();
                        final ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
                        api.updateKeahlian(txtPelatihan.getText().toString().trim(), new Callback<BaseResponse>() {
                            @Override
                            public void success(BaseResponse baseResponse, Response response) {
                                getMyInformation(api_key);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                            }
                        });
                    }
                });

                alert.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.show();
            }
        });

        // End Update Pelatihan


        // Update Penghargaan
        bPenghargaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                txtPenghargaan = new EditText(getApplicationContext());
                txtPenghargaan.setTextColor(getResources().getColor(android.R.color.black));
                alert.setTitle(getResources().getString(R.string.penghargaan));
                alert.setView(txtPenghargaan);

                alert.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RequestWithKey req = new RequestWithKey();
                        final ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
                        api.updatePenghargaan(txtPenghargaan.getText().toString().trim(), new Callback<BaseResponse>() {
                            @Override
                            public void success(BaseResponse baseResponse, Response response) {
                                getMyInformation(api_key);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                            }
                        });
                    }
                });

                alert.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.show();
            }
        });

        // End Update Penghargaan

        // Update Referensi

        bReferensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                txtReferensi = new EditText(getApplicationContext());
                txtReferensi.setTextColor(getResources().getColor(android.R.color.black));
                alert.setTitle(getResources().getString(R.string.referensi));
                alert.setView(txtReferensi);

                alert.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RequestWithKey req = new RequestWithKey();
                        final ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
                        api.updateReferensi(txtReferensi.getText().toString().trim(), new Callback<BaseResponse>() {
                            @Override
                            public void success(BaseResponse baseResponse, Response response) {
                                getMyInformation(api_key);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                            }
                        });
                    }
                });

                alert.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.show();
            }
        });

        // End Update Referensi

        // Update Minat

        bMinat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                txtMinat = new EditText(getApplicationContext());
                txtMinat.setTextColor(getResources().getColor(android.R.color.black));
                alert.setTitle(getResources().getString(R.string.minat));
                alert.setView(txtMinat);

                alert.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RequestWithKey req = new RequestWithKey();
                        final ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
                        api.updateMinat(txtMinat.getText().toString().trim(), new Callback<BaseResponse>() {
                            @Override
                            public void success(BaseResponse baseResponse, Response response) {
                                getMyInformation(api_key);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                            }
                        });
                    }
                });

                alert.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.show();
            }
        });

        // End Update Minat

        // update foto

        final String[] items = new String[]{"Ambil dari Kamera", "Pilih dari Galeri"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Pilih Foto");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { //pick from camera
                if (item == 0) {
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                        requestTakePhotoPermissions();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                                "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

                        try {
                            intent.putExtra("return-data", true);

                            startActivityForResult(intent, PICK_FROM_CAMERA);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                                "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

                        try {
                            intent.putExtra("return-data", true);

                            startActivityForResult(intent, PICK_FROM_CAMERA);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                } else { //pick from file
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        openAlbumWithPermissionsCheck();
                        Intent intent = new Intent();

                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);

                        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
                    } else {
                        Intent intent = new Intent();

                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);

                        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
                    }
                }
            }
        });

        final AlertDialog dialog = builder.create();


        fotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                dialog.show();
            }
        });
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode != RESULT_OK) return;

            switch (requestCode) {
                case PICK_FROM_CAMERA:

                    doCrop();
                    break;
                case PICK_FROM_FILE:
                    mImageCaptureUri = data.getData();
                    doCrop();
                    break;
                case CROP_FROM_CAMERA:
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        photo = extras.getParcelable("data");
                        fotoView.setImageBitmap(photo);
                        // Log.e(TAG, "foto : "+getStringImage(photo));
                        // TODO: Method Upload Foto
                        updateFoto();
                    }
                    File f = new File(mImageCaptureUri.getPath());
                    if (f.exists()) f.delete();
                    break;

            }
        }

        private void doCrop() {
            final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setType("image/*");

            List<ResolveInfo> list = getPackageManager().queryIntentActivities( intent, 0 );

            int size = list.size();

            if (size == 0) {
                Toast.makeText(this, "Tidak dapat menemukan aplikasi untuk memotong foto", Toast.LENGTH_SHORT).show();

                return;
            } else {
                intent.setData(mImageCaptureUri);

                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 200);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);

                if (size == 1) {
                    Intent i 		= new Intent(intent);
                    ResolveInfo res	= list.get(0);

                    i.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                    startActivityForResult(i, CROP_FROM_CAMERA);
                } else {
                    for (ResolveInfo res : list) {
                        final CropOption co = new CropOption();

                        co.title 	= getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                        co.icon		= getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                        co.appIntent= new Intent(intent);

                        co.appIntent.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                        cropOptions.add(co);
                    }

                    CropOptionAdapter adapter = new CropOptionAdapter(getApplicationContext(), cropOptions);

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Choose Crop App");
                    builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
                        public void onClick( DialogInterface dialog, int item ) {
                            startActivityForResult( cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                        }
                    });

                    builder.setOnCancelListener( new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel( DialogInterface dialog ) {

                            if (mImageCaptureUri != null ) {
                                getContentResolver().delete(mImageCaptureUri, null, null );
                                mImageCaptureUri = null;
                            }
                        }
                    } );

                    AlertDialog alert = builder.create();

                    alert.show();
                }
            }
        }
        public String getStringImage(Bitmap bmp){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }


    private void getMyInformation(final String api_key) {
        RequestWithKey req = new RequestWithKey();
        ApiInterface api = req.RequestWithToken(api_key).create(ApiInterface.class);
        api.getmyinfo(new Callback<Me>() {
            @Override
            public void success(Me _me, Response response) {
                me = _me;
                emailView.setText(_me.getEmail());
                nameView.setText(_me.getName());
                //fotoView.setImageUrl(_me.getFoto(), imageLoader);
                String foto = _me.getFoto();
                String foto_url = foto + "?time=" + System.currentTimeMillis();
                Picasso.with(getApplicationContext()).load(Uri.parse(foto_url)).transform(new CircleTransform()).into(fotoView);
                infoView.setText(_me.getJurusan()+" - "+_me.getAngkatan());
                telpView.setText(_me.getTelp());
                bioView.setText(_me.getBio());
                if (_me.getProfesi()==null) {
                    profesiView.setText(getResources().getString(R.string.data_null));
                } else {
                    profesiView.setText(_me.getProfesi());
                    ic_addProfesi.setImageDrawable(getResources().getDrawable(R.drawable.ic_ubah));
                    bProfesi.setText(getResources().getString(R.string.edit));
                }
                if (_me.getPenghargaan()==null) {
                    penghargaanView.setText(getResources().getString(R.string.data_null));
                } else {
                    penghargaanView.setText(_me.getPenghargaan());
                    ic_addPenghargaan.setImageDrawable(getResources().getDrawable(R.drawable.ic_ubah));
                    bPenghargaan.setText(getResources().getString(R.string.edit));
                }
                if (_me.getMinatProfesi()==null) {
                    minatView.setText(getResources().getString(R.string.data_null));
                } else {
                    minatView.setText(_me.getMinatProfesi());
                    ic_addMinat.setImageDrawable(getResources().getDrawable(R.drawable.ic_ubah));
                    bMinat.setText(getResources().getString(R.string.edit));
                }
                if (_me.getKeahlian()==null) {
                    pelatihanView.setText(getResources().getString(R.string.data_null));
                } else {
                    pelatihanView.setText(_me.getKeahlian());
                    ic_addPelatihan.setImageDrawable(getResources().getDrawable(R.drawable.ic_ubah));
                    bPelatihan.setText(getResources().getString(R.string.edit));
                }
                if (_me.getReferensiRekomendasi()==null) {
                    referensiView.setText(getResources().getString(R.string.data_null));
                } else {
                    referensiView.setText(_me.getReferensiRekomendasi());
                    ic_addReferensi.setImageDrawable(getResources().getDrawable(R.drawable.ic_ubah));
                    bReferensi.setText(getResources().getString(R.string.edit));
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
/*
    private void updateFoto() {
        String pp = getStringImage(photo);
        RequestWithoutKey req = new RequestWithoutKey();
        ApiInterface api = req.RequestWithoutKey().create(ApiInterface.class);
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        api.uploadFoto(pp, api_key, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                loading.dismiss();
                getMyInformation(api_key);
            }

            @Override
            public void failure(RetrofitError error) {
                loading.dismiss();
            }
        });
    }
    */
    private void updateFoto() {
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.UPLOAD_FOTO,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String pp = getStringImage(photo);
                Map<String, String> params = new Hashtable<String, String>();
                params.put("foto", pp);
                params.put("api_key", api_key);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    /**
     * 请求拍照权限
     */
    private void requestTakePhotoPermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA},
                    PICK_FROM_CAMERA);
            return;
        }
    }

    private void openAlbumWithPermissionsCheck() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    PICK_FROM_FILE);
            return;
        }
    }

    private void finishAction() {
        finish();
        overridePendingTransition(R.anim.anim_pop_right, R.anim.anim_push_right);
    }

    public void onBackPressed() {
        finishAction();
    }
}
