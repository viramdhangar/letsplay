package com.waio.cricapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BowlingScore {

	private int pid;
	@JsonProperty("6s")
	private int sixes;
	@JsonProperty("4s")
	private int fours;
	@JsonProperty("0s")
	private int zeros;
	@JsonProperty("W")
	private int wicket;
	@JsonProperty("R")
	private int run;
	@JsonProperty("M")
	private int maiden;
	@JsonProperty("O")
	private double over;
	@JsonProperty("Econ")
	private double economy;
	private String bowler;
	/**
	 * @return the pid
	 */
	public int getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(int pid) {
		this.pid = pid;
	}
	/**
	 * @return the sixes
	 */
	public int getSixes() {
		return sixes;
	}
	/**
	 * @param sixes the sixes to set
	 */
	public void setSixes(int sixes) {
		this.sixes = sixes;
	}
	/**
	 * @return the fours
	 */
	public int getFours() {
		return fours;
	}
	/**
	 * @param fours the fours to set
	 */
	public void setFours(int fours) {
		this.fours = fours;
	}
	/**
	 * @return the zeros
	 */
	public int getZeros() {
		return zeros;
	}
	/**
	 * @param zeros the zeros to set
	 */
	public void setZeros(int zeros) {
		this.zeros = zeros;
	}
	/**
	 * @return the wicket
	 */
	public int getWicket() {
		return wicket;
	}
	/**
	 * @param wicket the wicket to set
	 */
	public void setWicket(int wicket) {
		this.wicket = wicket;
	}
	/**
	 * @return the run
	 */
	public int getRun() {
		return run;
	}
	/**
	 * @param run the run to set
	 */
	public void setRun(int run) {
		this.run = run;
	}
	/**
	 * @return the maiden
	 */
	public int getMaiden() {
		return maiden;
	}
	/**
	 * @param maiden the maiden to set
	 */
	public void setMaiden(int maiden) {
		this.maiden = maiden;
	}

	/**
	 * @return the over
	 */
	public double getOver() {
		return over;
	}
	/**
	 * @param over the over to set
	 */
	public void setOver(double over) {
		this.over = over;
	}
	/**
	 * @return the economy
	 */
	public double getEconomy() {
		return economy;
	}
	/**
	 * @param economy the economy to set
	 */
	public void setEconomy(double economy) {
		this.economy = economy;
	}
	/**
	 * @return the over
	/**
	 * @return the bowler
	 */
	public String getBowler() {
		return bowler;
	}
	/**
	 * @param bowler the bowler to set
	 */
	public void setBowler(String bowler) {
		this.bowler = bowler;
	}
}
