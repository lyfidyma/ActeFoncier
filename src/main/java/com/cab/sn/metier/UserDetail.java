package com.cab.sn.metier;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cab.sn.dao.UtilisateurRepository;
import com.cab.sn.entities.Utilisateur;

@Service
public class UserDetail implements UserDetailsService {
	
	@Autowired
	UtilisateurRepository utilisateurReporitory;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Utilisateur> utilisateur = utilisateurReporitory.findByEmail(email);
        if(utilisateur==null){
               throw new UsernameNotFoundException("Email non trouvé");
             }
      Set<GrantedAuthority> authorities = utilisateur.get().getProfil().stream()
              .map((role) -> new SimpleGrantedAuthority(role.getNomProfil()))
              .collect(Collectors.toSet());
      return new org.springframework.security.core.userdetails.User(email, utilisateur.get().getPassword(),authorities);
  }

}
