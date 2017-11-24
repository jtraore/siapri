package com.siapri.broker.business.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "INSURER")
@DiscriminatorValue("INSURER")
public class Insurer extends Company {
	
	private static final long serialVersionUID = 1L;
	
}
