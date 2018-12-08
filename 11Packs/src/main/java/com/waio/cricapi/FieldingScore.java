package com.waio.cricapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class FieldingScore {

	private int pid;
	private String name;
	private int runout;
	private int stumped;
	private int bowled;
	private int lbw;
	@JsonProperty("catch")
	private int catches;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the runout
	 */
	public int getRunout() {
		return runout;
	}
	/**
	 * @param runout the runout to set
	 */
	public void setRunout(int runout) {
		this.runout = runout;
	}
	/**
	 * @return the stumped
	 */
	public int getStumped() {
		return stumped;
	}
	/**
	 * @param stumped the stumped to set
	 */
	public void setStumped(int stumped) {
		this.stumped = stumped;
	}
	/**
	 * @return the bowled
	 */
	public int getBowled() {
		return bowled;
	}
	/**
	 * @param bowled the bowled to set
	 */
	public void setBowled(int bowled) {
		this.bowled = bowled;
	}
	/**
	 * @return the lbw
	 */
	public int getLbw() {
		return lbw;
	}
	/**
	 * @param lbw the lbw to set
	 */
	public void setLbw(int lbw) {
		this.lbw = lbw;
	}
	/**
	 * @return the catches
	 */
	public int getCatches() {
		return catches;
	}
	/**
	 * @param catches the catches to set
	 */
	public void setCatches(int catches) {
		this.catches = catches;
	}
}
