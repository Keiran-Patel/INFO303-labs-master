
import com.apollographql.apollo3.ApolloClient;
import com.apollographql.apollo3.api.ApolloResponse;
import com.apollographql.apollo3.rx3.Rx3Apollo;
import graphql.AddItemMutation;
import graphql.RemoveItemMutation;
import graphql.ShoppingListQuery;
import graphql.ShoppingListQuery.ShoppingList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GraphQLClientTest {

	private ApolloClient client;

	@BeforeEach
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public void setUp() {

		client = new ApolloClient.Builder()
			.serverUrl("http://localhost:8083/graphql")
			.build();

		// add some items to test with
		Rx3Apollo.single(client.mutation(new AddItemMutation("name1", "desc1"))).blockingGet();
		Rx3Apollo.single(client.mutation(new AddItemMutation("name2", "desc2"))).blockingGet();
	}

	@AfterEach
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public void tearDown() {
		// remove the test items
		Rx3Apollo.single(client.mutation(new RemoveItemMutation("name1"))).blockingGet();
		Rx3Apollo.single(client.mutation(new RemoveItemMutation("name2"))).blockingGet();
		Rx3Apollo.single(client.mutation(new RemoveItemMutation("name3"))).blockingGet();
	}

	@Test
	public void testGetShoppingList() {
		// call shoppingList query
		ApolloResponse<ShoppingListQuery.Data> rsp = Rx3Apollo.single(client.query(new ShoppingListQuery())).blockingGet();

		// get list from response
		ShoppingList list = rsp.dataAssertNoErrors().shoppingList;

		// test that list contains the expected items
		assertThat(list.items, hasItem(new ShoppingListQuery.Item("name1", "desc1")));
		assertThat(list.items, hasItem(new ShoppingListQuery.Item("name2", "desc2")));
	}

	@Test
	public void testAddItem() {
		// call addItem mutation
		ApolloResponse<AddItemMutation.Data> addRsp = Rx3Apollo.single(client.mutation(new AddItemMutation("name3", "desc3"))).blockingGet();

		// make sure the response includes the new item
		List<AddItemMutation.Item> items = addRsp.dataAssertNoErrors().addItem.items;

		// test that list contains the expected items
		assertThat(items, hasItem(new AddItemMutation.Item("name3", "desc3")));

		// check that the item is added to actual list
		ApolloResponse<ShoppingListQuery.Data> getRsp = Rx3Apollo.single(client.query(new ShoppingListQuery())).blockingGet();
		ShoppingList list = getRsp.dataAssertNoErrors().shoppingList;
		assertThat(list.items, hasItem(new ShoppingListQuery.Item("name3", "desc3")));
	}

	@Test
	public void testRemoveItem() {
		// call removeItem mutation
		ApolloResponse<RemoveItemMutation.Data> removeRsp = Rx3Apollo.single(client.mutation(new RemoveItemMutation("name1"))).blockingGet();

		// make sure the response does not includes the removed item
		List<RemoveItemMutation.Item> items = removeRsp.dataAssertNoErrors().removeItem.items;

		// test that list contains the expected items
		assertThat(items, not(hasItem(new RemoveItemMutation.Item("name1", "desc1"))));

		// check that the item was removed from the actual list
		ApolloResponse<ShoppingListQuery.Data> getRsp = Rx3Apollo.single(client.query(new ShoppingListQuery())).blockingGet();
		ShoppingList list = getRsp.dataAssertNoErrors().shoppingList;
		assertThat(list.items, not(hasItem(new ShoppingListQuery.Item("name1", "desc1"))));
	}

}
