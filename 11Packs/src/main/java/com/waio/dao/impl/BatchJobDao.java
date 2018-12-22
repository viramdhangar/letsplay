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
import com.waio.dao.IBatchJobDao;
import com.waio.model.LeagueDTO;
import com.waio.model.PlayerDTO;

@Repository("BatchJobDao")
public class BatchJobDao extends JdbcDaoSupport implements IBatchJobDao{

	@Autowired
	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public int insertMatches(final List<MatchesDTO> matchesList) {

		String sql = "insert into matches (unique_id, date, datetime, team1, team2, type, squad, toss_winner_team, winner_team, matchStarted ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE date=?, datetime=?, team1=?, team2=?, type=?, squad=?, toss_winner_team=?, winner_team=?, matchStarted=?";
		for (MatchesDTO match : matchesList) {
			getJdbcTemplate().update(sql, new Object[] { 
					match.getUnique_id(), 
					match.getDate(), 
					match.getDatetime(),
					match.getTeam1(), 
					match.getTeam2(), 
					match.getType(), 
					match.getSquad(), 
					match.getToss_winner_team(),
					match.getWinner_team(), 
					match.getMatchStarted(),
					match.getDate(), 
					match.getDatetime(), 
					match.getTeam1(), 
					match.getTeam2(), 
					match.getType(),
					match.getSquad(), 
					match.getToss_winner_team(), 
					match.getWinner_team(), 
					match.getMatchStarted() 
				});
		}
		return matchesList.size();
	}

	@Override
	public int insertSquad(final String uniqueId, final List<PlayerDTO> playerList) {
		String sql = "insert into match_squad (match_id, player_id) values (?, ?) ON DUPLICATE KEY update player_id=?";		
		
		int[] insertedPlayers = getJdbcTemplate().batchUpdate(sql,
	            new BatchPreparedStatementSetter() {
	                @Override
	                public void setValues(PreparedStatement ps, int i)
	                        throws SQLException {
	                	PlayerDTO player = playerList.get(i);
	                    ps.setString(1, uniqueId);
	                    ps.setString(2, player.getPid());
	                    ps.setString(3, player.getPid());
	                }

	                @Override
	                public int getBatchSize() {
	                    return playerList.size();
	                }
	            });
		return insertedPlayers.length;
	}
	
	@Override
	public int insertPlayerInfo(final List<PlayerDTO> playerList) {
		String sql = "insert into player (pid, name, imageURL, country, playingRole, credit, major_teams, current_age, born, battingStyle, bowlingStyle) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE name=?, imageURL=?";
		int[] insertedPlayers = getJdbcTemplate().batchUpdate(sql,
	            new BatchPreparedStatementSetter() {
	                @Override
	                public void setValues(PreparedStatement ps, int i)
	                        throws SQLException {
	                	PlayerDTO player = playerList.get(i);
	                    ps.setString(1, player.getPid());
	                    ps.setString(2, player.getName());
	                    ps.setString(3, player.getImageURL());
	                    ps.setString(4, player.getCountry());
	                    ps.setString(5, player.getPlayingRole());
	                    ps.setString(6, player.getCredit());
	                    ps.setString(7, player.getMajorTeams());
	                    ps.setString(8, player.getCurrentAge());
	                    ps.setString(9, player.getBorn());
	                    ps.setString(10, player.getBattingStyle());
	                    ps.setString(11, player.getBowlingStyle());
	                    
	                    ps.setString(12, player.getName());
	                    ps.setString(13, player.getImageURL());
	                }

	                @Override
	                public int getBatchSize() {
	                    return playerList.size();
	                }
	            });
		return insertedPlayers.length;
	}
	
	@Override
	public List<LeagueDTO> getLeagues() {
		String sql = "select * from league";
		return getJdbcTemplate().query(sql, new Object[] {}, new BeanPropertyRowMapper<LeagueDTO>(LeagueDTO.class));
	}
	
	@Override
	public int insertLeagues(List<MatchesDTO> matchesList) {
		String sql = "insert into match_leagues (league_id, match_id, size) select league.id, ? match_id, league.size from league on duplicate key update created=current_timestamp";
		int[] insertedLeagues = getJdbcTemplate().batchUpdate(sql,
	            new BatchPreparedStatementSetter() {
	                @Override
	                public void setValues(PreparedStatement ps, int i)
	                        throws SQLException {
	                	MatchesDTO matches = matchesList.get(i);
	                    ps.setString(1, matches.getUnique_id());
	                }

	                @Override
	                public int getBatchSize() {
	                    return matchesList.size();
	                }
	            });
		return insertedLeagues.length;
	}
}
