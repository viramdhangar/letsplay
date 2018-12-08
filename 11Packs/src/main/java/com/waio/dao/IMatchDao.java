package com.waio.dao;

import java.util.List;

import com.waio.cricapi.MatchesDTO;
import com.waio.model.LeagueDTO;
import com.waio.model.PlayerSquadDTO;

public interface IMatchDao {

	public List<MatchesDTO> getMatches();
	public List<LeagueDTO> getLeagues(int matchId);
	public List<PlayerSquadDTO> getSquad(int matchId);
	public List<PlayerSquadDTO> createTeam(List<PlayerSquadDTO> teamList, int matchId, String teamName, int profileId);
	public int insertTeam(String teamName, int profileId);
}
