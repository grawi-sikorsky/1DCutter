package pl.printo3d.waluty.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransRepo extends CrudRepository<TransEntity, Long> {
}
