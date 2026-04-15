package ihanoi.ihanoi_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.Instant;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "post_likes")
@IdClass(PostLikeId.class)
@DynamicInsert
public class PostLike {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "liked_at")
    private ZonedDateTime likedAt;

    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User createdUser;

}