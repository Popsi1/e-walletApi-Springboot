package com.example.walletapp.models;

import com.example.walletapp.dtos.KycMasterDto;
import com.example.walletapp.dtos.KycUltimateDto;
import com.example.walletapp.enums.KycLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class KycUltimate extends BaseModel{

    private String nationalId;

    @OneToOne
    @JsonIgnore
    private Wallet wallet;

    public static KycUltimate from(KycUltimateDto kycUltimateDto) {
        KycUltimate kycUltimate = new KycUltimate();
        kycUltimate.setNationalId(kycUltimateDto.getNationalId());

        return kycUltimate;
    }
}
