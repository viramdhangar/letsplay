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

import com.waio.model.LeagueDTO;
import com.waio.model.LeaguesResponse;
import com.waio.model.MatchDTO;
import com.waio.model.PlayerSquadDTO;
import com.waio.model.ProfileDTO;
import com.waio.model.SquedResponse;
import com.waio.service.IMatchService;

@CrossOrigin(origins="http://localhost:8100", maxAge = 3600)
@RestController
@RequestMapping({"/api"})
public class MatchController {

	@Autowired
	IMatchService matchService;
	
	@GetMapping("/matches")
	public List<MatchDTO> getMatches() {
		List<MatchDTO> matches = new ArrayList<MatchDTO>();
		matches = matchService.getMatches();
		return matches;
	}
	

	@RequestMapping(value="/leagues/{matchId}", produces = {"application/JSON"})
	public LeaguesResponse getLeagues(@PathVariable int matchId) {
		LeaguesResponse leaguesRes = new LeaguesResponse();
		List<LeagueDTO> leagues = new ArrayList<LeagueDTO>();
		leagues = matchService.getLeagues(matchId);
		leaguesRes.setLeagues(leagues);
		leaguesRes.setMatchId(matchId);
		return leaguesRes;
	}

	@RequestMapping(value="/squad/{matchId}", produces = {"application/JSON"})
	public SquedResponse getSquad(@PathVariable int matchId) {
		SquedResponse sRes = new SquedResponse();
		List<PlayerSquadDTO> squad = new ArrayList<PlayerSquadDTO>();
		squad = matchService.getSquad(matchId);
		sRes.setPlayers(squad);
		sRes.setMatchId(matchId);
		return sRes;
	}
	
	@PostMapping(value="/createSquad/{matchId}/{teamName}", produces = {"application/JSON"})
	public @ResponseBody List<PlayerSquadDTO> createSquad(@PathVariable int matchId, @PathVariable String teamName, @RequestBody List<PlayerSquadDTO> body) {
		ProfileDTO profile = new ProfileDTO();
		profile.setId(1000);
		System.out.println(body);
		List<PlayerSquadDTO> createTeam = matchService.createTeam(body, matchId, teamName, profile.getId());
		return createTeam;
	}
}
