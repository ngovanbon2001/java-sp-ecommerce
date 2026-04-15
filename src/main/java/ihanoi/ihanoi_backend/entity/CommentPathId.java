package ihanoi.ihanoi_backend.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class CommentPathId implements java.io.Serializable {
    private Long ancestorId;
    private Long descendantId;
}
