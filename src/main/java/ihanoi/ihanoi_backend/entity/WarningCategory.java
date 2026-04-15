package ihanoi.ihanoi_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "warning_categories")
@DynamicInsert
public class WarningCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "description", nullable = false)
    private String description;

    @ColumnDefault("now()")
    @Column(name = "created", nullable = false)
    private ZonedDateTime created;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

}