package com.org.adoption.model;

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

import com.org.core.model.enums.PostulantStatus;
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
@SequenceGenerator(name = "SEQ_POSTULANT", sequenceName = "SEQ_POSTULANT", allocationSize = 1)
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "id", "names", "lastNames", "email" })
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Postulant implements BaseModelEntity<Long> {

	private static final long serialVersionUID = 63565756332L;

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_POSTULANT")
	@Column(nullable = false)
	private Long id;

	@Column(length = 10, nullable = false, unique = true)
	private String dui;

	@Column(length = 255, nullable = false)
	private String names;

	@Column(length = 255, nullable = false)
	private String lastNames;

	@Column(length = 255, nullable = false, unique = true)
	private String email;

	@Column(length = 255, nullable = false)
	private String cellPhone;

	@Column(length = 255, nullable = false)
	private String phone;

	@Column(length = 255, nullable = false)
	private String address;

	@Column(length = 255, nullable = false)
	private String postulantStatus;

	// Relacion por enum

	public PostulantStatus getPostulantStatus() {
		return PostulantStatus.getPetStatus(this.postulantStatus);
	}

	public void setPetOrigin(PostulantStatus status) {
		this.postulantStatus = status != null ? status.getCode() : null;
	}

	@OneToMany(mappedBy = "postulant" ,cascade = CascadeType.ALL)
	private Set<PostulantAnswerEvaluation> postulantAnswerEvaluation;
	
}
