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

import com.waio.dao.IMatchDao;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchDTO;
import com.waio.model.PlayerSquadDTO;

@Repository("MatchDao")
public class MatchDao extends JdbcDaoSupport implements IMatchDao {

	@Autowired
	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public List<MatchDTO> getMatches() {
		String sql = "SELECT ID, MATCH_START_DATE, MATCH_START_TIME, TEAM1, TEAM2 FROM MATCHES WHERE MATCH_START_DATE>=CURRENT_TIMESTAMP ";
		// AND MATCH_START_TIME>=CURRENT_TIME
		List<MatchDTO> matches = getJdbcTemplate().query(sql, new Object[] {},
				new BeanPropertyRowMapper<MatchDTO>(MatchDTO.class));
		return matches;
	}

	@Override
	public List<LeagueDTO> getLeagues(int matchId) {
		String sql = "SELECT l.ID,l.LEAGUE,l.SIZE,l.ENTRY_FEE,wb.RANK,wb.AMOUNT FROM LEAGUE l, WINNING_BREAKUP wb, MATCHES M, MATCH_LEAGUE ML WHERE l.WINNING_amount=wb.league_id AND M.ID= ML.MATCH_ID AND ML.LEAGUE_ID=l.ID AND M.ID=?";
		List<LeagueDTO> leagues = getJdbcTemplate().query(sql, new Object[] { matchId },
				new BeanPropertyRowMapper<LeagueDTO>(LeagueDTO.class));
		return leagues;
	}

	@Override
	public List<PlayerSquadDTO> getSquad(int matchId) {
		String sql = "select p.id, p.player, p.type, p.nation, p.credit from player p, match_squad s where s.match_id=? and s.player_id=p.id order by p.type ";
		List<PlayerSquadDTO> leagues = getJdbcTemplate().query(sql, new Object[] { matchId },
				new BeanPropertyRowMapper<PlayerSquadDTO>(PlayerSquadDTO.class));
		return leagues;
	}

	@Override
	public List<PlayerSquadDTO> createTeam(final List<PlayerSquadDTO> teamList, int matchId, String teamName, int profileId) {
		// insert team name
		int teamId = insertTeam(teamName, profileId);
		// create team
		String sql = "insert into team_player (team_id, player_id) values (?, ?)";
		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				PlayerSquadDTO players = teamList.get(i);
				ps.setInt(1, teamId);
				ps.setInt(2, players.getId());
			}

			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return teamList.size();
			}
		});
		// add team with user
		String sqlUser = "insert into profile_match_team (profile_id, match_id, team_id) values (?, ?, ?)";
		getJdbcTemplate().update(sqlUser, new Object[] { profileId, matchId, teamId });

		// get all teams of match
		String getTeamSql = "select team.id teamId, team.team_name, player.id, player.player, player.type, player.nation, player.credit from team_player tp, profile_match_team pmt, player, team where tp.team_id=pmt.team_id and pmt.profile_id=? and match_id=? and tp.player_id=player.id and team.id=tp.team_id";
		return getJdbcTemplate().query(getTeamSql, new Object[] { profileId, matchId },
				new BeanPropertyRowMapper<PlayerSquadDTO>(PlayerSquadDTO.class));
	}

	@Override
	public int insertTeam(String teamName, int profileId) {
		String sql = "insert into team (team_name, created, createdid) values (?, current_timestamp, ?)";
		getJdbcTemplate().update(sql, new Object[] { teamName, profileId });
		return getJdbcTemplate().queryForObject("select LAST_INSERT_ID()", Integer.class);
	}
}
