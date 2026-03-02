ALTER TABLE members
    ALTER COLUMN sleep_hours TYPE VARCHAR(30)
    USING sleep_hours::VARCHAR;
