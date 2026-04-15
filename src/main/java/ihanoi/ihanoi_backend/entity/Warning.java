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
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "warnings")
@DynamicInsert
public class Warning {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = Integer.MAX_VALUE)
    private String title;

    @Column(name = "content", nullable = false, length = Integer.MAX_VALUE)
    private String content;

    @ColumnDefault("now()")
    @Column(name = "created", nullable = false)
    private ZonedDateTime created;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "thumbnail")
    @Type(JsonBinaryType.class)
    @JdbcTypeCode(SqlTypes.JSON)
    private FileUploadResponse thumbnail;

    @Column(name = "category_id")
    private Long categoryId;

    @OneToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private WarningCategory category;
}