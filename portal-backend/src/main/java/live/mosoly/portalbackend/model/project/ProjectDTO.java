package live.mosoly.portalbackend.model.project;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class ProjectDTO {

    private String id;
    private String picture;
    private String name;
    private String description;
    private String estimatedImpact;
    private String place;
    private Integer minimumToGoLive;
    private Integer createdBy;
    private Date plannedDate;
    private List<ProjectToolsNeeded> toolsNeeded = new ArrayList<>();
    private List<ProjectPeopleNeeded> peopleNeeded = new ArrayList<>();
    private List<ProjectRating> rating = new ArrayList<>();
    private Double longitude;
    private Double latitude;

}
