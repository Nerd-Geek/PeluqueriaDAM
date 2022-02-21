package ies.luisvives.peluqueriadamtpv.restcontroller;

import ies.luisvives.peluqueriadamtpv.model.Service;
import ies.luisvives.peluqueriadamtpv.model.UserDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Date;
import java.util.List;

public interface RestApiUsers {

    @GET("users")
    Call<List<UserDTO>> usersGetAll();

    @GET("username/{username}")
    Call<?> getByUserName();

    @GET("users/{id}")
    Call<?> usersGetById(@Path("id")String id);

    @POST("users")
    Call<UserDTO> insertusers(@Body Service service);

    @PUT("users/{id}")
    Call<?> updateusers();

    @DELETE("users/{id}")
    Call<UserDTO> deleteusers(@Path("id")String id);

}
