package ru.chainichek.neoflexdemo.domain.usecase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.chainichek.neoflexdemo.domain.dto.VacationPaymentIn;
import ru.chainichek.neoflexdemo.domain.gateway.ProductionCalendarGateway;
import ru.chainichek.neoflexdemo.domain.util.VacationPaymentCalculator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculateVacationPaymentUseCaseTest {
    private static final CalculateVacationPaymentUseCase calculateVacationPayment;
    private static final ProductionCalendarGateway gateway;
    private static final VacationPaymentCalculator calculator;
    private static final double minAvgSalary = 1.0;
    private static final int minVacationDays = 1;

    static {
        gateway = mock(ProductionCalendarGateway.class);
        calculator = mock(VacationPaymentCalculator.class);
        calculateVacationPayment = new CalculateVacationPaymentUseCase(gateway,
                calculator,
                minAvgSalary,
                minVacationDays);
    }

    @Test
    void calculate_whenStartsFromIsNull() {
        final double avgSalaryPerYear = 1000;
        final int vacationDays = 10;
        final VacationPaymentIn vacationPaymentIn = new VacationPaymentIn(avgSalaryPerYear,
                vacationDays, null);
        when(calculator.calculate(avgSalaryPerYear, vacationDays)).thenReturn(1.0);
    }
    @Test
    void calculate_whenStartsFromNotNull() {
        final double avgSalaryPerYear = 1000;
        final int vacationDays = 10;
        final LocalDate startsFrom = LocalDate.now();
        final VacationPaymentIn vacationPaymentIn = new VacationPaymentIn(avgSalaryPerYear,
                vacationDays, startsFrom);
        when(calculator.calculate(avgSalaryPerYear, vacationDays)).thenReturn(1.0);
        when(gateway.getAllWorkingDaysBetweenTwoDates(startsFrom, startsFrom.plusDays(vacationDays)))
                .thenReturn(new ArrayList<>(Collections.singleton(startsFrom)));
        calculateVacationPayment.execute(vacationPaymentIn);
    }

    @Test
    void calculate_whenStartsFromNotNull_usesRealVacationDays() {
        final double avgSalaryPerYear = 1000;
        final int vacationDays = 10;
        final LocalDate startsFrom = LocalDate.now();
        final VacationPaymentIn vacationPaymentIn = new VacationPaymentIn(avgSalaryPerYear,
                vacationDays, startsFrom);
        final double ifNotRealPay = 1.0;
        final double ifRealPay = 0.5;
        when(calculator.calculate(avgSalaryPerYear, vacationDays)).thenReturn(1.0);
        when(calculator.calculate(avgSalaryPerYear, 3)).thenReturn(0.5);
        when(gateway.getAllWorkingDaysBetweenTwoDates(startsFrom, startsFrom.plusDays(vacationDays)))
                .thenReturn(List.of(startsFrom, startsFrom, startsFrom));
        assertEquals(0.5, calculateVacationPayment.execute(vacationPaymentIn).vacationPay());
    }
    @Test
    void calculate_whenAvgSalary_throwsIllegalArgumentException() {
        final double avgSalaryPerYear = 0;
        final int vacationDays = 10;
        final VacationPaymentIn vacationPaymentIn = new VacationPaymentIn(avgSalaryPerYear,
                vacationDays, null);
        assertThrows(IllegalArgumentException.class, () -> calculateVacationPayment.execute(vacationPaymentIn));
    }

    @Test
    void calculate_whenVacationDays_throwsIllegalArgumentException() {
        final double avgSalaryPerYear = 1000;
        final int vacationDays = 0;
        final VacationPaymentIn vacationPaymentIn = new VacationPaymentIn(avgSalaryPerYear,
                vacationDays, null);
        assertThrows(IllegalArgumentException.class, () -> calculateVacationPayment.execute(vacationPaymentIn));
    }

    @Test
    void calculate_whenRealVacationDays_throwsIllegalArgumentException() {
        final double avgSalaryPerYear = 1000;
        final int vacationDays = 10;
        final LocalDate startsFrom = LocalDate.now();
        final VacationPaymentIn vacationPaymentIn = new VacationPaymentIn(avgSalaryPerYear,
                vacationDays, startsFrom);
        when(gateway.getAllWorkingDaysBetweenTwoDates(startsFrom, startsFrom.plusDays(vacationDays)))
                .thenReturn(new ArrayList<>());
        assertThrows(IllegalArgumentException.class, () -> calculateVacationPayment.execute(vacationPaymentIn));
    }


}