package ihanoi.ihanoi_backend.entity;

import ihanoi.ihanoi_backend.dto.user.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "complaint_histories")
@DynamicInsert
public class ComplaintHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "complaint_id", nullable = false)
    private Long complaintId;

    @Size(max = 255)
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ColumnDefault("now()")
    @Column(name = "created", nullable = false)
    private ZonedDateTime created;

    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}