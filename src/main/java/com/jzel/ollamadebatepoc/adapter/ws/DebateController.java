package com.jzel.ollamadebatepoc.adapter.ws;

import com.jzel.ollamadebatepoc.adapter.model.DebateRequest;
import com.jzel.ollamadebatepoc.service.DebateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
class DebateController {

  private final DebateService debateService;
  private final SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/debate")
  public void handleDebate(final @Valid DebateRequest request) {
    debateService.conductDebate(request.input(), request.exchanges())
        .thenAccept(
            debateResponses -> messagingTemplate.convertAndSend(
                STR."/topic/debate/\{request.sessionId()}",
                debateResponses
            )
        );
  }
}
