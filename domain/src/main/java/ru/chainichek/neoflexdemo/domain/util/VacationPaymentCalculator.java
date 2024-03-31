package ru.chainichek.neoflexdemo.domain.util;

public interface VacationPaymentCalculator {
    /** <h2>Подсчитывает сумму отпускных</h2>
     * @param averageSalaryPerYear - средняя заработная плата за год
     * @param vacationDays - количество отпускных дней
     * @return сумма отпускных
     */
    double calculate(double averageSalaryPerYear, int vacationDays);
}
