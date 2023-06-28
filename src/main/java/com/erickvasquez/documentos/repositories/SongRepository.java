package com.erickvasquez.documentos.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

import com.erickvasquez.documentos.models.entities.Song;

public interface SongRepository extends JpaRepository<Song, UUID>{

Song findOneByTitle(String title);
	
	List<Song> findByTitleContaining (String title);
	Page<Song> findByTitleContaining (String title, Pageable pageable );
	
}
