package com.cab.sn.entities;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PiecesJointes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPj;
	private String numPj;
	private String objetPj;
	@DateTimeFormat(pattern="dd/mm/yyyy")
	@Column(nullable=true)
	private LocalDate datePj;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="idDocument")
	private Documents documents;
	public PiecesJointes(String numPj, String objetPj, LocalDate datePj, Documents documents) {
		super();
		this.numPj = numPj;
		this.objetPj = objetPj;
		this.datePj = datePj;
		this.documents = documents;
	}
	
	
	
	
	
	
	
	
	
	

}
