package com.koreait.commons.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koreait.entities.Configs;
import com.koreait.repositories.ConfigsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigSaveService {

    private final ConfigsRepository repository;

    public <T> void save(String code, T t){ // t는 Configs엔티티의 value
        Configs configs = repository.findById(code).orElseGet(Configs::new);

        ObjectMapper om = new ObjectMapper();
        String value = null;
        try {
            value = om.writeValueAsString(t); // JSON 형태로 저장
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        configs.setCode(code);
        configs.setValue(value);

        repository.saveAndFlush(configs);
    }

}
