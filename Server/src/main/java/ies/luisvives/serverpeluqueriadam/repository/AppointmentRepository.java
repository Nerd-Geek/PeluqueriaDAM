package ies.luisvives.serverpeluqueriadam.repository;

import ies.luisvives.serverpeluqueriadam.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface AppointmentRepository extends CrudRepository<Appointment,String> {
    Appointment findByDate(Date date);
    Page<Appointment> findByMonth(Pageable pageable, Date date);
}
