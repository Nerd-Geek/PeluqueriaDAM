package ies.luisvives.peluqueriadamtpv.restcontroller;

import ies.luisvives.peluqueriadamtpv.model.Service;
import javafx.collections.ObservableList;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface RestApiService {

    @GET("services")
    Call<List<Service>> serviceGetAll();

    @GET("services/{name}")
    Call<?> getByName();

    @GET("services/{name}")
    Call<?> getByNameByOrderByPriceAsc();

    @GET("services/{id}")
    Call<?> serviceGetById(@Path("id")String id);

    @POST("services")
    Call<?> insertService();

    @PUT("services/{id}")
    Call<?> updateService();

    @DELETE("services/{id}")
    Call<?> deleteService();
}
