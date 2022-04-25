package ma.emsi.hospital.repositories;

import ma.emsi.hospital.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    Page<Doctor> findByNomContainsAndAndSpecialtyContains(String keyword, String specialty, Pageable pageable);
}
