package live.mosoly.portalbackend.model.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class ProjectSupport {

    @Id
    private String id = UUID.randomUUID().toString();

    private Integer userId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="tool_needed_id")
    private ProjectToolsNeeded toolNeeded;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="people_needed_id")
    private ProjectPeopleNeeded peopleNeeded;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    @PrePersist
    public void prePersist(){
        this.updatedAt = new Date();
    }

}
