package com.project.eventsapp.business.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.eventsapp.dao.entities.User;
import com.project.eventsapp.dao.repository.UserRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServices implements UserDetailsService {

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> optUser = userRepository.findUserByEmail(email);

		org.springframework.security.core.userdetails.User springUser = null;

		if (optUser.isEmpty()) {
			throw new UsernameNotFoundException("User with email: " + email + " not found");
		}
		User user = optUser.get();
		List<String> roles = user.getRoles();
		Set<GrantedAuthority> ga = new HashSet<>();
		for (String role : roles) {
			ga.add(new SimpleGrantedAuthority(role));
		}

		springUser = new org.springframework.security.core.userdetails.User(
				email,
				user.getPassword(),
				ga);
		return springUser;
	}

	public User saveUser(User user) {
		if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
			throw new DataIntegrityViolationException(
					"User with this email '" + user.getEmail() + "' address already exists");
		}
		String passwd = user.getPassword();
		String encodedPasswod = passwordEncoder.encode(passwd);
		user.setPassword(encodedPasswod);
		return userRepository.save(user);
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public Page<User> getUsersPagination(Pageable pegeable) {
		if (pegeable == null) {
			return null;
		}
		return this.userRepository.findAll(pegeable);
	}

	public User getUserById(Integer id) {
		if (id == null) {
			return null;
		}
		return this.userRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
	}

	public User updateUser(Integer id, User user) {
		User existingUser = this.getUserById(id);
		existingUser.setEmail(user.getEmail());
		existingUser.setUsername(user.getUsername());
		return userRepository.save(existingUser);
	}

	public boolean deleteUser(Integer id) {
		userRepository.deleteById(id);
		return true;
	}

	public Long getNbuser() {
		return userRepository.count();
	}
}