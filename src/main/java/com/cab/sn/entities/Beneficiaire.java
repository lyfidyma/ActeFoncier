package com.cab.sn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter


@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name="TYPE_BENEFICIAIRE", discriminatorType = DiscriminatorType.STRING, length = 10)
public class Beneficiaire {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idBeneficiaire;
	
}
