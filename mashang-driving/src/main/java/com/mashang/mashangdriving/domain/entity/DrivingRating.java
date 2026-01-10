package com.mashang.mashangdriving.domain.entity;

import lombok.Data;

@Data
public class DrivingRating {

  private Long ratingId;
  private Long instructorId;
  private Long studentId;
  private String contanct;
  private Double score;

}
