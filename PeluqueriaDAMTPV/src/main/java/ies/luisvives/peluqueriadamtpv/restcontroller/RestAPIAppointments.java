package ies.luisvives.peluqueriadamtpv.restcontroller;

import retrofit2.Call;
import retrofit2.http.*;

public interface RestAPIAppointments {
    //Personajes
    @GET("appointments")
    Call<?> appointmentsGetAll();

    @GET("appointments/{id}")
    Call<?> appointmentGetById();

    @POST("appointments")
    Call<?> insertAppointments();

    @PUT("appointments/{id}")
    Call<?> updateAppointments();

    @DELETE("appointments/{id}")
    Call<?> deleteAppointments();
}
