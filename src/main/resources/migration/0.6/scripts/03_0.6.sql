ALTER TABLE memetick_inventories ADD COLUMN cell_combo integer;
UPDATE memetick_inventories SET cell_combo = 1 WHERE true;
ALTER TABLE memetick_inventories ALTER COLUMN cell_combo SET NOT NULL;
