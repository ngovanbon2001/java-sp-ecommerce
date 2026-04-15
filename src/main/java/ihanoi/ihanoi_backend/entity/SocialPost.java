package ihanoi.ihanoi_backend.entity;

import ihanoi.ihanoi_backend.dto.FileUploadResponse;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "social_posts")
@DynamicInsert
public class SocialPost {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "title")
    private String title;

    @Column(name = "content", nullable = false, length = Integer.MAX_VALUE)
    private String content;

    @Type(JsonBinaryType.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "media_urls", columnDefinition = "jsonb")
    private List<FileUploadResponse> mediaUrls;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "comment_count", nullable = false)
    private Integer commentCount;

    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    @ColumnDefault("now()")
    @Column(name = "created", nullable = false)
    private ZonedDateTime created;

    @ColumnDefault("now()")
    @Column(name = "updated", nullable = false)
    private ZonedDateTime updated;

    @Column(name = "updated_by", nullable = false)
    private Long updatedBy;

    @OneToOne
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private User createdUser;

    @OneToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private SocialPostCategory category;

}