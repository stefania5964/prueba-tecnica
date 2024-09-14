package com.example.PruebaTecnica.Repository;
import com.example.PruebaTecnica.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
