ALTER TABLE token_wallets ADD COLUMN IF NOT EXISTS memetick_id uuid constraint fk2iwq6k17uujix2kv6xwwk4h5t references memeticks;
UPDATE token_wallets AS tw SET memetick_id = mi.memetick_id FROM memetick_inventories AS mi WHERE mi.token_wallet_id = tw.id;