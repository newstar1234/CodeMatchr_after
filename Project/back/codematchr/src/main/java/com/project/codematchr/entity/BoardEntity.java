package com.project.codematchr.entity;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.project.codematchr.dto.request.board.PatchBoardRequestDto;
import com.project.codematchr.dto.request.board.PostBoardRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="board")
@Table(name="board")
public class BoardEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardNumber; 
    private String boardTitle; 
    private String boardContents;
    private String boardImageUrl;
    private int boardViewCount; 
    private int boardCommentCount; 
    private int boardFavoriteCount; 
    private String boardWriteDatetime; 
    private String boardWriterEmail;

    public BoardEntity(String boardWriterEmail, PostBoardRequestDto dto) {
        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writeDatetime = simpleDateFormat.format(now);

        this.boardTitle = dto.getBoardTitle();
        this.boardContents = dto.getBoardContents();
        this.boardImageUrl = dto.getBoardImageUrl();
        this.boardWriteDatetime = writeDatetime;
        this.boardWriterEmail = boardWriterEmail;
    }

    public void patch(PatchBoardRequestDto dto) {
        this.boardTitle = dto.getBoardTitle();
        this.boardContents = dto.getBoardContents();
        this.boardImageUrl = dto.getBoardImageUrl();
    }

    public void increaceViewCount() {
        this.boardViewCount++;
    }

    public void increaceCommentCount(){
        this.boardCommentCount++;
    }

    public void increaceFavoriteCount(){
        this.boardFavoriteCount++;
    }

    public void decreaceFavoriteCount(){
        this.boardFavoriteCount--;
    }
    
}
