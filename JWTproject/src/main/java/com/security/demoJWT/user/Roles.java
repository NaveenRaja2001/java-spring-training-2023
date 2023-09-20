package com.security.demoJWT.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@Entity
@Table(name = "Roles")
public class Roles {

    @PrePersist
    protected void onCreate()
    {
        this.created_At=new Date(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate(){
        this.updated_At=new Date(System.currentTimeMillis());
    }
    @Id
    @SequenceGenerator(name = "role_sequence",
    sequenceName = "role_sequence",
    allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO,
    generator = "role_sequence")
    private Long id;

  private String name;
  private String description;
  private Date created_At;
    private Date updated_At;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "roles_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> user=new HashSet<>();

    public Roles(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated_At() {
        return created_At;
    }

    public void setCreated_At(Date created_At) {
        this.created_At = created_At;
    }

    public Date getUpdated_At() {
        return updated_At;
    }

    public void setUpdated_At(Date updated_At) {
        this.updated_At = updated_At;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }

    public Roles(String name) {
        this.name = name;
    }
}