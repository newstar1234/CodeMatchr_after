package com.project.codematchr.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "room_view")
@Table(name = "room_view")
public class RoomViewEntity {

    @Id
    private int roomNumber;
    private String roomTitle;
    private String roomImageUrl;
    private String roomPassword;
    private String roomDatetime;
    private int roomUserCount;
    private String roomManagerEmail;
    private String roomManagerNickname;
    private String roomManagerProfileImageUrl;

}
