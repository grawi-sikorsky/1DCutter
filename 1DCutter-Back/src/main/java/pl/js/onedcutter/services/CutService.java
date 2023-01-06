package pl.js.onedcutter.services;

import org.springframework.stereotype.Service;

import pl.js.onedcutter.models.project.ProjectModel;
import pl.js.onedcutter.models.results.CutterProduct;
import pl.js.onedcutter.models.results.ResultModel;

@Service
public class CutService {

    private final ResultService resultService;
    private final ProjectService projectService;
    private final ResolveService resolveService;

    public CutService(ResultService resultService, ProjectService projectService, ResolveService resolveService) {
        this.resultService = resultService;
        this.projectService = projectService;
        this.resolveService = resolveService;
    }

    /**
     * Wykonuje obliczenia dla zalogowanego Usera
     * 
     * @param projectModel
     * @return ResultModel
     */
    public ResultModel calculateProject(ProjectModel projectModel) {
        projectService.saveActiveProject(projectModel);
        CutterProduct cutterProduct = resolveService.firstFit(projectModel);
        if( projectModel.getCutOptions().isOptionAlgo() ){
            cutterProduct = resolveService.newAlgo(cutterProduct, projectModel);
        }
        return resultService.makeFullResults(cutterProduct, projectModel);
    }

    /**
     * Wykonuje obliczenia dla usera niezalogowanego
     * 
     * @param projectModel
     * @return ResultModel
     */
    public ResultModel calculateProjectFree(ProjectModel projectModel) {
        return resultService.makeFullResults(resolveService.firstFit(projectModel), projectModel);
    }
}