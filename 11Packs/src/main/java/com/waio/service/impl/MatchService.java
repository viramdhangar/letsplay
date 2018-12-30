package com.waio.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.waio.cricapi.MatchesDTO;
import com.waio.dao.IMatchDao;
import com.waio.model.JoinLeague;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchTeam;
import com.waio.model.MatchTeamBean;
import com.waio.model.PlayerDTO;
import com.waio.model.WinningBreakupDTO;
import com.waio.service.IMatchService;
import com.waio.util.DataUtils;

@Service("MatchService")
public class MatchService implements IMatchService{

	@Autowired
	IMatchDao matchDao;
	
	@Override
	public List<MatchesDTO> getMatches() {
		List<MatchesDTO> matchesList = matchDao.getMatches();
		for(MatchesDTO matches : matchesList) {
			matches.setTeam1Short(DataUtils.getShortForm(matches.getTeam1()));
			matches.setTeam2Short(DataUtils.getShortForm(matches.getTeam2()));
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

	@Override
	public List<LeagueDTO> getLeagues(String matchId) {
		return matchDao.getLeagues(matchId);
	}
	@Override
	public List<PlayerDTO> getSquad(String matchId) {
		return matchDao.getSquad(matchId);
	}

	@Override
	public String createTeam(MatchTeam team) {
		String result = StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(team.getId())) {
			// update existing team
			// 1. delete team first from team_player
			matchDao.deleteTeam(team.getId());
			// 2. insert team again with new players
			int insertedPlayers = matchDao.createTeam(team);
			if (insertedPlayers > 0) {
				result = "Team updated successfully.";
			}
		} else {
			// create new team
			// 1. insert team info
			BigInteger teamId = matchDao.insertTeam(team);
			// 2. set team id
			team.setId(String.valueOf(teamId));
			// 3. insert new team
			int insertedPlayers = matchDao.createTeam(team);
			if (insertedPlayers > 0) {
				result = "Team created successfully. Please join league now.";
			}
		}
		return result;
	}
	
	/**
	 * @param team
	 * @return validation message
	 */
	@Override
	public String teamValidations(MatchTeam team) {
		List<PlayerDTO> listBat = new ArrayList<PlayerDTO>();
		List<PlayerDTO> listBowl = new ArrayList<PlayerDTO>();
		List<PlayerDTO> listAll = new ArrayList<PlayerDTO>();
		List<PlayerDTO> listWk = new ArrayList<PlayerDTO>();
		
		List<PlayerDTO> team1 = new ArrayList<PlayerDTO>();
		List<PlayerDTO> team2 = new ArrayList<PlayerDTO>();
		
		String str = "";
		String teamName = StringUtils.EMPTY;
		double totalCredits = 0;
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
			
			// create separate team list to check each team size
			if(StringUtils.isEmpty(teamName)) {
				team1.add(player);
				teamName = player.getPlayingTeamName();
			}
			if(player.getPlayingTeamName().equalsIgnoreCase(teamName)) {
				team1.add(player);
			} else {
				team2.add(player);
			}
			
			// add credit
			totalCredits = totalCredits + player.getCredit();
		}
		
		// check each team size validations
		if(team1.size() > 7 || team2.size() > 7) {
			str = "Maximum 7 players allowed from one team";
		}
		
		// validate credits should not go beyond 100
		if(totalCredits >100) {
			str = "You are crossing maximum salary limit of 100 Cr";
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

	@Override
	public List<MatchTeam> getCreatedTeams(String uniqueNumber) {
		return matchDao.getCreatedTeams(uniqueNumber);
	}

	@Override
	public List<MatchTeam> getCreatedTeamsOfMatch(String uniqueNumber, String matchId) {
		return matchDao.getCreatedTeamsOfMatch(uniqueNumber, matchId);
	}
	
	@Override
	public MatchTeam getTeam(String uniqueNumber, String matchId, String teamId) {
		List<MatchTeamBean> team = matchDao.getTeam(uniqueNumber, matchId, teamId);
		return setValuesInMatchTeam(team, uniqueNumber, matchId, teamId);
	}
	
	public MatchTeam setValuesInMatchTeam(List<MatchTeamBean> team, String uniqueNumber, String matchId, String teamId){	
		MatchTeam matchTeam = new MatchTeam();
		List<PlayerDTO> players = new ArrayList<PlayerDTO>();
		for(MatchTeamBean matchBean : team) {
			PlayerDTO playerDTO = new PlayerDTO();
			playerDTO.setPid(matchBean.getPid());
			playerDTO.setName(matchBean.getName());
			playerDTO.setPlayingRole(matchBean.getPlayingRole());
			playerDTO.setImageURL(matchBean.getImageURL());
			playerDTO.setCountry(matchBean.getCountry());
			playerDTO.setCaptain(matchBean.getCaptain());
			playerDTO.setViceCaptain(matchBean.getViceCaptain());
			playerDTO.setPlayingTeamName(matchBean.getPlayingTeamName());
			players.add(playerDTO);
		}
		matchTeam.setId(teamId);
		if(team.size()>0) {
			matchTeam.setTeamName(team.get(0).getTeamName());
		}
		matchTeam.setMatchId(matchId);
		matchTeam.setUniqueNumber(uniqueNumber);
		matchTeam.setPlayers(players);
		return matchTeam;
	}

	@Override
	public List<PlayerDTO> setSelectedPlayersInSquad(List<PlayerDTO> squad, MatchTeam matchTeam) {
		Collection<String> playerIds = CollectionUtils.collect(matchTeam.getPlayers(), new Transformer<PlayerDTO, String>(){
			@Override
			public String transform(PlayerDTO object) {
				return object.getPid();
			}
		});
		for(PlayerDTO squa : squad) {
			if(playerIds.contains(squa.getPid())) {
				squa.setSelected("Y");
			}
		}
		return squad;
	}

	@Override
	public String joinLeague(JoinLeague joinLeague) {
		return matchDao.joinLeague(joinLeague);
	}

	@Override
	public List<WinningBreakupDTO> getWinningBreakup(String breakupId) {
		return matchDao.getWinningBreakup(breakupId);
	}

	@Override
	public MatchesDTO getMatchLiveStatus(String matchId) {
		return matchDao.getMatchLiveStatus(matchId);
	}

	@Override
	public List<LeagueDTO> getJoinedLeagues(String uniqueNumber, String matchId) {
		return matchDao.getJoinedLeagues(uniqueNumber, matchId);
	}

	@Override
	public List<LeagueDTO> getJoinedLeagues(String uniqueNumber) {
		return matchDao.getJoinedLeagues(uniqueNumber);
	}

	@Override
	public List<MatchTeam> getJoinedLeagueTeams(String uniqueNumber, String matchId, String leagueId) {
		return matchDao.getJoinedLeagueTeams(uniqueNumber, matchId, leagueId);
	}

	@Override
	public String switchTeam(MatchTeam matchTeam, String leagueId) {
		String result = StringUtils.EMPTY;
		if(matchDao.switchTeam(matchTeam, leagueId) > 0) {
			result = "Team swithed successfully.";
		} else {
			result = "Team Not switched.";
		}
		return result;
	}
}
