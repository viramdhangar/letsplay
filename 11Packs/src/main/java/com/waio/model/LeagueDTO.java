package com.waio.model;

public class LeagueDTO {

	private String id;
	private String league;
	private int size;
	private int entryFee;
	private String matchId;
	
	/**
	 * @return the matchId
	 */
	public String getMatchId() {
		return matchId;
	}
	/**
	 * @param matchId the matchId to set
	 */
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the league
	 */
	public String getLeague() {
		return league;
	}
	/**
	 * @param league the league to set
	 */
	public void setLeague(String league) {
		this.league = league;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * @return the entryFee
	 */
	public int getEntryFee() {
		return entryFee;
	}
	/**
	 * @param entryFee the entryFee to set
	 */
	public void setEntryFee(int entryFee) {
		this.entryFee = entryFee;
	}
}
