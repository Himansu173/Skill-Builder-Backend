package com.infy.skillbuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRetentionDTO {
  private String cohort;
  private String activityMonth;
  private Long activeUsers;
  private Long cohortSize;
  private Double retentionRate;
}