package ihanoi.ihanoi_backend.entity;

import ihanoi.ihanoi_backend.dto.FileUploadResponse;
import ihanoi.ihanoi_backend.dto.user.UserDto;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "complaints")
@DynamicInsert
public class Complaint {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "category", nullable = false)
    private String category;

    @Size(max = 255)
    @NotNull
    @Column(name = "level", nullable = false)
    private String level;

    @ColumnDefault("now()")
    @Column(name = "created", nullable = false)
    private ZonedDateTime created;

    @NotNull
    @Column(name = "location", nullable = false, length = Integer.MAX_VALUE)
    private String location;

    @NotNull
    @Column(name = "title", nullable = false, length = Integer.MAX_VALUE)
    private String title;

    @NotNull
    @Column(name = "content", nullable = false, length = Integer.MAX_VALUE)
    private String content;

    @Size(max = 255)
    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "updated")
    private ZonedDateTime updated;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Size(max = 255)
    @NotNull
    @Column(name = "ward_code", nullable = false)
    private String wardCode;

    @NotNull
    @Column(name = "lat", nullable = false)
    private Double lat;

    @NotNull
    @Column(name = "lng", nullable = false)
    private Double lng;

    @Column(name = "assigned_department")
    private Long assignedDepartment;

    @Column(name = "previous_department")
    private Long previousDepartment;

    @OneToOne
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private User user;

    @Type(JsonBinaryType.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "media_urls", columnDefinition = "jsonb")
    private List<FileUploadResponse> mediaUrls;

    @Column(name = "due_date")
    private ZonedDateTime dueDate;
}