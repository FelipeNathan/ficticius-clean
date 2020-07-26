package br.com.ficticiusclean.repository;

import br.com.ficticiusclean.model.EntityBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryBase<T extends EntityBase> extends JpaRepository<T, Long> {
}

