package com.waio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waio.cricapi.MatchesDTO;
import com.waio.dao.IMatchDao;
import com.waio.model.LeagueDTO;
import com.waio.model.PlayerDTO;
import com.waio.model.PlayerSquadDTO;
import com.waio.service.IMatchService;

@Service("MatchService")
public class MatchService implements IMatchService{

	@Autowired
	IMatchDao matchDao;
	
	@Override
	public List<MatchesDTO> getMatches() {
		return matchDao.getMatches();
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
	public List<PlayerSquadDTO> createTeam(List<PlayerSquadDTO> teamList, int matchId, String teamName, int profileId) {
		return matchDao.createTeam(teamList, matchId, teamName, profileId);
	}
}
