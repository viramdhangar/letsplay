drop table matches;

CREATE TABLE matches (
    unique_id VARCHAR(20) NOT NULL,
    date DATE NOT NULL,
    datetime DATETIME NOT NULL,
    team1 VARCHAR(50) NOT NULL,
    team2 VARCHAR(50) NOT NULL,
    type VARCHAR(20) NOT NULL,
    squad BOOLEAN,
    toss_winner_team VARCHAR(50),
    winner_team VARCHAR(50),
    matchStarted BOOLEAN,
    matchLive BOOLEAN
);


insert into matches (unique_id,
    date,
    datetime,
    team1,
    team2,
    type,
    squad,
    toss_winner_team,
    winner_team,
    matchStarted,
    matchLive )
values 
	('500',
	'2018-12-08',
	'2018-12-08 01:30:00',
	'Sydney Sixers Women',
	'Sydney Thunder Women',
	'Twenty20',
	'1',
	'',
	'',
	'0',
	'0'
);