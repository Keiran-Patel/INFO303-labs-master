package integration;

import domain.ShoppingItem;
import domain.ShoppingList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IShoppingListService {

	@POST("shopping")
	Call<Void> addItem(@Body ShoppingItem item);

	@GET("shopping/{name}")
	Call<ShoppingItem> getItem(@Path("name") String name);

	@PUT("shopping/{name}")
	Call<ShoppingItem> updateItem(@Path("name") String name, @Body ShoppingItem item);

	@DELETE("shopping/{name}")
	Call<Void> deleteItem(@Path("name") String name);

	@GET("shopping")
	Call<ShoppingList> getShoppingList();

}
