package com.iwomi.authms.frameworks.data.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iwomi.authms.core.constants.AppConst;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@ToString
@EqualsAndHashCode
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "created_at", updatable = false)
    @JsonFormat(pattern = AppConst.DATEFORMAT, timezone = AppConst.BACKEND_TIME_ZONE)
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", updatable = false)
    @JsonFormat(pattern = AppConst.DATEFORMAT, timezone = AppConst.BACKEND_TIME_ZONE)
    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    private Date updatedAt;
}
