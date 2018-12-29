package com.waio.dao;

import java.math.BigInteger;
import java.util.List;

import com.waio.cricapi.MatchesDTO;
import com.waio.model.JoinLeague;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchTeam;
import com.waio.model.MatchTeamBean;
import com.waio.model.PlayerDTO;
import com.waio.model.WinningBreakupDTO;

public interface IMatchDao {

	public List<MatchesDTO> getMatches();
	public List<LeagueDTO> getLeagues(String matchId);
	public List<PlayerDTO> getSquad(String matchId);
	int createTeam(MatchTeam team);
	BigInteger insertTeam(MatchTeam matchTeam);
	List<MatchTeam> getCreatedTeams(String uniqueNumber);
	List<MatchTeam> getCreatedTeamsOfMatch(String uniqueNumber, String matchId);
	List<MatchTeamBean> getTeam(String uniqueNumber, String matchId, String teamId);
	String joinLeague(JoinLeague joinLeague);
	List<WinningBreakupDTO> getWinningBreakup(String LeagueId);
	void setTeamName (MatchTeam matchTeam);
	int deleteTeam(String teamId);
	MatchesDTO getMatchLiveStatus (String matchId);
	List<LeagueDTO> getJoinedLeagues(String uniqueNumber, String matchId);
	List<LeagueDTO> getJoinedLeagues(String uniqueNumber);
	List<MatchTeam> getJoinedLeagueTeams(String uniqueNumber, String matchId, String leagueId);
	int switchTeam(MatchTeam matchTeam, String leagueId);
}
