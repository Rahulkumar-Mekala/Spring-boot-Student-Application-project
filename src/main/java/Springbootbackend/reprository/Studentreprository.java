package Springbootbackend.reprository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Springbootbackend.student.Student;


@Repository
public interface Studentreprository extends JpaRepository<Student, Integer> {
    Optional<Student> findByEmail(String email);

	boolean existsByEmail(String email);

}
