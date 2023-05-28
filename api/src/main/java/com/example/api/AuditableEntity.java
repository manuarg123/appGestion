package com.example.api;

import com.example.api.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class AuditableEntity {

    @Column(name = "created_at", nullable=true)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable=true)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", nullable=true)
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable=true)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by", nullable=true)
    private User updatedBy;

    @ManyToOne
    @JoinColumn(name = "deleted_by", nullable=true)
    private User deletedBy;
}
