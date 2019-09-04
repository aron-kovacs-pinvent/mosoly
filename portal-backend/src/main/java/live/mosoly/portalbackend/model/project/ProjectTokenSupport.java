package live.mosoly.portalbackend.model.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Data
@Entity
public class ProjectTokenSupport {

    @Id
    private String id = UUID.randomUUID().toString();

    private Integer userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="project_id", nullable=false)
    private Project project;

}
