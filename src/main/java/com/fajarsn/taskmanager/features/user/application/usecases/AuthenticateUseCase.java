package com.fajarsn.taskmanager.features.user.application.usecases;

import com.fajarsn.taskmanager.features.user.application.commands.AuthenticateUserCommand;
import com.fajarsn.taskmanager.features.user.application.mappers.UserMapper;
import com.fajarsn.taskmanager.features.user.application.responses.AuthenticationResponse;
import com.fajarsn.taskmanager.features.user.domain.User;
import com.fajarsn.taskmanager.features.user.domain.UserService;
import com.fajarsn.taskmanager.shared.application.UseCase;
import com.fajarsn.taskmanager.shared.domain.Email;
import com.fajarsn.taskmanager.shared.infrastructure.security.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Use case for user authentication.
 */
public class AuthenticateUseCase implements UseCase<AuthenticateUserCommand, AuthenticationResponse> {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticateUseCase.class);

    private final UserService userService;
    private final TokenService tokenService;

    public AuthenticateUseCase(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public AuthenticationResponse execute(AuthenticateUserCommand request) {
        logger.info("Authenticating user with email: {} (Request ID: {}", request.email(), request.getRequestId());

        try {
            Email email = Email.of(request.email());

            Optional<User> userOptional = userService.authenticateUser(email, request.password());

            if (userOptional.isEmpty()) {
                logger.warn("Authentication failed for email: {} (Request ID: {})", request.email(), request.requestId());
                return AuthenticationResponse.failure("Invalid email or password");
            }

            User user = userOptional.get();

            // Generate token
            String token = tokenService.generateToken(user);
            long expiresIn = tokenService.getTokenExpirationTime();

            logger.info("User authenticated successfully: {} (Request ID: {})", user.getId(), request.getRequestId());

            return new AuthenticationResponse(UserMapper.toResponse(user), token, expiresIn);
        } catch (Exception e) {
            logger.error("Authentication error for email: {} (Request ID: {})", request.email(), request.getRequestId(), e);

            return AuthenticationResponse.failure("Authentication failed due to system error");
        }
    }
}
