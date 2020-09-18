UPDATE evolve_memes AS em SET step = null FROM memes m
WHERE em.meme_id = m.id AND m.type != 'EVLV' AND em.step IS NOT NULL;
