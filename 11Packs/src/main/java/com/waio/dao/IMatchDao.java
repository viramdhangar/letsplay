package com.waio.dao;

import java.util.List;

import com.waio.cricapi.MatchesDTO;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchTeam;
import com.waio.model.PlayerDTO;

public interface IMatchDao {

	public List<MatchesDTO> getMatches();
	public List<LeagueDTO> getLeagues(int matchId);
	public List<PlayerDTO> getSquad(int matchId);
	int createTeam(MatchTeam team);
	long getTeamSequence();
	long insertTeam(MatchTeam matchTeam);
}
