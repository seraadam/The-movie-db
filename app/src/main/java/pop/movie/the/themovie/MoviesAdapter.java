package pop.movie.the.themovie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by mac on 11/28/16.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>
    {

        private Context mContext;
        private List<Movie> movieList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title, genre,release_date;
            public ImageView poster_path;
            public RatingBar vote_average;

            public MyViewHolder(View view) {
                super(view);

                poster_path = (ImageView) view.findViewById(R.id.thumbnail);
                title = (TextView) view.findViewById(R.id.title);
                genre = (TextView) view.findViewById(R.id.genre);
                release_date = (TextView) view.findViewById(R.id.release_date);
                vote_average = (RatingBar)view.findViewById(R.id.vote_average);

            }


        }

        public MoviesAdapter(Context mContext, List<Movie> movieList) {
            this.mContext = mContext;
            this.movieList = movieList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MoviesAdapter.MyViewHolder holder, int position) {

            final Movie movie = movieList.get(position);
            holder.title.setText(movie.getTitle());
            holder.release_date.setText(movie.getRelease_date());

            holder.vote_average.setRating(movie.getVote_average());
           /*...// TODO: 11/28/16  genres
            for (int x =0 ; x<= movie.getGenre_ids().length; x++){
                String genres = ;
            }*/
            holder.genre.setText(movie.getGenre_ids().toString());
            // loading movie cover using Glide library
            Glide.with(mContext).load(movie.getPoster_path()).into(holder.poster_path);
            holder.poster_path.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent movieid = new Intent(mContext , DetailsActivity.class);
                    movieid.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    movieid.putExtra("movieid", String.valueOf(movie.getId()));
                    Log.i("movieid : " , String.valueOf(movie.getId()));
                    mContext.startActivity(movieid);

                }
            });
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }




    }




