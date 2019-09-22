-- GLAD VALAKAS
INSERT INTO memotype_set (id, description, name, size)
VALUES (uuid_generate_v4(),
        'У этой личности много имен... но смысл один. Глад Валакас (Пенис Детров, Жмышенко Валерий Альбертович) — стример онлайн-игр, ставший известным благодаря образу 54-летнего деда. Записывает видео, напрягая голосовые связки и говоря с акцентом.',
        'Глад Валакас',
        5);

INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        'https://www.memastick.ru/assets/images/memotypes/gladvalakas/valera_gladiator.png',
        1,
        'CLASSIC',
        'идеал мужчины 21 века',
        'Гладиатор',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'Глад Валакас'));
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        'https://www.memastick.ru/assets/images/memotypes/gladvalakas/glad_valakas.png',
        2,
        'RARE',
        'хаааяй зяблс энд беби бонс',
        'Валакас',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'Глад Валакас'));
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        'https://www.memastick.ru/assets/images/memotypes/gladvalakas/zhmyshenko.png',
        3,
        'EPIC',
        'Жмышенко Валерий Альбертович - заслуженный герой Майдана',
        'Жмышенко',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'Глад Валакас'));
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        'https://www.memastick.ru/assets/images/memotypes/gladvalakas/kroll.png',
        4,
        'LEGENDARY',
        'кааавооо, кто я крол???',
        'КРОЛЛ',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'Глад Валакас'));
INSERT INTO memotype (id, image, number, rarity, text, title, set_id)
VALUES (uuid_generate_v4(),
        'https://www.memastick.ru/assets/images/memotypes/gladvalakas/penis.png',
        5,
        'INCREDIBLE',
        'Я Пенис Детров, я апущеный лох, у меня маска',
        'ПеНиС',
        (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'Глад Валакас'));