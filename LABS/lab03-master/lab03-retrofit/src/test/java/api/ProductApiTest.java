package api;

import domain.Product;
import java.io.IOException;
import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductApiTest {

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
    public void testDeleteProduct() throws IOException {
        Response<Product> result = productApi.getProduct(prod1.getId()).execute(); //get
        assertThat(result.code(), is(200));

        Response<Void> deleteresult = productApi.deleteProduct(prod1.getId()).execute(); //delete

        assertThat(deleteresult.code(), is(204));

        Response<Product> getresult = productApi.getProduct(prod1.getId()).execute(); //get product again

        assertThat(getresult.code(), is(404));

    }

    @Test
    public void testGetProduct() throws IOException {
        Response<Product> result = productApi.getProduct(prod1.getId()).execute();
        Product testprod = result.body();
        assertThat(result.code(), is(200));
        assertThat(testprod, samePropertyValuesAs(prod1));
        Response<Product> badresult = productApi.getProduct("badid").execute();
        assertThat(badresult.code(), is(404));

    }

    @Test
    public void testUpdateProduct() throws IOException {
        prod1.setName("newname");

        Response<Void> putresponse = productApi.updateProduct(prod1.getId(), prod1).execute();
        assertThat(putresponse.code(), is(204));
        Response<Product> getresponse = productApi.getProduct(prod1.getId()).execute();
        Product testprod = getresponse.body();
        assertThat(testprod.getName(), is("newname"));
        prod1.setId("newid");
        Response<Void> badresponse = productApi.updateProduct("p1", prod1).execute();
        assertThat(badresponse.code(), is(409));


    }

}
