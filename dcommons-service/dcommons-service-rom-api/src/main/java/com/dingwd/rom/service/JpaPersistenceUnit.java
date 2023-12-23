package com.dingwd.rom.service;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public enum JpaPersistenceUnit {
    JPA("GENERIC_JPA");

    private final String persistenceUnitName;

    JpaPersistenceUnit(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }

    public EntityManagerFactory createEntityManagerFactory() {
        return Persistence.createEntityManagerFactory(persistenceUnitName);
    }
}