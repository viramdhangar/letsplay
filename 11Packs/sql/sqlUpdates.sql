drop table matches;

CREATE TABLE matches (
    unique_id VARCHAR(20) NOT NULL,
    date DATE NOT NULL,
    datetime DATETIME NOT NULL,
    team1 VARCHAR(50) NOT NULL,
    team2 VARCHAR(50) NOT NULL,
    type VARCHAR(20) NOT NULL,
    squad VARCHAR(20),
    toss_winner_team VARCHAR(50),
    winner_team VARCHAR(50),
    matchStarted VARCHAR(20),
    matchLive VARCHAR(20)
);

ALTER TABLE matches
ADD PRIMARY KEY(unique_id);
