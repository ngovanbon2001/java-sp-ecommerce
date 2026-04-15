package ihanoi.ihanoi_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "complaint_response_reviews")
@DynamicInsert
public class ComplaintResponseReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "complaint_id", nullable = false)
    private Long complaintId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ColumnDefault("now()")
    @Column(name = "submitted_at", nullable = false)
    private ZonedDateTime submittedAt;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comment", length = Integer.MAX_VALUE)
    private String comment;

}