package com.masolria.repository.Jdbc;

import com.masolria.entity.Audit;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static com.masolria.repository.Jdbc.Queries.*;

@Repository
@RequiredArgsConstructor
public class JdbcAuditRepository {
    private final DriverManagerDataSource datasource;

    public Optional<Audit> findById(Long id) {
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AUDIT_FIND_BY_ID)) {
            preparedStatement.setLong(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(Audit.builder().
                            userEmail(rs.getString("user_email"))
                            .method(rs.getString("message"))
                            .whenExecuted(rs.getTimestamp("when_executed").toLocalDateTime())
                            .build()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Audit> findAll() {
        try (Connection connection = datasource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(AUDIT_FIND_ALL);
            List<Audit> audits = new ArrayList<>();
            while (rs.next()) {
                Audit audit = Audit.builder().id(rs.getLong("id"))
                        .userEmail(rs.getString("user_email"))
                        .method(rs.getString("message"))
                        .whenExecuted(rs.getTimestamp("when_executed").toLocalDateTime())
                        .build();
                audits.add(audit);
            }
            rs.close();
            return audits;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Audit save(Audit audit) {
        Audit saved;
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AUDIT_SAVE, Statement.RETURN_GENERATED_KEYS)) {


            preparedStatement.setString(1, audit.getUserEmail());
            preparedStatement.setString(2, audit.getMethod());
            preparedStatement.setString(3, audit.getAuditType().name());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(audit.getWhenExecuted()));

            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    saved = Audit.builder().id((rs.getLong(1)))
                            .method(audit.getMethod())
                            .userEmail(audit.getUserEmail())
                            .whenExecuted(audit.getWhenExecuted())
                            .build();
                } else {
                    throw new SQLException("Creating booking failed, no ID obtained.");
                }
            }
            return saved;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}