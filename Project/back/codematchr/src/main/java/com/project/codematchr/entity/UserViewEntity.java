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
@Entity(name="user_view")
@Table(name="user_view")
public class UserViewEntity {
    
    @Id
    private String userEmail;
    
    private String userNickname;
    private String userStateMessage;
    private String userProfileImageUrl;

}
