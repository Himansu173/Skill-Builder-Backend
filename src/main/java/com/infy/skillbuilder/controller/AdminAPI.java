package com.infy.skillbuilder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.infy.skillbuilder.dto.AdminDTO;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.service.AdminService;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@RestController
@Validated
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/adminapi")
public class AdminAPI {
    @Autowired
    private AdminService adminService;
    @Autowired
    private Environment environment;
    
    @GetMapping("/login")
    public ResponseEntity<Integer> loginMentee(@RequestParam @Pattern(regexp="[A-Za-z].*@[A-Za-z]{4,}.[A-Za-z]{2,}", message="{user.email.invalid}")  String email,
            @RequestParam String password) throws SkillBuilderException{
        Integer id=adminService.loginAdmin(email,password);
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

    @GetMapping("/getAdmin/{id}")
    public ResponseEntity<AdminDTO> getMentor(@PathVariable Integer id) throws SkillBuilderException{
        AdminDTO admin=adminService.getAdmin(id);
        return new ResponseEntity<>(admin,HttpStatus.OK);
    }
    
    @GetMapping("/getName/{adminId}")
    public ResponseEntity<String> getMenteeName(@PathVariable Integer adminId) throws SkillBuilderException{
        return new ResponseEntity<>(adminService.getAdminName(adminId),HttpStatus.OK);
    }
    
    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable Integer id, @RequestParam String oldpass, 
            @RequestParam 
            @NotNull(message="{user.password.notpresent}") 
            @Pattern(regexp="(?=.*[A-Z])(?=.*[a-z])(?=.*[\\d])(?=.*[^A-Za-z\\d])([A-Za-z\\d[^A-Za-z\\d]]{8,})", message="{user.password.invalid}")
            String newpass) throws SkillBuilderException{
        adminService.updatePassword(id, oldpass, newpass);
        return new ResponseEntity<>(environment.getProperty("API.USER_PASSWORD_UPDATED"),HttpStatus.OK);
    }

    @PutMapping("/updatePersonalDetails/{id}")
    public ResponseEntity<String> updatePersonalDetails(@PathVariable Integer id, @RequestBody AdminDTO adminDTO) throws SkillBuilderException{
        adminService.updatePersonalDetails(id, adminDTO);
        return new ResponseEntity<>(environment.getProperty("API.USER_DETAILS_UPDATED"),HttpStatus.OK);
    }

    @PostMapping("/uploadProfiePicture/{id}")
    public ResponseEntity<String> uploadProfiePicture(@PathVariable Integer id, @RequestParam("profilePic") MultipartFile profilePic) throws SkillBuilderException{
        adminService.uploadProfiePicture(id, profilePic);
        return new ResponseEntity<>(environment.getProperty("API.USER_PICTURE_UPDATED"),HttpStatus.OK);
    }
}