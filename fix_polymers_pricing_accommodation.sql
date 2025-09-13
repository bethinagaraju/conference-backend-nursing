-- Fix Polymers Pricing Config for REGISTRATION_AND_ACCOMMODATION with 1 night and 0 guests

-- Step 1: Check existing presentation types for polymers
SELECT * FROM polymers_presentation_type;

-- Step 2: Check existing accommodation options for polymers
SELECT * FROM polymers_accommodation;

-- Step 3: Insert accommodation option for 1 night, 0 guests if not exists
INSERT INTO polymers_accommodation (nights, guests, price)
SELECT 1, 0, 100.00  -- Adjust price as needed
WHERE NOT EXISTS (
    SELECT 1 FROM polymers_accommodation
    WHERE nights = 1 AND guests = 0
);

-- Step 4: Get the accommodation ID we just inserted or that already exists
SELECT id, nights, guests, price FROM polymers_accommodation WHERE nights = 1 AND guests = 0;

-- Step 5: Insert pricing configs for SPEAKER presentation type with the accommodation
-- Replace SPEAKER_ID with the actual ID from polymers_presentation_type table
-- Replace ACCOMMODATION_ID with the ID from step 4
INSERT INTO polymers_pricing_config (presentation_type_id, accommodation_option_id, processing_fee_percent, total_price)
SELECT
    pt.id as presentation_type_id,
    acc.id as accommodation_option_id,
    5.0 as processing_fee_percent,
    (pt.price + acc.price + ((pt.price + acc.price) * 0.05)) as total_price
FROM polymers_presentation_type pt
CROSS JOIN polymers_accommodation acc
WHERE pt.type = 'SPEAKER'
AND acc.nights = 1 AND acc.guests = 0
AND NOT EXISTS (
    SELECT 1 FROM polymers_pricing_config pc
    WHERE pc.presentation_type_id = pt.id
    AND pc.accommodation_option_id = acc.id
);

-- Step 6: Verify the insertions
SELECT pc.*, pt.type as presentation_type, acc.nights, acc.guests
FROM polymers_pricing_config pc
JOIN polymers_presentation_type pt ON pc.presentation_type_id = pt.id
LEFT JOIN polymers_accommodation acc ON pc.accommodation_option_id = acc.id
WHERE pt.type = 'SPEAKER';

-- Step 7: Test the API again after running this script
-- Use the request:
-- POST http://localhost:8906/api/registration/get-pricing-config
-- Body: {"registrationType": "REGISTRATION_AND_ACCOMMODATION", "presentationType": "SPEAKER", "numberOfNights": 1, "numberOfGuests": 0}
-- Header: Origin: https://polyscienceconference.com
