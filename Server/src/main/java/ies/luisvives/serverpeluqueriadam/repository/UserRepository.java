package ies.luisvives.serverpeluqueriadam.repository;

import java.util.Optional;

import ies.luisvives.serverpeluqueriadam.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

}
