package com.jzel.ollamadebatepoc.adapter.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DebateRequest(@NotBlank @Size(max = 1000) String input, @Min(1) @Max(5) int exchanges, String sessionId) {

}
