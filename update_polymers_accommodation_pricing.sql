-- Update Polymers Accommodation and Pricing Configs
-- Pricing rules: 1 night = 175, guests don't add extra cost
-- Generate combinations for nights 1-10 and guests 0-10

-- Step 1: Clear existing accommodation options (optional, uncomment if needed)
-- DELETE FROM polymers_accommodation WHERE nights BETWEEN 1 AND 10 AND guests BETWEEN 0 AND 10;

-- Step 2: Insert accommodation options for all combinations
-- Price = nights * 175 (guests don't affect price)
INSERT INTO polymers_accommodation (nights, guests, price)
SELECT n.nights, g.guests, n.nights * 175.00
FROM (
    SELECT 1 as nights UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
    UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10
) n
CROSS JOIN (
    SELECT 0 as guests UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4
    UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10
) g
WHERE NOT EXISTS (
    SELECT 1 FROM polymers_accommodation a
    WHERE a.nights = n.nights AND a.guests = g.guests
);

-- Step 3: Verify accommodation options
SELECT * FROM polymers_accommodation
WHERE nights BETWEEN 1 AND 10 AND guests BETWEEN 0 AND 10
ORDER BY nights, guests;

-- Step 4: Insert pricing configs for SPEAKER presentation type with all accommodation combinations
INSERT INTO polymers_pricing_config (presentation_type_id, accommodation_option_id, processing_fee_percent, total_price)
SELECT
    pt.id as presentation_type_id,
    acc.id as accommodation_option_id,
    5.0 as processing_fee_percent,
    (pt.price + acc.price + ((pt.price + acc.price) * 0.05)) as total_price
FROM polymers_presentation_type pt
CROSS JOIN polymers_accommodation acc
WHERE pt.type = 'SPEAKER'
AND acc.nights BETWEEN 1 AND 10
AND acc.guests BETWEEN 0 AND 10
AND NOT EXISTS (
    SELECT 1 FROM polymers_pricing_config pc
    WHERE pc.presentation_type_id = pt.id
    AND pc.accommodation_option_id = acc.id
);

-- Step 5: Verify pricing configs
SELECT pc.id, pt.type as presentation_type, acc.nights, acc.guests, acc.price as accommodation_price,
       pc.processing_fee_percent, pc.total_price
FROM polymers_pricing_config pc
JOIN polymers_presentation_type pt ON pc.presentation_type_id = pt.id
LEFT JOIN polymers_accommodation acc ON pc.accommodation_option_id = acc.id
WHERE pt.type = 'SPEAKER'
AND (acc.nights BETWEEN 1 AND 10 OR acc.nights IS NULL)
ORDER BY acc.nights, acc.guests;

-- Step 6: Test examples
-- 1 night + 0 guests: should be 175 + SPEAKER price + 5% processing fee
-- 1 night + 2 guests: should be same as 1 night + 0 guests (175)
-- 2 nights + 3 guests: should be 350 + SPEAKER price + 5% processing fee

-- Test the API with various combinations:
-- POST http://localhost:8906/api/registration/get-pricing-config
-- Header: Origin: https://polyscienceconference.com
-- Body examples:
-- {"registrationType": "REGISTRATION_AND_ACCOMMODATION", "presentationType": "SPEAKER", "numberOfNights": 1, "numberOfGuests": 0}
-- {"registrationType": "REGISTRATION_AND_ACCOMMODATION", "presentationType": "SPEAKER", "numberOfNights": 1, "numberOfGuests": 2}
-- {"registrationType": "REGISTRATION_AND_ACCOMMODATION", "presentationType": "SPEAKER", "numberOfNights": 2, "numberOfGuests": 3}
