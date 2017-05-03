package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.BehaviourMonitoring;
import com.org.school.repository.BehaviourMonitoringRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class BehaviourMonitoringService extends BaseService<BehaviourMonitoring, Long> {

	@Inject
	private BehaviourMonitoringRepository behaviourMonitoringRepository;

	@Override
	public BaseRepository<BehaviourMonitoring, Long> getRepository() {
		return behaviourMonitoringRepository;
	}

}
