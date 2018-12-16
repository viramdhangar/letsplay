package com.waio.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.waio.cricapi.MatchesDTO;
import com.waio.dao.IMatchDao;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchTeam;
import com.waio.model.PlayerDTO;

@Repository("MatchDao")
public class MatchDao extends JdbcDaoSupport implements IMatchDao {

	@Autowired
	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

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
		String sql = "SELECT l.ID,l.LEAGUE,l.SIZE,l.ENTRY_FEE,wb.RANK,wb.AMOUNT FROM LEAGUE l, WINNING_BREAKUP wb, MATCHES M, MATCH_LEAGUE ML WHERE l.WINNING_amount=wb.league_id AND M.unique_id= ML.MATCH_ID AND ML.LEAGUE_ID=l.ID AND M.unique_id=?";
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
}
