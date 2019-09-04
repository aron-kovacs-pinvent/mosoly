package live.mosoly.portalbackend.model.dto;

import live.mosoly.portalbackend.model.PhotoIdType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MentoreeDTO implements MentoreeInterface{

    private Integer userId;
    private String customNameForMentor;
    private String account;
    private String email;
    private Long tokenBalance;
    private Boolean validated;
    private Date mentorshipStarted;
    private PhotoIdType photoIdType;
    private String photoIdNumber;

}
