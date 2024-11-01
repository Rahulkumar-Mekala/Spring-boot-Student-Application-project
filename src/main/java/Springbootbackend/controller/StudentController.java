package Springbootbackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Springbootbackend.reprository.Studentreprository;
import Springbootbackend.student.Student;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class StudentController {
    @Autowired(required = true)
    Studentreprository studentRepository;
    
    @GetMapping("/welcome")
    public String getWelcome() {
       return "Appstart"; 
    }
    
   
    @GetMapping("/adminapplication")
    public String adminapplication() {
       return "Adminpage"; 
    }
   
    @GetMapping("/userapplication")
    public String userapplication() {
       return "userpage"; 
    }
   

    @GetMapping("/registerform")
    public String getRegister(Model model) {
        model.addAttribute("student", new Student());
        return "register";
    }

    @PostMapping("/registerform")
    public String registerStudent(@ModelAttribute("student") Student student, Model model) {
    	
    	 if (studentRepository.existsByEmail(student.getEmail())) {
    		  model.addAttribute("message", "This email Address already used please use another email address");
    		 return "register";
			
		}
    	 else {
        studentRepository.save(student);
            return "login";
		
    	 }
    }
    
  
   
   
    
    @GetMapping("/registerform/allviewdetails")
    public String uservewalldetails(Model model) {
    	List<Student> alluser = studentRepository.findAll();
    	model.addAttribute("user", alluser);
    	
    	return "Alluser";
    }
    
   @GetMapping("/registerfor/userupdatedetails")
     public String userdetails() {
		return "userupdate";
		
	}
   
    @GetMapping("/login")
    public String getlogin(Model model) {
        model.addAttribute("student",new Student());
        return "login";
        
    }
    @PostMapping("/studentlogin")
    public String Studentlogin(@ModelAttribute("student") Student student, Model model) {
        Optional<Student> optional = studentRepository.findByEmail(student.getEmail());
        
        if (optional.isPresent()) {
            Student existingStudent = optional.get();
            
            if (existingStudent.getPassword().equals(student.getPassword())) {
                model.addAttribute("message", "Login successful!");
                Student student2 = optional.get();
                model.addAttribute("user", student2);
                return "home";
            } else {
                model.addAttribute("error", "Invalid password.");
                return "login";
            }
        } else {
            model.addAttribute("error", "Email not registered.");
            return "login";
        }
    }
     
    
   
    @GetMapping("registerform/update")
        public String update(Model model) {
            model.addAttribute("updatestudent", new Student());
            return "update";  
        }
    

    @PostMapping("registerform/update")
    public String getupdate(@ModelAttribute("updatestudent") Student student, Model model) {
        Optional<Student> optionalStudent = studentRepository.findByEmail(student.getEmail());
        
      
        if (optionalStudent.isEmpty()) {
            model.addAttribute("error", "No student found with the email " + student.getEmail());
            return "studentuserupdate";
        }
       
        
        Student existingStudent = optionalStudent.get();
        existingStudent.setName(student.getName());
        existingStudent.setFatherName(student.getFatherName());
        existingStudent.setMotherName(student.getMotherName());
        existingStudent.setPhoneNumber(student.getPhoneNumber());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setPassword(student.getPassword());
        existingStudent.setGender(student.getGender());
        existingStudent.setDateOfBirth(student.getDateOfBirth());
        existingStudent.setAddress(student.getAddress());
        existingStudent.setBloodGroup(student.getBloodGroup());
        existingStudent.setDepartment(student.getDepartment());
        existingStudent.setCourse(student.getCourse());

        studentRepository.save(existingStudent);
        model.addAttribute("updatestudent", existingStudent);
        model.addAttribute("message", "Student details updated successfully!");

        return "userupdatesucessfully";
    }

        
     @GetMapping("registerfrom/userupdatedetails")
    public  String getstudentupdatedata(Model model){
    	 model.addAttribute("userupdatestudent", new Student());
		return "studentuserupdate";
    	
    }
     @PostMapping("registerfrom/userupdatedetails")
     public String getuserupdate(@ModelAttribute("userupdatestudent") Student student, Model model) {
    	 
         Optional<Student> optionalStudent = studentRepository.findByEmail(student.getEmail());
         if (optionalStudent.isEmpty()) {
             model.addAttribute("error", "No student found with the email " + student.getEmail());
             return "studentuserupdate";
         }
         
         
         Student existingStudent = optionalStudent.get();
         existingStudent.setName(student.getName());
         existingStudent.setFatherName(student.getFatherName());
         existingStudent.setMotherName(student.getMotherName());
         existingStudent.setPhoneNumber(student.getPhoneNumber());
         existingStudent.setEmail(student.getEmail());
         existingStudent.setPassword(student.getPassword());
         existingStudent.setGender(student.getGender());
         existingStudent.setDateOfBirth(student.getDateOfBirth());
         existingStudent.setAddress(student.getAddress());
         existingStudent.setBloodGroup(student.getBloodGroup());
         existingStudent.setDepartment(student.getDepartment());
         existingStudent.setCourse(student.getCourse());

         studentRepository.save(existingStudent);
         model.addAttribute("userupdatestudent", existingStudent);
         model.addAttribute("message", "Student details updated successfully!");

         return "userupdatesucessfully";
     }
   
     @GetMapping("registerform/delete")
     public String userdelete(Model model) {
         model.addAttribute("userdelete", new Student());
         return "userdelete";
     }

     @PostMapping("registerform/delete")
     public String deleteUser(@ModelAttribute("userdelete") Student student, Model model) {
         Optional<Student> optionalDelete = studentRepository.findByEmail(student.getEmail());

         if (optionalDelete.isPresent()) {
             Student existingStudent = optionalDelete.get();

             if (existingStudent.getPassword().equals(student.getPassword())) {
                 studentRepository.delete(existingStudent);
                 model.addAttribute("message", "Delete successful!");
                 return "userdeletesucess";
             } else {
                 model.addAttribute("message", "Incorrect password. Deletion failed.");
                 return "userdelete";
             }
         } else {
             model.addAttribute("message", "User not found with provided email.");
             return "userdelete";
         }
     }
     
     @GetMapping("Adminregisterform/delete")
     public String Adminuserdelete(Model model) {
         model.addAttribute("admindelete", new Student());
         return "admindelete";
     }

     @PostMapping("Adminregisterform/delete")
     public String AdmindeleteUser(@ModelAttribute("admindelete") Student student, Model model) {
         Optional<Student> optionalDelete = studentRepository.findByEmail(student.getEmail());

         if (optionalDelete.isPresent()) {
             Student existingStudent = optionalDelete.get();

             if (existingStudent.getPassword().equals(student.getPassword())) {
                 studentRepository.delete(existingStudent);
                 model.addAttribute("message", "Delete successful!");
                 return "admindeletesucessfully";
             } else {
                 model.addAttribute("message", "Incorrect password. Deletion failed.");
                 return "admindelete";
             }
         } else {
             model.addAttribute("message", "User not found with provided email.");
             return "admindelete";
         }
     }
     
     @GetMapping("/logout")
     public String logout(Model model, HttpServletRequest request) {
         HttpSession session = request.getSession(false); 
         if (session != null) {
             session.invalidate(); 
             model.addAttribute("message", "Logout successful.");
         }
         return "redirect:/login"; 
     }
     
     @GetMapping("/Adminlogin")
     public String getAdminlogin(Model model) {
         model.addAttribute("adminstudent", new Student());
         return "Adminlogin";
     }

     @PostMapping("/Adminstudentlogin")
     public String adminStudentlogin(@ModelAttribute("adminstudent") Student student, Model model) {
         Optional<Student> optional = studentRepository.findByEmail(student.getEmail());
         
         if (optional.isPresent()) {
             Student existingStudent = optional.get();
             
             if (existingStudent.getPassword().equals(student.getPassword())) {
                 model.addAttribute("message", "Admin Login successful!");
                 model.addAttribute("user", existingStudent);
                 return "Alluser";
             } else {
                 model.addAttribute("error", "Invalid password.");
                 return "Adminlogin";
             }
         } else {
             model.addAttribute("error", "Email not registered.");
             return "Adminlogin";
         }
     }

     @GetMapping("/Adminlogout")
     public String Adminlogout(RedirectAttributes redirectAttributes, HttpServletRequest request) {
         HttpSession session = request.getSession(false); 
         if (session != null) {
             session.invalidate(); 
             redirectAttributes.addFlashAttribute("message", "Logout successful.");
         }
         return "redirect:/Adminlogin"; 
     }


}

   
        
    

   

 
