package com.org.school.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.BehaviourMonitoring;
import com.org.school.model.QBehaviourMonitoring;
import com.org.school.model.StudentsPerCourse;
import com.org.school.repository.BehaviourMonitoringRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class BehaviourMonitoringService extends BaseService<BehaviourMonitoring, Long> {

	@Inject
	private BehaviourMonitoringRepository behaviourMonitoringRepository;

	private final static QBehaviourMonitoring qBehaviourMonitoring = QBehaviourMonitoring.behaviourMonitoring;

	@Override
	public BaseRepository<BehaviourMonitoring, Long> getRepository() {
		return behaviourMonitoringRepository;
	}

	public List<BehaviourMonitoring> findBehaviourMonitoringByStudent(StudentsPerCourse studentsPerCourse) {

		return newJpaQuery().from(qBehaviourMonitoring)
				.where(qBehaviourMonitoring.studentsPerCourse.id.eq(studentsPerCourse.getId()))
				.list(qBehaviourMonitoring);
	}

}
