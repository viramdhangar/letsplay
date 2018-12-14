drop table player;

CREATE TABLE player (
    pid VARCHAR(20) NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
	imageURL VARCHAR(200),
    country VARCHAR(50) NOT NULL,
    playingRole VARCHAR(60) DEFAULT 'BAT',
    credit VARCHAR(10) DEFAULT '8',
    major_teams VARCHAR(500),
    current_age VARCHAR(50),
    born VARCHAR(100),
    battingStyle VARCHAR(50),
    bowlingStyle VARCHAR(100)
);
