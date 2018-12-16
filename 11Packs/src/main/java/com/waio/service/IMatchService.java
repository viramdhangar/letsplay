package com.waio.service;

import java.util.List;

import com.waio.cricapi.MatchesDTO;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchTeam;
import com.waio.model.PlayerDTO;

public interface IMatchService {

	public List<MatchesDTO> getMatches();
	public List<LeagueDTO> getLeagues(int matchId);
	public List<PlayerDTO> getSquad(int matchId);
	public String createTeam(MatchTeam team);
}
