package com.erickvasquez.documentos.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.erickvasquez.documentos.models.dtos.playlists.AllSongDTO;
import com.erickvasquez.documentos.models.dtos.playlists.SavePlayListDTO;
import com.erickvasquez.documentos.models.dtos.playlists.UpdatePlayListDTO;
import com.erickvasquez.documentos.models.entities.PlayList;
import com.erickvasquez.documentos.models.entities.User;

public interface PlaylistService {
	
	Page<PlayList>getAll(User user, int page, int size);
	
	Page<PlayList>findByTitle(User user, String title, int page, int size);
	
	Page<AllSongDTO> getPaginatedList(List<AllSongDTO> list, int page, int size);
	
	void save(SavePlayListDTO playlistInfo, User user) throws Exception;
	 PlayList findOneById(String code);

}
