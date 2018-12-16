drop table profile_match_team;

CREATE TABLE team (
    id VARCHAR(20) NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    match_id VARCHAR(20) NOT NULL,
    created_id VARCHAR(20) NOT NULL,
    created datetime DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE match_squad
ADD CONSTRAINT UC_MATCH_SQUAD UNIQUE (match_id,player_id);


ALTER TABLE match_league
ADD CONSTRAINT UC_MATCH_League UNIQUE (match_id,league_id);



