package com.org.security.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.security.model.User;
import com.org.security.repository.UserRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class UserService extends BaseService<User, Long> {

	@Inject
	private UserRepository userRepository;
	

	@Override
	public BaseRepository<User, Long> getRepository() {
		return userRepository;
	}

}
