package com.jzel.ollamadebatepoc.adapter.rest;

import com.jzel.ollamadebatepoc.adapter.model.DebateResponse;
import com.jzel.ollamadebatepoc.service.DebateService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class DebateController {

  private final DebateService debateService;

  @PostMapping("/debate/{exchanges}")
  ResponseEntity<List<DebateResponse>> debate(@RequestBody @Size(max = 1000) final String input,
      @PathVariable @Min(1) @Max(5) final int exchanges) {
    return ResponseEntity.ok(debateService.conductDebate(input, exchanges));
  }
}
