package com.consulteer.webShop.mappers;

import com.consulteer.webShop.dto.CityDto;
import com.consulteer.webShop.model.ReportCity;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    public CityMapper() {
    }

    public CityDto map(ReportCity reportCity, Double price){
        return new CityDto(reportCity.getCityName(), reportCity.getTotalAmount(), reportCity.getTotalAmount()*price);
    }
}
