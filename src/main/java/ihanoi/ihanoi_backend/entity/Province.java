package ihanoi.ihanoi_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "provinces", indexes = {
        @Index(name = "idx_provinces_unit", columnList = "administrative_unit_id")
})
@NamedEntityGraph(
        name = "province-ward",
        attributeNodes = {
                @NamedAttributeNode(value = "wards"),
        }

)
public class Province {
    @Id
    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "full_name_en")
    private String fullNameEn;

    @Column(name = "code_name")
    private String codeName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "province")
    private Set<Ward> wards;

}