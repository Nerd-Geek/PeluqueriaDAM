package ies.luisvives.peluqueriadamtpv.mapper;

import ies.luisvives.peluqueriadamtpv.model.Appointment;
import ies.luisvives.peluqueriadamtpv.model.AppointmentList;

import java.util.List;
import java.util.stream.Collectors;

public class AppointmentListMapper {
    public AppointmentList toListItem(Appointment appointment) {
        return new AppointmentList(
                appointment.getId()
                , appointment.getUser().getUsername()
                , appointment.getService().getName()
                , appointment.getTime()
                , appointment.getDate()
        );
    }

    public List<AppointmentList> toList(List<Appointment> list) {
        return list.stream().map(this::toListItem).collect(Collectors.toList());
    }
}
