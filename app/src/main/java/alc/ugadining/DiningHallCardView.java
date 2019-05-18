package alc.ugadining;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;

public class DiningHallCardView extends MaterialCardView {

    public DiningHallCardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.dining_hall_card_view_children, this);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DiningHallCardView,
                0, 0
        );

        try {
            String title = a.getString(R.styleable.DiningHallCardView_title);
            Drawable icon = a.getDrawable(R.styleable.DiningHallCardView_icon);
            ((TextView) findViewById(R.id.card_title)).setText(title);
            ((ImageView) findViewById(R.id.card_icon)).setImageDrawable(icon);
        } finally {
            a.recycle();
        }

        RelativeLayout bar = findViewById(R.id.card_title_bar);
        bar.setOnClickListener(this::toggleCollapsed);
    }

    public void setOccupancy(int occupancy) {
        ((TextView) findViewById(R.id.card_pc))
                .setText(getResources().getString(R.string.occupancy_pc, occupancy));
        ((TextView) findViewById(R.id.card_pc2))
                .setText(getResources().getString(R.string.occupancy_pc_with_pc, occupancy));
    }

    public void setServing(JSONArray meals) {
        String serving = "";
        if (meals == null) {
            serving = "N/A";
        } else {
            for (int i = 0; i < meals.length(); i++) {
                try {
                    String meal = meals.getString(i);
                    serving += meal.charAt(0) + " ";
                } catch (JSONException e) {
                    System.out.println("[ERROR] Failed to parse meals.");
                }
            }
        }
        ((TextView) findViewById(R.id.card_meals)).setText(serving.trim());
    }

    public void toggleCollapsed(View v) {
        LinearLayout collapse = findViewById(R.id.card_collapse);
        boolean collapsed = collapse.getVisibility() == View.GONE;
        collapse.setVisibility((collapsed) ? View.VISIBLE : View.GONE);

        float start = (collapsed) ? 0 : 180;
        float end = (collapsed) ? 180 : 0;
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                v.findViewById(R.id.card_drop),
                "rotationX",
                start,
                end
        );
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }
}
