package com.jzel.llamadebatepoc.persistence;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED, force = true)
public class AnswerEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private final int id;

  @Column(columnDefinition = "VARCHAR(MAX)")
  private final String debateArguments;
}
