package com.elgendy.invitationservice.repository;

import com.elgendy.invitationservice.model.Invitation;

import java.util.List;

public interface InvitationRepository {    //extends Repository {

    List<Invitation> findAll();

	Invitation findById(Integer id);

//    Optional<Invitation> findById(Integer id); #TODO: implement the Optional orElseThrow() if not found

    void save(Invitation invitation);

//    Integer saveAndFlush(Invitation invitation); #TODO: return the microservice ID to include in saving the other microservice data for completing the transaction

    void update(Invitation invitation);

//    boolean existsById(ID var1);

    void delete(Invitation invitation);

//    void deleteById(ID var1);
//
//    void delete(T var1);
}
