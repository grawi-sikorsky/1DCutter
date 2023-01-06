package pl.js.onedcutter.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pl.js.onedcutter.models.project.ProjectModel;
import pl.js.onedcutter.models.user.UserModel;

@Getter
@Setter
public class UserDTO {

    private String uuid;
    private String username;
    private String role;
    private String email;
    private String phone;
    private String website;
    private Integer numberOfSavedItems;
    private Integer activeProjectId;

    private ProjectModel activeProjectModel;
    private List<ProjectModel> savedProjectModels = new ArrayList<ProjectModel>();

    public UserDTO() {
    }

    public UserDTO(UserModel userModel){
        this.uuid       = userModel.getUuid();
        this.username   = userModel.getUsername();
        this.role       = userModel.getRole();
        this.email      = userModel.getEmail();
        this.phone      = userModel.getPhone();
        this.website    = userModel.getWebsite();
        this.numberOfSavedItems = userModel.getNumberOfSavedItems();
        this.activeProjectId    = userModel.getActiveProjectId();
        this.activeProjectModel = userModel.getActiveProjectModel();
        this.savedProjectModels = userModel.getSavedProjectModels(); // todo: out!
    }

    public UserModel toEntity(){
        UserModel userModel = new UserModel();

        userModel.setUuid(this.uuid);
        userModel.setUsername(this.username);
        userModel.setRole(this.role);
        userModel.setEmail(this.email);
        userModel.setPhone(this.phone);
        userModel.setWebsite(this.website);
        userModel.setNumberOfSavedItems(this.numberOfSavedItems);
        userModel.setActiveProjectId(this.activeProjectId);
        userModel.setActiveProjectModel(this.activeProjectModel);
        userModel.setSavedProjectModels(this.savedProjectModels);

        return userModel;
    }
}
