package eu.badeasabina.daw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.badeasabina.daw.model.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository <Faculty, Long> { 
    List<Faculty> findByName(String name); 
}