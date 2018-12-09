package com.waio.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.waio.cricapi.MatchesDTO;
import com.waio.cricapi.Players;
import com.waio.cricapi.Team;
import com.waio.cricapi.TeamSquad;
import com.waio.dao.IBatchJobDao;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchesLeagues;
import com.waio.model.PlayerSquadDTO;

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
		
/*		int[] insertedMatches = getJdbcTemplate().batchUpdate(sql,
	            new BatchPreparedStatementSetter() {
	                @Override
	                public void setValues(PreparedStatement ps, int i)
	                        throws SQLException {
	                    MatchesDTO match = matchesList.get(i);
	                    ps.setString(1, match.getUnique_id());
	                    ps.setDate(2, (Date) match.getDate());
	                    ps.setDate(3, (Date) match.getDatetime());
	                    ps.setString(4, match.getTeam1());
	                    ps.setString(5, match.getTeam2());
	                    ps.setString(6, match.getType());
	                    ps.setString(7, match.getSquad());
	                    ps.setString(8, match.getToss_winner_team());
	                    ps.setString(9, match.getWinner_team());
	                    ps.setString(10, match.getMatchStarted());
	                    
	                    ps.setDate(11, (Date) match.getDate());
	                    ps.setDate(12, (Date) match.getDatetime());
	                    ps.setString(13, match.getTeam1());
	                    ps.setString(14, match.getTeam2());
	                    ps.setString(15, match.getType());
	                    ps.setString(16, match.getSquad());
	                    ps.setString(17, match.getToss_winner_team());
	                    ps.setString(18, match.getWinner_team());
	                    ps.setString(19, match.getMatchStarted());
	                }

	                @Override
	                public int getBatchSize() {
	                    return matchesList.size();
	                }
	            });*/
		for(MatchesDTO match : matchesList) {
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
	public int insertSquad(final String uniqueId, final TeamSquad teamSquad) {
		String sql = "insert into match_squad (match_id, player_id) values (?, ?)";
		final List<Players> players = new ArrayList<Players>();
		List<PlayerSquadDTO> psDTOList = new ArrayList<PlayerSquadDTO>();
		for(Team team : teamSquad.getSquad()){
			players.addAll(team.getPlayers());
			setPlayerInfo(psDTOList, team);
		}
		
		insertPlayerInfo(psDTOList);
		
		int[] insertedPlayers = getJdbcTemplate().batchUpdate(sql,
	            new BatchPreparedStatementSetter() {
	                @Override
	                public void setValues(PreparedStatement ps, int i)
	                        throws SQLException {
	                	Players player = players.get(i);
	                    ps.setString(1, uniqueId);
	                    ps.setString(2, player.getPid());
	                }

	                @Override
	                public int getBatchSize() {
	                    return players.size();
	                }
	            });
		return insertedPlayers.length;
	}

	/**
	 * @param psDTOList
	 * @param team
	 */
	private void setPlayerInfo(List<PlayerSquadDTO> psDTOList, Team team) {
		for(Players player : team.getPlayers()) {
			PlayerSquadDTO psDTO = new PlayerSquadDTO();
			psDTO.setId(player.getPid());
			psDTO.setPlayer(player.getName());
			psDTO.setCredit(9);
			psDTO.setNation(team.getName());
			psDTO.setType("Cric");
			psDTOList.add(psDTO);
		}
	}
	
	public int insertPlayerInfo(final List<PlayerSquadDTO> psDTOList) {
		String sql = "insert into player (id, player, nation, credit, type) values (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE player=?, nation=?, credit=?, type=?";
		int[] insertedPlayers = getJdbcTemplate().batchUpdate(sql,
	            new BatchPreparedStatementSetter() {
	                @Override
	                public void setValues(PreparedStatement ps, int i)
	                        throws SQLException {
	                	PlayerSquadDTO player = psDTOList.get(i);
	                    ps.setString(1, player.getId());
	                    ps.setString(2, player.getPlayer());
	                    ps.setString(3, player.getNation());
	                    ps.setInt(4, player.getCredit());
	                    ps.setString(5, player.getType());
	                    
	                    ps.setString(6, player.getPlayer());
	                    ps.setString(7, player.getNation());
	                    ps.setInt(8, player.getCredit());
	                    ps.setString(9, player.getType());
	                }

	                @Override
	                public int getBatchSize() {
	                    return psDTOList.size();
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
	public int insertLeagues(List<MatchesLeagues> matchesLeagesList) {
		String sql = "insert into match_league (match_id, league_id) values (?, ?)";
		int[] insertedLeagues = getJdbcTemplate().batchUpdate(sql,
	            new BatchPreparedStatementSetter() {
	                @Override
	                public void setValues(PreparedStatement ps, int i)
	                        throws SQLException {
	                	MatchesLeagues matchesLeague = matchesLeagesList.get(i);
	                    ps.setString(1, matchesLeague.getMatchId());
	                    ps.setString(2, matchesLeague.getLeagueId());
	                }

	                @Override
	                public int getBatchSize() {
	                    return matchesLeagesList.size();
	                }
	            });
		return insertedLeagues.length;
	}
}
