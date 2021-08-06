package pl.printo3d.waluty.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.printo3d.waluty.repository.UserEntity;
import pl.printo3d.waluty.repository.UserService;

@Controller
public class ProfileController {

  @Autowired
  private UserService uService;
  
  @GetMapping("/profile")
  public String profil(Model mdl)
  {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserEntity ue = (UserEntity)uService.loadUserByUsername(((UserEntity)principal).getUsername());
    mdl.addAttribute("phone", ue.getPhone());
    mdl.addAttribute("website", ue.getWebsite());

    return "profil";
  }

  @RequestMapping(value="/profile", method = RequestMethod.POST)
  public String profileUpdate(
    @RequestParam(value = "phone", required = false) String phone,
    @RequestParam(value = "website", required = false) String website,
    Model mdl )
  {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserEntity ue = (UserEntity)uService.loadUserByUsername(((UserEntity)principal).getUsername());

    ue.setPhone(phone);
    ue.setWebsite(website);
    uService.updateUser(ue);

    mdl.addAttribute("phone", ue.getPhone());
    mdl.addAttribute("website", ue.getWebsite());

    return "profil";
  }

}