package com.endava.AnimeExplorer.Repositories;

import com.endava.AnimeExplorer.Model.AnimeManager.AnimeFile;
import org.springframework.data.repository.CrudRepository;

public interface AnimeRepository extends CrudRepository<AnimeFile,Integer>{


}
