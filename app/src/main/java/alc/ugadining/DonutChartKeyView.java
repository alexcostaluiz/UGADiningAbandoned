package alc.ugadining;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DonutChartKeyView extends LinearLayout {

    LayoutInflater inflater;
    int[] colors;

    public DonutChartKeyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflater = LayoutInflater.from(context);
        setOrientation(VERTICAL);
    }

    public void initLabels(String partNames) {
        String[] names = partNames.split(" ");
        for (int i = 0; i < names.length; i++) {
            View v = inflater.inflate(R.layout.linear_layout_donut_chart_key_label,this, false);
            ((TextView) v.findViewById(R.id.key_label)).setText(names[i]);
            addView(v);
        }
    }

    public void setColors(int[] colors) {
        this.colors = colors;
        updateColors();
    }

    private void updateColors() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Drawable background = getResources().getDrawable(R.drawable.key_color_background, null);
            background.setColorFilter(colors[i], PorterDuff.Mode.SRC_OVER);
            child.findViewById(R.id.key_color).setBackground(background);
        }
    }
}
