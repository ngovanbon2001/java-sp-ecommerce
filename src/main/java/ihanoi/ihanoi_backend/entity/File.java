package ihanoi.ihanoi_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "files")
public class File {
    @Id
    @Size(max = 255)
    @ColumnDefault("nextval('files_id_seq')")
    @Column(name = "id", nullable = false)
    private String id;

    @Size(max = 255)
    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull
    @Column(name = "file_name", nullable = false, length = Integer.MAX_VALUE)
    private String fileName;

    @NotNull
    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Size(max = 255)
    @Column(name = "mime_type")
    private String mimeType;

    @ColumnDefault("now()")
    @Column(name = "created", nullable = false)
    private ZonedDateTime created;

    @Size(max = 255)
    @NotNull
    @Column(name = "fid", nullable = false)
    private String fid;

    @Size(max = 255)
    @NotNull
    @Column(name = "volume", nullable = false)
    private String volume;

    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    private Complaint complaint;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    private OfficerReport report;

}