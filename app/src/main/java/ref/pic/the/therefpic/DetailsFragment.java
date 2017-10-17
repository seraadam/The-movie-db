package ref.pic.the.therefpic;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class DetailsFragment extends Fragment {
    public String movieID;
    private Context mContext;
    private  View view;
    private  View mainview;
    private TextView movietitle;
    private TextView movieoverview;
    private TextView moviedate;
    private TextView movievote;


    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        view = inflater.inflate(R.layout.fragment_details, container, false);
       // mainview = inflater.inflate(R.layout.activity_details, container, false);

        movietitle = (TextView) view.findViewById(R.id.mtitle);
        movieoverview = (TextView) view.findViewById(R.id.overview);
        moviedate = (TextView) view.findViewById(R.id.rdate);
         movievote = (TextView) view.findViewById(R.id.vote);


        // get the 'extra'
        movieID = getActivity().getIntent().getStringExtra("movieid");
        fetch();
        // Inflate the layout for this fragment
        return view;
    }


    private void fetch() {
        Log.i("movie id : ", movieID);
        String url = "https://api.themoviedb.org/3/movie/" + Integer.parseInt(movieID) + "?api_key=6b048567fc3948dc98e266e25cbea74d&language=en-EN&append_to_response=videos";
        Log.i("url : ", url);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Log.i("response : ", response.toString());

                            // the response is already constructed as a JSONObject!

                            String budget = response.getString("budget");
                             Log.i("budget : " , budget);

                            JSONObject videoobject  = response.getJSONObject("videos");
                            JSONArray videos = videoobject.getJSONArray("results");
                            for(int i=0;i< videos.length();i++){
                                JSONObject video = videos.getJSONObject(i);
                                if(video.getString("site").equalsIgnoreCase("YouTube")){
                                    String ytkey = video.getString("key");
                                    Log.i("youtube key "+ i + " :  " ,ytkey);
                                }

                            }

                          //  Glide.with(getActivity().getApplicationContext()).load("https://image.tmdb.org/t/p/w500/rSZs93P0LLxqlVEbI001UKoeCQC.jpg").into((ImageView) findViewById(R.id.detailsbackdrop));

                            JSONArray gen = response.getJSONArray("genres");
                            for (int i = 0; i < gen.length(); i++) {
                                JSONObject genjobject = gen.getJSONObject(i);
                               // List<String> genre = null;
                               // genre.add(jobject.getString("name"));
                                Log.i("genre "+ i + " :  " , genjobject.getString("name"));
                            }

                            JSONArray pcompanies = response.getJSONArray("production_companies");
                            for (int i = 0; i < pcompanies.length(); i++) {
                                JSONObject pjobject = pcompanies.getJSONObject(i);
                               // List<String> production = null;
                               // production.add(pjobject.getString("name"));
                               // Log.i("production "+ i + " :  " , production.get(i));
                            }

                            String imdb_id = response.getString("imdb_id");
                            Log.i("imdb_id : " , imdb_id);

                            String original_language = response.getString("original_language");
                            Log.i("original_language : " , original_language);

                            String original_title = response.getString("original_title");
                            movietitle.setText(original_title);
                            Log.i("original_title : " , original_title);

                            String overview = response.getString("overview");
                            movieoverview.setText(overview);
                            Log.i("overview : " , overview);

                            String popularity = response.getString("popularity");
                            //Log.i("popularity : " , popularity);

                            String backdrop_path =  "https://image.tmdb.org/t/p/w500" + response.getString("backdrop_path");
                            Log.i("backdrop_path : " , backdrop_path);

                            String poster_path = "https://image.tmdb.org/t/p/w500" + response.getString("poster_path");
                            Log.i("poster_path : " , poster_path);

                            try {

                                loadImage(Glide.with(getActivity().getApplicationContext()), backdrop_path, (ImageView) getActivity().findViewById(R.id.detailsbackdrop));
                                loadImage(Glide.with(getActivity().getApplicationContext()), poster_path, (ImageView) getActivity().findViewById(R.id.thumbnail));

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.i("image error : " , "Glide image error"+ poster_path);
                            }



                            String release_date = response.getString("release_date");
                            Log.i("release_date : " , release_date);
                            moviedate.setText(release_date);

                            String revenue = response.getString("revenue");
                            Log.i("revenue : " , revenue);
                            String status = response.getString("status");
                            Log.i("status : " , status);
                            String video = response.getString("video");
                            Log.i("video : " , video);

                            String vote_average = response.getString("vote_average");
                            Log.i("vote_average : " , vote_average);
                            movievote.setText(vote_average);

                            boolean adult = response.getBoolean("adult");


                        } catch (JSONException e) {

                            e.printStackTrace();
                            Toast.makeText(getContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // As of f605da3 the following should work
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                        Log.i("obj : ", obj.toString());
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
            }
        });
        RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());

        rq.add(jsonObjReq);
    }

    public static void loadImage(RequestManager glide, String url, ImageView view) {
        glide.load(url).into(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


}
