package com.siapri.broker.business.service.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;
import com.siapri.broker.business.dao.repository.IBasicRepository;
import com.siapri.broker.business.model.AbstractEntity;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Sinister;
import com.siapri.broker.business.service.IBasicDaoService;
import com.siapri.broker.business.service.impl.DaoEvent.EventType;

@Service
// @org.springframework.context.annotation.Profile("prod")
@SuppressWarnings("unchecked")
public class BasicDaoService implements IBasicDaoService {
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private EventBus daoEventBus;
	
	@SuppressWarnings("rawtypes")
	private <T extends AbstractEntity> Optional<IBasicRepository> getBean(final Class<T> clazz) {
		for (final Entry<String, IBasicRepository> entry : appContext.getBeansOfType(IBasicRepository.class).entrySet()) {
			for (final Class<?> interfaze : entry.getValue().getClass().getInterfaces()) {
				for (final Type type : interfaze.getGenericInterfaces()) {
					if (type instanceof ParameterizedType) {
						final Type[] genericTypes = ((ParameterizedType) type).getActualTypeArguments();
						if (genericTypes.length == 2 && genericTypes[0].equals(clazz) && genericTypes[1].equals(Long.class)) {
							return Optional.of(entry.getValue());
						}
					}
				}
			}
		}
		return Optional.empty();
	}
	
	@SuppressWarnings("rawtypes")
	private <T extends AbstractEntity> IBasicRepository<T, Long> getRepository(final Class<T> clazz) {
		final Optional<IBasicRepository> result = getBean(clazz);
		if (!result.isPresent()) {
			throw new IllegalArgumentException(String.format("No repository found for '%s' class", clazz));
		}
		return result.get();
	}
	
	@Override
	public <T extends AbstractEntity> T save(final T entity) {
		final T entitySaved = getRepository((Class<T>) entity.getClass()).saveAndFlush(entity);
		daoEventBus.post(new DaoEvent(entity.getId() == null ? EventType.CREATE : EventType.UPDATE, entitySaved));
		return entitySaved;
	}
	
	@Override
	public <T extends AbstractEntity> List<T> saveAll(final List<T> entities) {
		final Class<T> entityClass = (Class<T>) entities.get(0).getClass();
		final IBasicRepository<T, Long> repository = getRepository(entityClass);
		final List<T> result = repository.save(entities);
		repository.flush();
		daoEventBus.post(new DaoEvent(entities.get(0).getId() == null ? EventType.CREATE : EventType.UPDATE, result.toArray(new AbstractEntity[result.size()])));
		return result;
	}
	
	@Override
	public <T extends AbstractEntity> List<T> getAll(final Class<T> clazz) {
		return getRepository(clazz).findAll(new Sort(Direction.DESC, "lastModifiedDate"));
	}
	
	@Override
	public <T extends AbstractEntity> Stream<T> getAllAsStream(final Class<T> clazz) {
		return getRepository(clazz).getAllAsStream();
	}
	
	@Override
	public <T extends AbstractEntity> void delete(final T entity) {
		getRepository((Class<T>) entity.getClass()).delete(entity);
	}
	
	@Override
	public <T extends AbstractEntity> void delete(final Class<T> clazz, final long id) {
		getRepository(clazz).delete(id);
	}
	
	@Override
	public <T extends AbstractEntity> void deleteAll(final Class<T> clazz) {
		getRepository(clazz).deleteAll();
	}
	
	@Override
	public <T extends AbstractEntity> Optional<T> find(final Class<T> clazz, final long entityId) {
		final T entity = getRepository(clazz).findOne(entityId);
		if (entity != null) {
			return Optional.of(entity);
		}
		return Optional.empty();
	}

	@Override
	public <T extends AbstractEntity> List<T> getLatestElements(final Class<T> clazz, final int limit) {
		final Page<T> page = getRepository(clazz).findAll(new PageRequest(0, limit, Direction.DESC, "lastModifiedDate"));
		return page.getContent();
	}

	@Transactional
	@Override
	public List<Company> getInsurers(final int limit) {
		if (limit < 0) {
			return getAllAsStream(Company.class).filter(c -> c.isInsurer()).collect(Collectors.toList());
		}
		return getAllAsStream(Company.class).filter(c -> c.isInsurer()).limit(limit).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public List<Company> getEntreprises(final int limit) {
		if (limit < 0) {
			return getAllAsStream(Company.class).filter(c -> !c.isInsurer()).collect(Collectors.toList());
		}
		return getAllAsStream(Company.class).filter(c -> !c.isInsurer()).limit(limit).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public List<Sinister> getSinistersByContract(final Contract contract) {
		return getAllAsStream(Sinister.class).filter(sinister -> sinister.getContract().equals(contract)).collect(Collectors.toList());
	}
	
}
