package com.springboot.government_data_project.repository;

import com.springboot.government_data_project.domain.News;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
    boolean existsNewsByUrl(String url);
    List<News> findTop25ByTwentiesViewsOOrderBy(String ageRange);

    List<News> findTop25ByThirtiesViews(String ageRange);

    List<News> findTop25ByFiftiesViews(String ageRange);

    List<News> findTop25ByFortiesViews(String ageRange);

    List<News> findTop25BySixtiesViews(String ageRange);

}
