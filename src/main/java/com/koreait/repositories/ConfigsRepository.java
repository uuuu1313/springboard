package com.koreait.repositories;

import com.koreait.entities.Configs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigsRepository extends JpaRepository<Configs, String> {
}
