ALTER TABLE memetick_inventories ADD COLUMN IF NOT EXISTS cell_notify boolean;
UPDATE memetick_inventories SET cell_notify = false;
ALTER TABLE memetick_inventories ALTER COLUMN cell_notify SET NOT NULL;