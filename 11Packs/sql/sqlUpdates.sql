

/*drop table match_league*/
 CREATE TABLE match_leagues (
    id int(10) NOT NULL  PRIMARY KEY AUTO_INCREMENT,
    league_id VARCHAR(20) NOT NULL,
    match_id VARCHAR(20) NOT NULL,
    size int(10) NOT NULL,
    created datetime DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE match_leagues
ADD CONSTRAINT UC_MATCH_Leagues UNIQUE (match_id,league_id);





/*drop table team_league*/
CREATE TABLE league_teams (
    league_id VARCHAR(20) NOT NULL,
    team_id VARCHAR(20) NOT NULL,
    match_id VARCHAR(20) NOT NULL,
    created_id VARCHAR(20) NOT NULL,
    created datetime DEFAULT CURRENT_TIMESTAMP
);

