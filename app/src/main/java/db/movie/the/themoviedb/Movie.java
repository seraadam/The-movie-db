package db.movie.the.themoviedb;

import java.lang.reflect.Array;

/**
 * Created by mac on 11/28/16.
 */
public class Movie {

    //.... define the movie variables

    private String title;
    private String release_date;
    private float vote_average;
    private String overview;
    private String genre_ids ;
    private Object poster_path;
    private String adult;
    private int id ;

    public Movie(){

    }
    //...constructor

    public Movie(  String title, String release_date, float vote_average, String genre_ids ,
                   String overview, Object poster_path, String adult, int id ){

        this.adult=adult;
        this.genre_ids=genre_ids;
        this.id=id;
        this.overview=overview;
        this.poster_path=poster_path;
        this.release_date=release_date;
        this.title=title;
        this.vote_average=vote_average;

    }

    //....setters and getter

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }



    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }



    public String getGenre_ids( ) {
        return genre_ids;
    }

    public void setGenre_ids(String genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }



    public Object getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(Object poster_path) {
        this.poster_path = poster_path;
    }



    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




}
