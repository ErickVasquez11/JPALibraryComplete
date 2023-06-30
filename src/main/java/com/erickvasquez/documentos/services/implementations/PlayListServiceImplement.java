package com.erickvasquez.documentos.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.erickvasquez.documentos.models.dtos.playlists.AllSongDTO;
import com.erickvasquez.documentos.models.dtos.playlists.SavePlayListDTO;
import com.erickvasquez.documentos.models.dtos.playlists.UpdatePlayListDTO;
import com.erickvasquez.documentos.models.entities.PlayList;
import com.erickvasquez.documentos.models.entities.User;
import com.erickvasquez.documentos.repositories.PlayListRepository;
import com.erickvasquez.documentos.services.PlaylistService;

@Service
public class PlayListServiceImplement implements PlaylistService {

	@Autowired
	private PlayListRepository playlistRepository;

	@Override
	public void save(SavePlayListDTO playlistInfo, User user) throws Exception {
		PlayList playlist = new PlayList(
				playlistInfo.getTitle(),
				playlistInfo.getDescription(),
				user
				);
				
		playlistRepository.save(playlist);
	}



	@Override
	public PlayList findOneById(String code) {
		try {
			UUID _code = UUID.fromString(code);
			return playlistRepository.findById(_code).orElse(null);
		} catch (Exception e) {
			return null;
		}
	}


		@Override
		public Page<PlayList> getAll(User user, int page, int size) {
			Pageable pageable = PageRequest.of(page, size);
			return playlistRepository.findAllByUser(user, pageable);
		}

		@Override
		public Page<PlayList> findByTitle(User user, String title, int page, int size) {
			Pageable pageable = PageRequest.of(page, size);
			return playlistRepository.findAllByUserAndTitleContaining(user, title, pageable);
		}
		
		@Override
		public Page<AllSongDTO> getPaginatedList(List<AllSongDTO> list, int page, int size) {
			int startIndex = page * size;
	        int endIndex = Math.min(startIndex + size, list.size());
	        
	        List<AllSongDTO> sublist = list.subList(startIndex, endIndex);
	        PageRequest pageRequest = PageRequest.of(page, size);
	        
	        return new PageImpl<>(sublist, pageRequest, list.size());
		}
}
