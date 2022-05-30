package api;

import domain.Product;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author Mark George
 */
public class CatalogueApiTest {

   private final Retrofit retrofit = new Retrofit.Builder()
           .baseUrl("http://localhost:8080/api/")
           .addConverterFactory(GsonConverterFactory.create())
           .build();

   private CatalogueApi catalogueApi;
   private ProductApi productApi;

   private Product prod1;
   private Product prod2;
   private Product prod3;

   @BeforeEach
   public void setUp() throws IOException {
      catalogueApi = retrofit.create(CatalogueApi.class);
      productApi = retrofit.create(ProductApi.class);

      prod1 = new Product();
      prod1.setId("p1");
      prod1.setName("p1_name");
      prod1.setDescription("p1_desc");
      prod1.setPrice(new BigDecimal("1.00"));

      prod2 = new Product();
      prod2.setId("p2");
      prod2.setName("p2_name");
      prod2.setDescription("p2_desc");
      prod2.setPrice(new BigDecimal("2.00"));

      prod3 = new Product();
      prod3.setId("p3");
      prod3.setName("p3_name");
      prod3.setDescription("p3_desc");
      prod3.setPrice(new BigDecimal("3.00"));

      // POST p1 and p2 to service (leave p3 for other testing the POST method itself)
      catalogueApi.addNewProduct(prod1).execute();
      catalogueApi.addNewProduct(prod2).execute();
   }

   @AfterEach
   public void tearDown() throws IOException {
      productApi.deleteProduct(prod1.getId()).execute();
      productApi.deleteProduct(prod2.getId()).execute();
      productApi.deleteProduct(prod3.getId()).execute();
   }

   @Test
   public void testCreateNewProduct() throws IOException {
      // call the method being tested
      Response<Void> createResponse = catalogueApi.addNewProduct(prod3).execute();

      // check for 201 response
      assertThat(createResponse.code(), is(201));

      // GET the product that was created to check it was saved properly
      Response<Product> getResponse = productApi.getProduct(prod1.getId()).execute();

      // get the product from the response body
      Product returnedProduct = getResponse.body();

      // check that returned product has the correct properties (except for URI which was set by the service, so will be different)
      assertThat(returnedProduct, samePropertyValuesAs(prod1, "uri"));

      // call the method again - should get 422 response this time
      createResponse = catalogueApi.addNewProduct(prod3).execute();
      assertThat(createResponse.code(), is(422));
   }

   @Test
   public void testGetAllProducts() throws IOException {
      // call the method being tested
      Response<List<Product>> getResponse = catalogueApi.getAllProducts().execute();

      // get the products from the response
      List<Product> returnedProducts = getResponse.body();

      // check for 200 response
      assertThat(getResponse.code(), is(200));

      // check that response includes both prod1 and prod2
      assertThat(returnedProducts, hasItems(prod1, prod2));
   }

}
