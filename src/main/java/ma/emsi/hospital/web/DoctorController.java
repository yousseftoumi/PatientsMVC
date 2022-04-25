package ma.emsi.hospital.web;


import lombok.AllArgsConstructor;
import ma.emsi.hospital.entities.Doctor;
import ma.emsi.hospital.entities.Doctor;
import ma.emsi.hospital.repositories.DoctorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.Doc;
import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class DoctorController {

    private DoctorRepository doctorRepository;
    @GetMapping(path = "/user/index/doctors")
    public String doctors(Model model,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5") int size,
                           @RequestParam(name = "keyword",defaultValue = "") String keyword,
                           @RequestParam(name = "specialty",defaultValue = "") String specialty){
        Page<Doctor> doctors= doctorRepository.findByNomContainsAndAndSpecialtyContains(keyword,specialty,PageRequest.of(page, size));
        model.addAttribute("listDoctors",doctors.getContent());
        model.addAttribute("pages",new int[doctors.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        model.addAttribute("specialty",specialty);
        return "doctors";
    }

    @GetMapping(path = "/admin/doctors/delete")
    public String delete(Long id,String keyword,int page){
        doctorRepository.deleteById(id);
        return "redirect:/user/index/doctors?keyword="+keyword+"&page="+page;
    }
    @GetMapping(path = "/admin/doctors")
    @ResponseBody
    public List<Doctor> doctors(){
        return doctorRepository.findAll();
    }
    @GetMapping(path = "/admin/doctors/formDoctors")
    public String formDoctors (Model model){
        model.addAttribute("doctor",new Doctor());
        return "formDoctors";
    }

    @PostMapping(path = "/admin/doctors/save")
    public String save(Model model, @Valid Doctor doctor, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "formDoctors";
        doctorRepository.save(doctor);
        return "redirect:/user/index/doctors";
    }
    @PostMapping(path = "/admin/doctors/saveEdited")
    public String saveEdited(@Valid Doctor doctor, BindingResult bindingResult,
                             @RequestParam(defaultValue = "") String keyword,
                             @RequestParam(defaultValue = "") String specialty,@RequestParam(defaultValue = "0") int page){
        if(bindingResult.hasErrors()) return "formDoctors";
        doctorRepository.save(doctor);
        return "redirect:/user/index/doctors?keyword="+keyword+"&specialty="+specialty+"&page="+page;
    }
    @GetMapping(path = "/admin/doctors/editDoctor")
    public String editDoctor (Model model,Long id,String keyword,String specialty,int page){
        Doctor p=doctorRepository.findById(id).orElse(null);
        if(p==null) throw new RuntimeException("Docteur introuvable !!!");
        model.addAttribute("doctor",p);
        model.addAttribute("keyword",keyword);
        model.addAttribute("specialty",specialty);
        model.addAttribute("page",page);
        return "editDoctor";
    }
    
}
