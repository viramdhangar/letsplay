/**
 * 
 */
package com.waio.dao;

import java.util.List;

import com.waio.cricapi.MatchesDTO;
import com.waio.cricapi.TeamSquad;

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
}
