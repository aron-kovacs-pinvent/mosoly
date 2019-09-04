package live.mosoly.portalbackend.model.project;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import live.mosoly.portalbackend.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Project {

    @Id
    private String id = UUID.randomUUID().toString();

    @Column(columnDefinition = "text")
    private String picture;

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String estimatedImpact;

    @Column(columnDefinition = "text")
    private String place;

    private Integer minimumToGoLive;

    private Integer createdBy;

    private Date plannedDate;

    private boolean live = false;

    @OneToMany(mappedBy="project", cascade = CascadeType.ALL)
    private List<ProjectToolsNeeded> toolsNeeded = new ArrayList<>();

    @OneToMany(mappedBy="project", cascade = CascadeType.ALL)
    private List<ProjectPeopleNeeded> peopleNeeded = new ArrayList<>();

    @OneToMany(mappedBy="project", cascade = CascadeType.ALL)
    private List<ProjectRating> rating = new ArrayList<>();

    @OneToMany(mappedBy="project", cascade = CascadeType.ALL)
    private List<ProjectTokenSupport> tokens = new ArrayList<>();

    private Double longitude;
    private Double latitude;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    public Integer getSupportTokens(){
        int tools =  toolsNeeded.stream().mapToInt(ProjectToolsNeeded::getActual).sum();
        int people = peopleNeeded.stream().mapToInt(ProjectPeopleNeeded::getActual).sum();

        return tools + people;
    }

    public Project(String picture, String name, String description, String estimatedImpact, String place, Integer minimumToGoLive, Integer createdBy, Date plannedDate, List<ProjectToolsNeeded> toolsNeeded, List<ProjectPeopleNeeded> peopleNeeded, Double longitude, Double latitude) {
        this.picture = picture;
        this.name = name;
        this.description = description;
        this.estimatedImpact = estimatedImpact;
        this.place = place;
        this.minimumToGoLive = minimumToGoLive;
        this.createdBy = createdBy;
        this.plannedDate = plannedDate;
        this.toolsNeeded = toolsNeeded;
        this.peopleNeeded = peopleNeeded;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @PrePersist
    public void prePersist(){
        this.updatedAt = new Date();
    }
}
