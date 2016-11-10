package com.org.adoption.model;

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

import com.org.core.model.enums.ProcessStatus;
import com.org.security.identity.model.UserTypeEntity;
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
@SequenceGenerator(name = "SEQ_ADOPTED_PETS", sequenceName = "SEQ_ADOPTED_PETS", allocationSize = 1)
@EqualsAndHashCode(of = { "id" })
@ToString
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class AdoptedPets implements BaseModelEntity<Long>{


	private static final long serialVersionUID = 2432342365756765L;

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADOPTED_PETS")
	@Column(nullable = false)
	Long id;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "PET_ID", referencedColumnName = "ID", nullable = false) })
	@NotFound(action = NotFoundAction.IGNORE)
	private Pet pet;
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "POSTULANT_ID", referencedColumnName = "ID", nullable = true) })
	@NotFound(action = NotFoundAction.IGNORE)
	private Postulant postulant;
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = true) })
	@NotFound(action = NotFoundAction.IGNORE)
	private UserTypeEntity userTypeEntity;
	
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaEnProceso;
	
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaAprobacion;
	
	@Column(length = 255, nullable = false)
	private String processStatus;

	public ProcessStatus getProcessStatus(){
		return ProcessStatus.getProcessStatus(processStatus);
	}
	
	public void setProcessStatus(ProcessStatus status){
		this.processStatus = status != null ? status.getCode() : null;
	}
}
