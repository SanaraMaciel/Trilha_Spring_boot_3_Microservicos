package br.com.alura.biblioteca.repository;

import br.com.alura.biblioteca.model.Livro;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    //locks otimistas
    //@Lock(LockModeType.OPTIMISTIC)
    //@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    //@Lock(LockModeType.WRITE)
    //@Lock(LockModeType.READ)

    //locks pessimistas
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    //@Lock(LockModeType.PESSIMISTIC_READ)
    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    Optional<Livro> findById(Long id);

}
