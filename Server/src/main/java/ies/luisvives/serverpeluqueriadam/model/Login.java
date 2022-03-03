package ies.luisvives.serverpeluqueriadam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Login")
public class Login {
    private String id;
    private String token;
    private Date instance;
    private User user;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotBlank(message = "EL token no puede estar vacío")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @NotNull(message = "La instancia no puede estar nula")
    public Date getInstance() {
        return instance;
    }

    public void setInstance(Date instance) {
        this.instance = instance;
    }

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    @NotNull(message = "El usuario no puede estar nulo")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", instance=" + instance +
//                ", user=" + user + TODO RECURSIVIDAD
                '}';
    }
}
