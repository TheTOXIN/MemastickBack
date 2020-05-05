ALTER TABLE memeticks ADD COLUMN creed boolean;
UPDATE memeticks SET creed = false WHERE true;
ALTER TABLE memeticks ALTER COLUMN creed SET NOT NULL;
