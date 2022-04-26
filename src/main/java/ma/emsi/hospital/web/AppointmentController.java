package ma.emsi.hospital.web;


import lombok.AllArgsConstructor;
import ma.emsi.hospital.entities.Appointment;
import ma.emsi.hospital.entities.Appointment;
import ma.emsi.hospital.repositories.AppointmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
public class AppointmentController {

    private AppointmentRepository appointmentRepository;
    @GetMapping(path = "/user/index/appointments")
    public String appointments(Model model,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5") int size,
                           @RequestParam(name = "date",defaultValue = "") Date date){
        Page<Appointment> appointments= appointmentRepository.findByDaterdvAfter(date,PageRequest.of(page, size));
        model.addAttribute("listAppointments",appointments.getContent());
        model.addAttribute("pages",new int[appointments.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("date",date);
        return "appointments";
    }

    @GetMapping(path = "/admin/appointments/delete")
    public String delete(Long id,Date date,int page){
        appointmentRepository.deleteById(id);
        return "redirect:/user/index/appointments?date="+date+"&page="+page;
    }

    @GetMapping(path = "/user/appointments/newAppointment")
    public String newAppointment (Model model){
        model.addAttribute("appointment",new Appointment());
        return "newAppointment";
    }

    @PostMapping(path = "/admin/appointments/save")
    public String save(Model model, @Valid Appointment appointment, BindingResult bindingResult,
                       @RequestParam(name = "date",defaultValue = "") Date date,
                       @RequestParam(defaultValue = "0") int page) {
        if (bindingResult.hasErrors()) return "newAppointment";
        appointmentRepository.save(appointment);
        return "redirect:/user/index/appointments?date=" + date + "&page=" + page;
    }

    @GetMapping(path = "/admin/appointments/editAppointment")
    public String editAppointment (Model model,Long id,Date date,int page){
        Appointment a = appointmentRepository.findById(id).orElse(null);
        if(a==null) throw new RuntimeException("Rendez-vous introuvable !!!");
        model.addAttribute("appointment",a);
        model.addAttribute("date",date);
        model.addAttribute("page",page);
        return "editAppointment";
    }
}
