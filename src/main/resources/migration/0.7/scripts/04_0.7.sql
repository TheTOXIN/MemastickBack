ALTER TABLE memetick_inventories ADD COLUMN pickaxe_creating timestamp;

UPDATE memetick_inventories AS mi set pickaxe_creating = p.creating FROM pickaxes p WHERE mi.memetick_id = p.memetick_id;
UPDATE memetick_inventories SET pickaxe_creating = '1970-01-01 00:00:00.000000' WHERE pickaxe_creating ISNULL;

ALTER TABLE memetick_inventories ALTER COLUMN pickaxe_creating SET NOT NULL;
