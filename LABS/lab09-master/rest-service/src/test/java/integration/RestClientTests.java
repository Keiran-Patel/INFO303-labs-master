package integration;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import domain.ShoppingItem;
import domain.ShoppingList;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

public class RestClientTests {

	Retrofit retrofit = new Retrofit.Builder()
			  .baseUrl("http://localhost:8081/api/")
			  .addConverterFactory(GsonConverterFactory.create())
			  .build();

	IShoppingListService service = retrofit.create(IShoppingListService.class);

	ShoppingItem testItem1 = new ShoppingItem("name1", "desc1");
	ShoppingItem testItem2 = new ShoppingItem("name2", "desc2");
	ShoppingItem testItem3 = new ShoppingItem("name3", "desc3");

	@BeforeEach
	public void setUp() throws IOException {
		service.addItem(testItem1).execute();
		service.addItem(testItem2).execute();
		// intentionally not adding testItem3
	}

	@AfterEach
	public void tearDown() throws IOException {
		service.deleteItem(testItem1.getName()).execute();
		service.deleteItem(testItem2.getName()).execute();
		service.deleteItem(testItem3.getName()).execute();
	}

	@Test
	@SuppressWarnings("null")
	public void testAddItem() throws IOException {
		service.addItem(testItem3).execute();
		Response<ShoppingList> getRsp = service.getShoppingList().execute();
		System.out.println(getRsp.code());
		assertThat("get list operation succeeeded", getRsp.isSuccessful(), is(true));
		assertThat("testItem3 exists in list", getRsp.body().getItems(), hasItem(testItem3));
	}

	@Test
	@SuppressWarnings("null")
	public void testDeleteItem() throws IOException {
		Response<ShoppingList> getRsp = service.getShoppingList().execute();
		assertThat("get list operation succeeded", getRsp.isSuccessful(), is(true));
		assertThat("testItem1 exists prior to delete", getRsp.body().getItems(), hasItem(testItem1));
		Response<Void> deleteRsp = service.deleteItem(testItem1.getName()).execute();
		assertThat("delete operation succeeeded", deleteRsp.isSuccessful(), is(true));
		getRsp = service.getShoppingList().execute();
		assertThat("testItem1 no longer exists", getRsp.body().getItems(), not(hasItem(testItem1)));
	}


	@Test
	@SuppressWarnings("null")
	public void testGetAll() throws IOException {
		Response<ShoppingList> getRsp = service.getShoppingList().execute();
		assertThat("get list operation succeeded", getRsp.isSuccessful(), is(true));
		ShoppingList list = getRsp.body();
		assertThat("list contains both test items", list.getItems(), hasItems(testItem1, testItem2));
		assertThat("list does NOT contain testItem3", list.getItems(), not(hasItem(testItem3)));
	}

}
