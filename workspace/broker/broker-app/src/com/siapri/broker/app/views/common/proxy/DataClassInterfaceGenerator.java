package com.siapri.broker.app.views.common.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class DataClassInterfaceGenerator {

	public static Class<?> createInterface(final Class<?> clazz) throws CannotCompileException, ClassNotFoundException {
		final ClassPool cp = ClassPool.getDefault();
		final String interfaceName = clazz.getName() + "$InterfaceGeneratedBySiapri";
		CtClass interfaze = cp.getOrNull(interfaceName);
		if (interfaze == null) {
			interfaze = cp.makeInterface(interfaceName);
			for (final Field field : getFields(clazz)) {
				if (!hasGetter(field)) {
					createGetter(field, interfaze);
				}
				if (!hasSetter(field) && !Modifier.isFinal(field.getModifiers())) {
					createSetter(field, interfaze);
				}
			}
			return interfaze.toClass(DataClassInterfaceGenerator.class.getClassLoader(), DataClassInterfaceGenerator.class.getProtectionDomain());
		}
		return Class.forName(interfaceName);
	}

	private static void createGetter(final Field field, final CtClass interfaze) throws CannotCompileException {
		final String fieldName = field.getName();
		final String type = field.getType().getName();
		interfaze.addMethod(CtMethod.make(String.format("public %s %s();", type, getGetterName(fieldName)), interfaze));
	}

	private static void createSetter(final Field field, final CtClass interfaze) throws CannotCompileException {
		final String fieldName = field.getName();
		final String type = field.getType().getName();
		interfaze.addMethod(CtMethod.make(String.format("public void %s(%s %s);", getSetterName(fieldName), type, fieldName), interfaze));
	}

	private static Set<Field> getFields(Class<?> clazz) {
		final Set<Field> fields = new HashSet<>();
		while (clazz != null) {
			Arrays.stream(clazz.getDeclaredFields()).filter(field -> !Modifier.isStatic(field.getModifiers())).forEach(fields::add);
			clazz = clazz.getSuperclass();
		}
		return fields;
	}

	private static boolean hasGetter(final Field field) {
		return findMethod(field.getDeclaringClass(), getGetterName(field.getName())).isPresent();
	}

	private static boolean hasSetter(final Field field) {
		return findMethod(field.getDeclaringClass(), getSetterName(field.getName())).isPresent();
	}

	private static Optional<Method> findMethod(final Class<?> clazz, final String methodName) {
		return Stream.of(clazz.getMethods()).filter(method -> method.getName().equals(methodName)).findFirst();
	}

	private static String getGetterName(final String propertyName) {
		return "get" + StringUtils.capitalize(propertyName);
	}

	private static String getSetterName(final String propertyName) {
		return "set" + StringUtils.capitalize(propertyName);
	}
}
