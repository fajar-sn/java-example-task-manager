package com.fajarsn.taskmanager.features.user.domain;

import com.fajarsn.taskmanager.features.user.domain.model.UserRole;
import com.fajarsn.taskmanager.shared.domain.DomainException;
import com.fajarsn.taskmanager.shared.domain.Email;
import com.fajarsn.taskmanager.shared.domain.Name;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Domain service for user-related business logic.
 * Handles complex business rules that don't belong to a single entity.
 */
public class UserService {
    private static final int SALT_LENGTH = 16;
    private static final String HASH_ALGORITHM = "SHA-256";

    private final UserRepository userRepository;
    private final SecureRandom secureRandom;

    public UserService(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository, "User repository cannot be null");
        this.secureRandom = new SecureRandom();
    }

    /**
     * Create a new user with validation
     */
    public User createUser(Email email, Name name, String plainPassword, UserRole role) {
        // Check if user already exists
        if (userRepository.existByEmail(email)) {
            throw new DomainException(
                    MessageFormat.format("User with email already exists: {0}", email.getValue()),
                    "USER_ALREADY_EXISTS"
            );
        }

        // Validate password strength
        validatePasswordStrength(plainPassword);

        // Hash password
        String hashedPassword = hashPassword(plainPassword);

        // Create user
        User user = new User(email, name, hashedPassword, role);

        return userRepository.save(user);
    }

    /**
     * Authenticate user with email and password
     */
    public Optional<User> authenticateUser(Email email, String plainPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        if (!user.isActive()) {
            throw new DomainException("User account is not active", "USER_NOT_ACTIVE");
        }

        if (!verifyPassword(plainPassword, user.getPasswordHash())) {
            return Optional.empty();
        }

        user.recordLogin();
        userRepository.save(user);
        return Optional.of(user);
    }

    /**
     * Change user password with validation
     */
    public void changeUserPassword(UUID userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DomainException("User not found", "USER_NOT_FOUND"));

        // Verify current user password
        if (!verifyPassword(currentPassword, user.getPasswordHash())) {
            throw new DomainException("Current password is incorrect", "INVALID_PASSWORD");
        }

        // Validate new password
        validatePasswordStrength(newPassword);

        // Hash new password
        String newPasswordHash = hashPassword(newPassword);

        user.changePassword(newPasswordHash);
        userRepository.save(user);
    }

    /**
     * Validate password strength
     */
    private void validatePasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            throw new DomainException("Password must be at least 8 characters long", "WEAK_PASSWORD");
        }

        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(ch -> "!@#$%^&*()_+-=[]{}|;:,.<>?".indexOf(ch) >= 0);

        if (!(hasUpperCase && hasLowerCase && hasDigit && hasSpecial)) {
            throw new DomainException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one " + "special character", "WEAK_PASSWORD");
        }
    }

    /**
     * Hash password with salt
     */
    private String hashPassword(String password) {
        try {
            // Generate salt
            byte[] salt = new byte[SALT_LENGTH];
            secureRandom.nextBytes(salt);

            // Hash password with salt
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            // Combine salt and hash
            byte[] combined = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(hashedPassword, 0, combined, salt.length, hashedPassword.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (NoSuchAlgorithmException e) {
            throw new DomainException("Password hashing failed", "HASH_ERROR", e);
        }
    }

    /**
     * Verify password against hash
     */
    private boolean verifyPassword(String password, String hashedPassword) {
        try {
            byte[] combined = Base64.getDecoder().decode(hashedPassword);

            // Extract salt
            byte[] salt = new byte[SALT_LENGTH];
            System.arraycopy(combined, 0, salt, 0, SALT_LENGTH);

            // Extract hash
            byte[] hash = new byte[combined.length - SALT_LENGTH];
            System.arraycopy(combined, SALT_LENGTH, hash, 0, hash.length);

            // Hash provided password with extracted salt
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(salt);
            byte[] hashedAttempt = md.digest(password.getBytes());

            // Compare hashes
            return MessageDigest.isEqual(hash, hashedAttempt);
        } catch (Exception e) {
            return false;
        }
    }
}
