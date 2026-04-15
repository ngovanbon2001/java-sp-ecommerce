package ihanoi.ihanoi_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ihanoi.ihanoi_backend.common.Const;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "staff_departments")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE staff_departments SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class StaffDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "role", length = 50)
    @Enumerated(EnumType.STRING)
    private Const.Department.Role role;

    @ColumnDefault("now()")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ColumnDefault("now()")
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonIgnore
    private Department department;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StaffDepartment)) return false;
        return id != null && id.equals(((StaffDepartment) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}