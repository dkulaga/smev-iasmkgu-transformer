package com.skat.smev.iasmkgu.services.impl;

import com.skat.smev.iasmkgu.domain.mkgu.views.*;
import com.skat.smev.iasmkgu.repository.MkguFromHistoryPacketsRepository;
import com.skat.smev.iasmkgu.services.MkguBasePackageService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MkguFromHistoryPackageService implements MkguBasePackageService {

    private final MkguFromHistoryPacketsRepository mkguFromHistoryPacketsRepository;

    public MkguFromHistoryPackageService(MkguFromHistoryPacketsRepository mkguFromHistoryPacketsRepository) {
         this.mkguFromHistoryPacketsRepository = mkguFromHistoryPacketsRepository;
    }

    /**
     * Метод для выбора пакетов по id_key и id отдела из mkgu_from_package
     * @return список найденный пакетов
     */
    public List<BaseMkguPacketsView> findPackagesByIdKeyAndMkguId(int id_key, String id_mkgu ){
        return mkguFromHistoryPacketsRepository.findPackagesByIdKeyAndIdMkgu(id_key, id_mkgu);
    }

    /**
     * Метод для поиска списка всех возможных уникальных mkgu_id для дальнейшего формирования в отдельные запросы
     * @return
     */
    public List<String> findAllMkguIdsFromPackets(){
        return mkguFromHistoryPacketsRepository.findAllIdMkgu();
    }

    /**
     * Метод для получения пакетов по mkgu_id
     * @param id_mkgu
     * @return
     */
    public List<MkguIds> findPacketsByIdMkguGroupByIdKey(String id_mkgu, Pageable pageable){
        return mkguFromHistoryPacketsRepository.findPacketsByIdMkguGroupByIdKey(id_mkgu, pageable);
    }

    /**
     * Метод для получения информации о пакетах по id_mkgu и id_key
     * @param id_mkgu
     * @return
     */
    public List<MkguFormInfo> findPacketsByIdMkguAndIdKey(String id_mkgu, int id_key){
        return mkguFromHistoryPacketsRepository.findPacketsInfoByIdMkguAndIdKey(id_mkgu, id_key);
    }
}
