package com.siapri.broker.business.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siapri.broker.business.dao.repository.IBasicRepository;
import com.siapri.broker.business.model.AbstractEntity;
import com.siapri.broker.business.model.Person;

@Service
public class DaoCache {

	@Autowired
	private List<IBasicRepository<? extends AbstractEntity, ? extends Serializable>> repositories;

	private final ArrayListValuedHashMap<Class<?>, AbstractEntity> data = new ArrayListValuedHashMap<>();

	@PostConstruct
	public void reload() {
		data.clear();
		repositories.forEach(repository -> data.putAll(getEntityType(repository), repository.findAll()));
	}
	
	public List<Person> getPersons() {
		return getAll(Person.class);
	}

	@SuppressWarnings("unchecked")
	private <T extends AbstractEntity> List<T> getAll(final Class<T> clazz) {
		return (List<T>) data.get(clazz);
	}
	
	private Class<?> getEntityType(final IBasicRepository<? extends AbstractEntity, ? extends Serializable> interfaze) {
		for (final Type type : interfaze.getClass().getGenericInterfaces()) {
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
		return null;
	}
}
