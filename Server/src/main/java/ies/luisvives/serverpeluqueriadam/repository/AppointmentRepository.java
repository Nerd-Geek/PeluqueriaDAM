package ies.luisvives.serverpeluqueriadam.repository;

import ies.luisvives.serverpeluqueriadam.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment,String> {
    Page<Appointment> findByDateAndUser_UsernameContainsIgnoreCaseAndService_Id(LocalDate date, String username, String id, Pageable pageable);

    List<Appointment> findByDateAndTimeAndService_Id(LocalDate date, LocalTime time, String serviceId);




}
