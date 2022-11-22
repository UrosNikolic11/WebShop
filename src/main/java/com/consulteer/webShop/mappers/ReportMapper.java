package com.consulteer.webShop.mappers;

import com.consulteer.webShop.dto.CityDto;
import com.consulteer.webShop.dto.ReportDto;
import com.consulteer.webShop.model.ReportProduct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportMapper {

    public ReportMapper() {
    }

    public ReportDto map(List<CityDto> cityDtoList, ReportProduct reportProduct){
        return new ReportDto(reportProduct.getProductName(), reportProduct.getProductPrice(), cityDtoList, calculateTotalIncome(cityDtoList));
    }

    private Double calculateTotalIncome(List<CityDto> cityDtoList){
        return cityDtoList.stream().map(CityDto::income).toList().stream().reduce(0.0, Double::sum);
    }
}
