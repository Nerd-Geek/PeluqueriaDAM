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
    Call<UserDTO> insertUsers(@Body UserDTO userDTO);

    @PUT("users/{id}")
    Call<?> updateUsers();

    @DELETE("users/{id}")
    Call<UserDTO> deleteUser(@Path("id")String id);

}
