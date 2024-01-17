package com.project.codematchr.entity;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.project.codematchr.dto.request.compare.PostCompareRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="compare")
@Table(name="compare")
public class CompareEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // AUTO INCREASEMENT
    private int compareResultNumber;
    private String compareControlGroup;
    private String compareExperimentalGroup;
    private String compareDatetime;
    private String compareResult;
    private String compareUserEmail;
    

    public CompareEntity(String compareUserEmail, PostCompareRequestDto postCompareRequestDto) {

        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String compareDatetime = simpleDateFormat.format(now);

        this.compareControlGroup = postCompareRequestDto.getCompareControlGroup();
        this.compareExperimentalGroup = postCompareRequestDto.getCompareExperimentalGroup();
        this.compareResult = postCompareRequestDto.getCompareResult();
        this.compareDatetime = compareDatetime;
        this.compareUserEmail = compareUserEmail;
        
    }

}
