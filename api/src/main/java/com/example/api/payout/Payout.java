package com.example.api.payout;

import com.example.api.AuditableEntity;
import com.example.api.clinicHistory.ClinicHistory;
import com.example.api.paymentType.PaymentType;
import com.example.api.payoutConcept.PayoutConcept;
import com.example.api.payoutStatus.PayoutStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class Payout extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private LocalDate payout_date;

    @Column(nullable = true)
    private Float amount;

    @Column(nullable = true)
    private String transaction_number;

    @Column(nullable = true)
    private String invoice_number;

    @ManyToOne
    @JoinColumn(name="clinic_history_id", nullable = true)
    private ClinicHistory clinicHistory;

    @ManyToOne
    @JoinColumn(name="payment_type_id", nullable = true)
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name="payout_status_id", nullable = true)
    private PayoutStatus payoutStatus;

    @ManyToOne
    @JoinColumn(name="payout_concept_id", nullable = true)
    private PayoutConcept payoutConcept;

    public Payout(){}

    public Payout(Long id, LocalDate payout_date, Float amount, String transaction_number, String invoice_number, ClinicHistory clinicHistory, PaymentType paymentType, PayoutStatus payoutStatus, PayoutConcept payoutConcept) {
        this.id = id;
        this.payout_date = payout_date;
        this.amount = amount;
        this.transaction_number = transaction_number;
        this.invoice_number = invoice_number;
        this.clinicHistory = clinicHistory;
        this.paymentType = paymentType;
        this.payoutStatus = payoutStatus;
        this.payoutConcept = payoutConcept;
    }

    public Payout(LocalDate payout_date, Float amount, String transaction_number, String invoice_number, ClinicHistory clinicHistory, PaymentType paymentType, PayoutStatus payoutStatus, PayoutConcept payoutConcept) {
        this.payout_date = payout_date;
        this.amount = amount;
        this.transaction_number = transaction_number;
        this.invoice_number = invoice_number;
        this.clinicHistory = clinicHistory;
        this.paymentType = paymentType;
        this.payoutStatus = payoutStatus;
        this.payoutConcept = payoutConcept;
    }
}
