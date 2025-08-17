package com.restaurante.pos.controller;

import com.restaurante.pos.entity.AppSettings;
import com.restaurante.pos.repository.AppSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    @Autowired
    private AppSettingsRepository appSettingsRepository;

    // Endpoint para OBTENER los precios actuales
    @GetMapping("/prices")
    public ResponseEntity<Map<String, BigDecimal>> getPrices() {
        BigDecimal menuCompletoPrice = new BigDecimal(
                appSettingsRepository.findById("precio_menu_completo").orElse(new AppSettings(){{ setValue("0.0"); }}).getValue()
        );
        BigDecimal soloSegundoPrice = new BigDecimal(
                appSettingsRepository.findById("precio_solo_segundo").orElse(new AppSettings(){{ setValue("0.0"); }}).getValue()
        );

        Map<String, BigDecimal> prices = new HashMap<>();
        prices.put("precio_menu_completo", menuCompletoPrice);
        prices.put("precio_solo_segundo", soloSegundoPrice);

        return ResponseEntity.ok(prices);
    }

    // Endpoint para ACTUALIZAR los precios
    @PutMapping("/prices")
    public ResponseEntity<Void> updatePrices(@RequestBody Map<String, BigDecimal> prices) {
        AppSettings menuCompletoSetting = new AppSettings();
        menuCompletoSetting.setKey("precio_menu_completo");
        menuCompletoSetting.setValue(prices.get("precio_menu_completo").toString());
        appSettingsRepository.save(menuCompletoSetting);

        AppSettings soloSegundoSetting = new AppSettings();
        soloSegundoSetting.setKey("precio_solo_segundo");
        soloSegundoSetting.setValue(prices.get("precio_solo_segundo").toString());
        appSettingsRepository.save(soloSegundoSetting);

        return ResponseEntity.ok().build();
    }
}