package ru.chainichek.neoflexdemo.domain.util;

import org.springframework.stereotype.Component;

/**
 * <h2>Калькулятор отпускных в Российской Федерации</h1>
 * <p>Формула расчёта взята из следующего ресурса:
 * <a href="https://journal.tinkoff.ru/guide/otpusk-stitaem/">ссылка</a></p>
 */
@Component
public class RussianVacationPaymentCalculator implements VacationPaymentCalculator{

    private static final double avgWorkingDaysPerMonth = 29.3;
    @Override
    public double calculate(double avgSalaryPerYear, int vacationDays) {
        return avgSalaryPerYear / avgWorkingDaysPerMonth * vacationDays;
    }
}
