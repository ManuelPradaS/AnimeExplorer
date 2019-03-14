package com.endava.AnimeExplorer.repositories;

import com.endava.AnimeExplorer.model.listManager.AnimeEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ListRepository extends CrudRepository<AnimeEntry, Integer> {

    Optional<AnimeEntry> findByUserIdAndAnimeId(int userId, int animeId);

    List<AnimeEntry> findByUserId(int userId);

}
