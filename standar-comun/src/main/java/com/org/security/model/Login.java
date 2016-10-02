package com.org.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@SequenceGenerator(name = "SEQ_LOGIN" ,sequenceName = "SEQ_LOGIN", allocationSize = 1)
@EqualsAndHashCode(of = { "id" })
@ToString(of = {"id"})
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Login implements BaseModelEntity<Long>{

	private static final long serialVersionUID = 8278212411957351223L;

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LOGIN")
	@Column(nullable = false)
	private Long id;

	@Column(length = 255, nullable = false)
	private String userName;

	@Column(length = 255, nullable = false)
	private String password;

}
