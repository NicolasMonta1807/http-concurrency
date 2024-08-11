package com.example.concurrencytest.services;

import com.example.concurrencytest.model.Country;
import com.example.concurrencytest.repositories.CountryRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PostConstruct
    public void init() throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get("src/main/resources/countries.json"));

        ObjectMapper objectMapper = new ObjectMapper();
        List<Country> countries = objectMapper.readValue(jsonData, new TypeReference<List<Country>>() {
        });

        countryRepository.saveAll(countries);
    }

    public List<Country> getAllCountries() {
        Iterable<Country> countries = countryRepository.findAll();
        List<Country> countriesList = new ArrayList<>();
        countries.iterator().forEachRemaining(countriesList::add);
        return countriesList;
    }

    public Country getCountryById(Long id) {
        Optional<Country> country = countryRepository.findById(id);
        return country.orElse(null);
    }

    public Country getCountryByCode(String code) {
        Optional<Country> country = countryRepository.findByCode(code.toUpperCase());
        return country.orElse(null);
    }
}
