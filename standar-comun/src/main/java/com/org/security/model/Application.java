package com.org.security.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.org.util.domain.BaseModelEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@SequenceGenerator(name = "SEQ_APPLICATION", sequenceName = "SEQ_APPLICATION", allocationSize = 1)
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "id", "description" })
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Application implements BaseModelEntity<Long> {

	private static final long serialVersionUID = -5821639334643246483L;

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_APPLICATION")
	@Column(nullable = false)
	private Long id;

	@Column(length = 255, nullable = false)
	private String description;

	@OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
	public Set<UserApplications> userApplications;

}
