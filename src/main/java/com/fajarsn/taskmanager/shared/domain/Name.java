package com.fajarsn.taskmanager.shared.domain;

import java.util.Objects;

/**
 * Value object representing a person's name.
 * Ensures name validation and proper formatting.
 */
public final class Name implements ValueObject {
    private final String firstName;
    private final String lastName;

    private Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static Name of(String firstName, String lastName) {
        validateName(firstName, "First name");
        validateName(lastName, "Last name");

        return new Name(capitalizeFirstLetter(firstName.trim()), capitalizeFirstLetter(lastName.trim()));
    }

    private static void validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }

        if (name.trim().length() < 2) {
            throw new IllegalArgumentException(fieldName + " must be at least 2 characters long");
        }

        if (name.trim().length() > 50) {
            throw new IllegalArgumentException(fieldName + " cannot exceed 50 characters");
        }
    }

    private static String capitalizeFirstLetter(String input) {
        if (input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Name name = (Name) obj;
        return Objects.equals(firstName, name.firstName) &&
                Objects.equals(lastName, name.lastName);
    }
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
