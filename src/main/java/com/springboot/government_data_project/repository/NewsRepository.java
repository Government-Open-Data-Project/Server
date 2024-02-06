package com.springboot.government_data_project.repository;

import com.springboot.government_data_project.domain.News;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
    boolean existsNewsByUrl(String url);
    List<News> findAllByOrderByTwentiesViewsDesc();

    List<News> findAllByOrderByThirtiesViewsDesc();

    List<News> findAllByOrderByFortiesViewsDesc();

    List<News> findAllByOrderByFiftiesViewsDesc();

    List<News> findAllByOrderBySixtiesViewsDesc();

    List<News> findTop25ByOrderByRegDateDesc();

    List<News> findTop25ByCompMainTitleContainingOrCompContentContainingOrderByRegDateDesc(String titleRegion, String contentRegion );


}
