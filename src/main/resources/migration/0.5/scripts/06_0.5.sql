-- DOLBOEBIKS
INSERT INTO memotype_set (id, description, name, size)
VALUES (uuid_generate_v4(),
        'Самые отбитые сверх разумы в видимой вселенной. Про них ходят легенды и сочиняют пророчества. Чтобы стать одним из них, тебе придется пройти через множество испытаний. Именно благодаря им, ты сейчас тут находишься. Встречайте, неповторимые - Долбоебики!',
        'ДОЛБОЕБИКИ',
        15);

--CLASSIC
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        '*C1.png',
        1,
        'CLASSIC',
        'сколько лаб не писано, говнокода написано',
        'ЛАБОДЕЛ',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ'));
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        '*C2.png',
        2,
        'CLASSIC',
        'обладатель премии самые крепкие нервы 2077',
        'ХВАТЬ',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ'));
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        '*C3.png',
        3,
        'CLASSIC',
        'так сказать доброе блувээээ.. утро',
        'БЛЕВУН',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ'));
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        '*C4.png',
        4,
        'CLASSIC',
        'незнаю чо вам аниме не нравится, я вот смотрю и мне норм',
        'ОНИМЬЕ',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ'));
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        '*C5.png',
        5,
        'CLASSIC',
        'алооо етаа палицая, у меня украли мой хебокс',
        'ПАЛИЦАЙ',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ'));

-- RARE
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        '*R1.png',
        1,
        'RARE',
        'ну вы поняли... это типо такая шутОЧКА',
        'ЮР ОЧКА',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ'));
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        '*R2.png',
        2,
        'RARE',
        'скидываемся на губозакаточную машину',
        'ГУБОКАТ',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ'));
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        '*R3.png',
        3,
        'RARE',
        'парашочек в роточек, и нету почек...',
        'ПАШАШОЧЕК',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ'));
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        '*R4.png',
        4,
        'RARE',
        'серега, серега, опухший немного',
        'ШНОБЕЛЬ',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ'));
-- EPIC
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        '*E1.png',
        1,
        'EPIC',
        'жава жава всегда была, есть, и будет',
        'ЖАВА',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ'));
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        '*E2.png',
        2,
        'EPIC',
        'ля какая бульмешичка, не епите её позязя',
        'ПУХЛЯШ',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ'));
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        '*E3.png',
        3,
        'EPIC',
        'а мы тут так сказать любим арееешки, да?',
        'АРЕХ',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ'));

-- LEGENDARY
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        '*L1.png',
        1,
        'LEGENDARY',
        'денастия блевавусовых на этом не заканчивается',
        'БЛЕВУРИЙ',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ'));
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        '*L2.png',
        2,
        'LEGENDARY',
        'спасите не пасите, вкусшняшкой угостите',
        'ПАСИИИТЕ',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ'));

-- INCREDIBLE
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        '*I1.png',
        1,
        'INCREDIBLE',
        'ававававававававававаавававвававававававававававаавава ТЫ ШО ПЁС?',
        'ДЕД ХУЮРИЙ',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ'));