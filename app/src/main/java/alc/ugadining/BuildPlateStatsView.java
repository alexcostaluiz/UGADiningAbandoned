package alc.ugadining;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.google.android.material.card.MaterialCardView;

public class BuildPlateStatsView extends MaterialCardView {

    public BuildPlateStatsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.relative_layout_build_plate_stats, this);
    }
}
