/**
 * 
 */
package com.waio.dao.impl;

import org.springframework.stereotype.Repository;

import com.waio.dao.IUserDao;
import com.waio.model.UserDTO;

/**
 * @author Viramm
 *
 */
@Repository("UserDao")
public class UserDao extends AbstractDaoSupport implements IUserDao {

	@Override
	public int saveUser(UserDTO userDTO) {
		String sql = "insert into users (username, password, unique_number, email, firstname, lastname, middlename) values (?, ?, ?, ?, ?, ?, ?)";
		int savedRecords = getJdbcTemplate().update(sql,
				new Object[] { userDTO.getUsername(), 
						userDTO.getPassword(), 
						userDTO.getUniqueNumber(),
						userDTO.getEmail(), 
						userDTO.getFirstname(), 
						userDTO.getLastName(), 
						userDTO.getMiddlename() });
		return savedRecords;
	}

}
