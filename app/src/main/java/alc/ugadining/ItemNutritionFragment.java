package alc.ugadining;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ItemNutritionFragment extends Fragment {

    private MenuItem item;
    private View root;

    public ItemNutritionFragment(MenuItem item) {
        this.item = item;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_nutrition, container, false);
    } // onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root = view;

        if (item != null) {
            ((TextView) root.findViewById(R.id.nutrition_num_servings)).setText(String.valueOf(item.getNumServings()));
            updateInformation();
        }

        ((EditText) view.findViewById(R.id.nutrition_num_servings)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    float numServings = Float.parseFloat(s.toString());
                    item.setNumServings(numServings);
                    updateInformation();
                }
                catch (NumberFormatException e) {}
            }
        });
    }

    public void setItem(MenuItem item) {
        this.item = item;
        ((TextView) root.findViewById(R.id.nutrition_num_servings)).setText(String.valueOf(item.getNumServings()));
        updateInformation();
    }

    public MenuItem getItem() {
        return item;
    }

    private void updateInformation() {
        ((TextView) root.findViewById(R.id.nutrition_title)).setText(item.getName());
        ((TextView) root.findViewById(R.id.nutrition_serving_size)).setText(item.getServingSize());
        ((TextView) root.findViewById(R.id.nutrition_calories)).setText(String.valueOf(item.getCalories()));
        ((TextView) root.findViewById(R.id.nutrition_calories_from_fat)).setText(String.valueOf(item.getCaloriesFromFat()));
        ((TextView) root.findViewById(R.id.nutrition_total_fat)).setText(item.getTotalFat() + "g");
        ((TextView) root.findViewById(R.id.nutrition_total_fat_dv)).setText(item.getTotalFatDv() + "%");
        ((TextView) root.findViewById(R.id.nutrition_sat_fat)).setText(item.getSatFat() + "g");
        ((TextView) root.findViewById(R.id.nutrition_sat_fat_dv)).setText(item.getSatFatDv() + "%");
        ((TextView) root.findViewById(R.id.nutrition_trans_fat)).setText(item.getTransFat() + "g");
        ((TextView) root.findViewById(R.id.nutrition_trans_fat_dv)).setText(item.getTransFatDv() + "%");
        ((TextView) root.findViewById(R.id.nutrition_cholesterol)).setText(item.getCholesterol() + "mg");
        ((TextView) root.findViewById(R.id.nutrition_cholesterol_dv)).setText(item.getCholesterolDv() + "%");
        ((TextView) root.findViewById(R.id.nutrition_sodium)).setText(item.getSodium() + "mg");
        ((TextView) root.findViewById(R.id.nutrition_sodium_dv)).setText(item.getSodiumDv() + "%");
        ((TextView) root.findViewById(R.id.nutrition_total_carb)).setText(item.getTotalCarb() + "g");
        ((TextView) root.findViewById(R.id.nutrition_total_carb_dv)).setText(item.getTotalCarbDv() + "%");
        ((TextView) root.findViewById(R.id.nutrition_dietary_fiber)).setText(item.getDietaryFiber() + "g");
        ((TextView) root.findViewById(R.id.nutrition_dietary_fiber_dv)).setText(item.getDietaryFiberDv() + "%");
        ((TextView) root.findViewById(R.id.nutrition_sugars)).setText(item.getSugars() + "g");
        ((TextView) root.findViewById(R.id.nutrition_sugars_dv)).setText(item.getSugarsDv() + "%");
        ((TextView) root.findViewById(R.id.nutrition_protein)).setText(item.getProtein() + "g");
        ((TextView) root.findViewById(R.id.nutrition_protein_dv)).setText(item.getProteinDv() + "%");
        ((TextView) root.findViewById(R.id.nutrition_allergens)).setText(item.getAllergens());
        ((TextView) root.findViewById(R.id.nutrition_ingredients)).setText(item.getIngredients());

        int fatCal = item.getTotalFat() * 9;
        int carbsCal = item.getTotalCarb() * 4;
        int proteinCal = item.getProtein() * 4;
        int[] values = new int[] {carbsCal, proteinCal, fatCal};
        for (int i = 0; i < 3; i++) {
            ((DonutChartLayout) root.findViewById(R.id.nutrition_calories_chart)).setValue(i, values[i]);
        }
        ((DonutChartLayout) root.findViewById(R.id.nutrition_calories_chart)).setCenter(item.getCalories());
    }
}
