package com.hipsterheaven.music.services.repositories;

import com.hipsterheaven.music.resources.Album;
import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<Album, Long> {
}
