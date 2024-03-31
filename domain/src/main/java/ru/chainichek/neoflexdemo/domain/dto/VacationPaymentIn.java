package ru.chainichek.neoflexdemo.domain.dto;

import java.time.LocalDate;

/** <h2>DTO информации для вычисления суммы отпускных</h2>
 * @param avgSalaryPerYear - средняя заработная плата за год
 * @param vacationDays - количество отпускных дней
 * @param startsFrom - дата начала отпуска
 */
public record VacationPaymentIn(double avgSalaryPerYear,
                                int vacationDays,
                                LocalDate startsFrom) {
}
