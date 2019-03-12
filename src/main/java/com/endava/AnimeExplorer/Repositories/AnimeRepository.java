package com.endava.AnimeExplorer.Repositories;

import com.endava.AnimeExplorer.Model.AnimeManager.AnimeFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AnimeRepository extends CrudRepository<AnimeFile,Integer>, PagingAndSortingRepository<AnimeFile,Integer> {

    @Override
    public List<AnimeFile> findAll();

    public Page<AnimeFile> findByEntryId(long id, Pageable pageable);
}
