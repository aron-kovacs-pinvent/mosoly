package live.mosoly.portalbackend.model.project;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
public class ProjectSupportDTO {

    private String toolNeededId;
    private String peopleNeededId;
}
