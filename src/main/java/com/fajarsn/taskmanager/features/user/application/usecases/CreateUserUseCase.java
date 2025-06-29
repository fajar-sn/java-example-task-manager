package com.fajarsn.taskmanager.features.user.application.usecases;

import com.fajarsn.taskmanager.features.user.application.commands.CreateUserCommand;
import com.fajarsn.taskmanager.features.user.application.mappers.UserMapper;
import com.fajarsn.taskmanager.features.user.application.responses.UserResponse;
import com.fajarsn.taskmanager.features.user.domain.User;
import com.fajarsn.taskmanager.features.user.domain.UserService;
import com.fajarsn.taskmanager.shared.application.UseCase;
import com.fajarsn.taskmanager.shared.domain.Email;
import com.fajarsn.taskmanager.shared.domain.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use case for creating new users.
 */
public class CreateUserUseCase implements UseCase<CreateUserCommand, UserResponse> {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserUseCase.class);

    private final UserService userService;

    public CreateUserUseCase(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserResponse execute(CreateUserCommand command) {
        logger.info("Creating user with email: {} (Request ID: {})", command.email(), command.getRequestId());

        try {
            // Create value objects
            Email email = Email.of(command.email());
            Name name = Name.of(command.firstName(), command.lastName());

            // Use domain service to create user
            User user = userService.createUser(email, name, command.password(), command.role());

            logger.info("User created successfully with ID: {} (Request ID: {})", user.getId(), command.getRequestId());

            // Return response with success message
            return UserMapper.toResponse(user, "User created successfully");
        } catch (Exception e) {
            logger.error("Failed to create user with email: {}, (Request ID: {}", command.email(),
                    command.getRequestId(), e);

            throw e;
        }
    }
}
