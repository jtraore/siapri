package com.siapri.broker.business.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@MappedSuperclass
public abstract class AbstractDocumentProvider extends AbstractEntity implements IDocumentProvider {
	
	private static final long serialVersionUID = 1L;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Document> documents = new ArrayList<>();
	
	@Override
	public List<Document> getDocuments() {
		return documents;
	}
	
	public void setDocuments(final List<Document> documents) {
		this.documents = documents;
	}
	
}
