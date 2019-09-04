package live.mosoly.portalbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegInfoObject {

    private String email;
    private String name;
    private String account;
    private PhotoIdType photoIdType;
    private String photoIdNumber;
    private String password;
    private String mentor1Hash;
    private String mentor2Hash;

}
