package live.mosoly.portalbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import live.mosoly.portalbackend.model.PhotoIdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDTO {

    private Integer id;
    private String name;
    private String account;
    private String role;
    private String email;
    private PhotoIdType photoIdType;
    private String photoIdNumber;
    private Long tokenBalance;
    private String inviteUrlHash;
    private boolean validated;
    private Integer validations;
    private Date joined;
    private String jwt;
    private List<MentoreeInterface> mentorees;

}
