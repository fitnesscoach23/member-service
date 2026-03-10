CREATE TABLE body_metrics (
    id UUID PRIMARY KEY,
    member_id UUID NOT NULL UNIQUE,
    height_cm DOUBLE PRECISION NOT NULL,
    current_weight_kg DOUBLE PRECISION NOT NULL,
    gender VARCHAR(20) NOT NULL,
    age INTEGER NOT NULL,
    is_lean BOOLEAN NOT NULL,
    activity_factor DOUBLE PRECISION NOT NULL,
    protein_rda DOUBLE PRECISION NOT NULL,
    carb_factor DOUBLE PRECISION NOT NULL,
    target_goal VARCHAR(20) NOT NULL,
    target_calorie_factor DOUBLE PRECISION NOT NULL,
    ibw_kg DOUBLE PRECISION NOT NULL,
    bmi DOUBLE PRECISION NOT NULL,
    bmr DOUBLE PRECISION NOT NULL,
    tdee DOUBLE PRECISION NOT NULL,
    target_calories DOUBLE PRECISION NOT NULL,
    protein_grams DOUBLE PRECISION NOT NULL,
    carbs_grams DOUBLE PRECISION NOT NULL,
    fats_grams DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    CONSTRAINT fk_body_metrics_member
        FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE,
    CONSTRAINT chk_body_metrics_goal
        CHECK (target_goal IN ('FAT_LOSS', 'MAINTENANCE', 'GAINING'))
);
