package com.elgendy.teamservice.service;

import com.elgendy.teamservice.model.Team;
import com.elgendy.teamservice.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository repository;

    @Override
    public List<Team> getAll() {
        List<Team> teamList = repository.findAll();
        return teamList;
    }

    @Override
    public Team getOne(Integer id) {
        Team teamById = repository.findById(id);
        return teamById;
    }

    @Override
    public void add(Team team) {
        repository.save(team);
    }

    @Override
    public void update(Team team) {
        repository.update(team);
    }

    @Override
    public void delete(Integer id) {
        Team deletedTeam = repository.findById(id);
        repository.delete(deletedTeam);
    }
}