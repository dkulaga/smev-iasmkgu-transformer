package com.skat.smev.iasmkgu.schedulers;


import com.skat.smev.iasmkgu.controllers.MkguFromPackageController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@ConditionalOnProperty(
        prefix = "mkgu",
        name = "scheduling.enable",
        havingValue = "true",
        matchIfMissing = true)

public class MkguScheduler {

    private static final Logger LOGGER = Logger.getLogger(MkguScheduler.class.getName());

    @Autowired
    private MkguFromPackageController mkguFromPackageController;


    public MkguScheduler(MkguFromPackageController mkguFromPackageController) {
        this.mkguFromPackageController = mkguFromPackageController;
    }

    /**
     * Метод для запланированного по cron получения оценок из БД АИС
     *
     * Выбирает из БД оценки, преобразует их в запрос к адаптеру,
     * отправляет запрос
     */
    @Scheduled(cron = "${mkgu.scheduling.rates}")
    public void fetchNewMkguRates() throws Exception {
        Pageable pageable = new PageRequest(0, 20, Sort.Direction.fromString("asc"), "id");
        LOGGER.info("Send rates scheduler started from mkgu_from_packets view");
        mkguFromPackageController.preparePacketsAndSend();

        LOGGER.info("Send rates scheduler started from mkgu_from_federal_packets view");
        mkguFromPackageController.prepareFederalPacketsAndSend();

        LOGGER.info("Send rates scheduler started from mkgu_from_history view");
        mkguFromPackageController.prepareFederalPacketsAndSend();
    }
}
