package alc.ugadining;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * The main activity of the UGA Dining application.
 *
 * @author Alexander Luiz Costa
 */
public class MainActivity extends AppCompatActivity {

    final Fragment menuFragment = new MenuFragment();
    final Fragment buildPlateFragment = new BuildPlateFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = menuFragment;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm.beginTransaction()
                .add(R.id.main_fragment_frame, buildPlateFragment, "build plate")
                .hide(buildPlateFragment)
                .commit();

        fm.beginTransaction()
                .add(R.id.main_fragment_frame, menuFragment, "menu")
                .commit();

        // retrieve bottom navigation view
        BottomNavigationView nav = findViewById(R.id.main_nav);
        setUpBottomNavigation(nav);

        fetchOccupancies();
    } // onCreate

    /**
     * Configures a {@link com.google.android.material.bottomnavigation.BottomNavigationView}
     * to switch {@link androidx.fragment.app.Fragment}s.
     *
     * @param nav the {@code BottomNavigationView} to configure
     */
    private void setUpBottomNavigation(BottomNavigationView nav) {
        nav.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.navigation_menu) {
                if (active != menuFragment) {
                    fm.beginTransaction().hide(active).show(menuFragment).commit();
                    active = menuFragment;
                    ((TextView) findViewById(R.id.main_title)).setText("Dining");
                }
            } else {
                if (active != buildPlateFragment) {
                    fm.beginTransaction().hide(active).show(buildPlateFragment).commit();
                    active = buildPlateFragment;
                    ((TextView) findViewById(R.id.main_title)).setText("Build Plate");
                }
            }
            return true;
        });

    } // setUpBottomNavigation

    public void fetchOccupancies() {
        LoadingBar loading = findViewById(R.id.main_loading_bar);
        loading.show();
        loading.setProgress(95, true);
        VolleySingleton singleton = VolleySingleton.getInstance(getApplicationContext());
        String url = "http://ugadining.000webhostapp.com/fetch_card_info.php";
        JsonObjectRequest occupancyRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONArray bolton = response.getJSONArray("bolton");
                        JSONArray oglethorpe = response.getJSONArray("ohouse");
                        JSONArray niche = response.getJSONArray("scott");
                        JSONArray snelling = response.getJSONArray("snelling");
                        JSONArray village = response.getJSONArray("summit");

                        DiningHallCardView boltonCard = findViewById(R.id.main_bolton_card);
                        boltonCard.setOccupancy(bolton.getInt(1));
                        boltonCard.setServing(bolton.optJSONArray(2));

                        DiningHallCardView oglethorpeCard = findViewById(R.id.main_oglethorpe_card);
                        oglethorpeCard.setOccupancy(oglethorpe.getInt(1));
                        oglethorpeCard.setServing(oglethorpe.optJSONArray(2));

                        DiningHallCardView nicheCard = findViewById(R.id.main_niche_card);
                        nicheCard.setOccupancy(niche.getInt(1));
                        nicheCard.setServing(niche.optJSONArray(2));

                        DiningHallCardView snellingCard = findViewById(R.id.main_snelling_card);
                        snellingCard.setOccupancy(snelling.getInt(1));
                        snellingCard.setServing(snelling.optJSONArray(2));

                        DiningHallCardView villageCard = findViewById(R.id.main_village_card);
                        villageCard.setOccupancy(village.getInt(1));
                        villageCard.setServing(village.optJSONArray(2));

                        animateOccupancyCards(boltonCard, oglethorpeCard, nicheCard,
                                snellingCard, villageCard);

                        loading.setProgress(100, true);

                        System.out.println("[INFO] Occupancies refreshed!");
                    } catch (JSONException e) {
                        System.out.println("[ERROR] Failed to parse JSONObject!");
                        e.printStackTrace();
                    }
                },
                error -> System.out.println("[ERROR] Failed to fetch occupancies!")
        );
        occupancyRequest.setShouldCache(false);
        singleton.addToRequestQueue(occupancyRequest);
    }

    public void refreshOccupancies() {
        //TODO: animate loading bar
    }

    public void animateOccupancyCards(DiningHallCardView... cards) {
        long delay = 0;
        int offset = 14;
        for (DiningHallCardView card : cards) {
            MaterialCardView pcCard = card.findViewById(R.id.card_pc_container);
            ObjectAnimator animator = ObjectAnimator.ofFloat(
                    pcCard,
                    "cardElevation",
                    4,
                    4 + offset,
                    4
            );
            animator.setDuration(350);
            animator.setStartDelay(delay);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.start();
            delay += 75;
            offset -= (delay % 150 == 0) ? 2 * (delay / 150) : 0;
        }
    }
}
