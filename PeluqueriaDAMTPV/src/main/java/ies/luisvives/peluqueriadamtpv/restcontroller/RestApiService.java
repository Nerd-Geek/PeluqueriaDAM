package ies.luisvives.peluqueriadamtpv.restcontroller;

import retrofit2.Call;
import retrofit2.http.*;

public interface RestApiService {

    @GET("service")
    Call<?> serviceGetAll();

    @GET("service/{name}")
    Call<?> getByName();

    @GET("service/{name}")
    Call<?> getByNameByOrderByPriceAsc();

    @GET("service/{id}")
    Call<?> serviceGetById(@Path("id")String id);

    @POST("service")
    Call<?> insertService();

    @PUT("service/{id}")
    Call<?> updateService();

    @DELETE("service/{id}")
    Call<?> deleteService();
}
