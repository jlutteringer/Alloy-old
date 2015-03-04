package org.alloy.persistence.dao;

import org.alloy.metal.domain.Identifiable;

public interface Dao<T extends Identifiable> extends DaoFacade<T> {

}