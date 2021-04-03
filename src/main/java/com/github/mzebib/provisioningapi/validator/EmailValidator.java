package com.github.mzebib.provisioningapi.validator;

/**
 * @author mzebib
 */
public class EmailValidator {

    private static EmailValidator instance;

    private static final String REGEX ="^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.(([0-9]{1,3})|([a-zA-Z]{2,3}))$";

    private EmailValidator() {
    }

    public static EmailValidator getInstance() {
        if (instance == null) {
            instance = new EmailValidator();
        }

        return instance;
    }

    public String validate(String value)
            throws NullPointerException, IllegalArgumentException {
        if (value == null) throw new NullPointerException("Input value is null");

        value = value.trim();

        if (value.matches(REGEX)) {
            return value;
        }

        throw new IllegalArgumentException("Invalid email: " + value);
    }

    public boolean isValid(String value) {
        try {
            validate(value);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
