package com.cab.sn.entities;

import java.util.Set;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idUtilisateur;
	@NotBlank(message="Renseigner le nom")
	private String nomUtilisateur;
	@NotBlank(message="Renseigner le prénom")
	private String prenomUtilisateur;
	@Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Email non valide")
	private String email;
	private String password;
	@ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Profil> profil;
	
	public Utilisateur(String nomUtilisateur, String prenomUtilisateur, String email, String password) {
		super();
		this.nomUtilisateur = nomUtilisateur;
		this.prenomUtilisateur = prenomUtilisateur;
		this.email = email;
		this.password = password;
	}

	public Utilisateur(@NotBlank(message = "Renseigner le nom") String nomUtilisateur,
			@NotBlank(message = "Renseigner le prénom") String prenomUtilisateur,
			@Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Email non valide") String email,
			String password, Set<Profil> profil) {
		super();
		this.nomUtilisateur = nomUtilisateur;
		this.prenomUtilisateur = prenomUtilisateur;
		this.email = email;
		this.password = password;
		this.profil = profil;
	}

	
}
