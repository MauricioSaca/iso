package com.org.adoption.model;

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

import com.org.core.model.enums.TypeQuestion;
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
@SequenceGenerator(name = "SEQ_POSTULANT_QUESTIONS", sequenceName = "SEQ_POSTULANT_QUESTIONS", allocationSize = 1)
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "id" })
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class PostulantQuestions implements BaseModelEntity<Long> {

	private static final long serialVersionUID = -3123210988L;

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_POSTULANT_QUESTIONS")
	@Column(nullable = false)
	private Long id;

	@Column(length = 255, nullable = false)
	private String question;

	@Column(length = 255, nullable = false)
	private String typeQuestion;

	// Relacion por enum

	public TypeQuestion getTypeQuestion() {
		return TypeQuestion.getTypeQuestion(this.typeQuestion);
	}

	public void setTypeQuestion(TypeQuestion typeQuestion) {
		this.typeQuestion = typeQuestion != null ? typeQuestion.getCode() : null;
	}

}
