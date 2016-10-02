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
import javax.persistence.Table;

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
@SequenceGenerator(name = "SEQ_ROLE", sequenceName = "SEQ_ROLE", allocationSize = 1)
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "id", "code", "description" })
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Role implements BaseModelEntity<Long>{


	private static final long serialVersionUID = 7888520766568082023L;

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROLE")
	@Column(nullable = false)
	private Long id;

	@Column(length = 255, nullable = false)
	private String code;

	@Column(length = 255, nullable = false)
	private String description;

	@OneToMany(mappedBy = "role" ,cascade = CascadeType.ALL)
	private Set<Permission> permission;

}
