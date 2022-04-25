package ma.emsi.hospital.repositories;

import ma.emsi.hospital.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    Page<Patient> findByNomContains(String kw, Pageable pageable);
    Page<Patient> findByNomContainsAndSexeContainsAndScoreGreaterThanEqual(String keyword,String sexe,int score, Pageable pageable);
}
