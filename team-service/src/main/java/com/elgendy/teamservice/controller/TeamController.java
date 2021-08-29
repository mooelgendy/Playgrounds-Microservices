package com.elgendy.teamservice.controller;

import com.elgendy.teamservice.exception.InternalServerErrorException;
import com.elgendy.teamservice.model.Team;
import com.elgendy.teamservice.model.dto.TeamDTO;
import com.elgendy.teamservice.service.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/api/team")
public class TeamController implements Serializable {

    private final TeamService service;

    @GetMapping("/")
    @Cacheable(value= "teamsListCache", unless= "#result.size() == 0")
    public List<TeamDTO> getAll(){
        List<Team> teams = null;
        List<TeamDTO> teamDTOs = null;
        try{
            teams = service.getAll();
            teamDTOs = teams.stream().map(team -> {
                TeamDTO dto = new TeamDTO();
                dto.setId(team.getId());
                dto.setName(team.getName());
                dto.setAddress(team.getAddress());
                dto.setBio(team.getBio());
                return dto;
            }).collect(Collectors.toList());
            return teamDTOs;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred");
        }
    }

    @GetMapping("/{id}")
    @Cacheable(value = "teamCache")
    public TeamDTO findOne(@PathVariable("id") Integer id){
        Team team = null;
        TeamDTO dto = null;
        try{
            team = service.getOne(id);
            if(team == null){
                return null;
            }
            dto = new TeamDTO();
            dto.setId(team.getId());
            dto.setName(team.getName());
            dto.setAddress(team.getAddress());
            dto.setBio(team.getBio());
            return dto;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred");
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @CachePut(value= "teamCache")
    public void create(@RequestBody TeamDTO dto) {
        Team team = null;
        try{
            team = new Team();
            team.setName(dto.getName());
            team.setAddress(dto.getAddress());
            team.setBio(dto.getBio());
            service.add(team);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred");
        }
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @CachePut(value= "teamCache")
    public void update(@RequestBody TeamDTO dto) {
        Team team = null;
        try{
            team = new Team();
            team.setId(dto.getId());
            team.setName(dto.getName());
            team.setAddress(dto.getAddress());
            team.setBio(dto.getBio());
            service.update(team);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CacheEvict(value= "teamCache")
    public void delete(@PathVariable("id") Integer id) {
        try{
            service.delete(id);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred");
        }
    }
}
