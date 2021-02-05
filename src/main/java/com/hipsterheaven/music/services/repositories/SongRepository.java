package com.hipsterheaven.music.services.repositories;

import com.hipsterheaven.music.resources.Song;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<Song, Long> {
}
