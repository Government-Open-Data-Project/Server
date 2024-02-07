package com.springboot.government_data_project.repository;

import com.springboot.government_data_project.domain.News;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NewsRepository extends JpaRepository<News, Long> {
    boolean existsNewsByUrl(String url);
    List<News> findAllByOrderByTwentiesViewsDesc();

    List<News> findAllByOrderByThirtiesViewsDesc();

    List<News> findAllByOrderByFortiesViewsDesc();

    List<News> findAllByOrderByFiftiesViewsDesc();

    List<News> findAllByOrderBySixtiesViewsDesc();

    List<News> findAllByOrderBySeventiesViewsDesc();

    List<News> findTop25ByOrderByRegDateDesc();


    @Modifying
    @Query("UPDATE News m SET m.twentiesViews = m.twentiesViews + 1 WHERE m.url = :url")
    void incrementTwentiesViewByUrl(@Param("url") String url);

    @Modifying
    @Query("UPDATE News m SET m.thirtiesViews = m.thirtiesViews + 1 WHERE m.url = :url")
    void incrementThirtiesViewByUrl(@Param("url") String url);

    @Modifying
    @Query("UPDATE News m SET m.fortiesViews = m.fortiesViews + 1 WHERE m.url = :url")
    void incrementFortiesViewByUrl(@Param("url") String url);

    @Modifying
    @Query("UPDATE News m SET m.fiftiesViews = m.fiftiesViews + 1 WHERE m.url = :url")
    void incrementFiftiesViewByUrl(@Param("url") String url);

    @Modifying
    @Query("UPDATE News m SET m.sixtiesViews = m.sixtiesViews + 1 WHERE m.url = :url")
    void incrementSixtiesViewByUrl(@Param("url") String url);

    @Modifying
    @Query("UPDATE News m SET m.seventiesViews = m.seventiesViews + 1 WHERE m.url = :url")
    void incrementSeventiesViewByUrl(@Param("url") String url);





    List<News> findTop25ByCompMainTitleContainingOrCompContentContainingOrderByRegDateDesc(String titleRegion, String contentRegion );


}
