
import com.apollographql.apollo3.ApolloCall;
import com.apollographql.apollo3.ApolloClient;
import com.apollographql.apollo3.api.ApolloResponse;
import com.apollographql.apollo3.rx3.Rx3Apollo;
import graphql.CreateProductMutation;
import graphql.DeleteProductMutation;
import graphql.ProductQuery;
import graphql.ProductQuery.Product;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductQueryTest {

	private ApolloClient client;

	@BeforeEach
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public void setUp() {

		client = new ApolloClient.Builder()
			.serverUrl("http://localhost:8080/graphql")
			.build();

		CreateProductMutation createProductMutation = new CreateProductMutation("T123", "TestName", "TestCat", 2.0, "TestDesc");

		ApolloCall<CreateProductMutation.Data> createMutation = client.mutation(createProductMutation);
		Rx3Apollo.single(createMutation).blockingGet();
	}

	@AfterEach
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public void tearDown() {
		ApolloCall<DeleteProductMutation.Data> deleteMutation = client.mutation(new DeleteProductMutation("T123"));
		Rx3Apollo.single(deleteMutation).blockingGet();
	}

	@Test
	public void testProductQueryTest() {
		ApolloCall<ProductQuery.Data> query = client.query(new ProductQuery("t123"));

		ApolloResponse<ProductQuery.Data> response = Rx3Apollo.single(query).blockingGet();

		Product product = response.dataAssertNoErrors().product;

		assertThat(product.id, is("t123"));
		assertThat(product.name, is("TestName"));
		assertThat(product.category, is("TestCat"));
		assertThat(product.price, is(closeTo(2.0, 0.0)));
		assertThat(product.description, is("TestDesc"));
	}

}
