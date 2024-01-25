package com.project.codematchr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.codematchr.entity.FriendViewEntity;

@Repository
public interface FriendViewRepository extends JpaRepository<FriendViewEntity, String>{
  
  List<FriendViewEntity> findByFriendMyEmail(String friendMyEmail);


}
