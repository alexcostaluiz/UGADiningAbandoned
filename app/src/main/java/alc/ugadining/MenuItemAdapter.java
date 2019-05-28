package alc.ugadining;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder> {

    private List<MenuItem> items;
    private SelectItemActivity activity;

    static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout root;
        TextView name, servingSize, calories;
        ViewHolder(RelativeLayout rl) {
            super(rl);
            root = rl;
            name = rl.findViewById(R.id.food_item_name);
            servingSize = rl.findViewById(R.id.food_item_info);
            calories = rl.findViewById(R.id.food_item_calories);
        }
    }

    public MenuItemAdapter(SelectItemActivity activity, List<MenuItem> items) {
        this.activity = activity;
        this.items = new ArrayList<>();
        this.items.addAll(items);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.relative_layout_meal_item, parent, false);
       return new ViewHolder(rl);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem item = items.get(position);
        holder.name.setText(item.getName());
        holder.servingSize.setText(item.getServingSize());
        holder.calories.setText(String.valueOf(item.getCalories()));
        holder.root.setOnClickListener(view -> activity.goToNutritionFragment(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<MenuItem> getItems() {
        return items;
    }
}
