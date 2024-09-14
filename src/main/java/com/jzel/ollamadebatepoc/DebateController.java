package com.jzel.ollamadebatepoc;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DebateController {

  private final DebateService debateService;

  @PostMapping("/debate/{exchanges}")
  public String debate(@RequestBody final String input, @PathVariable final int exchanges) {
    return debateService.conductDebate(input, exchanges);
  }
}
