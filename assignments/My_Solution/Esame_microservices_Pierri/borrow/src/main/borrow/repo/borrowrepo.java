package org.pierri.borrow.repos;

import org.pierri.borrow.model.Borrow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepository extends CrudRepository<Borrow, Long>{
}