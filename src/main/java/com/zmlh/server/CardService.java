package com.zmlh.server;

import com.zmlh.entity.Response;

import java.time.Instant;

public interface CardService {

    Response editStudentLessons ( String cardId, String coachId, Instant time );

    Response getCardId ();
}
