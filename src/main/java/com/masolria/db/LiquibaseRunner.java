package com.masolria.db;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
@RequiredArgsConstructor
public class LiquibaseRunner {
    private  final ConnectionManager cManager;
    public void runMigration(){
        try(Connection connection = cManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE SCHEMA IF NOT EXISTS migration")){
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            preparedStatement.execute();
            database.setLiquibaseSchemaName("migration");
            Liquibase liquibase = new Liquibase("db/changelog/db.changelog-master.yaml",
                    new ClassLoaderResourceAccessor(),database);
            liquibase.update();
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }
}