package pl.printo3d.waluty.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long>
{
  UserEntity findByUsername(String uname);
}
