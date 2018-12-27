package com.waio.dao.impl;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.waio.cricapi.MatchesDTO;
import com.waio.dao.IMatchDao;
import com.waio.model.JoinLeague;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchTeam;
import com.waio.model.MatchTeamBean;
import com.waio.model.PlayerDTO;
import com.waio.model.WinningBreakupDTO;

@Repository("MatchDao")
public class MatchDao extends AbstractDaoSupport implements IMatchDao {
	
	/*@Override
	@Resource(name="matchDataSqlMap")
	public void setSqlMap(Map<String, String> sqlMap) {
		super.setSqlMap(sqlMap);
	}*/

	@Override
	public List<MatchesDTO> getMatches() {
		String sql = "SELECT unique_id, DATE_FORMAT(datetime, '%Y-%m-%d') date, DATE_FORMAT(datetime,'%H:%i:%s') time, team1, datetime, team2, type, squad, toss_winner_team, winner_team, matchStarted, matchLive FROM MATCHES where date>=current_date() and date<=(current_date()+2) and matchStarted='false' order by date asc";
		// AND MATCH_START_TIME>=CURRENT_TIME
		List<MatchesDTO> matches = getJdbcTemplate().query(sql, new Object[] {},
				new BeanPropertyRowMapper<MatchesDTO>(MatchesDTO.class));
		return matches;
	}

	@Override
	public List<LeagueDTO> getLeagues(int matchId) {
		String sql = "select ml.id, ml.league_id, league.league, league.size, league.entry_fee, league.winning_amount, league.winners, ml.match_id, ml.league_id breakupId, ml.joined_team from league, match_leagues ml where league.id = ml.league_id and ml.match_id=? and ml.status is null";
		List<LeagueDTO> leagues = getJdbcTemplate().query(sql, new Object[] { matchId },
				new BeanPropertyRowMapper<LeagueDTO>(LeagueDTO.class));
		return leagues;
	}
	
	@Override
	public List<WinningBreakupDTO> getWinningBreakup(String breakupId) {
		String sql = "select id, prizeMoney, prizeRank from winning_breakup where id=?";
		List<WinningBreakupDTO> breakup = getJdbcTemplate().query(sql, new Object[] { breakupId },
				new BeanPropertyRowMapper<WinningBreakupDTO>(WinningBreakupDTO.class));
		return breakup;
	}

	@Override
	public List<PlayerDTO> getSquad(String matchId) {
		String sql = "select p.pid, p.name, p.imageURL, p.playingRole, p.country, p.credit, p.major_teams, p.current_age, p.born, p.battingStyle, p.bowlingStyle from player p, match_squad s where s.match_id=? and s.player_id=p.pid order by p.playingRole";
		List<PlayerDTO> leagues = getJdbcTemplate().query(sql, new Object[] { matchId },
				new BeanPropertyRowMapper<PlayerDTO>(PlayerDTO.class));
		return leagues;
	}

	@Override
	public int createTeam(MatchTeam team) {
		// insert team name
		setTeamName(team);
		// insert team info
		BigInteger teamId = insertTeam(team);
		// create team
		String sql = "insert into team_player (team_id, player_id) values (?, ?)";
		int[] insertedRecords = getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				PlayerDTO players = team.getPlayers().get(i);
				ps.setString(1, String.valueOf(teamId));
				ps.setString(2, players.getPid());
			}

			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return team.getPlayers().size();
			}
		});		
		return insertedRecords.length;
	}

	@Override
	public synchronized BigInteger insertTeam(MatchTeam matchTeam) {
		String sql = "insert into team (name, match_id, created_id) values (?, ?, ?)";
		getJdbcTemplate().update(sql, new Object[] { matchTeam.getTeamName(), matchTeam.getMatchId(), matchTeam.getUniqueNumber() });
		return getJdbcTemplate().queryForObject("SELECT LAST_INSERT_ID() as last from team", BigInteger.class);
	}
	
	public void setTeamName (MatchTeam matchTeam) {
		String sql = "select count(id) from team where match_id=? and created_id=?";
		int teamCount = getJdbcTemplate().queryForObject(sql, new Object[] { matchTeam.getMatchId(), matchTeam.getUniqueNumber() }, Integer.class);
		matchTeam.setTeamName("Team "+teamCount+1);
	}
	
	@Override
	public List<MatchTeam> getCreatedTeams(String uniqueNumber){
		String sql = "select team.id, team.name teamName, team.match_id, team.created_id uniqueNumber from team where team.created_id=? order by team.id";
		List<MatchTeam> teamList = getJdbcTemplate().query(sql, new Object[] {uniqueNumber}, new BeanPropertyRowMapper<MatchTeam>(MatchTeam.class));
		return teamList;
	}
	
	public List<MatchTeam> getCreatedTeamsOfMatch(String uniqueNumber, String matchId){
		String sql = "select distinct team.id, team.name teamName, team.match_id, team.created_id uniqueNumber from team where team.created_id=? and team.match_id=? order by team.id";
		List<MatchTeam> teamList = getJdbcTemplate().query(sql, new Object[] {uniqueNumber, matchId}, new BeanPropertyRowMapper<MatchTeam>(MatchTeam.class));
		return teamList;
	}

	@Override
	public List<MatchTeamBean> getTeam(String uniqueNumber, String matchId, String teamId) {
		String sql = "select team.id, team.name teamName, team.match_id, team.created_id uniqueNumber, p.pid, p.name, p.playingRole, p.credit, p.imageURL, p.country from team, team_player tp, player p where team.id=? and team.id=tp.team_id and tp.player_id = p.pid order by team.id";
		List<MatchTeamBean> team = getJdbcTemplate().query(sql, new Object[] { teamId }, new BeanPropertyRowMapper<MatchTeamBean>(MatchTeamBean.class));
		return team;
	}
	
	@Override
	public synchronized String joinLeague(JoinLeague joinLeague) {

		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(getJdbcTemplate())
				.withProcedureName("join_league");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("teamId", joinLeague.getTeam().getId());
		inParamMap.put("leagueId", joinLeague.getLeague().getId());
		inParamMap.put("matchId", joinLeague.getTeam().getMatchId());
		inParamMap.put("createdId", joinLeague.getTeam().getUniqueNumber());
		inParamMap.put("result", Types.VARCHAR);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);

		if (simpleJdbcCallResult.get("result").toString().equalsIgnoreCase("SUCCESS")) {
			return "League joined successfully, please join other league now.";
		} else {
			return "League is full, please join other league now.";
		}
	}
}
