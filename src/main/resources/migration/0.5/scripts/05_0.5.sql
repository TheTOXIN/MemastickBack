ALTER TABLE users ADD COLUMN IF NOT EXISTS ban boolean;
UPDATE users SET ban = false ;
ALTER TABLE users ALTER COLUMN ban SET NOT NULL;