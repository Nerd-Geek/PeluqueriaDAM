package ies.luisvives.serverpeluqueriadam.services.appointments;

import ies.luisvives.serverpeluqueriadam.exceptions.appointment.OutOfStockException;
import ies.luisvives.serverpeluqueriadam.exceptions.appointment.UserDuplicatedAppointment;
import ies.luisvives.serverpeluqueriadam.model.Appointment;
import ies.luisvives.serverpeluqueriadam.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> findAppointmentById(String id) {
        return appointmentRepository.findById(id);
    }

    public Appointment saveAppointment(Appointment appointment) {
        checkServiceAvailability(appointment);
        checkUserAvailability(appointment);
        return appointmentRepository.save(appointment);
    }

    private void checkServiceAvailability(Appointment appointment) {
        if (appointment.getService().getStock() <= appointmentRepository.findByDateAndTime(appointment.getDate(), appointment.getTime()).size()) {
            throw new OutOfStockException(appointment.getService().getId(), appointment.getDate(), appointment.getTime());
        }
    }

    private void checkUserAvailability(Appointment appointment) {
        if (appointment.getUser().getAppointments().stream().anyMatch(a -> a.getDate().equals(appointment.getDate()) && a.getTime().equals(appointment.getTime()))) {
            throw new UserDuplicatedAppointment(appointment.getService().getId(), appointment.getDate(), appointment.getTime());
        }
    }

    public Appointment updateAppointment(Appointment appointment) {
        checkServiceAvailability(appointment);
        checkUserAvailability(appointment);
        return appointmentRepository.save(appointment);
    }

    public Appointment deleteAppointment(Appointment appointment) {
        appointmentRepository.delete(appointment);
        return appointment;
    }

    public Page<Appointment> findAllAppointments(Pageable paging) {
        return appointmentRepository.findAll(paging);
    }
}
