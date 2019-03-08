package com.endava.AnimeExplorer.Repositories;

import com.endava.AnimeExplorer.Model.ListingManager.AnimeEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface ListRepository extends CrudRepository<AnimeEntry, Integer> {


     Optional<AnimeEntry> findByUserIdAndAnimeId(int userId,int animeId);

     List<AnimeEntry> findByUserId(int userId);


}
