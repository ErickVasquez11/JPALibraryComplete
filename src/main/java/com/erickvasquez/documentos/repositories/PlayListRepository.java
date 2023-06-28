package com.erickvasquez.documentos.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

import com.erickvasquez.documentos.models.entities.PlayList;
import com.erickvasquez.documentos.models.entities.Song;
import com.erickvasquez.documentos.models.entities.User;

public interface PlayListRepository extends JpaRepository<PlayList, UUID> {

	Page<PlayList> findAllByUser(User user, Pageable pageable);
	Page<PlayList> findAllByUserAndTitleContaining(User user, String title, Pageable pageable);
}
