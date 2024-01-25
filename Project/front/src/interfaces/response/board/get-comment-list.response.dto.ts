import ResponseDto from '../response.dto';

export interface CommentListResponseDto {
    commentNumber : number;
    nickname : string;
    contents : string;
    writeDatetime : string;
    profileImageUrl : string;
}

export default interface GetCommentListResponseDto extends ResponseDto {
    commentList : CommentListResponseDto[];
}