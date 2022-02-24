package ies.luisvives.peluqueriadamtpv.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppointmentList {
    private String id;
    private String user;
    private String service;
    private String time;
    private String date;
}
