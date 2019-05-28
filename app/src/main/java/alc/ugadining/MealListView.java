package alc.ugadining;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class MealListView extends MaterialCardView {

    public enum MealType {BREAKFAST, LUNCH, DINNER}

    MealType meal;
    int totalCalories;
    List<MealItemView> items;

    DonutChartLayout calorieChart;

    public MealListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        items = new ArrayList<>();

        LayoutInflater.from(context).inflate(R.layout.linear_layout_meal_list, this);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MealListView,
                0, 0
        );

        try {
            int meal = a.getInteger(R.styleable.MealListView_meal, 0);
            this.meal = MealType.values()[meal];
            String mealString = this.meal.toString();
            mealString = mealString.substring(0, 1) + mealString.substring(1).toLowerCase();
            ((TextView) findViewById(R.id.meal_name)).setText(mealString);
        } finally {
            a.recycle();
        }

        ((TextView) findViewById(R.id.meal_calories)).setText(String.valueOf(totalCalories));

        findViewById(R.id.meal_add_item).setOnClickListener(view -> {

        });
    }

    public void setCalorieChart(DonutChartLayout chart) {
        this.calorieChart = chart;
    }

    public MealType getMeal() {
        return meal;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    private void addCalories(int caloriesToAdd) {
        totalCalories += caloriesToAdd;
        calorieChart.setValue(meal.ordinal(), totalCalories);
        ((TextView) findViewById(R.id.meal_calories)).setText(String.valueOf(totalCalories));
    }

    private void removeCalories(int caloriesToRemove) {
        totalCalories -= caloriesToRemove;
        calorieChart.setValue(meal.ordinal(), totalCalories);
        ((TextView) findViewById(R.id.meal_calories)).setText(String.valueOf(totalCalories));
    }

    public void addMealItem(MealItemView itemView) {
        if (checkForDuplicate(itemView.getItem())) {
            return;
        }

        if (items.size() == 0) {
            findViewById(R.id.meal_list_divider).setVisibility(VISIBLE);
        }
        items.add(itemView);
        LinearLayout ll = findViewById(R.id.meal_list_container);
        ll.addView(itemView, ll.getChildCount() - 2);
        addCalories(itemView.getItem().getCalories());
    }

    public void addMealItems(MealItemView... items) {
        for (MealItemView item : items) {
            addMealItem(item);
        }
    }

    private boolean checkForDuplicate(MenuItem item) {
        LinearLayout ll = findViewById(R.id.meal_list_container);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof MealItemView) {
                MealItemView miv = (MealItemView) child;
                if (miv.getItem().getName().equals(item.getName())) {
                    int beforeCal = miv.getItem().getCalories();
                    miv.getItem().setNumServings(miv.getItem().getNumServings() +
                            item.getNumServings());
                    miv.updateInformation();
                    int afterCal = miv.getItem().getCalories();
                    int netCal = afterCal - beforeCal;
                    addCalories(netCal);
                    return true;
                }
            }
        }
        return false;
    }

    public void updateMealItem(MenuItem item) {
        LinearLayout ll = findViewById(R.id.meal_list_container);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof MealItemView) {
                MealItemView miv = (MealItemView) child;
                if (miv.getItem().getName().equals(item.getName())) {
                    int beforeCal = miv.getItem().getCalories();
                    miv.getItem().setNumServings(item.getNumServings());
                    miv.updateInformation();
                    int afterCal = miv.getItem().getCalories();
                    int netCal = afterCal - beforeCal;
                    addCalories(netCal);
                }
            }
        }
    }

    public void removeMealItem(MealItemView item) {
        items.remove(item);
        ((LinearLayout) findViewById(R.id.meal_list_container)).removeView(item);
        removeCalories(item.getItem().getCalories());
        if (items.size() == 0) {
            findViewById(R.id.meal_list_divider).setVisibility(GONE);
        }
    }
}
