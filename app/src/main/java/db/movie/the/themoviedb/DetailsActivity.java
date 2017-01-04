package db.movie.the.themoviedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String movieID = intent.getStringExtra("movieid");

        Log.i("movieid : " , movieID);
        String url = "https://api.themoviedb.org/3/movie/"+movieID+"?api_key=6b048567fc3948dc98e266e25cbea74d&language=ar-AR";
        Log.i("url : " , url);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // the response is already constructed as a JSONObject!
                            String backdrop_path = response.getString("backdrop_path");
                            String budget = response.getString("backdrop_path");

                            JSONArray gen = response.getJSONArray("genres");
                            for(int i=0 ; i< gen.length(); i++){
                                JSONObject jobject = gen.getJSONObject(i);
                                String genre = jobject.getString("name");
                            }
                            JSONArray pcompanies = response.getJSONArray("production_companies");
                            for(int i=0 ; i< gen.length(); i++){
                                JSONObject jobject = gen.getJSONObject(i);
                                String production = jobject.getString("name");
                            }

                            String imdb_id = response.getString("imdb_id");
                            String original_language = response.getString("original_language");
                            String original_title = response.getString("original_title");
                            String overview = response.getString("overview");
                            String popularity = response.getString("popularity");
                            String poster_path = response.getString("poster_path");
                            String release_date = response.getString("release_date");
                            String revenue = response.getString("revenue");
                            String status = response.getString("status");
                            String video = response.getString("video");
                            String vote_average = response.getString("vote_average");
                            String adult = response.getString("adult");


                            
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
        }
    });
    Volley.newRequestQueue(this).add(jsonObjReq);




}

}