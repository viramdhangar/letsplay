/**
 * 
 */
package com.waio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waio.dao.IUserDao;
import com.waio.model.UserDTO;
import com.waio.service.IUserService;

/**
 * @author Viramm
 *
 */
@Service("UserService")
public class UserService implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	@Override
	public int saveUser(UserDTO userDTO) {
		
		return userDao.saveUser(userDTO);
	}
}
