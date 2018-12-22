/**
 * 
 */
package com.waio.dao.impl;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * @author Viramm
 *
 */
public class AbstractDaoSupport extends JdbcDaoSupport {
	
	private Map<String, String> sqlMap;
	
	@Autowired
	DataSource dataSource;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}

	/**
	 * @return the sqlMap
	 */
	public Map<String, String> getSqlMap() {
		return sqlMap;
	}

	/**
	 * @param sqlMap
	 *            the sqlMap to set
	 */
	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}
}
