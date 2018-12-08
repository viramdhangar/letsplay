package com.waio.cricapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BattingScore {

	private int pid;
	@JsonProperty("6s")
	private int sixes;
	@JsonProperty("4s")
	private int fours;
	@JsonProperty("R")
	private int run;
	@JsonProperty("B")
	private int ball;
	@JsonProperty("SR")
	private int sRate;
	private String dismissal;
/*	@JsonProperty("dismissal-info")
	private String dismissalInfo;*/
	private String batsman;
/*	@JsonProperty("dismissal-by")
	private Players dismissalBy;*/
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
	 * @return the ball
	 */
	public int getBall() {
		return ball;
	}
	/**
	 * @param ball the ball to set
	 */
	public void setBall(int ball) {
		this.ball = ball;
	}
	/**
	 * @return the sRate
	 */
	public int getsRate() {
		return sRate;
	}
	/**
	 * @param sRate the sRate to set
	 */
	public void setsRate(int sRate) {
		this.sRate = sRate;
	}
	/**
	 * @return the dismissal
	 */
	public String getDismissal() {
		return dismissal;
	}
	/**
	 * @param dismissal the dismissal to set
	 */
	public void setDismissal(String dismissal) {
		this.dismissal = dismissal;
	}
	/**
	 * @return the batsman
	 */
	public String getBatsman() {
		return batsman;
	}
	/**
	 * @param batsman the batsman to set
	 */
	public void setBatsman(String batsman) {
		this.batsman = batsman;
	}
}
