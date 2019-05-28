package alc.ugadining;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class BuildPlateFragment extends Fragment {

    private View root;
    private ArrayList<MenuItem> items;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_build_plate, container, false);
    } // onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root = view;

        ((MealListView) view.findViewById(R.id.build_plate_breakfast))
                .setCalorieChart(view.findViewById(R.id.build_plate_calories_chart));

        ((MealListView) view.findViewById(R.id.build_plate_lunch))
                .setCalorieChart(view.findViewById(R.id.build_plate_calories_chart));

        ((MealListView) view.findViewById(R.id.build_plate_dinner))
                .setCalorieChart(view.findViewById(R.id.build_plate_calories_chart));

        setAddItemListeners(view);
        // TODO: animate layout changes
    }

    private void setAddItemListeners(View v) {
        int[] ids = new int[] {R.id.build_plate_breakfast, R.id.build_plate_lunch,
                R.id.build_plate_dinner};

        for (int id : ids) {
            v.findViewById(id).findViewById(R.id.meal_add_item).setOnClickListener(view -> {
                Intent intent = new Intent(getContext(), SelectItemActivity.class);
                intent.putExtra("meal", ((TextView) v.findViewById(id)
                        .findViewById(R.id.meal_name)).getText().toString());
                intent.putParcelableArrayListExtra("items", items);
                if (getActivity() != null) {
                    getActivity().startActivityForResult(intent, MainActivity.SELECT_MENU_ITEM_REQUEST);
                }
            });
        }
    }

    public void addMenuItem(List<MenuItem> items, String meal) {
        MealListView mealList = getMealList(meal);
        for (MenuItem item : items) {
            MealItemView mealItem = new MealItemView(root.getContext(), null, item);
            mealItem.setOnClickListener(view -> {
                if (!mealItem.cancelClick()) {
                    Intent intent = new Intent(getContext(), SelectItemActivity.class);
                    intent.putExtra("meal", meal);
                    intent.putExtra("nutrition", item);
                    if (getActivity() != null) {
                        getActivity().startActivityForResult(intent, MainActivity.VIEW_NUTRITION_REQUEST);
                    }
                }
            });
            ((DraggableRelativeLayout) mealItem.findViewById(R.id.food_item)).setDraggable(true);
            mealItem.findViewById(R.id.meal_item_delete)
                    .setOnClickListener(view -> mealList.removeMealItem(mealItem));
            mealList.addMealItem(mealItem);
        }
    }

    public void updateMenuItem(MenuItem item, String meal) {
        MealListView mealList = getMealList(meal);
        mealList.updateMealItem(item);
    }

    private MealListView getMealList(String meal) {
        MealListView mealList;
        switch (meal) {
            case "Breakfast" :
                mealList = root.findViewById(R.id.build_plate_breakfast);
                break;
            case "Lunch" :
                mealList = root.findViewById(R.id.build_plate_lunch);
                break;
            default :
                mealList = root.findViewById(R.id.build_plate_dinner);
                break;
        }
        return mealList;
    }

    public void setItems(ArrayList<MenuItem> items) {
        this.items = items;
    }

} // BuildPlateFragment
