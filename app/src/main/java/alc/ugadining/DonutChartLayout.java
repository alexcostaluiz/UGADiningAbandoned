package alc.ugadining;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class DonutChartLayout extends FrameLayout {

    private int[] values;

    public DonutChartLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.constraint_layout_donut_chart, this);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DonutChartLayout,
                0, 0
        );

        try {
            String title = a.getString(R.styleable.DonutChartLayout_chart_title);
            String parts = a.getString(R.styleable.DonutChartLayout_chart_parts);
            String palette = a.getString(R.styleable.DonutChartLayout_chart_palette);
            boolean useCenter = a.getBoolean(R.styleable.DonutChartLayout_chart_use_center, false);

            ((TextView) findViewById(R.id.chart_title)).setText(title);
            if (parts != null) {
                values = new int[parts.split(" ").length];
            }
            ((DonutChartKeyView) findViewById(R.id.chart_key)).initLabels(parts);
            Palette p = ColorPalette.getPalette(palette);
            if (p != null) {
                ((DonutChartView) findViewById(R.id.chart)).setColors(p.getColors());
                ((DonutChartKeyView) findViewById(R.id.chart_key)).setColors(p.getColors());
            }
            if (useCenter) {
                findViewById(R.id.chart_center).setVisibility(VISIBLE);
            }
        } finally {
            a.recycle();
        }
    }

    public void setCenter(int value) {
        ((TextView) findViewById(R.id.chart_center)).setText(String.valueOf(value));
    }

    public void setValue(int index, int value) {
        values[index] = value;
        int sum = sumValues();
        float[] fractions = new float[values.length];
        for (int i = 0; i < fractions.length; i++) {
            fractions[i] = values[i] / (float) sum;
        }
        ((DonutChartView) findViewById(R.id.chart)).setValues(true, fractions);
    }

    private int sumValues() {
        int sum = 0;
        for (int i : values) {
            sum += i;
        }
        ((TextView) findViewById(R.id.chart_center)).setText(String.valueOf(sum));
        return sum;
    }
}
