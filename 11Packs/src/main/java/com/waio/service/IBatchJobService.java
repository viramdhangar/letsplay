/**
 * 
 */
package com.waio.service;

import java.util.List;

import com.waio.cricapi.NewMatchesData;
import com.waio.model.PlayerDTO;
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
