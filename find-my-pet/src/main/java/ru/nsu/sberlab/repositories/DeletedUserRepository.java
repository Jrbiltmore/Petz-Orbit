package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.sberlab.models.entities.DeletedUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeletedUserRepository extends JpaRepository<DeletedUser, Long> {
    Optional<DeletedUser> findByUsername(String username);
    
    List<DeletedUser> findByEmail(String email);
    
    List<DeletedUser> findByRole(String role);
    
    List<DeletedUser> findByDeletedAtIsNull();
    
    List<DeletedUser> findByDeletedAtIsNotNull();
    
    List<DeletedUser> findByDeletedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    
    List<DeletedUser> findByDeletedAtBefore(LocalDateTime dateTime);
    
    List<DeletedUser> findByDeletedAtAfter(LocalDateTime dateTime);
    
    void deleteByUsername(String username);
    
    void deleteByEmail(String email);
    
    void deleteByRole(String role);
    
    void deleteByDeletedAtBefore(LocalDateTime dateTime);
    
    void deleteByDeletedAtAfter(LocalDateTime dateTime);
    
    List<DeletedUser> findByUsernameContainingIgnoreCase(String keyword);
    
    List<DeletedUser> findByEmailContainingIgnoreCase(String keyword);
    
    List<DeletedUser> findByRoleContainingIgnoreCase(String keyword);
    
    List<DeletedUser> findByUsernameOrEmailContainingIgnoreCase(String usernameKeyword, String emailKeyword);
    
    List<DeletedUser> findByRoleIn(List<String> roles);
}
