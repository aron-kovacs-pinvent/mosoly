package live.mosoly.portalbackend.model.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
public class ProjectPeopleNeeded {

    @Id
    private String id = UUID.randomUUID().toString();

    private String name;
    private int needed;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="project_id", nullable=false)
    private Project project;

    @OneToMany(mappedBy = "peopleNeeded")
    private List<ProjectSupport> supportLisŧ = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    public int getActual(){
        return supportLisŧ.size();
    }

    public boolean isFulfilled(){
        return needed == getActual();
    }

    @PrePersist
    public void prePersist(){
        this.updatedAt = new Date();
    }
}
