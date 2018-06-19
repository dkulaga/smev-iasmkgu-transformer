package com.skat.smev.iasmkgu.services;

import com.skat.smev.iasmkgu.domain.mkgu.views.BaseMkguPacketsView;
import com.skat.smev.iasmkgu.domain.mkgu.views.MkguFormInfo;
import com.skat.smev.iasmkgu.domain.mkgu.views.MkguFromPacketsView;
import com.skat.smev.iasmkgu.domain.mkgu.views.MkguIds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author daria.kulaga
 * @since 19.06.2018.
 */
public interface MkguBasePackageService {

    List<BaseMkguPacketsView> findPackagesByIdKeyAndMkguId(int id_key, String id_mkgu );

    List<String> findAllMkguIdsFromPackets();

    List<MkguIds> findPacketsByIdMkguGroupByIdKey(String id_mkgu, Pageable pageable);

    List<MkguFormInfo> findPacketsByIdMkguAndIdKey(String id_mkgu, int id_key);
}
