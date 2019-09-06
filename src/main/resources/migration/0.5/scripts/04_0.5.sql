ALTER TABLE memes ADD COLUMN likes int;
UPDATE memes SET likes = 0;
ALTER TABLE memes ALTER COLUMN likes SET NOT NULL;

UPDATE memes AS m SET likes = (
    SELECT count(*) FROM meme_likes as ml WHERE m.id= ml.meme_id AND is_like = TRUE
) WHERE TRUE;