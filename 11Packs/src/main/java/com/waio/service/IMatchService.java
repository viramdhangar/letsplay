package com.waio.service;

import java.util.List;

import com.waio.cricapi.MatchesDTO;
import com.waio.model.JoinLeague;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchTeam;
import com.waio.model.PlayerDTO;
import com.waio.model.WinningBreakupDTO;

public interface IMatchService {

	public List<MatchesDTO> getMatches();
	public List<LeagueDTO> getLeagues(int matchId);
	public List<PlayerDTO> getSquad(String matchId);
	public String createTeam(MatchTeam team);
	List<MatchTeam> getCreatedTeams(String uniqueNumber);
	List<MatchTeam> getCreatedTeamsOfMatch(String uniqueNumber, String matchId);
	MatchTeam getTeam(String uniqueNumber, String matchId, String teamId);
	List<PlayerDTO> setSelectedPlayersInSquad(List<PlayerDTO> squad, MatchTeam matchTeam);
	String joinLeague(JoinLeague joinLeague);
	List<WinningBreakupDTO> getWinningBreakup(String LeagueId);
}
