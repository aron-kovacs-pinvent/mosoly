package live.mosoly.portalbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyncMentoreeDTO implements MentoreeInterface {

    private Integer userId;
    private String customNameForMentor;
    private String account;
    private Long tokenBalance;
    private Boolean validated;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Date mentorshipStarted;

}
