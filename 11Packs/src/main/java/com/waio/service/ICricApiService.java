/**
 * 
 */
package com.waio.service;

import com.waio.cricapi.NewMatchesData;
import com.waio.cricapi.TeamSquad;

/**
 * @author Viramm
 *
 */
public interface ICricApiService {

	public NewMatchesData newMatches();
	public TeamSquad getSquad(String uniqueId);
}
