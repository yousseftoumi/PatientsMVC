package ma.emsi.hospital;

import ma.emsi.hospital.entities.Doctor;
import ma.emsi.hospital.entities.Patient;
import ma.emsi.hospital.repositories.DoctorRepository;
import ma.emsi.hospital.repositories.PatientRepository;
import ma.emsi.hospital.security.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class HospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
        return args -> {
            patientRepository.save(new Patient(null,"Hassan","M",new Date(),false,12));
            patientRepository.save(new Patient(null,"Hamid","M",new Date(),true,90));
            patientRepository.save(new Patient(null,"Achraf","M",new Date(),false,78));
            patientRepository.save(new Patient(null,"Imad","M",new Date(),true,45));

            patientRepository.findAll().forEach(p->
                    System.out.println(p.getNom()));
        };
    }

    //@Bean
    CommandLineRunner commandLineRunner(DoctorRepository doctorRepository) {
        return args -> {
            doctorRepository.save(new Doctor(null,"Abdellah","Pédiatre","Rabat","0675463783"));
            doctorRepository.save(new Doctor(null,"Amine","Psychiatre","Casablanca","0675463783"));
            doctorRepository.save(new Doctor(null,"Mohammed","Pneumoologue","Tanger","0675463783"));
            doctorRepository.save(new Doctor(null,"Said","Dermatologue","Fes","0675463783"));


            doctorRepository.findAll().forEach(d->
                    System.out.println(d.getNom()));
        };

    }

    //@Bean
    CommandLineRunner saveUsers(SecurityService securityService) {
        return args -> {
            securityService.saveNewUser("youssef","123456","123456");
            securityService.saveNewUser("nizar","123456","123456");

            securityService.saveNewRole("USER","Basic tasks");
            securityService.saveNewRole("ADMIN","Admin tasks");

            securityService.addRoleToUser("youssef","USER");
            securityService.addRoleToUser("youssef","ADMIN");
            securityService.addRoleToUser("nizar","USER");
        };

    }
}
