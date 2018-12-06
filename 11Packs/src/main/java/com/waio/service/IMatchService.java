package com.waio.service;

import java.util.List;

import com.waio.model.LeagueDTO;
import com.waio.model.MatchDTO;
import com.waio.model.PlayerSquadDTO;

public interface IMatchService {

	public List<MatchDTO> getMatches();
	public List<LeagueDTO> getLeagues(int matchId);
	public List<PlayerSquadDTO> getSquad(int matchId);
	public List<PlayerSquadDTO> createTeam(List<PlayerSquadDTO> teamList, int matchId, String teamName, int profileId);
}
