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

public interface ProductApi {
  /**
   * Delete a product from the catalogue.
   * Delete the product that matches the supplied ID from the catalogue.
   * @param id The item&#39;s name. (required)
   * @return Call&lt;Void&gt;
   */
  @DELETE("catalogue/product/{id}")
  Call<Void> deleteProduct(
    @retrofit2.http.Path("id") String id
  );

  /**
   * Get a product&#39;s details.
   * Get the complete details for a product that matches the supplied product ID.
   * @param id The item&#39;s name. (required)
   * @return Call&lt;Product&gt;
   */
  @GET("catalogue/product/{id}")
  Call<Product> getProduct(
    @retrofit2.http.Path("id") String id
  );

  /**
   * Update a product&#39;s details.
   * &lt;p&gt;Replace the details of an existing product that matches the supplied product ID with the details supplied in the message body.&lt;/p&gt; &lt;p&gt;Note that the product&#39;s ID can &lt;b&gt;not&lt;/b&gt; be modified via this operation.&lt;/p&gt;
   * @param id The item&#39;s name. (required)
   * @param product The details for the product. (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("catalogue/product/{id}")
  Call<Void> updateProduct(
    @retrofit2.http.Path("id") String id, @retrofit2.http.Body Product product
  );

}
