package com.siapri.broker.business.dao;

import java.net.MalformedURLException;
import java.net.URL;

import org.hibernate.boot.archive.internal.StandardArchiveDescriptorFactory;
import org.hibernate.boot.archive.scan.internal.ScanResultCollector;
import org.hibernate.boot.archive.scan.spi.AbstractScannerImpl.ArchiveContextImpl;
import org.hibernate.boot.archive.scan.spi.ScanEnvironment;
import org.hibernate.boot.archive.scan.spi.ScanOptions;
import org.hibernate.boot.archive.scan.spi.ScanParameters;
import org.hibernate.boot.archive.scan.spi.ScanResult;
import org.hibernate.boot.archive.scan.spi.Scanner;
import org.hibernate.boot.archive.spi.ArchiveContext;
import org.hibernate.boot.archive.spi.ArchiveDescriptor;

public class ResourceScanner implements Scanner {
	
	@Override
	public ScanResult scan(final ScanEnvironment environment, final ScanOptions options, final ScanParameters params) {
		final ScanResultCollector resultCollector = new ScanResultCollector(environment, options, params);
		final StandardArchiveDescriptorFactory archiveDescriptorFactory = new StandardArchiveDescriptorFactory();
		final ArchiveDescriptor archiveDescriptor = archiveDescriptorFactory.buildArchiveDescriptor(getScanUrl());
		final ArchiveContext context = new ArchiveContextImpl(true, resultCollector);
		archiveDescriptor.visitArchive(context);
		return resultCollector.toScanResult();
	}

	private static URL getScanUrl() {
		try {
			return new URL(System.getProperty("scanUrl"));
		} catch (final MalformedURLException e) {
			return null;
		}
	}
}
