/**
 * 
 */
package com.waio.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waio.cricapi.MatchesDTO;
import com.waio.cricapi.NewMatchesData;
import com.waio.cricapi.TeamSquad;
import com.waio.dao.IBatchJobDao;
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
			if((matches.getDate().equals(today) || matches.getDate().after(today)) && matches.getDate().before(today2)) {
				
			}else {
				it.remove();
			}
		}
		
		if(batchJobDao.insertMatches(matchesList)>0) {
			for(MatchesDTO matches : newMatchesData.getMatches()) {
				insertSquad(matches.getUnique_id());
			}
			return newMatchesData;	
		}else {
			return null;
		}
	}

	public int insertSquad(String uniqueId) {
		TeamSquad teamSquad = cricApiService.getSquad(uniqueId);
		return batchJobDao.insertSquad(uniqueId, teamSquad);
	}
}
