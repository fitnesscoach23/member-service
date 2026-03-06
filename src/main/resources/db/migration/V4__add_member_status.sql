ALTER TABLE members
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE';

ALTER TABLE members
    ADD CONSTRAINT chk_members_status
    CHECK (status IN ('ACTIVE', 'INACTIVE'));
