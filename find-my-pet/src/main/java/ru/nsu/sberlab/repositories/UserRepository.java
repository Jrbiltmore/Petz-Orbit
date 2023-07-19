package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.sberlab.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
    
    Optional<User> findByUserId(Long userId);
    
    List<User> findByFirstNameIgnoreCase(String firstName);
    
    List<User> findByLastNameIgnoreCase(String lastName);
    
    List<User> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);
    
    List<User> findByFirstNameContainingIgnoreCase(String keyword);
    
    List<User> findByLastNameContainingIgnoreCase(String keyword);
    
    List<User> findByEmailContainingIgnoreCase(String keyword);
    
    List<User> findByEnabled(boolean enabled);
    
    List<User> findByRole(String role);
    
    List<User> findByRoleAndEnabled(String role, boolean enabled);
    
    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    
    List<User> findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String firstName, String email);
    
    List<User> findByLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String lastName, String email);
    
    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String firstName, String lastName, String email);
    
    List<User> findByLastNameContainingIgnoreCaseOrderByFirstNameAsc(String lastName);
    
    List<User> findByFirstNameContainingIgnoreCaseOrderByLastNameAsc(String firstName);
    
    List<User> findByEnabledOrderByFirstNameAsc(boolean enabled);
    
    List<User> findByEnabledOrderByLastNameAsc(boolean enabled);
    
    long countByRole(String role);
    
    long countByEnabled(boolean enabled);
    
    boolean existsByEmail(String email);
}
