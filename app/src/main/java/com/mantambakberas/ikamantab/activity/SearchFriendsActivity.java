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
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mantambakberas.ikamantab.R;
import com.mantambakberas.ikamantab.helper.SQLiteHandler;
import com.mantambakberas.ikamantab.helper.SessionManager;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchFriendsActivity extends AppCompatActivity {

    private static final String TAG = SearchFriendsActivity.class.getSimpleName();

    @Bind(R.id.menuToolbar) Toolbar toolbar;
    @Bind(R.id.bSearch) Button bSearch;
    private SQLiteHandler db;
    private SessionManager session;
    String api_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friends);
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
        session = new SessionManager(getApplicationContext());

        Spannable buttonLabel = new SpannableString(" "+getResources().getString(R.string.search));
        buttonLabel.setSpan(new ImageSpan(getApplicationContext(), R.drawable.places_ic_search,
                ImageSpan.ALIGN_BOTTOM), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        bSearch.setText(buttonLabel);

        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchFriendsActivity.this, SearchDetailActivity.class);
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
                Intent i = new Intent(SearchFriendsActivity.this, SearchDetailActivity.class);
                startActivity(i);
                break;
            case R.id.request:
                Intent ii = new Intent(SearchFriendsActivity.this, FriendRequestActivity.class);
                startActivity(ii);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
