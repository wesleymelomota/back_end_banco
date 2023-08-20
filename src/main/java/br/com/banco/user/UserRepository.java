package br.com.banco.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usuario, Long>{
	Usuario findByusername(String username);
	Boolean existsByusername(String username);
	Boolean existsByid(Long id);
	Usuario findByid(Long id);
}
