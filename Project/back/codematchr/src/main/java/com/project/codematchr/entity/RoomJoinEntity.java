package com.project.codematchr.entity;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import com.project.codematchr.entity.pk.RoomJoinPk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="room_join")
@Table(name="room_join")
@IdClass(RoomJoinPk.class)
public class RoomJoinEntity implements Serializable {

    @Id
    private int roomNumber;

    @Id
    private String userEmail;
    
}
