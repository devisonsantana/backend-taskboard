package org.boardtask.app;

import org.boardtask.app.dbconnect.ConnectionConfig;
import org.boardtask.app.migration.LiquibaseConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskBoardAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TaskBoardAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var connection = ConnectionConfig.getConnection();
		new LiquibaseConfig(connection).migrate();
	}

}
