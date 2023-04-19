package com.nichoshop.main.model;

import java.util.Date;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "specifics")
public class Specific {

    @Id @Column private Long id;
    @Column private String name;
    @Column(name = "value_type") private String valueType;
    @Column private Boolean required = false;
    @Column(name = "default_value", nullable = true) private String defaultValue;
    @Column(name = "created_at", nullable = false, updatable = false) @Temporal(TemporalType.TIMESTAMP) @CreatedDate private Date createdAt = new Date();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValueType() {
        return valueType;
    }
    public void setValueType(String valueType) {
        this.valueType = valueType;
    }
    public Boolean isRequired() {
        return required;
    }
    public void setRequired(Boolean required) {
        this.required = required;
    }
    public String getDefaultValue() {
        return defaultValue;
    }
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
