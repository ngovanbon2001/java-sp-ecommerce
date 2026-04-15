package ecomerce.entity;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "departments")
@DynamicInsert
@DynamicUpdate
@NamedEntityGraph(
        name = "staff",
        attributeNodes = {
                @NamedAttributeNode(value = "staffDepartments"),
        }
)
@SQLDelete(sql = "UPDATE departments SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "path", length = Integer.MAX_VALUE)
    private String path;

    @ColumnDefault("now()")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ColumnDefault("now()")
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<StaffDepartment> staffDepartments = new ArrayList<>();

    // public void addStaffDepartment(StaffDepartment staffDepartment) {
    //     staffDepartments.add(staffDepartment);
    //     staffDepartment.setDepartment(this);
    // }

    // @OneToMany(mappedBy = "administrator", cascade = CascadeType.ALL)
    // private Set<WardAdministrator> wardAdministrators;

    // public Set<Ward> getWards() {
    //     return wardAdministrators.stream()
    //             .map(WardAdministrator::getWard)
    //             .collect(Collectors.toSet());
    // }
}