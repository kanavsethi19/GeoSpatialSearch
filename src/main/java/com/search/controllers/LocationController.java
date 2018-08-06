package com.search.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.search.beans.Places;
import com.search.service.LatLong;
import com.search.service.QuerySolr;



@Controller
public class LocationController {
	
	@Autowired
	private LatLong latLong;
	
	@Autowired
	private QuerySolr querySolr;
	
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
        	if(user.getLocation()!=null) {
        		String[] latLongs = latLong.getLatLongPositions(user.getLocation());
        		String latlong = latLongs[0] + ","+latLongs[1];
        		Map<Object,Object> locs= querySolr.query(latlong);
        		user.setLocations(locs);
        		model.addAttribute("map",locs);
        	}
        } catch (Exception e) {
			e.printStackTrace();
		}
        return "result";
    }
}
