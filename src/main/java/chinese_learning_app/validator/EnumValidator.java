package ecomerce.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
public class EnumValidator implements ConstraintValidator<EnumValid, String> {
    Set<String> acceptedValues = new HashSet<String>();

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String anEnum, ConstraintValidatorContext constraintValidatorContext) {
        if (anEnum == null) {
            return false;
        }
        return acceptedValues.contains(anEnum);
    }
}
