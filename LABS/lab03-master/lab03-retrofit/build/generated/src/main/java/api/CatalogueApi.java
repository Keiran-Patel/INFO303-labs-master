package api;

import util.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.MultipartBody;

import domain.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CatalogueApi {
  /**
   * Add a new product.
   * Add a new product to the catalogue.
   * @param product The details for the product. (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("catalogue")
  Call<Void> addNewProduct(
    @retrofit2.http.Body Product product
  );

  /**
   * Get all of the products.
   * Get the entire catalogue of products.
   * @return Call&lt;List&lt;Product&gt;&gt;
   */
  @GET("catalogue")
  Call<List<Product>> getAllProducts();
    

}
