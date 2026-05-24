package in.mirai.bms.daoimpl;

import in.mirai.bms.dao.AccountDAO;
import in.mirai.bms.model.Account;
import in.mirai.bms.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AccountDAOImpl implements AccountDAO
{
    @Override
    public boolean createAccount(Account account)
    {
        // Query
        String sql = "insert into accounts(account_id, holder_name, balance, pin, created_at) values (?,?,?,?,?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, account.getAccountId());
            ps.setString(2, account.getHolderName());
            ps.setInt(3, account.getBalance());
            ps.setInt(4, account.getPin());
            ps.setTimestamp(5, account.getCreatedAt());
            ps.addBatch();

            int[] rows = ps.executeBatch();

            if ( rows.length == 5 )
                return true;
        }
        catch (SQLException e)
        {
            System.out.println("SQL Error: " + e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println("Server Error in AccountDAOImpl.createAccount : " + e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<Account> getAccountById(int accountId, int pin)
    {

        String sql = """
            SELECT account_id,
                   holder_name,
                   balance,
                   created_at
            FROM accounts
            WHERE account_id = ?
            AND pin = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {

            ps.setInt(1, accountId);
            ps.setInt(2, pin);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Account account = new Account();

                    account.setAccountId(rs.getInt("account_id"));

                    account.setHolderName(rs.getString("holder_name"));

                    account.setBalance(rs.getInt("balance"));

                    account.setCreatedAt(rs.getTimestamp("created_at"));

                    return Optional.of(account);
                }
            }

        }
        catch (SQLException e)
        {
            System.out.println("SQL Error: " + e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println("Server Error in AccountDAOImpl.getAccountById : " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean updateBalance(int accountId, int amount, int pin)
    {
        String sql = """
            UPDATE accounts
            SET balance = ?
            WHERE account_id = ?
            AND pin = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, amount);
            ps.setInt(2, accountId);
            ps.setInt(3, pin);

            int rows = ps.executeUpdate();

            return rows != 0;

        }
        catch (SQLException e)
        {
            System.out.println("SQL Error: " + e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println("Server Error in AccountDAOImpl.updateBalance : " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean transfer(int senderId,
                            int receiverId,
                            int amount,
                            int pin)
    {

        String withdrawSql = """
            UPDATE accounts
            SET balance = balance - ?
            WHERE account_id = ?
            AND pin = ?
            AND balance >= ?
            """;

        String depositSql = """
            UPDATE accounts
            SET balance = balance + ?
            WHERE account_id = ?
            """;

        Connection connection = null;

        try {

            connection =
                    DBConnection.getConnection();

            connection.setAutoCommit(false);

            try (PreparedStatement psWithdraw =
                         connection.prepareStatement(
                                 withdrawSql);

                 PreparedStatement psDeposit =
                         connection.prepareStatement(
                                 depositSql))
            {

                // Withdraw from sender
                psWithdraw.setInt(1, amount);
                psWithdraw.setInt(2, senderId);
                psWithdraw.setInt(3, pin);
                psWithdraw.setInt(4, amount);

                int withdrawRows =
                        psWithdraw.executeUpdate();

                if (withdrawRows == 0)
                {
                    System.out.println(
                            "Invalid PIN or "
                                    + "Insufficient Balance!"
                    );

                    connection.rollback();
                    return false;
                }

                // Deposit to receiver
                psDeposit.setInt(1, amount);
                psDeposit.setInt(2, receiverId);

                int depositRows =
                        psDeposit.executeUpdate();

                if (depositRows == 0)
                {
                    System.out.println(
                            "Receiver Account "
                                    + "Not Found!"
                    );

                    connection.rollback();
                    return false;
                }

                connection.commit();
                return true;
            }

        }
        catch (SQLException e)
        {
            try {
                if (connection != null)
                {
                    connection.rollback();
                }
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }

            System.out.println(
                    "SQL Error: "
                            + e.getMessage()
            );
        }
        catch (Exception e)
        {
            System.out.println(
                    "Server Error in "
                            + "AccountDAOImpl.transfer(): "
                            + e.getMessage()
            );
        }

        return false;
    }
}
