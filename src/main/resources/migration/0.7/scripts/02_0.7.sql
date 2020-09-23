DROP TABLE IF EXISTS donate_ratings;
DROP TABLE IF EXISTS donate_messages;

ALTER TABLE IF EXISTS donater_ratings RENAME TO donate_ratings;
ALTER TABLE IF EXISTS donater_messages RENAME TO donate_messages;
