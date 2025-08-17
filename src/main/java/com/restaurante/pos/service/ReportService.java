package com.restaurante.pos.service;

import com.restaurante.pos.dto.DishSaleDTO;
import com.restaurante.pos.dto.SalesReportDTO;
import com.restaurante.pos.entity.Order;
import com.restaurante.pos.entity.OrderItem;
import com.restaurante.pos.entity.OrderType;
import com.restaurante.pos.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private OrderRepository orderRepository;

    public SalesReportDTO generateSalesReport(LocalDate startDate, LocalDate endDate) {
        // Buscamos todos los pedidos dentro del rango de fechas.
        // Se busca desde el inicio del día de startDate hasta el final del día de endDate.
        List<Order> orders = orderRepository.findAllByOrderTimestampBetween(
                startDate.atStartOfDay(),
                endDate.atTime(LocalTime.MAX)
        );

        // --- 1. CÁLCULOS GENERALES ---
        BigDecimal totalRevenue = orders.stream()
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long menuCompletoCount = orders.stream()
                .filter(order -> order.getOrderType() == OrderType.MENU_COMPLETO)
                .count();

        long soloSegundoCount = orders.stream()
                .filter(order -> order.getOrderType() == OrderType.SOLO_SEGUNDO)
                .count();

        // --- 2. CÁLCULO DE PLATOS MÁS VENDIDOS ---
        Map<String, Long> dishSalesMap = orders.stream()
                .flatMap(order -> order.getOrderItems().stream()) // Juntamos todos los items de todos los pedidos en una sola lista
                .collect(Collectors.groupingBy(
                        orderItem -> orderItem.getDish().getName(), // Agrupamos por nombre del plato
                        Collectors.counting() // Contamos cuántas veces aparece cada uno
                ));

        List<DishSaleDTO> dishSales = dishSalesMap.entrySet().stream()
                .map(entry -> new DishSaleDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());


        // --- 3. CONSTRUCCIÓN DEL REPORTE FINAL ---
        SalesReportDTO report = new SalesReportDTO();
        report.setTotalRevenue(totalRevenue);
        report.setTotalOrders(orders.size());
        report.setMenuCompletoCount(menuCompletoCount);
        report.setSoloSegundoCount(soloSegundoCount);
        report.setDishSales(dishSales);

        return report;
    }
}