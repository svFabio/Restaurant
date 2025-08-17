package com.restaurante.pos.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReportRequestDTO {
    private LocalDate startDate;
    private LocalDate endDate;
}