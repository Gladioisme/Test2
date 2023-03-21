package be.thomasmore.party.model;

import jakarta.persistence.Id;

public class Invoice {

        private String companyName;
        private int amountInEuro;
        private String section;
        private boolean paid;
        @Id
        private Integer penaltyOvertimeInEuro;
}
