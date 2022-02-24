package ies.luisvives.peluqueriadamtpv.model;

import lombok.Data;

@Data
public class Appointment {
    private String id;
    private String date;
    private String time;
    private User user;
    private Service service;
}
