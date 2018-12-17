package com.waio.dao;

import java.util.List;
import java.util.Map;

import com.waio.cricapi.MatchesDTO;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchTeam;
import com.waio.model.MatchTeamBean;
import com.waio.model.PlayerDTO;

public interface IMatchDao {

	public List<MatchesDTO> getMatches();
	public List<LeagueDTO> getLeagues(int matchId);
	public List<PlayerDTO> getSquad(int matchId);
	int createTeam(MatchTeam team);
	long getTeamSequence();
	long insertTeam(MatchTeam matchTeam);
	List<MatchTeam> getCreatedTeams(String uniqueNumber);
	List<MatchTeam> getCreatedTeamsOfMatch(String uniqueNumber, String matchId);
	List<MatchTeamBean> getTeam(String uniqueNumber, String matchId, String teamId);
}
