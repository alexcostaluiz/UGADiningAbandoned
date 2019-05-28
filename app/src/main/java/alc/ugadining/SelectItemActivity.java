package alc.ugadining;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class SelectItemActivity extends AppCompatActivity {

    Fragment itemListFragment;
    ItemNutritionFragment itemNutritionFragment;
    Fragment active;
    FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item);

        MenuItem item = getIntent().getParcelableExtra("nutrition");
        if (item != null) {
            itemNutritionFragment = new ItemNutritionFragment(item);
            active = itemNutritionFragment;
            fm = getSupportFragmentManager();

            fm.beginTransaction()
                    .add(R.id.select_item_fragment_frame, itemNutritionFragment, "item nutrition")
                    .commit();

            ((TextView) findViewById(R.id.select_item_toolbar_title)).setText(item.getName());
            findViewById(R.id.select_item_check).setVisibility(View.VISIBLE);
            findViewById(R.id.select_item_select_many).setVisibility(View.GONE);

            findViewById(R.id.select_item_check).setOnClickListener(view -> {
                Intent intent = new Intent();
                intent.putExtra("meal", getIntent().getStringExtra("meal"));
                intent.putExtra("item", itemNutritionFragment.getItem());
                setResult(RESULT_OK, intent);
                finish();
            });
        }
        else {
            ArrayList<MenuItem> items = getIntent().getParcelableArrayListExtra("items");

            itemListFragment = new ItemListFragment(this, items);
            itemNutritionFragment = new ItemNutritionFragment(null);
            active = itemListFragment;
            fm = getSupportFragmentManager();

            fm.beginTransaction()
                    .add(R.id.select_item_fragment_frame, itemNutritionFragment, "item nutrition")
                    .hide(itemNutritionFragment)
                    .commit();

            fm.beginTransaction()
                    .add(R.id.select_item_fragment_frame, itemListFragment, "item list")
                    .commit();

            findViewById(R.id.select_item_back).setOnClickListener(view -> goToItemListFragment());

            findViewById(R.id.select_item_check).setOnClickListener(view -> {
                Intent intent = new Intent();
                intent.putExtra("meal", getIntent().getStringExtra("meal"));
                ArrayList<MenuItem> itemsToAdd = new ArrayList<>();
                itemsToAdd.add(itemNutritionFragment.getItem());
                intent.putParcelableArrayListExtra("items", itemsToAdd);
                setResult(RESULT_OK, intent);
                finish();
            });
        }

        findViewById(R.id.select_item_close).setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });

    }

    public void goToNutritionFragment(MenuItem item) {
        if (active == itemListFragment) {
            ((TextView) findViewById(R.id.select_item_toolbar_title)).setText(item.getName());
            findViewById(R.id.select_item_back).setVisibility(View.VISIBLE);
            findViewById(R.id.select_item_close).setVisibility(View.GONE);
            findViewById(R.id.select_item_check).setVisibility(View.VISIBLE);
            findViewById(R.id.select_item_select_many).setVisibility(View.GONE);
            itemNutritionFragment.setItem(item);
            fm.beginTransaction().hide(active).show(itemNutritionFragment).commit();
            active = itemNutritionFragment;

            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = getCurrentFocus();
            if (view == null) {
                view = new View(this);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void goToItemListFragment() {
        if (active == itemNutritionFragment) {
            ((TextView) findViewById(R.id.select_item_toolbar_title)).setText("Select Item");
            ((BounceScrollView) findViewById(R.id.nutrition_scroll_view)).updateToolbarTitle();
            itemNutritionFragment.getItem().setNumServings(1f);
            findViewById(R.id.select_item_back).setVisibility(View.GONE);
            findViewById(R.id.select_item_close).setVisibility(View.VISIBLE);
            findViewById(R.id.select_item_check).setVisibility(View.GONE);
            findViewById(R.id.select_item_select_many).setVisibility(View.VISIBLE);
            fm.beginTransaction().hide(active).show(itemListFragment).commit();
            active = itemListFragment;

            // TODO: open keyboard
        }
    }
}
