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

    protected Instant getDateCreated() {
        return this.dateCreated;
    }

    protected Instant getDateUpdated() {
        return this.dateUpdated;
    }

    protected boolean getDeleted() {
        return this.deleted;
    }

    protected void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    protected void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    protected void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}