package ies.luisvives.serverpeluqueriadam.repository;

import ies.luisvives.serverpeluqueriadam.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment,String> {
    List<Appointment> findByDate(Date date);
    //Page<Appointment> findByMonth(Pageable pageable, Date date);

    List<Appointment> findByUser_UsernameContains(Optional<String> username);
}
