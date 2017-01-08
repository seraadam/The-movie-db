package db.movie.the.themoviedb;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class DetailsFragment extends Fragment {
    public String movieID;
    private OnFragmentInteractionListener mListener;

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
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ;
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
                            String backdrop_path = response.getString("backdrop_path");
                            Log.i("backdrop_path : " , backdrop_path);
                            String budget = response.getString("budget");
                             Log.i("budget : " , budget);

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
                            Log.i("original_title : " , original_title);
                            String overview = response.getString("overview");
                            Log.i("overview : " , overview);
                            String popularity = response.getString("popularity");
                            Log.i("popularity : " , popularity);
                            String poster_path = response.getString("poster_path");
                            Log.i("poster_path : " , poster_path);
                            String release_date = response.getString("release_date");
                            Log.i("release_date : " , release_date);
                            String revenue = response.getString("revenue");
                            Log.i("revenue : " , revenue);
                            String status = response.getString("status");
                            Log.i("status : " , status);
                            String video = response.getString("video");
                            Log.i("video : " , video);
                            String vote_average = response.getString("vote_average");
                            Log.i("vote_average : " , vote_average);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
