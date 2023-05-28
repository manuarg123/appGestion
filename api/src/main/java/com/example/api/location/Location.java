package com.example.api.location;

import com.example.api.AuditableEntity;
import com.example.api.province.Province;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Location extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 255)
    private String name;

    @ManyToOne
    @JoinColumn(name="province_id", nullable = false)
    private Province province;

    public Location() {
    }

    public Location(Long id, String name, Province province) {
        this.id = id;
        this.name = name;
        this.province = province;
    }

    public Location(String name, Province province) {
        this.name = name;
        this.province = province;
    }
}
