package org.boardtask.app.migration;

import java.sql.Connection;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

public class LiquibaseConfig {
    private final Connection connection;

    public LiquibaseConfig(final Connection connection) {
        this.connection = connection;
    }

    public void migrate() {
        var changeLogFile = "/db/changelog/db.changelog-master.yaml";
        var jdbcConnection = new JdbcConnection(connection);
        try (var liquibase = new Liquibase(changeLogFile,
                new ClassLoaderResourceAccessor(),
                jdbcConnection)) {
            liquibase.update();
        } catch (LiquibaseException e) {
            e.printStackTrace();
        }
    }
}
