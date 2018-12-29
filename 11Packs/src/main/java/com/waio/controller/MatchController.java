package com.waio.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.waio.cricapi.MatchesDTO;
import com.waio.model.JoinLeague;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchTeam;
import com.waio.model.PlayerDTO;
import com.waio.model.WinningBreakupDTO;
import com.waio.service.IMatchService;

@CrossOrigin(origins = "http://localhost:8100", maxAge = 3600)
@RestController
@RequestMapping({ "/api" })
public class MatchController {

	@Autowired
	IMatchService matchService;

	@GetMapping("/1.0/matches")
	public List<MatchesDTO> getMatches() {
		List<MatchesDTO> matches = new ArrayList<MatchesDTO>();
		matches = matchService.getMatches();
		return matches;
	}

	@RequestMapping(value = "/1.0/leagues/{matchId}", produces = { "application/JSON" })
	public List<LeagueDTO> getLeagues(@PathVariable String matchId) {
		List<LeagueDTO> leagues = new ArrayList<LeagueDTO>();
		if(matchService.getMatchLiveStatus(matchId).getIsActive().equalsIgnoreCase("Y")){
			leagues = matchService.getLeagues(matchId);
		}
		return leagues;
	}

	@GetMapping(value = "/1.0/winningBreakup/{breakupId}")
	public List<WinningBreakupDTO> getWinningBreakup(@PathVariable String breakupId) {
		List<WinningBreakupDTO> breakup = new ArrayList<WinningBreakupDTO>();
		breakup = matchService.getWinningBreakup(breakupId);
		return breakup;
	}

	@RequestMapping(value = "/1.0/squad/{matchId}", produces = { "application/JSON" })
	public MatchTeam getSquad(@PathVariable String matchId) {
		MatchTeam matchSquad = new MatchTeam();
		List<PlayerDTO> squad = new ArrayList<PlayerDTO>();
		squad = matchService.getSquad(matchId);
		matchSquad.setPlayers(squad);
		matchSquad.setMatchId(matchId);
		return matchSquad;
	}

	@PostMapping(value = "/1.0/createTeam")
	public @ResponseBody String createTeam(@RequestBody MatchTeam matchTeam,
			@RequestParam(value = "id", required = false) String id) {
		String result = StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(id)) {
			matchTeam.setId(id);
		}
		// performing validations
		result = matchService.teamValidations(matchTeam);
		if (StringUtils.isNotEmpty(result)) {
			return result;
		}
		// check if match is not started then only insert/update
		if (StringUtils.equalsIgnoreCase("false", matchService.getMatchLiveStatus(matchTeam.getMatchId()).getMatchStarted())) {
			// insert/update team
			result = matchService.createTeam(matchTeam);
		} else {
			result = "Match is already started.";
		}
		return result;
	}

	@GetMapping(value = "/1.0/allMatchesTeams/{uniqueNumber}")
	public @ResponseBody List<MatchTeam> getAllMatchesTeams(@PathVariable String uniqueNumber) {
		List<MatchTeam> matchTeam = matchService.getCreatedTeams(uniqueNumber);
		return matchTeam;
	}

	@GetMapping(value = "/1.0/teamsOfMatch/{uniqueNumber}/{matchId}")
	public @ResponseBody List<MatchTeam> teamsOfMatch(@PathVariable String uniqueNumber, @PathVariable String matchId) {
		List<MatchTeam> matchTeam = matchService.getCreatedTeamsOfMatch(uniqueNumber, matchId);
		return matchTeam;
	}

	@GetMapping(value = "/1.0/teamView/{uniqueNumber}/{matchId}/{teamId}")
	public @ResponseBody MatchTeam getTeamView(@PathVariable String uniqueNumber, @PathVariable String matchId,
			@PathVariable String teamId) {
		MatchTeam matchTeam = matchService.getTeam(uniqueNumber, matchId, teamId);
		return matchTeam;
	}

	@GetMapping(value = "/1.0/teamEdit/{uniqueNumber}/{matchId}/{teamId}")
	public @ResponseBody MatchTeam getSquad(@PathVariable String uniqueNumber, @PathVariable String matchId,
			@PathVariable String teamId) {
		List<PlayerDTO> squad = new ArrayList<PlayerDTO>();
		MatchTeam matchTeam = matchService.getTeam(uniqueNumber, matchId, teamId);
		squad = matchService.setSelectedPlayersInSquad(matchService.getSquad(matchId), matchTeam);
		matchTeam = new MatchTeam();
		matchTeam.setPlayers(squad);
		return matchTeam;
	}

	@PostMapping(value = "/1.0/joinLeague")
	public @ResponseBody String joinLeague(@RequestBody JoinLeague joinLeague) {
		String message = StringUtils.EMPTY;
		if(matchService.getMatchLiveStatus(joinLeague.getTeam().getMatchId()).getMatchStarted().equalsIgnoreCase("false")) {
			message = matchService.joinLeague(joinLeague);
		}
		return message;
	}

	@GetMapping(value = "/1.0/joinedLeagues/{uniqueNumber}/{matchId}")
	public List<LeagueDTO> getJoinedLeagues(@PathVariable String uniqueNumber, @PathVariable String matchId) {
		List<LeagueDTO> leagues = new ArrayList<LeagueDTO>();
		leagues = matchService.getJoinedLeagues(uniqueNumber, matchId);
		return leagues;
	}

	@GetMapping(value = "/1.0/joinedLeagues/{uniqueNumber}")
	public List<LeagueDTO> getJoinedLeagues(@PathVariable String uniqueNumber) {
		List<LeagueDTO> leagues = new ArrayList<LeagueDTO>();
		leagues = matchService.getJoinedLeagues(uniqueNumber);
		return leagues;
	}

	@GetMapping(value = "/1.0/joinedLeagueTeams/{uniqueNumber}/{matchId}/{leagueId}")
	public List<MatchTeam> getJoinedLeagueTeams(@PathVariable String uniqueNumber, @PathVariable String matchId,
			@PathVariable String leagueId) {
		List<MatchTeam> matchTeam = new ArrayList<MatchTeam>();
		matchTeam = matchService.getJoinedLeagueTeams(uniqueNumber, matchId, leagueId);
		return matchTeam;
	}
	
	@PostMapping(value = "/1.0/switchTeam/{leagueId}")
	public String switchTeam(@RequestBody MatchTeam matchTeam, @PathVariable String leagueId) {
		String result = StringUtils.EMPTY;
		if(matchService.getMatchLiveStatus(matchTeam.getMatchId()).getMatchStarted().equalsIgnoreCase("false")) {
			result = matchService.switchTeam(matchTeam, leagueId);
		}
		return result;
	}
}
