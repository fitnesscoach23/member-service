package com.coach.member.dto;

import com.coach.member.entity.TargetGoal;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BodyMetricsRequestValidationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidator() {
        validatorFactory.close();
    }

    @Test
    void allowsAnyCarbFactorValue() {
        assertThat(validateCarbFactor(4.2)).isEmpty();
        assertThat(validateCarbFactor(-1.0)).isEmpty();
    }

    private Set<ConstraintViolation<BodyMetricsRequest>> validateCarbFactor(Double carbFactor) {
        BodyMetricsRequest request = new BodyMetricsRequest(
                170.0,
                70.0,
                "Male",
                30,
                false,
                1.2,
                1.0,
                carbFactor,
                TargetGoal.FAT_LOSS,
                11.0,
                65.0,
                24.2,
                1560.0,
                1872.0,
                1575.0,
                65.0,
                273.0,
                0.0
        );

        return validator.validateProperty(request, "carbFactor");
    }
}
