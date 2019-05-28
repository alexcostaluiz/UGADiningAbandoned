package alc.ugadining;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppViewModel extends ViewModel {

    private MutableLiveData<List<MenuItem>> menuItems;

    public LiveData<List<MenuItem>> getMenuItems() {
        if (menuItems == null) {
            menuItems = new MutableLiveData<>();
            loadMenuItems();
        }
        return menuItems;
    }

    private void loadMenuItems() {
        GetDataService service = RetrofitClient.getInstance().create(GetDataService.class);
        Call<List<MenuItem>> call = service.getMenuItems();
        call.enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                List<MenuItem> items = response.body();
                if (items != null) {
                    for (MenuItem item : items) {
                        item.setNumServings(1f);
                    }
                }
                menuItems.setValue(items);
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
