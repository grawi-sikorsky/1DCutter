package pl.js.onedcutter.controllers.cutter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.js.onedcutter.services.CutService;
import pl.js.onedcutter.models.project.ProjectModel;
import pl.js.onedcutter.models.results.ResultModel;


@CrossOrigin(origins = { "http://localhost:4200", "http://10.0.2.2:8080", "http://localhost", "https://api.cutter.grawires.pl", "http://217.182.73.214", "http://cutter.grawires.pl", "https://cutter.grawires.pl", "http://api.cutter.grawires.pl", "*" })
@RestController
public class CutController {

    private CutService cutService;

    public CutController(CutService cutService){
        this.cutService = cutService;
    }

    // OBLICZ LOGGED
    @PostMapping("/cut")
    public ResponseEntity<ResultModel> resolveCutting(@RequestBody ProjectModel projectModel) {
        return ResponseEntity.status(HttpStatus.OK).body(cutService.calculateProject(projectModel));
    }

    // Oblicz nie Logged
    @PostMapping("/cutfree")
    public ResponseEntity<ResultModel> processOrderFree(@RequestBody ProjectModel projectModel) {
        return ResponseEntity.status(HttpStatus.OK).body( cutService.calculateProjectFree(projectModel) );
    }
}
