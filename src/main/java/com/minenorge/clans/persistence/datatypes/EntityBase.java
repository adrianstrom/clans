package com.minenorge.clans.persistence.datatypes;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EntityBase
{
    @Column(name = "DateCreated")
    public Instant dateCreated = Instant.now();
    @Column(name = "DateUpdated")
    public Instant dateUpdated = Instant.now();
    @Column(name = "Deleted")
    public boolean deleted;
}