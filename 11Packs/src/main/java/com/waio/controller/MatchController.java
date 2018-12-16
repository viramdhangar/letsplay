package com.waio.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.waio.cricapi.MatchesDTO;
import com.waio.model.LeagueDTO;
import com.waio.model.LeaguesResponse;
import com.waio.model.MatchTeam;
import com.waio.model.PlayerDTO;
import com.waio.model.SquedResponse;
import com.waio.service.IMatchService;

@CrossOrigin(origins="http://localhost:8100", maxAge = 3600)
@RestController
@RequestMapping({"/api"})
public class MatchController {

	@Autowired
	IMatchService matchService;
	
	@GetMapping("/1.0/matches")
	public List<MatchesDTO> getMatches() {
		List<MatchesDTO> matches = new ArrayList<MatchesDTO>();
		matches = matchService.getMatches();
		return matches;
	}
	

	@RequestMapping(value="/1.0/leagues/{matchId}", produces = {"application/JSON"})
	public LeaguesResponse getLeagues(@PathVariable int matchId) {
		LeaguesResponse leaguesRes = new LeaguesResponse();
		List<LeagueDTO> leagues = new ArrayList<LeagueDTO>();
		leagues = matchService.getLeagues(matchId);
		leaguesRes.setLeagues(leagues);
		leaguesRes.setMatchId(matchId);
		return leaguesRes;
	}

	@RequestMapping(value="/1.0/squad/{matchId}", produces = {"application/JSON"})
	public SquedResponse getSquad(@PathVariable int matchId) {
		SquedResponse sRes = new SquedResponse();
		List<PlayerDTO> squad = new ArrayList<PlayerDTO>();
		squad = matchService.getSquad(matchId);
		sRes.setPlayers(squad);
		sRes.setMatchId(matchId);
		return sRes;
	}
	
	@PostMapping(value="/createSquad")
	public @ResponseBody String createSquad(@RequestBody MatchTeam matchTeam) {
		String inserted = matchService.createTeam(matchTeam);
		return inserted;
	}
}
