package pl.js.onedcutter.repo;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import pl.js.onedcutter.models.project.ProjectModel;

public interface ProjectRepository extends CrudRepository<ProjectModel, Long>{

    ProjectModel findProjectModelById(Long id);
    ProjectModel getById(Long id);
    ProjectModel findProjectModelByIdAndUserId(Long id, Long userId);

    @Transactional
    void deleteProjectModelById(Long id);
}
