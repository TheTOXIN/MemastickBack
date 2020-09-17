ALTER TABLE memes ADD COLUMN IF NOT EXISTS epi jsonb;

UPDATE memes m
SET epi = ('{' ||
           '"evolution": "' || m.evolution || '", ' ||
           '"population": "' || m.population || '", ' ||
           '"individuation": "'|| m.individuation ||'"' ||
           '}') :: jsonb
WHERE TRUE;

ALTER TABLE memes ALTER COLUMN epi SET NOT NULL;

ALTER table memes DROP COLUMN evolution;
ALTER table memes DROP COLUMN population;
ALTER table memes DROP COLUMN individuation;
