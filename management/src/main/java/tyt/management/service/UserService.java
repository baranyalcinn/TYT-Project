package tyt.management.service;

import tyt.management.model.dto.UserDTO;

import java.util.List;

/**
 * UserService is an interface that defines the contract for user management operations.
 * Implementations of this interface must provide the functionality for each method.
 */
public interface UserService {

    /**
     * Creates a new user.
     *
     * @param userDTO The data transfer object containing the details of the user to be created.
     * @return A string message indicating the result of the operation.
     */
    String createUser(UserDTO userDTO);

    /**
     * Updates an existing user.
     *
     * @param userDTO The data transfer object containing the updated details of the user.
     * @return A string message indicating the result of the operation.
     */
    String updateUser(UserDTO userDTO);

    /**
     * Deletes a user.
     *
     * @param userDTO The data transfer object containing the details of the user to be deleted.
     */
    void deleteUser(UserDTO userDTO);

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return The data transfer object of the retrieved user.
     */
    UserDTO getUser(Long id);

    /**
     * Retrieves all users.
     *
     * @return A list of data transfer objects of all users.
     */
    List<UserDTO> getAllUsers();
}