import React, { useEffect, useState } from 'react'
import { CommentListResponseDto } from '../../interfaces/response/board/get-comment-list.response.dto';
import './style.css';
import { useCookies } from 'react-cookie';
import { DeleteCommentResponseDto } from 'src/interfaces/response/board';
import ResponseDto from 'src/interfaces/response/response.dto';
import { deleteCommentRequest } from 'src/apis';

interface Props {
item: CommentListResponseDto;
}

export default function CommentListItem({ item }: Props) {

const { nickname, contents, writeDatetime, profileImageUrl, commentNumber } = item;

const [comments, setComments] = useState<boolean>(false);

// Cookies //
const [cookies, setCookie] = useCookies();

// 현재시간과 작성시간의 차이 함수//
const getTimeGap = () => {
  const writeDate = new Date(writeDatetime);
  writeDate.setHours(writeDate.getHours() - 9);

  const writeDateNumber = writeDate.getTime();
  const nowDateNumber = new Date().getTime();

  const gap = Math.floor((nowDateNumber - writeDateNumber) / 1000);

  let result = '';
  if (gap >= 3600) result = `${Math.floor(gap /3600)}시간 전`;
  if (gap < 3600) result = `${Math.floor(gap / 60)}분 전`;
  if (gap < 60) result = `${gap}초 전`;

  return result;
}

// 댓글 삭제 응답 //
const deleteCommentResponseHandler = (result : DeleteCommentResponseDto | ResponseDto) => {
  const { code } = result;
  if (code === 'NE') alert('존재하지 않는 사용자 이메일입니다.');
  if (code === 'NB') alert('존재하지 않는 게시물입니다.');
  if (code === 'NP') alert('권한이 없습니다.');
  if (code === 'VF') alert('잘못된 입력입니다.');
  if (code === 'DE') alert('데이터베이스 에러입니다.');
  if (code !== 'SU') return;
  
  setComments(true);
  alert('댓글 삭제 성공');
}


// 댓글 삭제 버튼 클릭 //
const onDeleteCommentClickHandler = () => {
    
  console.log(commentNumber);

  const token = cookies.accessToken;
  
  if(!commentNumber) return;
  deleteCommentRequest(commentNumber, token).then(deleteCommentResponseHandler);
};

// 댓글이 삭제되면 댓글삭제응답함수가 true가 됨. 이때 comments가 true면 null을 반환하므로 다시 랜더링 //
if (comments) {
  return null;
}

return (
  <div className='comment-list-item-box'>
    <div className='comment-list-item-writer-profile'>
        <div className='comment-list-item-profile-image' style={{ backgroundImage: `url(${profileImageUrl})` }} ></div>
        <div className='comment-list-item-writer-data'>
            <div className='comment-list-item-writer-nickname'>{nickname}</div>
            <div className='comment-list-item-writer-divider'>|</div>
            <div className='comment-list-item-write-time'>{ getTimeGap() }</div>
        </div>
    </div>
    <div className='comment-list-item-comment'>{ contents }</div>
    <div className='comment-list-item-delete-box' onClick={onDeleteCommentClickHandler} >
        <div className='comment-list-item-delete-button'>삭제</div>
    </div>
  </div>
  
  )
}