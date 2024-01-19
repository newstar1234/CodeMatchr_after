import React, { useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { useNavigate, useParams } from 'react-router-dom';
import { getUserRequest } from 'src/apis';
import { MAIN_PATH, USER_PAGE_PATH_VARIABLE, USER_PATH } from 'src/constants';
import { GetUserResponseDto } from 'src/interfaces/response/User';
import ResponseDto from 'src/interfaces/response/response.dto';
import { useUserStore } from 'src/store';

export default function OAuth() {

  const { token, expiredTime } = useParams();

  const [cookie, setCookies] = useCookies();

  const navigate = useNavigate();


  useEffect(() => {
    // oauth 토큰 전달해주기 SNS 로그인 //
    if(!token || !expiredTime) return;

    const now = (new Date().getTime()) * 1000;
    const expires = new Date(now + Number(expiredTime));


    setCookies('accessToken', token, { expires, path: MAIN_PATH});

    navigate(MAIN_PATH);

  }, [token]);

  return (
    <></>
  )
}

