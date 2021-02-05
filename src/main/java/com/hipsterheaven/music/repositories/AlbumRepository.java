package com.hipsterheaven.music.repositories;

import com.hipsterheaven.music.resources.Album;
import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<Album, Long> {
}
