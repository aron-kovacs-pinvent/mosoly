package live.mosoly.portalbackend.model.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class ProjectRating {

    @Id
    private String id = UUID.randomUUID().toString();

    private Integer userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="project_id", nullable=false)
    private Project project;

    private int rating;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();
}
