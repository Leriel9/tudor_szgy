package hu.elte.szgy.tudor.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ValaszRepository extends JpaRepository<Valasz, Integer>  {
	List<Valasz> findValaszByTudor(Tudor tudor);
	Valasz findValaszByKerdes(Kerdes kerdes);
}
