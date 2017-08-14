package com.siapri.broker.business.service.impl;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.xml.bind.DatatypeConverter;

public final class PasswordUtils {
	
	private static final String DIGEST_ALGORITHM = "SHA-512";
	private static final String DIGEST_ALGORITHM_PROVIDER = "SUN";
	
	public static String hash(final char[] password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(DIGEST_ALGORITHM, DIGEST_ALGORITHM_PROVIDER);
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			throw new RuntimeException(e);
		}
		final ByteBuffer passwordBytes = Charset.forName("UTF-8").encode(CharBuffer.wrap(password));
		md.update(passwordBytes);
		return DatatypeConverter.printBase64Binary(md.digest());
	}
}
