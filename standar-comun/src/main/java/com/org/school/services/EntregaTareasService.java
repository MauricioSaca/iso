package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.mysema.query.types.expr.BooleanExpression;
import com.org.school.model.Assigments;
import com.org.school.model.EntregaTareas;
import com.org.school.model.QEntregaTareas;
import com.org.school.model.StudentsPerCourse;
import com.org.school.repository.EntregaTareasRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class EntregaTareasService extends BaseService<EntregaTareas, Long> {

	@Inject
	private EntregaTareasRepository entregaTareasRepository;

	private final static QEntregaTareas qEntregaTareas = QEntregaTareas.entregaTareas;

	@Override
	public BaseRepository<EntregaTareas, Long> getRepository() {
		return entregaTareasRepository;
	}

	public EntregaTareas findEntregaTareas(StudentsPerCourse studentsPerCourse, Assigments assigments) {
		BooleanExpression exp1 = qEntregaTareas.studentsPerCourse.id.eq(studentsPerCourse.getId());
		BooleanExpression exp2 = qEntregaTareas.assigments.id.eq(assigments.getId());

		return newJpaQuery().from(qEntregaTareas).where(exp1.and(exp2)).singleResult(qEntregaTareas);
	}

}
