package com.org.security.model;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name = "USUARIO")
@SequenceGenerator(name = "SEQ_USER", sequenceName = "SEQ_USER", allocationSize = 1)
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "id", "name", "lastName", "email" })
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class User implements BaseModelEntity<Long> {

	private static final long serialVersionUID = 7348359270075592247L;

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER")
	@Column(nullable = false)
	private Long id;

	@Column(length = 255, nullable = false)
	private String name;

	@Column(length = 255, nullable = false)
	private String lastName;

	@Column(length = 255, nullable = false)
	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date lastAccess;

	@Column(nullable = true)
	private Boolean isEnabled;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "LOGIN_ID", referencedColumnName = "ID") })
	@NotFound(action = NotFoundAction.IGNORE)
	private Login login;

	@ManyToOne
	@JoinColumns({@JoinColumn(name = "ROL_ID", referencedColumnName = "ID")})
	@NotFound(action = NotFoundAction.IGNORE)
	private Role role;

}
