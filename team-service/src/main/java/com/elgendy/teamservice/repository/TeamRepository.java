package com.elgendy.teamservice.repository;

import com.elgendy.teamservice.model.Team;

import java.util.List;

public interface TeamRepository {

    void save(Team team);

    void update(Team team);

    Team findById(Integer id);

    List<Team> findAll();

    void delete(Team team);
}
