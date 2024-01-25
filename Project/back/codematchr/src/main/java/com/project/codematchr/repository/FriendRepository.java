package com.project.codematchr.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.codematchr.entity.FriendEntity;
import com.project.codematchr.entity.pk.FriendPk;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, FriendPk> {


}
