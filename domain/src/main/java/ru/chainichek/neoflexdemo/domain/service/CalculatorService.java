package ru.chainichek.neoflexdemo.domain.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.chainichek.neoflexdemo.domain.dto.VacationPaymentIn;
import ru.chainichek.neoflexdemo.domain.dto.VacationPaymentOut;
import ru.chainichek.neoflexdemo.domain.usecase.CalculateVacationPaymentUseCase;

@Service
@AllArgsConstructor
public class CalculatorService {
    private final CalculateVacationPaymentUseCase calculateVacationPayment;

    @SneakyThrows
    public VacationPaymentOut calculate(final VacationPaymentIn vacationPaymentIn) {
        return calculateVacationPayment.execute(vacationPaymentIn);
    }
}
