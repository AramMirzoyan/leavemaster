package com.leave.master.leavemaster.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "holiday")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Holiday extends BaseEntity {

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "local_name")
  private String localName;

  @Column(name = "name")
  private String name;

  @Column(name = "country_code")
  private String countryCode;
}
