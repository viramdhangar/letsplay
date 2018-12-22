package com.waio.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.waio.cricapi.MatchesDTO;
import com.waio.dao.IMatchDao;
import com.waio.model.JoinLeague;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchTeam;
import com.waio.model.MatchTeamBean;
import com.waio.model.PlayerDTO;

@Repository("MatchDao")
public class MatchDao extends AbstractDaoSupport implements IMatchDao {
	
	/*@Override
	@Resource(name="matchDataSqlMap")
	public void setSqlMap(Map<String, String> sqlMap) {
		super.setSqlMap(sqlMap);
	}*/

	@Override
	public List<MatchesDTO> getMatches() {
		String sql = "SELECT unique_id, DATE_FORMAT(datetime, '%Y-%m-%d') date, DATE_FORMAT(datetime,'%H:%i:%s') time, team1, datetime, team2, type, squad, toss_winner_team, winner_team, matchStarted, matchLive FROM MATCHES where date>=current_date() and date<=(current_date()+2) order by date asc";
		// AND MATCH_START_TIME>=CURRENT_TIME
		List<MatchesDTO> matches = getJdbcTemplate().query(sql, new Object[] {},
				new BeanPropertyRowMapper<MatchesDTO>(MatchesDTO.class));
		return matches;
	}

	@Override
	public List<LeagueDTO> getLeagues(int matchId) {
		String sql = "select ml.id, ml.league_id, league.league, league.size, league.entry_fee, ml.match_id from league, match_leagues ml where league.id = ml.league_id and ml.match_id=? and ml.status is null";
		List<LeagueDTO> leagues = getJdbcTemplate().query(sql, new Object[] { matchId },
				new BeanPropertyRowMapper<LeagueDTO>(LeagueDTO.class));
		return leagues;
	}

	@Override
	public List<PlayerDTO> getSquad(int matchId) {
		String sql = "select p.pid, p.name, p.imageURL, p.playingRole, p.country, p.credit, p.major_teams, p.current_age, p.born, p.battingStyle, p.bowlingStyle from player p, match_squad s where s.match_id=? and s.player_id=p.pid order by p.playingRole";
		List<PlayerDTO> leagues = getJdbcTemplate().query(sql, new Object[] { matchId },
				new BeanPropertyRowMapper<PlayerDTO>(PlayerDTO.class));
		return leagues;
	}

	@Override
	public int createTeam(MatchTeam team) {
		// insert team name
		long teamId = insertTeam(team);
		// create team
		String sql = "insert into team_player (team_id, player_id) values (?, ?)";
		int[] insertedRecords = getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				PlayerDTO players = team.getPlayers().get(i);
				ps.setLong(1, teamId);
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
	public long insertTeam(MatchTeam matchTeam) {
		String sql = "insert into team (id, name, match_id, created_id) values (?, ?, ?, ?)";
		long teamId = getTeamSequence();
		getJdbcTemplate().update(sql, new Object[] { teamId, matchTeam.getTeamName(), matchTeam.getMatchId(), matchTeam.getUniqueNumber() });
		return teamId;
	}

	@Override
	public long getTeamSequence() {
		String sql = "select max(id) from team";
		long teamId = getJdbcTemplate().queryForObject(sql, Long.class);
		return teamId+1;
	}
	
	@Override
	public List<MatchTeam> getCreatedTeams(String uniqueNumber){
		String sql = "select team.id, team.name teamName, team.match_id from team where team.created_id=? order by team.id";
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
		String sql = "select team.id, team.name teamName, team.match_id, p.pid, p.name, p.playingRole, p.credit, p.imageURL, p.country from team, team_player tp, player p where team.id=? and team.id=tp.team_id and tp.player_id = p.pid order by team.id";
		List<MatchTeamBean> team = getJdbcTemplate().query(sql, new Object[] { teamId }, new BeanPropertyRowMapper<MatchTeamBean>(MatchTeamBean.class));
		return team;
	}
	
	@Override
	public String joinLeague(JoinLeague joinLeague) {
		String sql1 = "select count(team_id) from league_teams where league_id=?";
		String sql = "insert into league_teams (league_id, team_id, created_id) values (?, ?, ?)";
		int insertedRecord = getJdbcTemplate().update(sql, new Object[] { joinLeague.getLeague().getId(), joinLeague.getTeam().getId(), joinLeague.getTeam().getUniqueNumber() });
		if(insertedRecord>0) {
			return "League joined successfully, please join other league now.";
		}else {
			return "League not joined, please contact admisnistrator.";
		}
	}
}
