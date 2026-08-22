package dao;

import model.Card;

public interface CardDAO {

    // Validate card details for a user
    Card validateCard(int userId,
                      String cardNumber,
                      String cardHolder,
                      String expiryMonth,
                      String expiryYear,
                      String cvv);

    // Deduct balance after successful payment
    void deductBalance(int cardId, double amount);
}
