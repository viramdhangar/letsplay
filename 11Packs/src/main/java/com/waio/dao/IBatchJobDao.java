/**
 * 
 */
package com.waio.dao;

import java.util.List;

import com.waio.cricapi.MatchesDTO;
import com.waio.model.LeagueDTO;
import com.waio.model.PlayerDTO;

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
	public int insertSquad(String uniqueId, List<PlayerDTO> playerList);
	/**
	 * @return
	 */
	public List<LeagueDTO> getLeagues();
	/**
	 * @param matchesLeagesList
	 * @return
	 */
	public int insertLeagues(List<MatchesDTO> matchesList);
	/**
	 * @param playerList
	 * @return
	 */
	int insertPlayerInfo(final List<PlayerDTO> playerList);
	String createLeague(LeagueDTO leagueDTO);
}
