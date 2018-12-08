package com.waio.cricapi;

import java.util.List;

public class Team {

	private String name;
	private List<Players> players;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Players> getPlayers() {
		return players;
	}
	public void setPlayers(List<Players> players) {
		this.players = players;
	}

}
