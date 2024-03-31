package ru.chainichek.neoflexdemo.domain.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDate;
import java.util.List;

public interface ProductionCalendarGateway {
    /**<h2>Получение списка рабочих дней из вне</h2>
     * @param startsFrom - дата начала отпуска
     * @param endsOn - дата окончания отпуска
     * @return - список дат рабочих дней или null, если обработка таковых не закончилась успешно
     * @throws JsonProcessingException - ошибка обработки JSON из запроса
     */
    List<LocalDate> getAllWorkingDaysBetweenTwoDates(LocalDate startsFrom, LocalDate endsOn);
}
