package alc.ugadining;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((ViewGroup) view.findViewById(R.id.main_card_container))
                .getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        int[] ids = new int[]{R.id.main_bolton_card, R.id.main_oglethorpe_card,
                R.id.main_niche_card, R.id.main_snelling_card, R.id.main_village_card};

        String[] names = new String[]{"Bolton", "Oglethorpe", "The Niche", "Snelling", "Village Summit"};

        for (int i = 0; i < ids.length; i++) {
            DiningHallCardView card = view.findViewById(ids[i]);
            card.setOnClickListener(v -> {});
        }
    }
}
