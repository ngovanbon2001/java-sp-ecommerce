package ihanoi.ihanoi_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "system_configs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemConfig {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "value")
    private String value;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime modified;
}
