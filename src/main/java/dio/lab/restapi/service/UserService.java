package dio.lab.restapi.service;

import dio.lab.restapi.domain.model.User;
import dio.lab.restapi.domain.repository.UserRepository;
import dio.lab.restapi.exception.BusinessException;
import dio.lab.restapi.exception.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Long UNCHANGEABLE_USER_ID = 1L;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    @Transactional
    public User create(User userToCreate) {
        if (userToCreate == null) {
            throw new BusinessException("O usuário não pode ser nulo");
        }
        if (userToCreate.getUsername() == null || userToCreate.getUsername().isEmpty()) {
            throw new BusinessException("O nome de usuário não pode ser vazio");
        }
        if (userRepository.existsByEmail(userToCreate.getEmail())) {
            throw new BusinessException("Esse e-mail já foi registrado");
        }

        if (userRepository.existsByUsername(userToCreate.getUsername())) {
            throw new BusinessException("Nome de usuário já existe");
        }

        userToCreate.setPassword(passwordEncoder.encode(userToCreate.getPassword()));

        return userRepository.save(userToCreate);
    }

    @Transactional
    public User update(Long id, User userToUpdate) {
        System.out.println("Attempting to update user with id: " + id);

        if (!id.equals(userToUpdate.getId())) {
            throw new BusinessException("O ID da URL deve ser o mesmo que o ID no corpo da requisição");
        }

        validateChangeableId(id, "updated");

        User dbUser = findById(id);

        dbUser.setUsername(userToUpdate.getUsername());
        dbUser.setEmail(userToUpdate.getEmail());

        if (userToUpdate.getPassword() != null) {
            dbUser.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));
        }

        return userRepository.save(dbUser);
    }


    @Transactional
    public void delete(Long id) {
        System.out.println("Attempting to delete user with id: " + id); // Log para depuração
        validateChangeableId(id, "deleted");

        User dbUser = findById(id);
        userRepository.delete(dbUser);
    }

    private void validateChangeableId(Long id, String operation) {
        if (UNCHANGEABLE_USER_ID.equals(id)) {
            throw new BusinessException("The User with ID %d cannot be %s.".formatted(UNCHANGEABLE_USER_ID, operation));
        }
    }

    @Transactional
    public boolean validateUserPassword(String plainPassword, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        return passwordEncoder.matches(plainPassword, user.getPassword());
    }
}
