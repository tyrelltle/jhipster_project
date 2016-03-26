package com.hak.haklist.web.rest.mapper;

import com.hak.haklist.domain.Country;
import com.hak.haklist.web.rest.dto.CountryDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Country and its DTO CountryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CountryMapper {

    CountryDTO countryToCountryDTO(Country country);

    Country countryDTOToCountry(CountryDTO countryDTO);
}
