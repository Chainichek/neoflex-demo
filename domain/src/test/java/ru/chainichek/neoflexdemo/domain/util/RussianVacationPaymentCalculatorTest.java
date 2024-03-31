package ru.chainichek.neoflexdemo.domain.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RussianVacationPaymentCalculatorTest {
    private final RussianVacationPaymentCalculator calculator = new RussianVacationPaymentCalculator();
    @Test
    void calculate_1() {
        final double avgSalaryPerYear = 100000.00;
        final int vacationDays = 28;
        final double sum = calculator.calculate(avgSalaryPerYear, vacationDays);
        assertEquals(95563.13993174062, sum);
    }
    @Test
    void calculate_2() {
        final double avgSalaryPerYear = 30000;
        final int vacationDays = 14;
        final double sum = calculator.calculate(avgSalaryPerYear, vacationDays);
        assertEquals(14334.470989761092, sum);
    }
    @Test
    void calculate_3() {
        final double avgSalaryPerYear = 0;
        final int vacationDays = 28;
        final double sum = calculator.calculate(avgSalaryPerYear, vacationDays);
        assertEquals(0, sum);
    }
    @Test
    void calculate_4() {
        final double avgSalaryPerYear = 100000.00;
        final int vacationDays = 0;
        final double sum = calculator.calculate(avgSalaryPerYear, vacationDays);
        assertEquals(0, sum);
    }
    @Test
    void calculate_5() {
        final double avgSalaryPerYear = -1;
        final int vacationDays = 28;
        final double sum = calculator.calculate(avgSalaryPerYear, vacationDays);
        assertEquals(-0.9556313993174061, sum);
    }
}