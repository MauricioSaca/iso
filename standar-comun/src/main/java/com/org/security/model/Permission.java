package com.org.security.model;

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
@SequenceGenerator(name = "SEQ_PERMISSION", sequenceName = "SEQ_PERMISSION", allocationSize = 1)
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "id", "code", "description" })
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Permission implements BaseModelEntity<Long>{

	
	private static final long serialVersionUID = -2481161615957960426L;

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERMISSION")
	@Column(nullable = false)
	private Long id;

	@Column(length = 255, nullable = false)
	private String code;

	@Column(length = 255, nullable = false)
	private String description;
	
	@ManyToOne
	@JoinColumns({@JoinColumn(name = "ROL_ID", referencedColumnName = "ID")})
	@NotFound(action = NotFoundAction.IGNORE)
	private Role role;

}
