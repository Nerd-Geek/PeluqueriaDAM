package ies.luisvives.serverpeluqueriadam.controller;

import ies.luisvives.serverpeluqueriadam.config.APIConfig;
import ies.luisvives.serverpeluqueriadam.dto.appointment.AppointmentDTO;
import ies.luisvives.serverpeluqueriadam.dto.appointment.ListAppointmentPageDTO;
import ies.luisvives.serverpeluqueriadam.exceptions.GeneralBadRequestException;
import ies.luisvives.serverpeluqueriadam.exceptions.appointment.AppointmentBadRequestException;
import ies.luisvives.serverpeluqueriadam.exceptions.appointment.AppointmentNotFoundException;
import ies.luisvives.serverpeluqueriadam.exceptions.appointment.AppointmentsNotFoundException;
import ies.luisvives.serverpeluqueriadam.mapper.AppointmentMapper;
import ies.luisvives.serverpeluqueriadam.model.Appointment;
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Autowired
    public AppointmentController(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @CrossOrigin(origins = "http://localhost:3306")
    @GetMapping("/")
    public ResponseEntity<?> findAll(@RequestParam(name = "limit") Optional<String> limit) {
        List<Appointment> appointments = null;
        try {
            appointments = appointmentRepository.findAll();

            if (limit.isPresent() && !appointments.isEmpty() && appointments.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(appointmentMapper.toDTO(appointments.subList(0, Integer.parseInt(limit.get()))));
            } else {
                if (!appointments.isEmpty()) {
                    return ResponseEntity.ok(appointmentMapper.toDTO(appointments));
                } else {
                    throw new AppointmentsNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment == null) {
            throw new AppointmentNotFoundException(id);
        } else {
            return ResponseEntity.ok(appointmentMapper.toDTO(appointment));
        }
    }

    // TODO: ¿Deberíamos de agregar findByUserId para que retorne las citas del usuario?
//    @GetMapping("/user/{id}")
//    public ResponseEntity<?> findByUserId(@PathVariable String id) {
//        Appointment appointment = appointmentRepository.findById(id).orElse(null);
//        if (appointment == null) {
//            throw new AppointmentNotFoundException(id);
//        } else {
//            return ResponseEntity.ok(appointmentMapper.toDTO(appointment));
//        }
//    }

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody AppointmentDTO appointmentDTO) {
        try {
            Appointment appointment = appointmentMapper.fromDTO(appointmentDTO);
            checkAppointmentData(appointment);
            Appointment inserted = appointmentRepository.save(appointment);
            return ResponseEntity.ok(appointmentMapper.toDTO(inserted));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar la cita. Campos incorrectos.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Appointment appointment) {
        try {
            Appointment updated = appointmentRepository.findById(id).orElse(null);
            if (updated == null) {
                throw new AppointmentNotFoundException(id);
            } else {
                checkAppointmentData(appointment);
                updated.setDate(appointment.getDate());
                updated.setUser(appointment.getUser());
                updated.setTime(appointment.getTime());

                updated = appointmentRepository.save(updated);
                return ResponseEntity.ok(appointmentMapper.toDTO(updated));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar la cita. Campos incorrectos.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            Appointment appointment = appointmentRepository.findById(id).orElse(null);
            if (appointment == null) {
                throw new AppointmentNotFoundException(id);
            } else {
                appointmentRepository.delete(appointment);
                return ResponseEntity.ok(appointmentMapper.toDTO(appointment));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar la cita");
        }
    }

    /**
     * Comprueba los campos obligatorios (requisitos)
     *
     * @param appointment DAO de Appointment
     */
    private String image;
    private String token;
    private Date instance;
    private User user;

    private void checkAppointmentData(Appointment appointment) {
        if (appointment.getUser() == null) {
            throw new AppointmentBadRequestException("User", "El usuario es obligatorio");
        }
        if (appointment.getDate() == null) {
            throw new AppointmentBadRequestException("Date", "La fecha es obligatoria");
        }
        if (appointment.getTime() == null) {
            throw new AppointmentBadRequestException("Time", "La hora es obligatoria");
        }
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> newAppointment(@RequestPart("appointment") AppointmentDTO appointmentDTO) {
        try {
            Appointment appointment = appointmentMapper.fromDTO(appointmentDTO);
            checkAppointmentData(appointment);
            Appointment inserted = appointmentRepository.save(appointment);
            return ResponseEntity.ok(appointmentMapper.toDTO(inserted));
        } catch (AppointmentNotFoundException ex) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar la cita. Campos incorrectos");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        Page<Appointment> pagedResult;
        try {
            pagedResult = appointmentRepository.findAll(paging);

            ListAppointmentPageDTO listAppointmentPageDTO = ListAppointmentPageDTO.builder()
                    .data(appointmentMapper.toDTO(pagedResult.getContent()))
                    .totalPages(pagedResult.getTotalPages())
                    .totalElements(pagedResult.getTotalElements())
                    .currentPage(pagedResult.getNumber())
                    .build();
            return ResponseEntity.ok(listAppointmentPageDTO);
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }
}