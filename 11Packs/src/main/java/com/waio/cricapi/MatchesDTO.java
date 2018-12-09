package com.waio.cricapi;

import java.sql.Time;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchesDTO {

	private String unique_id;
	private Date date;
	private Time time;
	@JsonProperty("dateTimeGMT")
	private Date datetime;
	@JsonProperty("team-1")
	private String team1;
	@JsonProperty("team-2")
	private String team2;
	private String type;
	private String squad;
	private String toss_winner_team;
	private String winner_team;
	private String matchStarted;
	
	public String getUnique_id() {
		return unique_id;
	}
	public void setUnique_id(String unique_id) {
		this.unique_id = unique_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public String getTeam1() {
		return team1;
	}
	public void setTeam1(String team1) {
		this.team1 = team1;
	}
	public String getTeam2() {
		return team2;
	}
	public void setTeam2(String team2) {
		this.team2 = team2;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSquad() {
		return squad;
	}
	public void setSquad(String squad) {
		this.squad = squad;
	}
	public String getToss_winner_team() {
		return toss_winner_team;
	}
	public void setToss_winner_team(String toss_winner_team) {
		this.toss_winner_team = toss_winner_team;
	}
	public String getWinner_team() {
		return winner_team;
	}
	public void setWinner_team(String winner_team) {
		this.winner_team = winner_team;
	}
	public String getMatchStarted() {
		return matchStarted;
	}
	public void setMatchStarted(String matchStarted) {
		this.matchStarted = matchStarted;
	}
}
