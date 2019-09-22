UPDATE memotype
SET number = number + 5
WHERE rarity = 'RARE'
  AND set_id = (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ');

UPDATE memotype
SET number = number + 9
WHERE rarity = 'EPIC'
  AND set_id = (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ');

UPDATE memotype
SET number = number + 12
WHERE rarity = 'LEGENDARY'
  AND set_id = (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ');

UPDATE memotype
SET number = number + 14
WHERE rarity = 'INCREDIBLE'
  AND set_id = (SELECT ms.id FROM memotype_set ms WHERE ms.name = 'ДОЛБОЕБИКИ');