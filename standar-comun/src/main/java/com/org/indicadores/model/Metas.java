package com.org.indicadores.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.org.indicadores.enums.EvaluationLevel;
import com.org.util.domain.BaseModelEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table()
@SequenceGenerator(name = "SEQ_METAS", sequenceName = "SEQ_METAS", allocationSize = 1)
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "id" })
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Metas implements BaseModelEntity<Long> {

	private static final long serialVersionUID = -34242342874354L;

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_METAS")
	@Column(nullable = false)
	private Long id;

	@Column(precision = 10, scale = 2, nullable = false)
	private Double meta;

	@Column(precision = 10, scale = 2, nullable = true)
	private Double resultadoMeta;

	@Column(nullable = true)
	private Boolean alcanzada;

	@Column(length = 255, nullable = true)
	private String evaluationLevel;

	@Column(length = 255, nullable = false)
	private String ordinalDate;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "INDICADOR_ID", referencedColumnName = "ID", nullable = false) })
	@NotFound(action = NotFoundAction.IGNORE)
	private Indicador indicador;

	// Relacion por enum

	public EvaluationLevel getEvaluationLevel() {
		return EvaluationLevel.getEvaluationLevel(this.evaluationLevel);
	}

	public void setEvaluationLevel(EvaluationLevel evaluation) {
		this.evaluationLevel = evaluation != null ? evaluation.getCode() : null;
	}

}
