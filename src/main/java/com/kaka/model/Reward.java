package com.kaka.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reward {
    private Integer id;

    private String fundRaiser;

    private String fundRaisingSources;

    private String fundraisingPlace;

    private Double rewardMoney;

    private String remarks;

    private Date rewardDate;

    private String rewardUrl;
}