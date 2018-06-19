package com.skat.smev.iasmkgu.repository;

import com.skat.smev.iasmkgu.domain.mkgu.views.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author daria.kulaga
 * @since 22.05.2018.
 */
public interface MkguFromFederalPacketsRepository extends JpaRepository<MkguFromFederalPacketsView, Long> {
    @Query(value =
            " SELECT DISTINCT id_mkgu FROM MkguFromFederalPacketsView ")
    List<String> findAllIdMkgu();

    @Query(value =
            " SELECT new com.skat.smev.iasmkgu.domain.mkgu.views.MkguIds(view.id_key, view.id_otdel, COUNT(id_key), view.id_mkgu) FROM MkguFromFederalPacketsView view " +
                    "WHERE view.id_mkgu= :id_mkgu " +
                    "GROUP BY id_key, id_otdel, id_mkgu")
    List<MkguIds> findPacketsByIdMkguGroupByIdKey(@Param("id_mkgu") String id_mkgu, Pageable pageable);

    @Query(value =
            " SELECT DISTINCT new com.skat.smev.iasmkgu.domain.mkgu.views.MkguFormInfo(view.id_key, view.okato, view.id_service, view.name_service, view.id_authority, view.name_authority, view.date_event, view.user_id) FROM MkguFromFederalPacketsView view " +
                    "WHERE view.id_mkgu = :id_mkgu AND view.id_key = :id_key ")
    List<MkguFormInfo> findPacketsInfoByIdMkguAndIdKey(@Param("id_mkgu") String id_mkgu, @Param("id_key") int id_key);

    @Query(value =
            " SELECT new com.skat.smev.iasmkgu.domain.mkgu.views.BaseMkguPacketsView(view.id_key, view.id_otdel, view.id_mkgu, view.okato, view.indicator_id, view.id_service, view.name_service, view.id_authority, view.name_authority, view.value_id, view.date_event, view.user_id) FROM MkguFromFederalPacketsView view " +
                    "WHERE view.id_key = :id_key " +
                    "AND view.id_mkgu= :id_mkgu")
    List<BaseMkguPacketsView> findPackagesByIdKeyAndIdMkgu(@Param("id_key") int id_key, @Param("id_mkgu") String id_mkgu);
}
