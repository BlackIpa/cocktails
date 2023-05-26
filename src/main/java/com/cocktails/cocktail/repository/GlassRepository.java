package com.cocktails.cocktail.repository;

import com.cocktails.cocktail.model.Glass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlassRepository extends JpaRepository<Glass, Long> {

}
