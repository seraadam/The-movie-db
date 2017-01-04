package db.movie.the.themoviedb;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//.....define the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//....the method when the toolbar collabse
        initCollapsingToolbar();

//...define recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

//....create list for the movies
        movieList = new ArrayList<>();

//...define the customed adapter
        adapter = new MoviesAdapter(this, movieList);

//...the cardview design in the layout
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);



//...setting the used data (i can replace it with the asynchronous method
        prepareMovies();

        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void prepareMovies() {
        //...calling api with volley
        // String url = "https://api.themoviedb.org/3/movie/550?api_key=6b048567fc3948dc98e266e25cbea74d";
     //   String url = "https://api.themoviedb.org/3/movie/popular?api_key=6b048567fc3948dc98e266e25cbea74d&language=ar-AR&page=1";


        for(int pages=1 ; pages<=9 ; pages++){

            String popularurl = "https://api.themoviedb.org/3/movie/popular?api_key=6b048567fc3948dc98e266e25cbea74d&language=ar-AR&page="
                    +Integer.toString(pages);

            Log.i("popularurl " ,popularurl);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, popularurl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            // the response is already constructed as a JSONObject!
                            try {
                                  Log.i("pages: " , Integer.toString(onSuccess(response.getString("total_pages"))))  ;
                                JSONArray obj = response.getJSONArray("results");
                                //int pages = Integer.getInteger(response.getString("total_pages"));
                                for (int i = 0; i < obj.length(); i++) {

                                    JSONObject jsonObject = obj.getJSONObject(i);
                                    // int id = jsonObject.getInt("id");
                                    String original_title = jsonObject.getString("original_title");
                                    String release_date = jsonObject.getString("release_date");
                                    String voteaverage = jsonObject.getString("vote_average");
                                    float vote_average = Float.valueOf(voteaverage);
                                    String overview = jsonObject.getString("overview");
                                    String adult = jsonObject.getString("adult");
                                    int id = jsonObject.getInt("id");
                                    String backdrop_path = "https://image.tmdb.org/t/p/w500" + jsonObject.getString("backdrop_path");
                                   // Picasso.with(getApplicationContext()).load(backdrop_path).error(R.mipmap.ic_launcher).into((ImageView) findViewById(R.id.thumbnail));
                                    // TODO ...this data should be filled with api data

                                    String[] gen = new String[]{
                                            "comedy" ," drama ", "drama", "light ", "family", "comedy", "family", "cartoon" , "family ", "comedy "," drama ", "drama", "light "," family",
                                            "comedy", "family", "cartoon , family ", "comedy" ," drama ", "drama", "light ", "family", "comedy",
                                            "family", "cartoon" , "family ", "comedy "," drama ", "drama", "light "," family", "comedy", "family", "cartoon , family ", "comedy" ," drama ", "drama", "light ", "family",
                                            "comedy", "family", "cartoon" , "family ", "comedy "," drama ", "drama", "light "," family",
                                            "comedy", "family", "cartoon , family ", "comedy" ," drama ", "drama", "light ", "family", "comedy", "family", "cartoon" , "family ", "comedy "," drama ",
                                            "drama", "light "," family", "comedy", "family", "cartoon , family ", "comedy" ," drama ", "drama", "light ", "family", "comedy", "family", "cartoon" , "family ",
                                            "comedy "," drama ", "drama", "light "," family", "comedy", "family", "cartoon , family ", "comedy" ," drama ",
                                            "drama", "light ", "family", "comedy", "family", "cartoon" , "family ", "comedy "," drama ", "drama", "light "," family", "comedy", "family", "cartoon , family ", "comedy" ," drama ", "drama", "light ", "family", "comedy", "family",
                                            "cartoon" , "family ", "comedy "," drama ", "drama", "light "," family", "comedy", "family", "cartoon , family ", "comedy" ," drama ", "drama", "light ", "family", "comedy", "family", "cartoon" , "family ", "comedy "," drama ", "drama", "light "," family",
                                            "comedy", "family", "cartoon , family ", "comedy" ," drama ", "drama", "light ", "family", "comedy", "family", "cartoon" , "family ", "comedy "," drama ", "drama", "light "," family", "comedy", "family", "cartoon , family ", "comedy" ," drama ", "drama",
                                            "light ", "family", "comedy", "family", "cartoon" , "family ", "comedy "," drama ", "drama", "light "," family", "comedy", "family", "cartoon , family "
                                    };
                                    //Movie( String title, String release_date, float vote_average, String genre_ids[] ,
                                    //  String overview, String poster_path, String adult, int id)




                                    Movie a = new Movie(original_title, release_date , vote_average , gen[i], overview, backdrop_path,adult , id );
                                       movieList.add(a);

                                }

                                adapter.notifyDataSetChanged();
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

/*
        for (int i = 1; i <= pages; i++) {
            String original_title = response.getString("original_title"),
                    overview = response.getString("overview"),
                    backdrop_path = "https://image.tmdb.org/t/p/w500" + response.getString("backdrop_path");

            //Picasso.with(getApplicationContext()).load(backdrop_path).error(R.mipmap.ic_launcher).into(image);

            Log.i("url", backdrop_path);
            // mTextView.setText(original_title + " : " +overview);
            System.out.println("Site: " + original_title + "\nNetwork: " + overview);

            //...this data should be filled with api data
            int[] covers = new int[]{
                    R.drawable.album1,
                    R.drawable.album2,
                    R.drawable.album3,
                    R.drawable.album4,
                    R.drawable.album5,
                    R.drawable.album6,
                    R.drawable.album7,
                    R.drawable.album8,
                    R.drawable.album9,
                    R.drawable.album10,
                    R.drawable.album11};

            String[] gen = new String[]{
                    "comedy , drama ",
                    "drama",
                    "light , family",
                    "comedy",
                    "family",
                    "cartoon , family ",
                    "comedy , drama ",
                    "drama", "light , family",
                    "comedy",
                    "family",
                    "cartoon , family "
            };

       // Movie( String title, String release_date, float vote_average, String genre_ids[] ,
                 //  String overview, String poster_path, String adult, int id)

          c
            a = new Movie("Arrival", "2016", 3, gen[1], "In 1926, Newt Scamander arrives at the Magical Congress of " +
                    "the United States of America with a magically " +
                    "expanded briefcase, which houses a number of " +
                    "dangerous creatures ", covers[1], "adult", 2);
            movieList.add(a);

            a = new Movie("Doctor Strange", "2016", 2, gen[2], "In 1926, Newt Scamander arrives at the Magical Congress of " +
                    "the United States of America with a magically " +
                    "expanded briefcase, which houses a number of " +
                    "dangerous creatures ", covers[2], "adult", 3);
            movieList.add(a);

            a = new Movie("Jason Bourne", "2016", 9, gen[3], "In 1926, Newt Scamander arrives at the Magical Congress of " +
                    "the United States of America with a magically " +
                    "expanded briefcase, which houses a number of " +
                    "dangerous creatures ", covers[3], "adult", 4);
            movieList.add(a);

            a = new Movie("Captain America: Civil War", "2016", 5, gen[4], "In 1926, Newt Scamander arrives at the Magical Congress of " +
                    "the United States of America with a magically " +
                    "expanded briefcase, which houses a number of " +
                    "dangerous creatures ", covers[4], "adult", 5);
            movieList.add(a);

            a = new Movie("Mad Max: Fury Road", "2016", 3, gen[5], "In 1926, Newt Scamander arrives at the Magical Congress of " +
                    "the United States of America with a magically " +
                    "expanded briefcase, which houses a number of " +
                    "dangerous creatures ", covers[5], "adult", 6);
            movieList.add(a);

            a = new Movie("Finding Dory ", "2016", 8, gen[6], "In 1926, Newt Scamander arrives at the Magical Congress of " +
                    "the United States of America with a magically " +
                    "expanded briefcase, which houses a number of " +
                    "dangerous creatures ", covers[6], "adult", 7);
            movieList.add(a);

            a = new Movie("Interstellar", "2016", 0, gen[7], "In 1926, Newt Scamander arrives at the Magical Congress of " +
                    "the United States of America with a magically " +
                    "expanded briefcase, which houses a number of " +
                    "dangerous creatures ", covers[7], "adult", 8);
            movieList.add(a);

            a = new Movie("The Secret Life of Pets", "2016", 2, gen[8], "In 1926, Newt Scamander arrives at the Magical Congress of " +
                    "the United States of America with a magically " +
                    "expanded briefcase, which houses a number of " +
                    "dangerous creatures ", covers[8], "adult", 9);
            movieList.add(a);

            a = new Movie("The BFG", "2016", 1, gen[9], "In 1926, Newt Scamander arrives at the Magical Congress of " +
                    "the United States of America with a magically " +
                    "expanded briefcase, which houses a number of " +
                    "dangerous creatures ", covers[9], "adult", 10);
            movieList.add(a);
            adapter.notifyDataSetChanged();

        }
*/







    }


    int onSuccess(String response){
// do what you want to do
     int number = Integer.parseInt(response);

        return number;

    }


    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
