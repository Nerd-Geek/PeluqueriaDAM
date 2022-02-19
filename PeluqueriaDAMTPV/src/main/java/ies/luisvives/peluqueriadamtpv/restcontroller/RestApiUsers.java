package ies.luisvives.peluqueriadamtpv.restcontroller;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.Date;

public interface RestApiUsers {

    @GET("users")
    Call<?> usersGetAll();

    @GET("users/{id}")
    Call<?> usersGetById();

    @POST("users")
    Call<?> insertusers();

    @PUT("users/{id}")
    Call<?> updateusers();

    @DELETE("users/{id}")
    Call<?> deleteusers();

}
