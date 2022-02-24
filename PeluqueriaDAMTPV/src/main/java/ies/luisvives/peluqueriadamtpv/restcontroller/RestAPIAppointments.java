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
    Call<AppointmentDTO> appointmentGetAllWithDate(@Query("date") Date date);

    @GET("appointments/")
    Call<List<AppointmentDTO>> appointmentGetAllWithUser_Username(@Query("username") String username);

    @GET("appointments/{id}")
    Call<AppointmentDTO> appointmentGetById();

    @POST("appointments/")
    Call<AppointmentDTO> insertAppointments();

    @PUT("appointments/{id}")
    Call<AppointmentDTO> updateAppointments();

    @DELETE("appointments/{id}")
    Call<AppointmentDTO> deleteAppointmentById(@Path("id")String id);
}
