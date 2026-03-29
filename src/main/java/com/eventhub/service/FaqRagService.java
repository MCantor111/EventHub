package com.eventhub.service;

import com.eventhub.dto.ai.FaqRagRequest;
import com.eventhub.dto.ai.FaqRagResponse;

public interface FaqRagService {

    FaqRagResponse answerQuestion(FaqRagRequest request);
}