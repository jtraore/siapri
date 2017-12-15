package com.siapri.broker.business.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.siapri.broker.business.dao.repository.IBasicRepository;
import com.siapri.broker.business.model.AbstractEntity;
import com.siapri.broker.business.model.Broker;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.model.Preference;
import com.siapri.broker.business.model.Sinister;

@Service
public class DaoCacheService {

	@Autowired
	private EventBus daoEventBus;
	
	@Autowired
	private List<IBasicRepository<? extends AbstractEntity, ? extends Serializable>> repositories;
	
	private final ArrayListValuedHashMap<Class<?>, AbstractEntity> data = new ArrayListValuedHashMap<>();
	
	@PostConstruct
	private void init() {
		daoEventBus.register(this);
		reload();
	}

	@Subscribe
	public void onDaoEvent(final DaoEvent event) {
		final Class<? extends AbstractEntity> entityClass = event.getEntities()[0].getClass();
		switch (event.getType()) {
			case CREATE:
				data.putAll(entityClass, Arrays.asList(event.getEntities()));
				break;
			case UPDATE:
				for (final AbstractEntity entity : event.getEntities()) {
					data.removeMapping(entityClass, entity);
				}
				data.putAll(entityClass, Arrays.asList(event.getEntities()));
				break;
			case DELETE:
				for (final AbstractEntity entity : event.getEntities()) {
					data.removeMapping(entityClass, entity);
				}
				break;
			default:
				break;
		}
	}
	
	private void reload() {
		data.clear();
		repositories.forEach(repository -> data.putAll(getEntityType(repository), repository.findAll()));
	}

	public List<InsuranceType> getInsuranceTypes() {
		return getAll(InsuranceType.class);
	}

	public List<Person> getPersons() {
		return getAll(Person.class);
	}
	
	public List<Company> getCompanies() {
		return getAll(Company.class);
	}
	
	public List<Company> getInsurers() {
		return getCompanies().stream().filter(Company::isInsurer).collect(Collectors.toList());
	}
	
	public List<Company> getEntreprises() {
		return getCompanies().stream().filter(c -> !c.isInsurer()).collect(Collectors.toList());
	}
	
	public List<Contract> getContracts() {
		return getAll(Contract.class);
	}
	
	public List<Sinister> getSinisters() {
		return getAll(Sinister.class);
	}
	
	public List<Broker> getBrokers() {
		return getAll(Broker.class);
	}
	
	public List<Preference> getPreferences() {
		return getAll(Preference.class);
	}
	
	@SuppressWarnings("unchecked")
	private <T extends AbstractEntity> List<T> getAll(final Class<T> clazz) {
		return (List<T>) data.get(clazz);
	}

	private Class<?> getEntityType(final IBasicRepository<? extends AbstractEntity, ? extends Serializable> interfaze) {
		for (final Class<?> subInterface : interfaze.getClass().getInterfaces()) {
			for (final Type type : subInterface.getGenericInterfaces()) {
				if (type instanceof ParameterizedType) {
					final Type[] genericTypes = ((ParameterizedType) type).getActualTypeArguments();
					if (genericTypes.length == 2 && genericTypes[1].equals(Long.class)) {
						try {
							return Class.forName(genericTypes[0].getTypeName());
						} catch (final ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}
}
