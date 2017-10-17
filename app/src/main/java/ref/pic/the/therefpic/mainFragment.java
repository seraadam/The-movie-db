package ref.pic.the.therefpic;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class mainFragment extends Fragment {
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movieList;




    public mainFragment() {
        // Required empty public constructor
    }


    public static mainFragment newInstance(String param1, String param2) {
        mainFragment fragment = new mainFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.content_main, container, false);

//...define recyclerview
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view);

//....create list for the movies
        movieList = new ArrayList<>();

//...define the customed adapter
        adapter = new MoviesAdapter(getActivity().getApplicationContext(), movieList);

//...the cardview design in the layout
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
//get the url from the main activity
        String url = getArguments().getString("url");

        String pages =getpages(url);

//...setting the used data (i can replace it with the asynchronous method

        prepareMovies(url,pages);

        // Inflate the layout for this fragment
        return rootview;
    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private String getpages(String pagesurl){
        final  List<String> pages = new ArrayList<String>();
        pagesurl= pagesurl+"1";
        JsonObjectRequest pajsonObjReq = new JsonObjectRequest(Request.Method.POST, pagesurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject presponse) {
                        // the response is already constructed as a JSONObject!
                        try {
                            pages.add(presponse.getString("total_pages") );
                            Log.i("pages 1: " , pages.get(0))  ;
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(pajsonObjReq);
       // String temp = pages.get(0);
        return "33";
    }

    private void prepareMovies(String url,String pages) {
        //...calling api with volley
        // String url = "https://api.themoviedb.org/3/movie/550?api_key=6b048567fc3948dc98e266e25cbea74d";
        //   String url = "https://api.themoviedb.org/3/movie/popular?api_key=6b048567fc3948dc98e266e25cbea74d&language=ar-AR&page=1";

        int pagesno = Integer.parseInt(pages);
        for(int i=1 ; i<=pagesno ; i++){

            String popularurl = url + i ;
            Log.i("popularurl " ,popularurl);
            Log.i("page number  " , String.valueOf(i));
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
            Volley.newRequestQueue(getActivity().getApplicationContext()).add(jsonObjReq);




        }
    }
    int onSuccess(String response){
// do what you want to do
        int number = Integer.parseInt(response);
        return number;
    }


    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
