ALTER TABLE token_wallets ALTER COLUMN memetick_id SET NOT NULL;
CREATE UNIQUE INDEX token_wallets_memetick_id_uindex ON token_wallets (memetick_id);