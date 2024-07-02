package com.masolria.db;

import com.masolria.util.PropertiesUtil;
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

/**
 * The Liquibase migration runner.
 */
@RequiredArgsConstructor
public class LiquibaseRunner {
    /**
     * The field configures the connection to the database
     */
    private  final ConnectionManager cManager;

    /**
     * Runs migration from db.changelog directory. Sets default schema for migration work tables
     */
    public void runMigration(){
        try(Connection connection = cManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE SCHEMA IF NOT EXISTS migration")){
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            preparedStatement.execute();
            database.setLiquibaseSchemaName("migration");
            Liquibase liquibase = new Liquibase(PropertiesUtil.getProperty("liquibase.changelogFile"),
                    new ClassLoaderResourceAccessor(),database);
            liquibase.update();
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }
}