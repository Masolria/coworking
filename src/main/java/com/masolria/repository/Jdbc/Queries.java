package com.masolria.repository.Jdbc;

public class Queries {
    protected static final String SPACE_INSERT = """
            INSERT INTO coworking_schema.space(location, space_type)
            VALUES(?,?::space_type)
            """;
    protected static final String SPACE_UPDATE = """
            UPDATE coworking_schema.space
            SET location = ?, space_type = ?::space_type
            WHERE id = ?
            """;
    protected static final String SPACE_DELETE = """
            DELETE FROM coworking_schema.space
            WHERE id = ?""";
    protected static final String SPACE_FIND_ALL = """
            SELECT * FROM coworking_schema.space;
            """;
    protected static final String USER_DELETE = """
            DELETE FROM coworking_schema.users WHERE id = ?
            """;
    protected static final String USER_FIND_ALL = """
            SELECT * FROM coworking_schema.users;
            """;
    protected static final String USER_FIND_BY_ID = """
            SELECT * FROM coworking_schema.users WHERE id = ?;
            """;
    protected static final String USER_SAVE = """
            INSERT INTO coworking_schema.users(email,password) VALUES (?, ?)
            """;
    protected static final String USER_UPDATE = """
            UPDATE coworking_schema.users
            SET email = ?, password = ?
            WHERE id = ?
            """;
    protected static final String BOOKING_DELETE = """
            DELETE FROM coworking_schema.booking WHERE id = ?
            """;
    protected static final String BOOKING_FIND_BY_ID = """
            SELECT * FROM coworking_schema.booking WHERE id = ?;
            """;
    protected static final String BOOKING_FIND_ALL = """
            SELECT * FROM coworking_schema.booking;
            """;
    protected static final String BOOKING_UPDATE = """
            UPDATE coworking_schema.booking
            SET is_Booked = ?,
            time_start = ?,
            time_end = ?,
            space_id = ?,
            for_user_id = ?
            WHERE id = ? ;
            """;
    protected static final String BOOKING_SAVE = """
            INSERT INTO coworking_schema.booking(is_booked,time_start,time_end,space_id,for_user_id)
            VALUES(?,?,?,?,?);
            """;
}
