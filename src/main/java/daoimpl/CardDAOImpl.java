package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.CardDAO;
import model.Card;
import util.DBConnection;

public class CardDAOImpl implements CardDAO {

	@Override
	public Card validateCard(int userId, String cardNumber, String cardHolder, String expiryMonth, String expiryYear,
			String cvv) {
		String query = """
				    SELECT id, balance
				    FROM cards
				    WHERE user_id = ?
				      AND card_number = ?
				      AND card_holder = ?
				      AND expiry_month = ?
				      AND expiry_year = ?
				      AND cvv = ?
				""";
		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {

			ps.setInt(1, userId);
			ps.setString(2, cardNumber);
			ps.setString(3, cardHolder);
			ps.setString(4, expiryMonth);
			ps.setString(5, expiryYear);
			ps.setString(6, cvv);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Card card = new Card();
				card.setId(rs.getInt("id"));
				card.setUserId(userId);
				card.setBalance(rs.getDouble("balance"));
				return card;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // invalid card
	}

	@Override
	public void deductBalance(int cardId, double amount) {
		// TODO Auto-generated method stub
		String query = "UPDATE cards SET balance = balance - ? WHERE id = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {

			ps.setDouble(1, amount);
			ps.setInt(2, cardId);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
