package alc.ugadining;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MealItemView extends FrameLayout {

    MenuItem item;
    float lastX;

    public MealItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.button_delete, this);
        LayoutInflater.from(context).inflate(R.layout.relative_layout_meal_item, this);

        init();
        ((DraggableRelativeLayout) findViewById(R.id.food_item)).init();
    }

    public MealItemView(Context context, AttributeSet attrs, MenuItem item) {
        this(context, attrs);
        this.item = item;

        if (item != null) {
            updateInformation();
        }
    }

    private void init() {
        DraggableRelativeLayout drl = findViewById(R.id.food_item);
        drl.setDelete(findViewById(R.id.meal_item_delete));
    }

    public MenuItem getItem() {
        return item;
    }

    public boolean cancelClick() {
        return ((DraggableRelativeLayout) findViewById(R.id.food_item)).cancelClick();
    }

    public void updateInformation() {
        ((TextView) findViewById(R.id.food_item_name)).setText(item.getName());
        ((TextView) findViewById(R.id.food_item_info)).setText(item.getServingSize());
        ((TextView) findViewById(R.id.food_item_calories))
                .setText(String.valueOf(item.getCalories()));
    }
}
