package ies.luisvives.peluqueriadamtpv.restcontroller;

import ies.luisvives.peluqueriadamtpv.model.Appointment;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Date;
import java.util.List;

public interface RestAPIAppointments {

    @GET("appointments/")
    Call<List<Appointment>> appointmentsGetAll();

    @GET("appointments/")
    Call<Appointment> appointmentGetAllWithDate(@Query("date") Date date);

    @GET("appointments/")
    Call<List<AppointmentDTO>> appointmentGetAllWithUser_Username(@Query("username") String username);

    @GET("appointments/{id}")
    Call<Appointment> appointmentGetById();

    @POST("appointments/")
    Call<Appointment> insertAppointments();

    @PUT("appointments/{id}")
    Call<Appointment> updateAppointments();

    @DELETE("appointments/{id}")
    Call<Appointment> deleteAppointmentById(@Path("id") String id);
}
