package com.minenorge.clans.persistence.datatypes;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EntityBase
{
    @Column(name = "DateCreated")
    private Instant dateCreated = Instant.now();
    @Column(name = "DateUpdated")
    private Instant dateUpdated = Instant.now();
    @Column(name = "Deleted")
    private boolean deleted;

    public Instant getDateCreated() {
        return this.dateCreated;
    }

    public Instant getDateUpdated() {
        return this.dateUpdated;
    }

    public boolean getDeleted() {
        return this.deleted;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}