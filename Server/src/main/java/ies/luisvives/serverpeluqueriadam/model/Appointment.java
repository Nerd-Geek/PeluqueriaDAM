package ies.luisvives.serverpeluqueriadam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="appointment")

public class Appointment {
private String id;
private LocalDate date;
private LocalTime time;
@ToString.Exclude
private User user;
private Service service;

    public Appointment(LocalDate date, LocalTime time, User user, Service service) {
        this.id = UUID.randomUUID().toString();
        this.date = date;
        this.time = time;
        this.user = user;
        this.service = service;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "appointmentDate")
    @NotNull(message = "La fecha no puede ser nula")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Column(name = "appointmentTime")
    @NotNull(message = "El tiempo no puede ser nulo")
    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @NotNull(message = "El usuario no puede ser nulo")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_service", referencedColumnName = "id")
    @NotNull(message = "EL servicio no puede ser nulo")
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }


    @Override
    public String toString() {
        return "Appointment{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", user=" + user +
                ", service= " + service +
                '}';
    }
}
