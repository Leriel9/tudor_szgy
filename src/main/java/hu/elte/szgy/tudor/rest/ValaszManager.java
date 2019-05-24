package hu.elte.szgy.tudor.rest;

import hu.elte.szgy.tudor.data.Valasz;
import hu.elte.szgy.tudor.data.ValaszRepository;
import hu.elte.szgy.tudor.data.TudorRepository;

import java.security.Principal;
import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("valasz")
@Transactional
public class ValaszManager {
	//private static Logger log = LoggerFactory.getLogger(ValaszManager.class);

	@Autowired
	private ValaszRepository valaszRepo;
	@Autowired
	private TudorRepository tudorRepo;
		
	@GetMapping("/{valaszId}")
    public ResponseEntity<Valasz> findValasz(@PathVariable("valaszId") Integer valaszId,
		Principal principal, Authentication auth) {
		Valasz v = valaszRepo.findById(valaszId).get();
		//if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
		//	throw new AccessDeniedException("Denied access");
		//}
		return new ResponseEntity<Valasz>(v, HttpStatus.OK);
    }

	@GetMapping("/tudor/{tudorId}")
    public ResponseEntity<List<Valasz>> listValaszok(@PathVariable("tudorId") Integer tudorId, Authentication auth) {
		//if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_UGYFEL"))) {
		//	throw new AccessDeniedException("Denied access");
		//}
		return new ResponseEntity<List<Valasz>>(valaszRepo.findValaszByTudor(tudorRepo.getOne(tudorId)),HttpStatus.OK);
	} 

	@PostMapping("/new")
	public ResponseEntity<Void> createValasz(@RequestBody Valasz v, UriComponentsBuilder builder) {
        boolean flag = true; 
		valaszRepo.save(v);
        if (flag == false) {
	       return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/{valaszId}").buildAndExpand(v.getValaszId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@PostMapping("/ertekeles/{valaszId}")
	public ResponseEntity<Void> rateValasz(@PathVariable("valaszId") Integer valaszId, @RequestBody Valasz.ValaszErtekeles ertekeles ) {
		Valasz v = valaszRepo.getOne(valaszId);
		v.setErtekeles(ertekeles);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	    
	}

	@PostMapping("/delete/{vid}/")
	public ResponseEntity<Void> deleteValasz(@PathVariable("valaszId") Integer valaszId ) { 
	    Valasz v = valaszRepo.getOne(valaszId);
	    valaszRepo.delete(v);
	    return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
}
