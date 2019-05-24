package hu.elte.szgy.tudor.rest;

import hu.elte.szgy.tudor.data.User;
import hu.elte.szgy.tudor.data.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public class TudorUserService  implements UserDetailsService {
		
	private static final Logger logger = LoggerFactory.getLogger( TudorUserService.class );
	
    @Autowired
    private UserRepository userRepository;
 
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
    	logger.info("Authenticating " + username);
        User user = userRepository.findById(username).get();
    	logger.info("User data " + user.getPassword() + " " + user.getType());
        return new TudorUserPrincipal(user);
    }
}
