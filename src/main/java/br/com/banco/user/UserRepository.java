package br.com.banco.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usuario, Integer>{
	Usuario findByusername(String username);
}
