ALTER TABLE memeticks ADD COLUMN IF NOT EXISTS cookies INT;
UPDATE memeticks SET cookies = 0;
ALTER TABLE memeticks ALTER COLUMN cookies SET NOT NULL;