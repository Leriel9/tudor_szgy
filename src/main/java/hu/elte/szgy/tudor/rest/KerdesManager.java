package hu.elte.szgy.tudor.rest;

import hu.elte.szgy.tudor.data.Kategoria;
import hu.elte.szgy.tudor.data.ValaszRepository;
import hu.elte.szgy.tudor.data.Kerdes;
import hu.elte.szgy.tudor.data.KerdesRepository;
import hu.elte.szgy.tudor.data.SzakteruletRepository;
import java.security.Principal;
import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("kerdes")
@Transactional
public class KerdesManager {
	//private static Logger log = LoggerFactory.getLogger(KerdesManager.class);

	@Autowired
	private KerdesRepository kerdesRepo;
	@Autowired
	private SzakteruletRepository szakteruletRepo;
	@Autowired
	private ValaszRepository valaszRepo;
	
	@GetMapping("/{kerdesId}")
    public ResponseEntity<Kerdes> findKerdes(@PathVariable("kerdesId") Integer kerdesId, 
		Principal principal, Authentication auth) {
		Kerdes k = kerdesRepo.findById(kerdesId).get();
		//if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
		//	throw new AccessDeniedException("Denied access");
		//}
		return new ResponseEntity<Kerdes>(k, HttpStatus.OK);
    }
	
	@GetMapping("/szakterulet/{szakId}")
    public ResponseEntity<List<Kerdes>> findKerdesBySzakterulet(@PathVariable("szakId") Integer szakId, 
		Principal principal, Authentication auth) {
		//if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TUDOR"))) {
		//	throw new AccessDeniedException("Denied access");
		//}
		return new ResponseEntity<List<Kerdes>>(kerdesRepo.findKerdesByKategoria(szakteruletRepo.getOne(szakId).getKategoria()),HttpStatus.OK);
    }
	
	@GetMapping("/bongeszes")
    public ResponseEntity<List<Kerdes>> findKerdesBongeszes(@RequestBody String szoveg, @RequestBody Kategoria temakor, 
		Principal principal, Authentication auth) {
		//if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_UGYFEL"))) {
		//	throw new AccessDeniedException("Denied access");
		//}
		return new ResponseEntity<List<Kerdes>>(kerdesRepo.bongeszes(szoveg, temakor), HttpStatus.OK);
    }
	
	@GetMapping("/all")
    public ResponseEntity<List<Kerdes>> findAllKerdes(Principal principal, Authentication auth) {
		if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			throw new AccessDeniedException("Denied access");
		}
		return new ResponseEntity<List<Kerdes>>(kerdesRepo.findAll(), HttpStatus.OK);
    }

	@PostMapping("/new")
	public ResponseEntity<Void> createKerdes(@RequestBody Kerdes k, UriComponentsBuilder builder) {
        boolean flag = true; 
		kerdesRepo.save(k);
        if (flag == false) {
	       return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/{kerdesId}").buildAndExpand(k.getKerdesId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PostMapping("/delete/{kerdesId}/")
	public ResponseEntity<Void> deleteKerdes(@PathVariable("kerdesId") Integer kerdesId ) { 
	    Kerdes k = kerdesRepo.getOne(kerdesId);
	    valaszRepo.delete(valaszRepo.findValaszByKerdes(k));
	    kerdesRepo.delete(k);
	    return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/masolat/{kerdesId}")
	public ResponseEntity<Void> setKerdesAsMasolat(@PathVariable("kerdesId") Integer kerdesId, @RequestBody Boolean masolat ) {
		Kerdes k = kerdesRepo.getOne(kerdesId);
		k.setMasolat(masolat);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	    
	}

}

