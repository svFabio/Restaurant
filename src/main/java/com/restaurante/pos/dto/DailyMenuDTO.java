package com.restaurante.pos.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class DailyMenuDTO {
    private Long id;
    private LocalDate menuDate;
    private List<DailyMenuDishDTO> dishes;
}