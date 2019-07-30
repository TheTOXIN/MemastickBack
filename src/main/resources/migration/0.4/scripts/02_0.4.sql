ALTER TABLE memes ADD COLUMN if not exists evolution BIGINT;
UPDATE memes SET evolution = population;
ALTER TABLE  memes ALTER COLUMN evolution SET NOT NULL;
ALTER TABLE memes RENAME COLUMN indexer TO individuation;