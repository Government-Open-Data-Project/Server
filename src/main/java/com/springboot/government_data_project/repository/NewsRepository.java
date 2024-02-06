package com.springboot.government_data_project.repository;

import com.springboot.government_data_project.domain.News;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
    boolean existsNewsByUrl(String url);
    List<News> findTop25ByTwentiesViews(int ageRange);

    List<News> findTop25ByThirtiesViews(int ageRange);

    List<News> findTop25ByFiftiesViews(int ageRange);

    List<News> findTop25ByFortiesViews(int ageRange);

    List<News> findTop25BySixtiesViews(int ageRange);

    List<News> findAllByOrderByTwentiesViewsDesc();

    List<News> findAllByOrderByThirtiesViewsDesc();

    List<News> findAllByOrderByFortiesViewsDesc();

    List<News> findAllByOrderByFiftiesViewsDesc();

    List<News> findAllByOrderBySixtiesViewsDesc();
}
