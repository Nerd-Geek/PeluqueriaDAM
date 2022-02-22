package ies.luisvives.peluqueriadamtpv.restcontroller;

import ies.luisvives.peluqueriadamtpv.model.AppointmentDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Date;
import java.util.List;

public interface RestAPIAppointments {

    @GET("appointments/")
    Call<List<AppointmentDTO>> appointmentsGetAll();

    @GET("appointments/")
    Call<?> appointmentGetAllWithDate(@Query("date") Date date);

    @GET("appointments/{id}")
    Call<?> appointmentGetById();

    @POST("appointments/")
    Call<?> insertAppointments();

    @PUT("appointments/{id}")
    Call<?> updateAppointments();

    @DELETE("appointments/{id}")
    Call<?> deleteAppointmentById(@Path("id")String id);
}
