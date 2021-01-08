package com.zmlh.server;

import com.zmlh.entity.Response;

public interface CardService {
    Response editStudentLessons ( String cardId );

    Response getCardId ();
}
