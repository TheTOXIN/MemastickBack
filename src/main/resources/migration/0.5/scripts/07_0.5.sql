DELETE
FROM meme_coins
WHERE TRUE;

INSERT INTO memotype_memetick (id, memotype_id, memetick_id)
SELECT uuid_generate_v4(),
       (SELECT id FROM memotype WHERE title = 'ЛАБОДЕЛ'),
       m.id
FROM memeticks m;