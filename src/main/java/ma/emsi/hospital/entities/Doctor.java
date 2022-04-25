package ma.emsi.hospital.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Doctor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min = 3,max=30)
    private String nom;
    @NotEmpty
    @Size(min = 3,max=30)
    private String specialty;
    @NotEmpty
    @Size(min = 3,max=30)
    private String ville;
    @NotEmpty
    @Size(min = 3,max=10)
    private String tel;

}
