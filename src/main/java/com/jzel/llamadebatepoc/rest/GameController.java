package com.jzel.llamadebatepoc.rest;

import static java.util.Objects.requireNonNull;

import com.jzel.llamadebatepoc.persistence.AnswerEntity;
import com.jzel.llamadebatepoc.persistence.AnswerPersistence;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GameController {

  private final ChatClient chatClient;
  private final AnswerPersistence answerPersistence;

  @GetMapping("/answer")
  public ResponseEntity<List<AnswerEntity>> answer() {
    return ResponseEntity.ok(answerPersistence.findAll());
  }

  @PostMapping("/answer/progressive/{topic}")
  public ResponseEntity<String> answerProgressively(@PathVariable final String topic) {
    return answer(topic, "progressive");
  }

  @PostMapping("/answer/conservative/{topic}")
  public ResponseEntity<String> answerConservatively(@PathVariable final String topic) {
    return answer(topic, "conservative");
  }

  private ResponseEntity<String> answer(final String topic, final String position) {
    try {
      final String response = requireNonNull(
          chatClient.prompt().user(
                  STR."Please give reasonable arguments regarding the topic \"\{topic}\". Your position on this is \{position}.")
              .call()
              .chatResponse().getResult()
              .getOutput()
              .getContent()
      );
      answerPersistence.save(new AnswerEntity(0, response));
      return ResponseEntity.ok(
          response
      );
    } catch (Exception e) {
      return answerConservatively(topic);
    }
  }
}
