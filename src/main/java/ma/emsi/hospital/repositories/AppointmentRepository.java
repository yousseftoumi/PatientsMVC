package ma.emsi.hospital.repositories;

import ma.emsi.hospital.entities.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    Page<Appointment> findByDaterdvAfter(Date date, Pageable pageable);
}
