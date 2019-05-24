package hu.elte.szgy.tudor.rest;

import hu.elte.szgy.tudor.data.Kategoria;
import hu.elte.szgy.tudor.data.KategoriaRepository;
import java.security.Principal;
import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kategoria")
@Transactional
public class KategoriaManager {
	//private static Logger log = LoggerFactory.getLogger(KerdesManager.class);

	@Autowired
	private KategoriaRepository kategoriaRepo;

	@GetMapping("/{katId}")
    public ResponseEntity<Kategoria> findKerdes(@PathVariable("katId") Integer katId, 
		Principal principal, Authentication auth) {
		Kategoria k = kategoriaRepo.findById(katId).get();
		//if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
		//	throw new AccessDeniedException("Denied access");
		//}
		return new ResponseEntity<Kategoria>(k, HttpStatus.OK);
    }

	@GetMapping("/all")
    public ResponseEntity<List<Kategoria>> findKerdesBongeszes(Principal principal, Authentication auth) {
		//if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
		//	throw new AccessDeniedException("Denied access");
		//}
		return new ResponseEntity<List<Kategoria>>(kategoriaRepo.findAll(), HttpStatus.OK);
    }
}
