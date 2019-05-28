package alc.ugadining;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemListFragment extends Fragment {

    private List<MenuItem> items;
    private SelectItemActivity activity;

    public ItemListFragment(SelectItemActivity activity, List<MenuItem> items) {
        this.activity = activity;
        this.items = items;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    } // onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.select_item_item_list);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        MenuItemAdapter adapter = new MenuItemAdapter(activity, items);
        rv.setAdapter(adapter);

        EditText et = view.findViewById(R.id.select_item_search);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String[] terms = s.toString().split(" +");
                boolean match;
                for (MenuItem item : items) {
                    match = true;
                    for (String term : terms) {
                        if (!item.getName().toUpperCase().contains(term.toUpperCase())) {
                            match = false;
                        }
                    }
                    int index = adapter.getItems().indexOf(item);
                    if (!match) {
                        if (index != -1) {
                            adapter.getItems().remove(index);
                            adapter.notifyItemRemoved(index);
                        }
                    }
                    else {
                        if (index == -1) {
                            int insertAt = adapter.getItems().size();
                            for (int i = 0; i < insertAt; i++) {
                                if (item.getId() < adapter.getItems().get(i).getId()) {
                                    insertAt = i;
                                    break;
                                }
                            }
                            adapter.getItems().add(insertAt, item);
                            adapter.notifyItemInserted(insertAt);
                        }
                    }
                }
            }
        });
    }
}
