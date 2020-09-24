ALTER TABLE donate_messages ADD COLUMN amount INTEGER;
ALTER TABLE donate_ratings ADD COLUMN amount INTEGER;

UPDATE donate_messages SET amount = 1000 WHERE TRUE;
UPDATE donate_ratings SET amount = (rarity + 1) * 100 WHERE TRUE;

ALTER TABLE donate_messages ALTER COLUMN amount SET NOT NULL;
ALTER TABLE donate_ratings ALTER COLUMN amount SET NOT NULL;
