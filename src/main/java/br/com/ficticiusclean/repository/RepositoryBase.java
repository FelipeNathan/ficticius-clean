package br.com.ficticiusclean.repository;

import br.com.ficticiusclean.model.EntityBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryBase<TEntity extends EntityBase> extends JpaRepository<TEntity, Long> {
}
