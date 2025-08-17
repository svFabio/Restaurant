package com.restaurante.pos.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SalesReportDTO {
    private BigDecimal totalRevenue;
    private long totalOrders;
    private long menuCompletoCount;
    private long soloSegundoCount;
    private List<DishSaleDTO> dishSales;
}