package in.mirai.bms.daoimpl;

import in.mirai.bms.dao.TransactionDAO;
import in.mirai.bms.model.Transaction;
import in.mirai.bms.model.TransactionType;
import in.mirai.bms.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of {@link TransactionDAO} using JDBC.
 * Handles logging and history retrieval of financial transactions.
 */
public class TransactionDAOImpl implements TransactionDAO {

    @Override
    public boolean saveTransaction(Transaction transaction) {
        String sql = """
            INSERT into transactions (transaction_id, account_id, type, amount, transaction_time)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, transaction.getTransactionId());
            ps.setInt(2, transaction.getAccountId());
            ps.setString(3, transaction.getType().name());
            ps.setInt(4, transaction.getAmount());
            ps.setTimestamp(5, transaction.getTransactionTime());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error (saveTransaction): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error in saveTransaction(): " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Transaction> getTransactionHistory(int accountId) {
        List<Transaction> transactions = new LinkedList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ? ORDER BY transaction_time DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, accountId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(rs.getString("transaction_id"));
                    transaction.setAccountId(rs.getInt("account_id"));
                    transaction.setType(TransactionType.valueOf(rs.getString("type")));
                    transaction.setAmount(rs.getInt("amount"));
                    transaction.setTransactionTime(rs.getTimestamp("transaction_time"));
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error (getTransactionHistory): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error in getTransactionHistory(): " + e.getMessage());
        }
        return transactions;
    }
}
