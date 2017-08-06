package com.siapri.broker.business.service;

import com.siapri.broker.business.model.Broker;
import com.siapri.broker.business.service.exception.BusinessException;

public interface IBusinessService {
	Broker addBroker(Broker broker) throws BusinessException;

}
