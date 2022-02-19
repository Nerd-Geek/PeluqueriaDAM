package ies.luisvives.peluqueriadamtpv.restcontroller;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.Date;

public interface RestAPIAppointments {

    @GET("appointments")
    Call<?> appointmentsGetAll();

    @GET("appointments")
    Call<?> appointmentGetAllWithDate(@Query("date") Date date);

    @GET("appointments/{id}")
    Call<?> appointmentGetById();

    @POST("appointments")
    Call<?> insertAppointments();

    @PUT("appointments/{id}")
    Call<?> updateAppointments();

    @DELETE("appointments/{id}")
    Call<?> deleteAppointments();
}
