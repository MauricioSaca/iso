package com.org.indicadores.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.org.indicadores.enums.EvaluationPeriod;
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
@SequenceGenerator(name = "SEQ_INDICADOR", sequenceName = "SEQ_INDICADOR", allocationSize = 1)
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "id" })
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Indicador implements BaseModelEntity<Long> {

	private static final long serialVersionUID = -42345645234L;

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INDICADOR")
	@Column(nullable = false)
	private Long id;

	@Column(length = 255, nullable = false)
	private String name;

	@Column(length = 255, nullable = false)
	private String description;

	@Column(length = 255, nullable = false)
	private String objective;

	@Column(length = 255, nullable = false)
	private String meaning;
	
	@Column(length = 255, nullable = false)
	private String evaluationPeriod;

	@OneToMany(mappedBy = "indicador", cascade = CascadeType.ALL)
	private Set<Metas> metas;

	// Relacion por enum

	public EvaluationPeriod getEvaluarionPeriod() {
		return EvaluationPeriod.getEvaluationPeriod(this.evaluationPeriod);
	}

	public void setEvaluarionPeriod(EvaluationPeriod evaluation) {
		this.evaluationPeriod = evaluation != null ? evaluation.getCode() : null;
	}

}
