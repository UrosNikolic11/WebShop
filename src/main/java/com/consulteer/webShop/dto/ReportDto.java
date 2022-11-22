package com.consulteer.webShop.dto;

import java.util.List;

public record ReportDto(String name,
                        Double price,
                        List<CityDto> cityDtoList,
                        Double totalINcome) {
}
