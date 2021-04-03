package com.github.mzebib.provisioningapi.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author mzebib
 */
@Entity(name = "credentials")
@Table(name = "credentials")
public class CredentialsEntity extends BaseEntity {

    @OneToOne(mappedBy = "credentials", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "token", length = 512)
    private String token;

    public CredentialsEntity() {

    }

    public CredentialsEntity(UserEntity user, String passwordHash) {
        this.user = user;
        this.passwordHash = passwordHash;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
