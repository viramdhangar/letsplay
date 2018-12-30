/**
 * 
 */
package com.waio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waio.model.UserDTO;

/**
 * @author Viramm
 * 
 * This controller will be used for authorization and authentication
 *
 */
@RestController
@RequestMapping({ "/identity" })
public class IdentityController {
	
	@GetMapping(value = "/login/{uniqueNumber}")
	public String login(@RequestBody(required = false) UserDTO userDTO) {

		if(userDTO != null) {
			System.out.println("Welcome");
		}
		return "login";
	}
}
