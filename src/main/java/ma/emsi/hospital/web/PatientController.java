package ma.emsi.hospital.web;


import lombok.AllArgsConstructor;
import lombok.Value;
import ma.emsi.hospital.entities.Patient;
import ma.emsi.hospital.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;
    @GetMapping(path = "/user/index/patients")
    public String patients(Model model,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5") int size,
                           @RequestParam(name = "keyword",defaultValue = "") String keyword,
                           @RequestParam(name = "sexe",defaultValue = "") String sexe,
                           @RequestParam(name = "score",defaultValue = "0") int score){
        //Page<Patient> patients= patientRepository.findByNomContains(keyword,PageRequest.of(page, size));
        Page<Patient> patients= patientRepository.findByNomContainsAndSexeContainsAndScoreGreaterThanEqual(keyword,sexe,score,PageRequest.of(page, size));
        model.addAttribute("listPatients",patients.getContent());
        model.addAttribute("pages",new int[patients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        model.addAttribute("sexe",sexe);
        model.addAttribute("score",score);
        return "patients";
    }

    @GetMapping(path = "/admin/patients/delete")
    public String delete(Long id,String keyword,String sexe,int score,int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index/patients?keyword="+keyword+"&sexe="+sexe+"&score="+score+"&page="+page;
    }
    @GetMapping(path = "/")
    public String home(){
        return "home";
    }

    @GetMapping(path = "/admin/patients")
    @ResponseBody
    public List<Patient> patients(){
        return patientRepository.findAll();
    }


    @GetMapping(path = "/admin/patients/formPatients")
    public String formPatients (Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }

    @PostMapping(path = "/admin/patients/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/user/index/patients";
    }
    @PostMapping(path = "/admin/patients/saveEdited")
    public String saveEdited(@Valid Patient patient, BindingResult bindingResult,
                       @RequestParam(defaultValue = "") String keyword,@RequestParam(defaultValue = "0") int page){
        if(bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/user/index/patients?keyword="+keyword+"&page="+page;
    }
    @GetMapping(path = "/admin/patients/editPatient")
    public String editPatient (Model model,Long id,String keyword,String sexe,int score,int page){
        Patient p=patientRepository.findById(id).orElse(null);
        if(p==null) throw new RuntimeException("Patient introuvable !!!");
        model.addAttribute("patient",p);
        model.addAttribute("keyword",keyword);
        model.addAttribute("sexe",sexe);
        model.addAttribute("score",score);
        model.addAttribute("page",page);
        return "editPatient";
    }
}
