package com.siapri.broker.business.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.siapri.broker.business.security.User;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@CreatedDate
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	@Column(name = "CREATED_DATE")
	private ZonedDateTime createdDate;

	@CreatedBy
	@ManyToOne
	@JoinColumn(name = "CREATED_BY_USER", referencedColumnName = "ID")
	private User createdBy;

	@LastModifiedDate
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	@Column(name = "LAST_MODIFIED_DATE")
	private ZonedDateTime lastModifiedDate;

	@LastModifiedBy
	@ManyToOne
	@JoinColumn(name = "MODIFIED_BY_USER", referencedColumnName = "ID")
	private User lastModifiedBy;
	
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public ZonedDateTime getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(final ZonedDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public User getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(final User createdBy) {
		this.createdBy = createdBy;
	}
	
	public ZonedDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	public void setLastModifiedDate(final ZonedDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	public User getLastModifiedBy() {
		return lastModifiedBy;
	}
	
	public void setLastModifiedBy(final User lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AbstractEntity other = (AbstractEntity) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}
