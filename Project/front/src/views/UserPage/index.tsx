import React, { ChangeEvent, useEffect, useRef, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import { useCookies } from 'react-cookie';

import UserBoardItem from '../../components/UserBoardItem';
import Pagination from '../../components/Pagination';
import ChatComePopUP from '../../components/PopUp/ChatComePopUp';
import RoomFullListItem from '../../components/RoomFullListItem';
import { getUserBoardListRequest, getUserRequest, getUserRoomListRequest, patchNicknameRequest, patchProfileImageUrlRequest, patchStateMessageRequest, uploadFileRequest } from 'src/apis';
import { usePagination } from '../../hooks';
import { useUserStore } from 'src/store';
import { BOARD_LIST_PATH, COUNT_BY_PAGE, MAIN_PATH, MAIN_ROOM_COUNT_BY_PAGE, WRITE_PATH } from '../../constants';
import { PatchNicknameRequestDto, PatchProfileImageUrlRequestDto, PatchStateMessageRequestDto } from 'src/interfaces/request/user';
import ResponseDto from 'src/interfaces/response/response.dto';
import { GetUserBoardListResponseDto, GetUserResponseDto, GetUserRoomListResponseDto } from 'src/interfaces/response/User';
import { BoardListResponseDto } from 'src/interfaces/response/board';
import { RoomListResponseDto } from 'src/interfaces/response/room';
import './style.css';


// 마이페이지 컴포넌트 // 
export default function UserPage() {

// 유저 이메일 상태 //
const { userEmail } = useParams();
// 유저페이지 여부 상태 //
const [userPage, setUserPage] = useState<boolean>(false);
// 로그인 유저 정보 상태 //
const {user, setUser} = useUserStore();
// Cookies 상태 //
const [cookies, setCookie] = useCookies();


const navigator = useNavigate();


  const UserPageTop = () => {

    // 인풋 요소 상태 //
    const fileInputRef = useRef<HTMLInputElement>(null);

    // 프로필 이미지 상태 //
    const [userProfileImageUrl, setUserProfileImageUrl] = useState<string>('');
    // 닉네임 상태 //
    const [userNickname, setUserNickname] = useState<string>('');
    // 닉네임 변경 버튼 상태 //
    const [nicknameChange, setNicknameChange] = useState<boolean>(false);
    // 상태메세지 상태 //
    const [userStateMessage, setUserStateMessage] = useState<string>('');
    // 상태메시지 변경 버튼 상태 //
    const [messageChange, setMessageChange] = useState<boolean>(false);


    // 유저 정보 응답 처리 함수 //
    const getUserResponseHandler = (result: GetUserResponseDto | ResponseDto) => {
      const { code } = result;
      if (code === 'NE') return;
      if (code === 'DE') return;
      if (code !== 'SU') return;

      const { userNickname, userProfileImageUrl, userStateMessage } = result as GetUserResponseDto;
      setUserNickname(userNickname);
      setUserStateMessage(userStateMessage);
      setUserProfileImageUrl(userProfileImageUrl);

      if (userEmail === user?.userEmail) {
        const after = { userEmail: userEmail as string, userNickname, userProfileImageUrl, userStateMessage };
        setUser(after);
      }
    }
    
    // 닉네임 변경 응답 처리 함수 //
    const patchNicknameResponseHandler = (code: string) => {
      if (!user) return;
      if (code === 'NE') alert('존재하지 않는 사용자 이메일 입니다.');
      if (code === 'EN') alert('중복되는 닉네임입니다.');
      if (code === 'VF') alert('잘못된 입력입니다.');
      if (code === 'DE') alert('데이터베이스 에러입니다.');
      if (code !== 'SU') {
        setUserNickname(user.userNickname);
        return;
      }

      getUserRequest(user.userEmail).then(getUserResponseHandler);

    }
    
    // 상태메세지 변경 응답 처리 함수 //
    const patchStateMessageResponseHandler = (code: string) => {
      if (!user) return;
      if (code === 'NE') alert('존재하지 않는 사용자 이메일입니다.');
      if (code === 'VF') alert('잘못된 입력입니다.');
      if (code === 'DE') alert('데이터베이스 에러입니다.');
      if (code !== 'SU') {
        setUserStateMessage(user.userStateMessage);
        return;
      }
      
      getUserRequest(user.userEmail).then(getUserResponseHandler);
    }
    
    
    // 프로필 이미지 변경 응답 처리 함수 //
    const patchProfileImageResponseHandler = (code: string) => {
      if (!user) return;
      if (code === 'NE') alert('존재하지 않는 사용자 이메일 입니다.');
      if (code === 'VF') alert('잘못된 입력입니다.');
      if (code === 'DE') alert('데이터베이스 에러입니다.');
      if (code !== 'SU') {
        setUserProfileImageUrl(user.userProfileImageUrl);
        return;
      }

      getUserRequest(user.userEmail).then(getUserResponseHandler);
    }
    
    // 프로필 이미지 업로드 응답 처리 함수 //
    const profileUploadResponseHandler = (url: string | null) => {
      if (!user) return;
      if (!url) {
        setUserProfileImageUrl(user.userProfileImageUrl);
        return;
      }

      const data: PatchProfileImageUrlRequestDto = { userProfileImageUrl: url };
      const token = cookies.accessToken;
      patchProfileImageUrlRequest(data, token).then(patchProfileImageResponseHandler);
    }

    // 파일 인풋 변경 시 이미지 미리보기 //
    const onImageInputChangeHandler = async (event: ChangeEvent<HTMLInputElement>) => {
      if(!event.target.files || !event.target.files.length) return;

      const data = new FormData();
      data.append('file', event.target.files[0])
      uploadFileRequest(data).then(profileUploadResponseHandler);
    }
    // 프로필 이미지 클릭시 파일 인풋창 열림 이벤트 //
    const onProfileClickHandler = () => {
      if (userEmail !== user?.userEmail) return;
      fileInputRef.current?.click();
    }
    // 닉네임 변경 이벤트 //
    const onNicknameChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setUserNickname(event.target.value);
    }
    // 닉네임 변경 버튼 클릭 이벤트 //
    const onNicknameButtonClickHandler = () => {
      if (nicknameChange) {
        const data: PatchNicknameRequestDto = { userNickname };
        const token = cookies.accessToken;
        patchNicknameRequest(data, token).then(patchNicknameResponseHandler);
      }
      setNicknameChange(!nicknameChange);
    }
    // 상태메세지 변경 이벤트 //
    const onMessageChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setUserStateMessage(event.target.value);
    }
    // 상태메세지 변경 버튼 클릭 이벤트 //
    const onMessageButtonClickHandler = () => {
      if (messageChange) {
        const data: PatchStateMessageRequestDto = { userStateMessage };
        const token = cookies.accessToken;
        patchStateMessageRequest(data, token).then(patchStateMessageResponseHandler);
      }
      setMessageChange(!messageChange);
    }
    // 글쓰기 버튼 클릭 이벤트 //
    const onWriteClickHandler = () => {
      navigator(WRITE_PATH);
    }

    
    // 유저 이메일 상태 또는 이메일이 바뀔 때마다 실행 //
    useEffect(() => {
      if(!userEmail) navigator(MAIN_PATH);
      if(!user) return;
            
      const isMyPage = user?.userEmail === userEmail;
      if (isMyPage) {

        if (user?.userProfileImageUrl) setUserProfileImageUrl(user?.userProfileImageUrl);
        else setUserProfileImageUrl('');

        setUserNickname(user?.userNickname as string);
        setUserStateMessage(user.userStateMessage as string);
      } else {
        getUserRequest(userEmail as string).then(getUserResponseHandler);
      }

    }, [userEmail , user]);
    

    return (
      <div className='userpage-top-wrapper'>
        <div className='userpage-profile-box'>
          <div className='userpage-profile-image' style={{ backgroundImage: `url(${userProfileImageUrl})` }} onClick={onProfileClickHandler}>
            <input type='file' style={{display: 'none'}} ref={fileInputRef} accept='image/*' onChange={onImageInputChangeHandler} />
          </div>
        </div>
        <div className='userpage-user-info-box'>
          <div className='userpage-user-nickname-box'>
            <div className='userpage-user-nickname-input-box'>
              {nicknameChange ? (
                <input className='userpage-user-nickname-input' type='text' value={userNickname} onChange={onNicknameChangeHandler} /> // false
                ) :(
                <input className='userpage-user-nickname-input' type='text' value={userNickname} readOnly /> // true
                )}
              <div className='userpage-user-nickname-button' onClick={onNicknameButtonClickHandler}></div>
            </div>
            <div className='userpage-user-email'>{userEmail}</div>
          </div>
          <div className='userpage-user-message-box'>
            {messageChange ? (
              <input className='userpage-user-message-text-input' type='text' value={userStateMessage} onChange={onMessageChangeHandler} />
            ) : (
              <input className='userpage-user-message-text-input' type='text' value={userStateMessage} readOnly />
            )}
            <div className='userpage-user-message-button' onClick={onMessageButtonClickHandler}></div>
          </div>
        </div>
        <div className='userpage-button-box'>
          <div className='userpage-button-text-box'>
            <button className='userpage-button-text' onClick={onWriteClickHandler}>글쓰기</button>
          </div>
        </div>
      </div>
    );
  }
  
  // 마이페이지 내 게시물 //
  const UserPageBoard = () => {

    //  페이지네이션 관련 상태 및 함수 //
    const {totalPage, currentPage, currentSection, onPageClickHandler, onNextClickHandler, onPreviousClickHandler, changeSection} = usePagination();
    
    // board 에 해당하는 전체 리스트 상태 //
    const[currentBoardList, setCurrentBoardList] = useState<BoardListResponseDto[]>([]);
    // 현재 페이지에서 보여줄 board 리스트 상태 //
    const [viewBoardList, setViewBoardList] = useState<BoardListResponseDto[]>([]);
    
    // 페이지네이션 함수 //
    const getViewBoardList = (boardList : BoardListResponseDto[]) => {
      const startIndex = MAIN_ROOM_COUNT_BY_PAGE * (currentPage -1);
      const lastIndex = boardList.length > MAIN_ROOM_COUNT_BY_PAGE * currentPage ? 
                        MAIN_ROOM_COUNT_BY_PAGE * currentPage : boardList.length;
      const viewBoardList = boardList.slice(startIndex, lastIndex);
      setViewBoardList(viewBoardList);
    }

     // 유저 작성 게시물 리스트 불러오기 응답 처리 함수 //
     const getUserBoardListResponseHandler = (responseBody: GetUserBoardListResponseDto | ResponseDto) => {
      const { code } = responseBody;
      if (code === 'VF') alert('잘못된 입력입니다.');
      if (code === 'DE') alert('데이터베이스 에러입니다.');
      if (code !== 'SU') return;
      
      const { boardList } = responseBody as GetUserBoardListResponseDto;
      setCurrentBoardList(boardList);
      getViewBoardList(boardList);
      changeSection(boardList.length, COUNT_BY_PAGE);
      
    }
    

    // 타이틀 클릭시 게시물 리스트로 이동 //
    const onBoardTitleClickHandler = () => {
      navigator(BOARD_LIST_PATH);
    }

    // 유저 이메일이 바뀔때 마다 게시물 리스트 불러오기 //
    useEffect(() => {
      if (!userEmail) {
        alert('잘못된 사용자 이메일입니다.');
        navigator(MAIN_PATH);
        return;
      }
      getUserBoardListRequest(userEmail).then(getUserBoardListResponseHandler);
    }, [userEmail]);

    // 현재 페이지가 바뀔 때마다 board 리스트 변경 //
    useEffect(() => {
      getViewBoardList(currentBoardList);
    }, [currentPage]);
    
    // 현재 섹션이 바뀔 때마다 board 리스트 변경 //
    useEffect(() => {
      changeSection(currentBoardList.length, MAIN_ROOM_COUNT_BY_PAGE);
    }, [currentSection]);

    return (
      <div className='userpage-board-wrapper'>
        <div className='userpage-board-title' onClick={onBoardTitleClickHandler} >내 게시물</div>
        <div className='userpage-board-contents-list'>
          {viewBoardList.map((item) => (<UserBoardItem item={item} />))}
        </div>
        <Pagination
          totalPage={totalPage}
          currentPage={currentPage}
          onPageClickHandler={onPageClickHandler}
          onNextClickHandler={onNextClickHandler}
          onPreviousClickHandler={onPreviousClickHandler}
        />
      </div>
    );
  } 

  // 마이페이지 채팅방 //
  const UserPageChat = () => {


    // 페이지네이션 //
    const {totalPage, currentPage, currentSection, onPageClickHandler, onNextClickHandler, onPreviousClickHandler, changeSection} = usePagination();
    // 현재 페이지에서 보여줄 채팅방 리스트 상태 //
    const [viewChatList, setViewChatList] = useState<RoomListResponseDto[]>([]);
    // Chat 에 해당하는 전체 리스트 상태 //
    const[currentChatList, setCurrentChatList] = useState<RoomListResponseDto[]>([]);
    // 채팅방 팝업창 상태 //
    const [popUpRoomVisible, setPopUpRoomVisible] = useState<boolean>(false);
    // 채팅방 팝업창 상태 //
    const [selectRoomNumber, setSelectRoomNumber] = useState<string>('');


    // 페이지네이션 함수 //
    const getViewChatList = (chatList : RoomListResponseDto[]) => {
      const startIndex = MAIN_ROOM_COUNT_BY_PAGE * (currentPage -1);
      const lastIndex = chatList.length > MAIN_ROOM_COUNT_BY_PAGE * currentPage ? 
      MAIN_ROOM_COUNT_BY_PAGE * currentPage : chatList.length;
      const viewChatList = chatList.slice(startIndex, lastIndex);
      setViewChatList(viewChatList);
    }
    // 사용자 작성 채팅방 리스트 불러오기 응답 처리 함수 //
    const getUserRoomListResponseHandler = (responseBody : GetUserRoomListResponseDto | ResponseDto) => {
      const {code} = responseBody;
      if(code === 'DE') alert('데이터베이스 에러입니다.');
      if(code !== 'SU') return;

      const { roomList } = responseBody as GetUserRoomListResponseDto;
      getViewChatList(roomList);
      setCurrentChatList(roomList);
      changeSection(roomList.length, MAIN_ROOM_COUNT_BY_PAGE);
    }
    

    // 팝업창 //
    const onRoomListItemClickHandler = (roomNumber: number) => {
      setPopUpRoomVisible(true);
      setSelectRoomNumber(String(roomNumber));
    }

    // 유저 이메일이 바뀔때 마다 채팅방 리스트 불러오기 //
    useEffect(() => {
      if (!userEmail) {
        alert('잘못된 사용자 이메일입니다.');
        navigator(MAIN_PATH);
        return;
      }
      const accessToken = cookies.accessToken;
      getUserRoomListRequest(userEmail, accessToken).then(getUserRoomListResponseHandler);
    }, [userEmail]);

    // 현재 페이지가 바뀔 때마다 chat 리스트 변경 //
    useEffect(() => {
      getViewChatList(currentChatList);
    }, [currentPage]);

    // 현재 섹션이 바뀔 때마다 chat 리스트 변경 //
    useEffect(() => {
      changeSection(currentChatList.length, MAIN_ROOM_COUNT_BY_PAGE);
    }, [currentSection]);


    return (
      <div className='userpage-chat-wrapper'>
        <div className='userpage-chat-title'>
          <div className='userpage-chat-text'>내 채팅방</div>
        </div>
        <div className='userpage-chat-room'>
          {viewChatList.map((item) => (<RoomFullListItem onClick={() => onRoomListItemClickHandler(item.roomNumber)} item={item}/>))}
          {popUpRoomVisible && <div className='chat-room-pop-up'><ChatComePopUP selectRoomNumber={selectRoomNumber} /></div>}
        </div>
        <div className='userpage-chat-pagination'>
          <Pagination
            totalPage={totalPage}
            currentPage={currentPage}
            onPageClickHandler={onPageClickHandler}
            onNextClickHandler={onNextClickHandler}
            onPreviousClickHandler={onPreviousClickHandler}
          />
        </div>
      </div>
    );
  } 

// 유저 이메일 상태가 바뀔때마다 실행 //
useEffect(() => {
  if (!userEmail) navigator(MAIN_PATH);
  const isMyPage = user?.userEmail === userEmail;
  setUserPage(isMyPage);

}, [userEmail, user]);


  return (
    <div id='userpage-wrapper'>
      <div className='userpage-wrapper-box'>
        {userPage && <UserPageTop/> }
        {userPage && <UserPageBoard/> }
        {userPage && <UserPageChat/> }
      </div>
    </div>
  )
}