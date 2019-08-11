ALTER TABLE memetick_inventories ADD COLUMN IF NOT EXISTS pickaxe_time timestamp;
UPDATE memetick_inventories SET pickaxe_time = '1970-01-01 00:00:00.000000';
ALTER TABLE memetick_inventories ALTER COLUMN pickaxe_time SET NOT NULL;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER TABLE memetick_inventories ADD COLUMN IF NOT EXISTS pickaxe_token uuid;
UPDATE memetick_inventories SET pickaxe_token = uuid_generate_v4();
ALTER TABLE memetick_inventories ALTER COLUMN pickaxe_token SET NOT NULL;