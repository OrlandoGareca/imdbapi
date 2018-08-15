package com.orlandogareca.imdbapi;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.orlandogareca.imdbapi.ListDataSource.CustomAdapter;
import com.orlandogareca.imdbapi.ListDataSource.ItenList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private ListView LIST;
    private ArrayList<ItenList> LISTINFO;
    private Context root;
    private CustomAdapter ADAPTER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        root = this;
        LISTINFO = new ArrayList<ItenList>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //loadInitialRestData();
        loadComponents();
    }

    private void loadInitialRestData(String keystr) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.omdbapi.com/?s="+keystr+"&page=1&apikey=b3ee81c2", new  JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray list = (JSONArray) response.get("Search");
                    for ( int i = 0;i < list.length(); i++){
                        JSONObject itenJson = list.getJSONObject(i);
                        String title = itenJson.getString("Title");
                        String year = itenJson.getString("Year");
                        String imdbID = itenJson.getString("imdbID");
                        String type = itenJson.getString("Type");
                        String Poster = itenJson.getString("Poster");
                        ItenList iten = new ItenList(Poster, title, year, type, imdbID);
                        LISTINFO.add(iten);
                    }
                    //FIX
                    ADAPTER = new CustomAdapter(root, LISTINFO);
                    LIST.setAdapter(ADAPTER);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(root, "FAIL", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadComponents() {
        LIST = (ListView) this.findViewById(R.id.listviewLayout);
        EditText search = (EditText)this.findViewById(R.id.searchMovie);
        //EVENTOSS
        search.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                loadInitialRestData(str);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        //LISTINFO.add(new ItenList("https://images-na.ssl-images-amazon.com/images/M/MV5BMjA4MzAyNDE1MF5BMl5BanBnXkFtZTgwODQxMjU5MzE@._V1_SX300.jpg", "prueba", "prueba", "movie"));

       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
