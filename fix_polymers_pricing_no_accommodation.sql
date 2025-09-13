-- Fix Polymers Pricing Config for REGISTRATION_ONLY (No Accommodation)

-- Step 1: Check existing presentation types for polymers
SELECT * FROM polymers_presentation_type;

-- Step 2: Check existing pricing configs for polymers
SELECT * FROM polymers_pricing_config;

-- Step 3: Insert pricing configs for all presentation types with no accommodation (if not exists)
INSERT INTO polymers_pricing_config (presentation_type_id, accommodation_option_id, processing_fee_percent, total_price)
SELECT pt.id, NULL, 5.0, (pt.price + (pt.price * 0.05))
FROM polymers_presentation_type pt
WHERE NOT EXISTS (
    SELECT 1 FROM polymers_pricing_config pc
    WHERE pc.presentation_type_id = pt.id
    AND pc.accommodation_option_id IS NULL
);

-- Step 4: Verify the insertions
SELECT pc.*, pt.type as presentation_type
FROM polymers_pricing_config pc
JOIN polymers_presentation_type pt ON pc.presentation_type_id = pt.id
WHERE pc.accommodation_option_id IS NULL;

-- Step 5: Test the API again after running this script
-- Use the request:
-- POST http://localhost:8906/api/registration/get-pricing-config
-- Body: {"registrationType": "REGISTRATION_ONLY", "presentationType": "SPEAKER", "numberOfNights": 0, "numberOfGuests": 0}
-- Header: Origin: https://polyscienceconference.com
