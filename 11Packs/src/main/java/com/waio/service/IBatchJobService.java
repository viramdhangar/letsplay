/**
 * 
 */
package com.waio.service;

import com.waio.cricapi.NewMatchesData;
/**
 * @author Viramm
 *
 */
public interface IBatchJobService {

	/**
	 * @return
	 */
	public NewMatchesData insertNewMatches();
}
