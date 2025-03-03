package com.SimpleBank.dtos;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long senderId, Long receiverId) { //here it stores the transfer information, the users involved and the total sent

}
