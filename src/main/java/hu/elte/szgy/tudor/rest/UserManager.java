package hu.elte.szgy.tudor.rest;

import hu.elte.szgy.tudor.data.Ugyfel;
import hu.elte.szgy.tudor.data.UgyfelRepository;
import hu.elte.szgy.tudor.data.User;
import hu.elte.szgy.tudor.data.UserRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@Transactional
public class UserManager {
    private static Logger log = LoggerFactory.getLogger(UserManager.class);

    @Autowired
    private UserRepository userDao;
    @Autowired
    private UgyfelRepository ugyfelRepo;

    private String printUser(User u) {
	return "{ \"username\":\"" + u.getUsername() + "\", " + "\"type\":\"" + u.getType().name() + "\", "
		+ "\"id\":\"" + u.getUserId() + "\"}";
    }

    @GetMapping("/self")
    public String selfUser(Authentication a) {
		User u = userDao.getOne(a.getName());
		return printUser(u);
    }

    @SuppressWarnings("unlikely-arg-type")
	@GetMapping("/{userid}")
    public String otherUser(@PathVariable("userid") String username, Authentication a) {
		User u = userDao.getOne(username);
		if (!a.getAuthorities().contains("ROLE_ADMIN")) {
		    throw new AccessDeniedException("No access to User: " + username);
		}
		return printUser(u);
    }

    @PostMapping("/password")
    public ResponseEntity<Void> setSelfpassword(@RequestBody PassDTO pass, Authentication a) {
    	return setPassword(a.getName(), pass, a);
    }

    @SuppressWarnings("unlikely-arg-type")
	@PostMapping("/password/{userId}")
    public ResponseEntity<Void> setPassword(@PathVariable("userid") String username, @RequestBody PassDTO pass,
	    Authentication a) {
		User u = userDao.getOne(username);
		if (!username.equals(a.getName()) && !a.getAuthorities().contains("ROLE_ADMIN")) {
		    throw new AccessDeniedException("Invalid access to password");
		}
		u.setPassword("{noop}" + pass.getNew_pass());
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/new")
    // public ResponseEntity<Void> createUser(@RequestBody(required=false) User u,
    // UriComponentsBuilder builder) {
    public ResponseEntity<Void> createUser(@RequestBody(required = false) User u, Authentication a) {
		boolean admin = a.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		log.info("CREATING NEW USER BY ADMIN");
		if (!admin) {
		    throw new AccessDeniedException("Not authorized!");
		}
		if (!u.getPassword().startsWith("{"))
		    u.setPassword("{noop}" + u.getPassword());
		if (userDao.existsById(u.getUsername())) {
		    throw new EntityExistsException("Name already used");
		}
		userDao.save(u);
		log.info("Creating user: " + u.getUserId());
		return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @SuppressWarnings("unlikely-arg-type")
	@PostMapping("/delete/{userid}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userid") String username, Authentication a) {
		User u = userDao.getOne(username);
		if (!a.getAuthorities().contains("ROLE_ADMIN")) {
		    throw new AccessDeniedException("Not authorized to delete");
		}
		userDao.delete(u);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/dispatch")
    public ResponseEntity<Void> dispatchUser() {
    	// log.debug("Into URI: " + rr.getURI().toString() );
		SecurityContext cc = SecurityContextHolder.getContext();
		HttpHeaders headers = new HttpHeaders();
		if (cc.getAuthentication() != null) {
		    Authentication a = cc.getAuthentication();
		    try {
			headers.setLocation(
				new URI("/" + userDao.getOne(a.getName()).getType().toString().toLowerCase() + "_home.html"));
		    } catch (URISyntaxException e) {
			log.warn("Dispatcher cannot redirect");
		    }
		}
	
		return new ResponseEntity<Void>(headers, HttpStatus.FOUND);
    }

	@GetMapping("/ugyfel/{ugyfelId}")
    public ResponseEntity<Ugyfel> findKerdes(@PathVariable("ugyfelId") Integer ugyfelId, 
		Principal principal, Authentication auth) {
		Ugyfel u = ugyfelRepo.findById(ugyfelId).get();
		//if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
		//	throw new AccessDeniedException("Denied access");
		//}
		return new ResponseEntity<Ugyfel>(u, HttpStatus.OK);
    }

	@GetMapping("/ugyfel/all")
    public ResponseEntity<List<Ugyfel>> findKerdesBongeszes(Principal principal, Authentication auth) {
		//if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
		//	throw new AccessDeniedException("Denied access");
		//}
		return new ResponseEntity<List<Ugyfel>>(ugyfelRepo.findAll(), HttpStatus.OK);
    }

}
