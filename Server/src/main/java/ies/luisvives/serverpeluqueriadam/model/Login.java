package ies.luisvives.serverpeluqueriadam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getInstance() {
        return instance;
    }

    public void setInstance(Date instance) {
        this.instance = instance;
    }

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
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
                ", image='" + image + '\'' +
                ", token='" + token + '\'' +
                ", instance=" + instance +
//                ", user=" + user + TODO RECURSIVIDAD
                '}';
    }
}
