package com.cocktails.repository;

import com.cocktails.domain.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
