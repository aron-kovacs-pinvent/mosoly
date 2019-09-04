package live.mosoly.portalbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class RestResponse {

    private Boolean success = true;
    private String name;
    private String attribute;
    private Long tokens;

    public static RestResponse failed(){
        return RestResponse.builder().success(false).build();
    }

}
