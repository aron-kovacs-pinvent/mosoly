package live.mosoly.portalbackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "mosoly_user")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PhotoIdType photoIdType;

    private String photoIdNumber;

    @NotNull
    @Column(name = "blockchain_id", nullable = false)
    private String account;

    @NotNull
    private String passwordDigest;

    @NotNull
    private String role = "ROLE_USER";

    private String email;

    private Boolean validated = Boolean.FALSE;
    private Boolean accountLocked = Boolean.FALSE;

    private long tokenBalance = 0L;

    @Temporal(TemporalType.TIMESTAMP)
    private Date joined = new Date();

    private String inviteUrlHash = UUID.randomUUID().toString();

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    @PrePersist
    public void prePersist(){
        this.updatedAt = new Date();
    }

    public void setEmail(String email){
        if(!StringUtils.isEmpty(email)){
            this.email = email.toLowerCase();
        }
    }

    public User(RegInfoObject regInfoObject){
        setEmail(regInfoObject.getEmail());
        setAccount(regInfoObject.getAccount());
        setName(regInfoObject.getName());
        setPhotoIdType(regInfoObject.getPhotoIdType());
        setPhotoIdNumber(regInfoObject.getPhotoIdNumber());

    }

}
