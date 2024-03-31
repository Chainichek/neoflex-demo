package ru.chainichek.neoflexdemo.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.chainichek.neoflexdemo.domain.dto.VacationPaymentOut;
import ru.chainichek.neoflexdemo.domain.service.CalculatorService;
import ru.chainichek.neoflexdemo.domain.dto.VacationPaymentIn;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
public class CalculatorController implements CalculatorApi {
    private final CalculatorService calculatorService;

    @Override
    public ResponseEntity<VacationPaymentOut> calculate(final double averageSalaryPerYear,
                                                        final int vacationDays,
                                                        final LocalDate startsFrom) {
        return ResponseEntity.ok(calculatorService.calculate(
                new VacationPaymentIn(
                        averageSalaryPerYear,
                        vacationDays,
                        startsFrom)));
    }
}
