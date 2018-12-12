/**
 * 
 */
package com.waio.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.cj.util.StringUtils;
import com.waio.cricapi.MatchesDTO;
import com.waio.cricapi.NewMatchesData;
import com.waio.cricapi.TeamSquad;
import com.waio.dao.IBatchJobDao;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchesLeagues;
import com.waio.service.IBatchJobService;
import com.waio.service.ICricApiService;

/**
 * @author Viramm
 *
 */
@Service("BatchJobService")
public class BatchJobService implements IBatchJobService{

	@Autowired
	private ICricApiService cricApiService;
	
	@Autowired
	private IBatchJobDao batchJobDao;
	
	@Autowired
	public NewMatchesData insertNewMatches() {
		
		NewMatchesData newMatchesData = cricApiService.newMatches();
		List<MatchesDTO> matchesList = newMatchesData.getMatches();
		Iterator<MatchesDTO> it = matchesList.iterator();
		
		Date today = new Date();
		
		long ltime=today.getTime()+2*24*60*60*1000;
		Date today2=new Date(ltime);
		
		while(it.hasNext()) {
			MatchesDTO matches = it.next();
			if((matches.getDatetime().equals(today) || matches.getDatetime().after(today)) && matches.getDatetime().before(today2)) {
				
			}else {
				it.remove();
			}
		}
		
		if(batchJobDao.insertMatches(matchesList)>0) {
			// set leagues bean for all matches
			List<LeagueDTO> leagueList = batchJobDao.getLeagues();
			List<MatchesLeagues> matchesLeagesList = new ArrayList<MatchesLeagues>();
			for(MatchesDTO matches : matchesList) {
				
				if(StringUtils.isNullOrEmpty(matches.getUnique_id()) || StringUtils.isNullOrEmpty(matches.getType()) || StringUtils.isNullOrEmpty(matches.getTeam1()) || StringUtils.isNullOrEmpty(matches.getTeam2()) || matches.getDatetime()==null) {
					continue;
				}
				
				insertSquad(matches.getUnique_id());
				
				for(LeagueDTO league : leagueList) {
					MatchesLeagues matchLeague = new MatchesLeagues();
					matchLeague.setMatchId(matches.getUnique_id());
					matchLeague.setLeagueId(league.getId());
					matchesLeagesList.add(matchLeague);
				}
			}
			
			// insert leagues for all matches
			insertLeagues(matchesLeagesList);
			
			return newMatchesData;	
		}else {
			return null;
		}
	}

	public int insertSquad(String uniqueId) {
		TeamSquad teamSquad = cricApiService.getSquad(uniqueId);
		return batchJobDao.insertSquad(uniqueId, teamSquad);
	}
	
	public int insertLeagues(List<MatchesLeagues> matchesLeagesList) {
		return batchJobDao.insertLeagues(matchesLeagesList);
	}
}
