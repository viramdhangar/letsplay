/**
 * 
 */
package com.waio.service;

import com.waio.cricapi.NewMatchesData;
import com.waio.model.LeagueDTO;
/**
 * @author Viramm
 *
 */
public interface IBatchJobService {

	/**
	 * @return
	 */
	public NewMatchesData insertNewMatches(); 
	String createLeague(LeagueDTO leagueDTO);
}
