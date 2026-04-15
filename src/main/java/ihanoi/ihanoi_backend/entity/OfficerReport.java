package ihanoi.ihanoi_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import ihanoi.ihanoi_backend.dto.FileUploadResponse;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "officer_reports")
public class OfficerReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "content", nullable = false, length = Integer.MAX_VALUE)
    private String content;

    @ColumnDefault("now()")
    @Column(name = "created", nullable = false)
    private ZonedDateTime created;

    @ColumnDefault("now()")
    @Column(name = "updated", nullable = false)
    private ZonedDateTime updated;

    @Column(name = "complaint_id")
    private Long complaintId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "department_name")
    private String departmentName;

    @Type(JsonBinaryType.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "media_urls", columnDefinition = "jsonb")
    private List<FileUploadResponse> mediaUrls;

    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}