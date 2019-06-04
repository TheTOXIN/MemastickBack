--==[0.1]==--

-- BEFORE
ALTER TABLE memeticks ADD COLUMN dna bigint;
UPDATE memeticks SET dna = 0;
ALTER TABLE memeticks ALTER COLUMN dna SET NOT NULL;

ALTER TABLE memeticks ADD COLUMN meme_created timestamp;
UPDATE memeticks SET meme_created = '1970-01-01 00:00:00.000000';
ALTER TABLE memeticks ALTER COLUMN meme_created SET NOT NULL;

ALTER TABLE memeticks ADD COLUMN nick_changed timestamp;
UPDATE memeticks SET nick_changed = '1970-01-01 00:00:00.000000';
ALTER TABLE memeticks ALTER COLUMN nick_changed SET NOT NULL;

--AFTER
INSERT INTO memetick_avatars (id, memetick_id) SELECT uuid_generate_v4(), id FROM memeticks;

--==[0.1.1]==--
drop table if exists oauth_access_token;
drop table if exists oauth_refresh_token;

create table if not exists oauth_access_token (
  token_id VARCHAR(256),
  token bytea,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication bytea,
  refresh_token VARCHAR(256)
);

create table if not exists oauth_refresh_token (
  token_id VARCHAR(256),
  token bytea,
  authentication bytea
);


--==[0.1.2]==--

--NOTHING

--==[0.1.3]==--

--BEFORE
--RUN MIGRATE URLS

--==[0.2]==--

--BEFORE
ALTER TABLE memes ADD COLUMN type varchar(32);
UPDATE memes SET type = 'INDIVID';
ALTER TABLE memes ALTER COLUMN type SET NOT NULL;

ALTER TABLE memes ADD COLUMN chromosomes INTEGER;
UPDATE memes SET chromosomes = 0;
ALTER TABLE memes ALTER COLUMN chromosomes SET NOT NULL;

ALTER TABLE memeticks DROP COLUMN meme_created;
--AFTER
--RUN MIGRATE CHROMOSOME
INSERT INTO evolve_memes SELECT uuid_generate_v4(), 100, 0, null, id FROM memes;

--==[0.2.1]==--
UPDATE meme_likes SET like_time = '1970-01-01 00:00:00.000000' WHERE like_time IS NULL;

--==[0.2.2]==--
ALTER TABLE evolve_memes ADD COLUMN chance_increase BOOLEAN;

UPDATE evolve_memes em SET chance_increase = false WHERE em.chance_increase IS NULL;
UPDATE evolve_memes em SET chance_survive = 50 WHERE em.chance_survive IS NULL;

ALTER TABLE evolve_memes ALTER COLUMN chance_increase SET NOT NULL;
ALTER TABLE evolve_memes ALTER COLUMN chance_survive SET NOT NULL;

ALTER TABLE evolve_memes ALTER COLUMN chance_increase SET DEFAULT false;
ALTER TABLE evolve_memes ALTER COLUMN chance_survive SET DEFAULT 0;

--==[0.3]=--

-- BEFORE
ALTER TABLE evolve_memes RENAME COLUMN chance_increase TO immunity;
ALTER TABLE evolve_memes RENAME COLUMN chance_survive TO chance;

ALTER TABLE token_wallets RENAME creating TO tube;
ALTER TABLE token_wallets RENAME fitness TO scope;
ALTER TABLE token_wallets RENAME mutation TO mutagen;
ALTER TABLE token_wallets RENAME selection TO antibiotic;

ALTER TABLE memetick_inventories ADD COLUMN cell_creating timestamp;
UPDATE memetick_inventories SET cell_creating = '1970-01-01 00:00:00.000000';
ALTER TABLE memetick_inventories ALTER COLUMN cell_creating SET NOT NULL;

ALTER TABLE evolve_memes ADD COLUMN adaptation integer;
UPDATE evolve_memes SET adaptation = 0;
ALTER TABLE  evolve_memes ALTER COLUMN adaptation SET NOT NULL;

ALTER TABLE memes ADD COLUMN indexer bigint;
UPDATE memes SET indexer = 0;
ALTER TABLE  memes ALTER COLUMN indexer SET NOT NULL;

ALTER TABLE memes ADD COLUMN population bigint;
UPDATE memes AS m SET population = em.population FROM evolve_memes AS em WHERE m.id = em.meme_id;
ALTER TABLE  memes ALTER COLUMN population SET NOT NULL;
ALTER TABLE evolve_memes DROP COLUMN population;

-- AFTER
ALTER TABLE memeticks DROP nick_changed;
INSERT INTO setting_users (id, nick_changed, push_work, user_id)
SELECT uuid_generate_v4(), '2018-01-01 00:00:00.000000', null, id
FROM users;
--

--==[0.3.1]=--
--BEFORE
ALTER TABLE memetick_inventories ADD COLUMN cell_notify boolean;
UPDATE memetick_inventories SET cell_notify = false;
ALTER TABLE memetick_inventories ALTER COLUMN cell_notify SET NOT NULL;

ALTER TABLE memetick_inventories DROP COLUMN cell_notify;