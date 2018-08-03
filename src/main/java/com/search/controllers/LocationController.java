package com.search.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.search.beans.Places;



@Controller
public class LocationController {
	@RequestMapping(value ="/form", method=RequestMethod.GET)
	public String save(Model model) {
		model.addAttribute("user",new Places());
		return "form";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
    public String customerSubmit(@ModelAttribute Places user, Model model) {
         
        model.addAttribute("user", user);
        try {
        	System.out.println(user.getLocation());
        } catch (Exception e) {
			e.printStackTrace();
		}
        return "result";
    }
}
