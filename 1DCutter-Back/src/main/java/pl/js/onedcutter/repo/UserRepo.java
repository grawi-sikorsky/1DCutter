package pl.js.onedcutter.repo;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import pl.js.onedcutter.models.user.UserModel;

public interface UserRepo extends CrudRepository<UserModel, Long> {
    
    UserModel findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    UserModel findByUuid(String uuid);

    @Transactional
    void deleteByUuid(String uuid);
}