/**
 * 
 */
package com.waio.dao;

import java.util.List;

import com.waio.cricapi.MatchesDTO;
import com.waio.cricapi.TeamSquad;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchesLeagues;

/**
 * @author Viramm
 *
 */
public interface IBatchJobDao {

	/**
	 * @param newMatchesData
	 * @return
	 */
	public int insertMatches(final List<MatchesDTO> matchesList);
	/**
	 * @param uniqueId
	 * @return
	 */
	public int insertSquad(String uniqueId, TeamSquad teamSquad);
	/**
	 * @return
	 */
	public List<LeagueDTO> getLeagues();
	/**
	 * @param matchesLeagesList
	 * @return
	 */
	public int insertLeagues(List<MatchesLeagues> matchesLeagesList);
}