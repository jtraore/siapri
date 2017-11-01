package com.siapri.broker.business.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.siapri.broker.business.model.AbstractEntity;

public interface IBasicDaoService {

	<T extends AbstractEntity> T save(T entity);

	<T extends AbstractEntity> List<T> saveAll(List<T> entities);

	<T extends AbstractEntity> List<T> getAll(Class<T> clazz);

	<T extends AbstractEntity> Stream<T> getAllAsStream(Class<T> clazz);

	<T extends AbstractEntity> void delete(T entity);

	<T extends AbstractEntity> void delete(Class<T> clazz, long id);

	<T extends AbstractEntity> void deleteAll(Class<T> clazz);

	<T extends AbstractEntity> Optional<T> find(Class<T> clazz, long entityId);

	<T extends AbstractEntity> List<T> getLatestElements(Class<T> clazz, int limit);
}