package com.masolria.repository;

import com.masolria.entity.Booking;
import com.masolria.entity.Space;
import com.masolria.entity.SpaceType;
import com.masolria.entity.User;
import com.masolria.repository.Jdbc.JdbcBookingRepository;
import com.masolria.repository.Jdbc.JdbcSpaceRepository;
import com.masolria.repository.Jdbc.JdbcUserRepository;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostgresTestContainer {
    @Container
    private final static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName
            .parse("postgres:16"));


    @BeforeAll
    static void beforeAll() {
        postgres.start();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(postgres.getUsername());
        dataSource.setUrl(postgres.getJdbcUrl());
        dataSource.setPassword(postgres.getPassword());
        dataSource.setDriverClassName(postgres.getDriverClassName());

       runMigration(dataSource);


    }
    private static void runMigration(DriverManagerDataSource dataSource){

        try(Connection connection = dataSource.getConnection();
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

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }


    @AfterEach
    void tearDown() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(PostgresTestContainer.postgres.getUsername());
        dataSource.setUrl(PostgresTestContainer.postgres.getJdbcUrl());
        dataSource.setPassword(PostgresTestContainer.postgres.getPassword());
        dataSource.setDriverClassName(PostgresTestContainer.postgres.getDriverClassName());
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("TRUNCATE TABLE  coworking_schema.booking;");
            statement.execute("TRUNCATE TABLE coworking_schema.space;");
            statement.execute("TRUNCATE TABLE  coworking_schema.users;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Nested
    class JdbcSpaceRepositoryTest {
        Space given;
        Space given2;
        JdbcSpaceRepository jdbcSpaceRepository;


        @BeforeEach
        void setupTestData() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();

            dataSource.setUsername(PostgresTestContainer.postgres.getUsername());
            dataSource.setUrl(PostgresTestContainer.postgres.getJdbcUrl());
            dataSource.setPassword(PostgresTestContainer.postgres.getPassword());
            dataSource.setDriverClassName(PostgresTestContainer.postgres.getDriverClassName());
            jdbcSpaceRepository = new JdbcSpaceRepository(dataSource);
            given = Space.builder().location("unknown").spaceType(SpaceType.CONFERENCE_HALL).build();
            given2 = Space.builder().location("unknown2").spaceType(SpaceType.WORKING_SPACE).build();
        }

        @Test
        @DisplayName("test checks for correct saving space")
        void save() {
            Space saved = jdbcSpaceRepository.save(given);
            assertThat(saved).isEqualTo(given);
            assertThat(saved.getId()).isNotNull();
        }

        @Test
        @DisplayName("test verifies correct updating space")
        void update() {
            Space saved = jdbcSpaceRepository.save(given);
            saved.setLocation("new location");
            saved.setSpaceType(SpaceType.WORKING_SPACE);
            Space updated = jdbcSpaceRepository.update(saved);
            assertThat(updated.getLocation()).isEqualTo("new location");
            assertThat(updated.getSpaceType()).isEqualTo(SpaceType.WORKING_SPACE);
            assertThat(updated.getId()).isNotNull();
        }

        @Test
        @DisplayName("test checks for correct deletion space")
        void delete() {
            Space saved = jdbcSpaceRepository.save(given2);
            jdbcSpaceRepository.delete(saved);
            Optional<Space> emptyOptional = jdbcSpaceRepository.findById(saved.getId());
            assertThat(emptyOptional).isEmpty();
        }

        @Test
        @DisplayName("test verifies space is found when exists in the table")
        void findById() {
            Space saved = jdbcSpaceRepository.save(given2);
            Optional<Space> foundSpace = jdbcSpaceRepository.findById(saved.getId());
            assertThat(foundSpace).isNotEmpty();
            assertThat(foundSpace.get()).isEqualTo(saved);
        }

        @Test
        @DisplayName("test verifies all spaces found if saved in the table")
        void findAll() {
            jdbcSpaceRepository.save(given);
            jdbcSpaceRepository.save(given2);
            List<Space> spaces = jdbcSpaceRepository.findAll();
            assertThat(spaces).isNotEmpty();
            assertThat(spaces).hasSize(2);
        }
    }
    @Nested
    class JdbcUserRepositoryTest {
        JdbcUserRepository jdbcUserRepository;
        User given = User.builder().email("some@mail.com").password("password").build();

        @BeforeEach
        void setupTestData() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();

            dataSource.setUsername(PostgresTestContainer.postgres.getUsername());
            dataSource.setUrl(PostgresTestContainer.postgres.getJdbcUrl());
            dataSource.setPassword(PostgresTestContainer.postgres.getPassword());
            dataSource.setDriverClassName(PostgresTestContainer.postgres.getDriverClassName());
            jdbcUserRepository = new JdbcUserRepository(dataSource);
        }

        @Test
        @DisplayName("test checks for correct saving user")
        void save() {
            User saved = jdbcUserRepository.save(given);
            assertThat(given.getId()).isNotNull();
            assertThat(saved).isEqualTo(given);
        }

        @Test
        @DisplayName("test verifies correct updating user")
        void update() {
            User saved = jdbcUserRepository.save(given);
            saved.setEmail("new@mail.com");
            User updated = jdbcUserRepository.update(saved);
            assertThat(updated.getId()).isNotNull();
            assertThat(updated.getEmail()).isEqualTo(saved.getEmail());
            assertThat(updated).isEqualTo(saved).isEqualTo(given);
        }

        @Test
        @DisplayName("test checks for correct deletion user")
        void delete() {
            User saved = jdbcUserRepository.save(given);
            jdbcUserRepository.delete(saved);
            Optional<User> findDeleted = jdbcUserRepository.findById(saved.getId());
            assertThat(findDeleted).isEmpty();
        }

        @Test
        @DisplayName("test verifies user is found when exists in the table")
        void findById() {
            User saved = jdbcUserRepository.save(given);
            Optional<User> optional = jdbcUserRepository.findById(saved.getId());
            assertThat(optional).isNotEmpty();
            assertThat(optional.get()).isEqualTo(saved);
        }

        @Test
        @DisplayName("test verifies all users found if saved in the table")
        void findAll() {
            User user1 = User.builder().email("email@mail.ru").password("password").build();
            jdbcUserRepository.save(user1);
            jdbcUserRepository.save(given);
            List<User> users = jdbcUserRepository.findAll();
            assertThat(users).isNotEmpty();
            assertThat(users).hasSize(2);
        }
    }

    @Nested
    class JdbcBookingRepositoryTest {
        Booking given;
        JdbcBookingRepository jdbcBookingRepository;

        @BeforeEach
        void setupTestData() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();

            dataSource.setUsername(PostgresTestContainer.postgres.getUsername());
            dataSource.setUrl(PostgresTestContainer.postgres.getJdbcUrl());
            dataSource.setPassword(PostgresTestContainer.postgres.getPassword());
            dataSource.setDriverClassName(PostgresTestContainer.postgres.getDriverClassName());
            jdbcBookingRepository = new JdbcBookingRepository(dataSource);
             given = Booking.builder()
                    .isBooked(true)
                    .spaceId(1L)
                    .timeEnd(LocalDateTime.of(2024, 6, 28, 12, 0))
                    .timeStart(LocalDateTime.of(2024, 6, 28, 13, 0))
                    .forUserId(11L)
                    .build();

        }

        @Test
        @DisplayName("test checks for correct deletion space")
        void delete() {
            jdbcBookingRepository.save(given);
            jdbcBookingRepository.delete(given);
            Optional<Booking> found = jdbcBookingRepository.findById(given.getId());
            assertThat(given.getId()).isNotNull();
            assertThat(found).isEmpty();
        }

        @Test
        @DisplayName("test verifies booking is found when exists in the table")
        void findById() {
            Booking saved = jdbcBookingRepository.save(given);
            Optional<Booking> found = jdbcBookingRepository.findById(saved.getId());
            assertThat(found).isNotEmpty();
            assertThat(found.get()).isEqualTo(saved);
        }

        @Test
        @DisplayName("test verifies all bookings found if saved in the table")
        void findAll() {
            Booking given2 = Booking.builder()
                    .timeStart(LocalDateTime.of(2024, 6, 29, 12, 0))
                    .timeEnd(LocalDateTime.of(2024, 6, 29, 14, 0))
                    .isBooked(true)
                    .spaceId(3L)
                    .forUserId(13L)
                    .build();
            List<Booking> givenList = List.of(given, given2);
            jdbcBookingRepository.save(given);
            jdbcBookingRepository.save(given2);
            List<Booking> found = jdbcBookingRepository.findAll();
            assertThat(found).hasSize(2);
            assertThat(givenList).isEqualTo(found);
        }

        @Test
        @DisplayName("test verifies correct updating booking")
        void update() {
            Booking booking = Booking.builder()
                    .isBooked(true)
                    .spaceId(1L)
                    .timeEnd(LocalDateTime.of(2024, 6, 28, 12, 0))
                    .timeStart(LocalDateTime.of(2024, 6, 28, 13, 0))
                    .forUserId(11L)
                    .build();
            jdbcBookingRepository.save(booking);
            booking.setBooked(true);
            booking.setForUserId(3L);
            Booking updated = jdbcBookingRepository.update(booking);
            assertThat(updated).isEqualTo(booking);
        }

        @Test
        @DisplayName("test checks for correct saving booking")
        void save() {
            Booking booking = Booking.builder()
                    .isBooked(true)
                    .spaceId(1L)
                    .timeEnd(LocalDateTime.of(2024, 6, 28, 12, 0))
                    .timeStart(LocalDateTime.of(2024, 6, 28, 13, 0))
                    .forUserId(11L)
                    .build();
            Booking saved = jdbcBookingRepository.save(booking);
            assertThat(saved.getId()).isNotNull();
            assertThat(saved).isEqualTo(booking);
        }
    }
}
