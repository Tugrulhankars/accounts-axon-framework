package com.tugrulhan.accountaxon.coreapi.event;

import lombok.Data;

@Data
public class AccountDeletedEvent {
    private Long accountNumber;
    private boolean activeSw;
}
