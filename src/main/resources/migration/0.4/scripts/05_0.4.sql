ALTER TABLE invite_codes ADD COLUMN IF NOT EXISTS date_send timestamp;
UPDATE invite_codes SET date_send = '1970-01-01 00:00:00.000000';
ALTER TABLE invite_codes ALTER COLUMN date_send SET NOT NULL;
ALTER TABLE invite_codes RENAME COLUMN date TO date_create;