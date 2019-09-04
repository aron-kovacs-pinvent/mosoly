package live.mosoly.portalbackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Mentoree {

    @EmbeddedId
    private MentoreeKey key;

    @Temporal(TemporalType.TIMESTAMP)
    private Date mentorshipStarted;

    private String customNameForMentor;

    private Boolean validated;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    public Mentoree(MentoreeKey key, Date date){
        this.key = key;
        this.mentorshipStarted = date;
    }

}
