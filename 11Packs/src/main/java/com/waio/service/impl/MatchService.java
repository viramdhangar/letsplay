package com.waio.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waio.cricapi.MatchesDTO;
import com.waio.dao.IMatchDao;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchTeam;
import com.waio.model.PlayerDTO;
import com.waio.service.IMatchService;

@Service("MatchService")
public class MatchService implements IMatchService{

	@Autowired
	IMatchDao matchDao;
	
	@Override
	public List<MatchesDTO> getMatches() {
		List<MatchesDTO> matchesList = matchDao.getMatches();
		for(MatchesDTO matches : matchesList) {
			matches.setTeam1Short(getShortForm(matches.getTeam1()));
			matches.setTeam2Short(getShortForm(matches.getTeam2()));
			matches.setFormattedTeamName(matches.getTeam1Short()+"  vs  "+matches.getTeam2Short());
			if(matches.getType().contains("20")) {
				matches.setTypeShort("T20");
			} else if (matches.getType().contains("10")) {
				matches.setTypeShort("T10");
			} else if (matches.getType().contains("ODI")) {
				matches.setTypeShort("ODI");
			} else if (matches.getType().contains("Test")) {
				matches.setTypeShort("Test");
			} else {
				matches.setTypeShort(matches.getType());
			}
		}
		return matchesList;
	}

	/**
	 * @param matches
	 */
	private String getShortForm(String teamName) {
		String[] words = teamName.split("\\W+");
		StringBuffer sb = new StringBuffer();
		if(words.length == 1) {
			return teamName.substring(0,  3).toUpperCase();
		}
		for(String str : words) {
			//if(sb.length()<1) {
				//sb.append(str.substring(0,3));
			//}else {
				sb.append(str.substring(0,1));	
			//}
		}
		return sb.toString().toUpperCase();
	}

	@Override
	public List<LeagueDTO> getLeagues(int matchId) {
		return matchDao.getLeagues(matchId);
	}
	@Override
	public List<PlayerDTO> getSquad(int matchId) {
		return matchDao.getSquad(matchId);
	}

	@Override
	public String createTeam(MatchTeam team) {
		String str = teamValidations(team);
		if(org.springframework.util.StringUtils.isEmpty(str)) {
			int insertedPlayers = matchDao.createTeam(team);
			if(insertedPlayers>0) {
				return "Team created successfully. Please join league now.";	
			}
		}
		return str;
	}
	
	/**
	 * @param team
	 * @return validation message
	 */
	private String teamValidations(MatchTeam team) {
		List<PlayerDTO> listBat = new ArrayList<PlayerDTO>();
		List<PlayerDTO> listBowl = new ArrayList<PlayerDTO>();
		List<PlayerDTO> listAll = new ArrayList<PlayerDTO>();
		List<PlayerDTO> listWk = new ArrayList<PlayerDTO>();
		String str = "";
		for(PlayerDTO player : team.getPlayers()) {
			if("BAT".equalsIgnoreCase(player.getPlayingRole())) {
				listBat.add(player);
			} else if("BOWL".equalsIgnoreCase(player.getPlayingRole())) {
				listBowl.add(player);
			} else if("ALL".equalsIgnoreCase(player.getPlayingRole())) {
				listAll.add(player);
			}else if("WK".equalsIgnoreCase(player.getPlayingRole())) {
				listWk.add(player);
			}
		}
		if(listBat.size()<3 || listBat.size()>5) {
			str = "3 to 5 batsman are compulsory";
		} else if(listBowl.size()<3 || listBowl.size()>5) {
			str = "3 to 5 bowlers are compulsory";
		} else if(listAll.size()<1 || listAll.size()>3) {
			str = "1 to 3 allrounders are compulsory";
		} else if(listWk.size()<1 || listWk.size()>1) {
			str = "Maximum or Minimun 1 Wicktkeeper allowed.";
		} else if(!((listBat.size()+listBowl.size()+listAll.size()+listWk.size()) == 11)) {
			str = "There should be 11 players in the team.";
		}
		return str;
	}
}
