package ies.luisvives.serverpeluqueriadam.controller;

import ies.luisvives.serverpeluqueriadam.dto.appointment.AppointmentDTO;
import ies.luisvives.serverpeluqueriadam.dto.appointment.CreateAppointmentDTO;
import ies.luisvives.serverpeluqueriadam.dto.appointment.ListAppointmentPageDTO;
import ies.luisvives.serverpeluqueriadam.exceptions.GeneralBadRequestException;
import ies.luisvives.serverpeluqueriadam.exceptions.ServiceNotFoundException;
import ies.luisvives.serverpeluqueriadam.exceptions.appointment.AppointmentBadRequestException;
import ies.luisvives.serverpeluqueriadam.exceptions.appointment.AppointmentNotFoundException;
import ies.luisvives.serverpeluqueriadam.exceptions.appointment.OutOfStockException;
import ies.luisvives.serverpeluqueriadam.exceptions.appointment.UserDuplicatedAppointment;
import ies.luisvives.serverpeluqueriadam.exceptions.user.UserNotFoundByIdException;
import ies.luisvives.serverpeluqueriadam.mapper.AppointmentMapper;
import ies.luisvives.serverpeluqueriadam.model.Appointment;
import ies.luisvives.serverpeluqueriadam.model.Service;
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
import ies.luisvives.serverpeluqueriadam.repository.UserRepository;
import ies.luisvives.serverpeluqueriadam.services.appointments.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentMapper appointmentMapper;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, UserRepository userRepository, ies.luisvives.serverpeluqueriadam.repository.ServiceRepository serviceRepository, AppointmentMapper appointmentMapper) {
        this.appointmentService = appointmentService;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @CrossOrigin(origins = "http://localhost:3306")
    @GetMapping("/")
    public ResponseEntity<?> findAll(
            @RequestParam(required  = false, name = "searchQuery") Optional<String> searchQuery
            , @RequestParam(required  = false, name = "date") Optional<String> date
            , @RequestParam(required  = false, name = "service_id") Optional<String> service_id
    ) {
        List<Appointment> appointments = null;
        try {
            appointments = appointmentService.findAllAppointments();
            if (searchQuery.isPresent()) {
                appointments = appointments.stream().filter(a -> a.getUser().getUsername().contains(searchQuery.get())).collect(Collectors.toList());
            }
            if (date.isPresent()) {
                LocalDate parsedDate = LocalDate.parse(date.get());
                appointments = appointments.stream().filter(a -> a.getDate().equals(parsedDate)).collect(Collectors.toList());
            }
            if (service_id.isPresent())
                appointments = appointments.stream().filter(a -> a.getService().getId().equals(service_id.get())).collect(Collectors.toList());

            return ResponseEntity.ok(appointmentMapper.toDTO(appointments));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @CrossOrigin(origins = "http://localhost:3306")
    @GetMapping("/mobile")
    public ResponseEntity<?> findAllUserless(
            @RequestParam(required  = false, name = "searchQuery") Optional<String> searchQuery
            , @RequestParam(required  = false, name = "date") Optional<String> date
            , @RequestParam(required  = false, name = "service_id") Optional<String> service_id
    ) {
        List<Appointment> appointments = null;
        try {
            appointments = appointmentService.findAllAppointments();
            if (searchQuery.isPresent()) {
                appointments = appointments.stream().filter(a -> a.getUser().getUsername().contains(searchQuery.get())).collect(Collectors.toList());
            }
            if (date.isPresent()) {
                LocalDate parsedDate = LocalDate.parse(date.get());
                appointments = appointments.stream().filter(a -> a.getDate().equals(parsedDate)).collect(Collectors.toList());
            }
            if (service_id.isPresent())
                appointments = appointments.stream().filter(a -> a.getService().getId().equals(service_id.get())).collect(Collectors.toList());

            return ResponseEntity.ok(appointmentMapper.toUserlessDTO(appointments));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Appointment appointment = appointmentService.findAppointmentById(id).orElse(null);
        if (appointment == null) {
            throw new AppointmentNotFoundException(id);
        } else {
            return ResponseEntity.ok(appointmentMapper.toDTO(appointment));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody CreateAppointmentDTO appointmentDTO) {
        try {
            Appointment appointment = buildAppointmentFromCreateDTO(appointmentDTO);
            checkAppointmentData(appointment);
            Appointment inserted = appointmentService.saveAppointment(appointment);
            return ResponseEntity.ok(appointmentMapper.toDTO(inserted));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar la cita." + e.getMessage());
        }
    }

    private Appointment buildAppointmentFromCreateDTO(CreateAppointmentDTO appointmentDTO) {
        Service service = serviceRepository.findById(appointmentDTO.getServiceId()).orElseThrow(() -> new ServiceNotFoundException(appointmentDTO.getServiceId()));
        User user = userRepository.findById(appointmentDTO.getUserId()).orElseThrow(() -> new UserNotFoundByIdException(appointmentDTO.getUserId()));
        Appointment appointment = Appointment.builder()
                .id(UUID.randomUUID().toString())
                .time(appointmentDTO.getTime())
                .date(appointmentDTO.getDate())
                .user(user)
                .service(service)
                .build();
        return appointment;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody CreateAppointmentDTO createAppointmentDTO) {
        try {

            Appointment updated = appointmentService.findAppointmentById(id).orElse(null);
            if (updated == null) {
                throw new AppointmentNotFoundException(id);
            } else {
                Appointment created = buildAppointmentFromCreateDTO(createAppointmentDTO);
                created.setId(updated.getId());
                checkAppointmentData(created);
                created = appointmentService.updateAppointment(created);
                return ResponseEntity.ok(appointmentMapper.toDTO(created));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar la cita." + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            Appointment appointment = appointmentService.findAppointmentById(id).orElse(null);
            if (appointment == null) {
                throw new AppointmentNotFoundException(id);
            } else {
                appointmentService.deleteAppointment(appointment);
                return ResponseEntity.ok(appointmentMapper.toDTO(appointment));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar la cita" + e.getMessage());
        }
    }

    private void checkAppointmentData(Appointment appointment) {
        if (appointment.getUser() == null) {
            throw new AppointmentBadRequestException("User", "El usuario es obligatorio");
        }
        if (appointment.getService() == null) {
            throw new AppointmentBadRequestException("Service", "El servicio es obligatorio");
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
            Appointment inserted = appointmentService.saveAppointment(appointment);
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
            pagedResult = appointmentService.findAllAppointments(paging);

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