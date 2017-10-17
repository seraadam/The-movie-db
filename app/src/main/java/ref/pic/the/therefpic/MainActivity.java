package ref.pic.the.therefpic;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movieList;
    String popularurl= "https://api.themoviedb.org/3/movie/popular?api_key=6b048567fc3948dc98e266e25cbea74d&language=en-US&page=" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//.....define the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//....the method when the toolbar collabse
        initCollapsingToolbar();

        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            Bundle bundle = new Bundle();
            bundle.putString("url", getpopularurl());
            Log.i("getpopularurl : " , getpopularurl());
            // set Fragmentclass Arguments
            // using a fragment transaction.
            mainFragment fragment = new mainFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.main, fragment)
                    .commit();
        }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_nowplaying:
                popularurl = "https://api.themoviedb.org/3/movie/now_playing?api_key=6b048567fc3948dc98e266e25cbea74d&language=en-US&page=";
                setpopularurl(popularurl);
                return true;
            case R.id.action_upcoming:
                popularurl = "https://api.themoviedb.org/3/movie/upcoming?api_key=6b048567fc3948dc98e266e25cbea74d&language=en-US&page=";
                setpopularurl(popularurl);
                return true;
            default:
                popularurl ="https://api.themoviedb.org/3/movie/popular?api_key=6b048567fc3948dc98e266e25cbea74d&language=ar-AR&page=";
                setpopularurl(popularurl);
                return super.onOptionsItemSelected(item);
        }

    }


    public void  setpopularurl (String popularurl){
        this.popularurl= popularurl;
    }

    public String getpopularurl(){

        return popularurl;
    }
}