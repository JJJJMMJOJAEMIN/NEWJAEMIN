

package com.example.login.repository;

import com.example.login.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c join fetch c.children where c.idx = :parentId")
    Optional<Category> findParentAndChildByParentId(@Param("parentId") Long parentId);

}



