package ihanoi.ihanoi_backend.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comment_paths")
@Data
@Getter
@Setter
@IdClass(CommentPathId.class)
public class CommentPath {
    @Id
    @Column(name = "ancestor_id")
    private Long ancestorId;

    @Id
    @Column(name = "descendant_id")
    private Long descendantId;

    private int depth;
}
