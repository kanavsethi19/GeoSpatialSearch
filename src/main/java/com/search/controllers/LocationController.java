package com.search.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.search.beans.Places;
import com.search.service.LatLong;
import com.search.service.QuerySolr;



/**
 * @author kanav.sethi
 *
 */
@Controller
public class LocationController {
	private static final Logger logger = LoggerFactory.getLogger(LocationController.class);
	
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
        	logger.info("User Location: "+user.getLocation());
        	logger.info("User Miles"+user.getMiles()+"");
        	
        	if(user.getLocation()!=null && user.getMiles()!=null) {
        		String latlong = latLong.getLatLongPositions(user.getLocation());
        		Map<Object,Object> locs= querySolr.query(latlong,user.getMiles());
        		user.setLocations(locs);
        		model.addAttribute("miles",user.getMiles());
        		model.addAttribute("locmap",locs);
        	}
        } catch (Exception e) {
			logger.info("Exception occurred in Location Controller!"+ e);
		}
        return "result";
    }
}
