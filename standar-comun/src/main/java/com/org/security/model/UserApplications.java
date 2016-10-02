package com.org.security.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table()
@SequenceGenerator(name = "SEQ_USER_APP", sequenceName = "SEQ_USER_APP", allocationSize = 1)
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "id"})
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class UserApplications {

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_APP")
	@Column(nullable = false)
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumns({ @JoinColumn(name = "APPLICATION_ID", referencedColumnName = "ID", nullable = false) })
	private Application application;
	
	@ManyToOne(optional = false)
	@JoinColumns({ @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false) })
	private User user;
	
	@Embedded
	private Period period;

}
