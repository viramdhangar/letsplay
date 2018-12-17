package com.waio.service;

import java.util.List;
import java.util.Map;

import com.waio.cricapi.MatchesDTO;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchTeam;
import com.waio.model.MatchTeamBean;
import com.waio.model.PlayerDTO;

public interface IMatchService {

	public List<MatchesDTO> getMatches();
	public List<LeagueDTO> getLeagues(int matchId);
	public List<PlayerDTO> getSquad(int matchId);
	public String createTeam(MatchTeam team);
	List<MatchTeam> getCreatedTeams(String uniqueNumber);
	List<MatchTeam> getCreatedTeamsOfMatch(String uniqueNumber, String matchId);
	MatchTeam getTeam(String uniqueNumber, String matchId, String teamId);
	List<PlayerDTO> setSelectedPlayersInSquad(List<PlayerDTO> squad, MatchTeam matchTeam);
}
