/**
 * 
 */
package com.waio.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waio.cricapi.MatchesDTO;
import com.waio.cricapi.NewMatchesData;
import com.waio.cricapi.Team;
import com.waio.cricapi.TeamSquad;
import com.waio.dao.IBatchJobDao;
import com.waio.model.LeagueDTO;
import com.waio.model.PlayerDTO;
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
				if(StringUtils.isEmpty(matches.getUnique_id()) || StringUtils.isEmpty(matches.getType()) || StringUtils.isEmpty(matches.getTeam1()) || StringUtils.isEmpty(matches.getTeam2()) || matches.getDatetime()==null) {
					it.remove();
				}
			}else {
				it.remove();
			}
		}
		
		if(batchJobDao.insertMatches(matchesList)>0) {
			// set leagues bean for all matches
			for(MatchesDTO matches : matchesList) {
				if(matches.getSquad().equalsIgnoreCase("false")) {
					System.out.println("Squad not present := "+matches.getUnique_id());
					continue;
				}
				insertSquad(matches);
			}
			
			// insert leagues for all matches
			insertLeagues(matchesList);
			
			return newMatchesData;	
		}else {
			return null;
		}
	}

	public int insertSquad(MatchesDTO matches) {
		try {
		TeamSquad teamSquad = cricApiService.getSquad(matches.getUnique_id());
		List<PlayerDTO> playerList = insertPlayerInfo(teamSquad, matches);
		// insert player information
		batchJobDao.insertPlayerInfo(playerList);
		// insert match squad
		int insertedRecords = batchJobDao.insertSquad(matches.getUnique_id(), playerList);
		return insertedRecords;
		}catch(Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	
	public int insertLeagues(List<MatchesDTO> matchesList) {
		return batchJobDao.insertLeagues(matchesList);
	}
	
	public List<PlayerDTO> insertPlayerInfo(TeamSquad teamSquad, MatchesDTO matches) {
		List<PlayerDTO> playerList = new ArrayList<PlayerDTO>();
		for(Team team : teamSquad.getSquad()) {
			for(PlayerDTO player : team.getPlayers()) {
				PlayerDTO playerDTO = cricApiService.playerInfo(player.getPid());	
				if(playerDTO!=null) {
					if(playerDTO.getPlayingRole()!=null && playerDTO.getPlayingRole().contains("Bowler")){
						playerDTO.setPlayingRole("BOWL");
					} else if(playerDTO.getPlayingRole()!=null && playerDTO.getPlayingRole().contains("Allrounder")){
						playerDTO.setPlayingRole("ALL");
					} else if(playerDTO.getPlayingRole()!=null && playerDTO.getPlayingRole().contains("Wicketkeeper")){
						playerDTO.setPlayingRole("WK");
					} else {
						playerDTO.setPlayingRole("BAT");
					}
					if(matches.getType().equalsIgnoreCase("Twenty20")  && playerDTO.getData().getBowling().getT20Is()!=null) {
						if(playerDTO.getPlayingRole().contains("BOWL")){
							if(playerDTO.getData().getBowling().getT20Is().getAve()<20) {
								playerDTO.setCredit("9.5");
							}else if (playerDTO.getData().getBowling().getT20Is().getAve()>=20 && playerDTO.getData().getBatting().getT20Is().getAve()==24.99) {
								playerDTO.setCredit("9");
							}else if (playerDTO.getData().getBowling().getT20Is().getAve()>25 && playerDTO.getData().getBatting().getT20Is().getAve()<=29.99) {
								playerDTO.setCredit("8.5");
							}else if (playerDTO.getData().getBowling().getT20Is().getAve()>30) {
								playerDTO.setCredit("8");
							}
						} else {
							if(playerDTO.getData().getBatting().getT20Is().getAve()>50) {
								playerDTO.setCredit("10");
							}else if (playerDTO.getData().getBatting().getT20Is().getAve()>35 && playerDTO.getData().getBatting().getT20Is().getAve()<=49.99) {
								playerDTO.setCredit("9.5");
							}else if (playerDTO.getData().getBatting().getT20Is().getAve()>25 && playerDTO.getData().getBatting().getT20Is().getAve()<=34.99) {
								playerDTO.setCredit("9");
							}else if (playerDTO.getData().getBatting().getT20Is().getAve()>15 && playerDTO.getData().getBatting().getT20Is().getAve()<=24.99) {
								playerDTO.setCredit("8.5");
							}else if (playerDTO.getData().getBatting().getT20Is().getAve()>0 && playerDTO.getData().getBatting().getT20Is().getAve()<=14.99) {
								playerDTO.setCredit("8");
							}						
						}
					}else {
						playerDTO.setCredit("9");
					}
				}
				playerList.add(playerDTO);
			}
		}
		
	return playerList;
	}

	@Override
	public String createLeague(LeagueDTO leagueDTO) {
		return batchJobDao.createLeague(leagueDTO);
	}
}
