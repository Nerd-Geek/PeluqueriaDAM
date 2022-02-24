package ies.luisvives.peluqueriadamtpv.restcontroller;

import ies.luisvives.peluqueriadamtpv.model.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface RestApiUsers {

    @GET("users")
    Call<List<User>> usersGetAll();

    @GET("username/{username}")
    Call<?> getByUserName();

    @GET("users/{id}")
    Call<?> usersGetById(@Path("id") String id);

    @POST("users")
    Call<User> insertUsers(@Body User user);

    @PUT("users/{id}")
    Call<?> updateUsers();

    @DELETE("users/{id}")
    Call<User> deleteUser(@Path("id") String id);

}
