package alc.ugadining;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/fetch_menu_items.php")
    Call<List<MenuItem>> getMenuItems();
}
