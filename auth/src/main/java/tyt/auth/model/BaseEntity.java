package tyt.auth.model;

import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseEntity implements Serializable {

    @Id
    protected Long id;

    protected String createdAt;
    protected String createdBy;
    protected String updatedAt;
    protected String updatedBy;
}
