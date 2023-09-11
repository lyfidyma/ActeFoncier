package com.cab.sn.entities;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name="TYPE_BENEFICIAIRE", discriminatorType = DiscriminatorType.STRING, length = 10)
public abstract class Beneficiaire {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idBeneficiaire;
	
}
