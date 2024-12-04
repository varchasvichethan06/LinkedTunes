package linkedtunes;
import java.sql.*;
import java.util.Scanner;

public class Album {
    private int albumId;
    private String albumName;

    public Album(int albumId, String albumName) {
        this.albumId = albumId;
        this.albumName = albumName;
    }

    public void printAllAlbums() {

            Connection connection = Database.getConnection();
            if (connection != null) {
                try {
                    String sql = "SELECT * FROM albums";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);

                    boolean albumsExist = false;


                    // Check if any albums are present
                    while (resultSet.next()) {
                        int albumId = resultSet.getInt("album_id");
                        String albumName = resultSet.getString("album_name");
                        System.out.println(albumId + " - " + albumName);
                        albumsExist = true;
                    }

                    if (!albumsExist) {
                        System.out.println("No albums available.");

                    }

                } catch (SQLException e) {
                    System.out.println("Error listing albums: " + e.getMessage());
                } finally {
                    Database.closeConnection(connection);
                }
            }


    }

    public void printAllSongsInAlbum() {
            printAllAlbums();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter album ID to list songs:");
            int albumId = scanner.nextInt();

            // Open database connection
            Connection connection = Database.getConnection();
            if (connection != null) {
                try {
                    // Query to retrieve all songs in the specified album
                    String sql = "SELECT * FROM songs WHERE album_id = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, albumId);  // Set the album_id parameter
                    ResultSet resultSet = statement.executeQuery();

                    System.out.println("Songs in album ID " + albumId + ":");
                    boolean songsExist = false;
                    while (resultSet.next()) {
                        int songId = resultSet.getInt("song_id");
                        String songName = resultSet.getString("song_name");
                        String songDuration = resultSet.getString("song_duration");
                        System.out.println(songId + " - " + songName + " (" + songDuration + ")");
                        songsExist = true;
                    }

                    if (!songsExist) {
                        System.out.println("No songs found for this album.");
                    }

                } catch (SQLException e) {
                    System.out.println("Error listing songs in album: " + e.getMessage());
                } finally {
                    Database.closeConnection(connection);
                }
            }


    }



    public void createAlbum() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter album name to create:");
        String albumName = scanner.nextLine();

        Connection connection = Database.getConnection();
        if (connection != null) {
            try {
                String sql = "INSERT INTO albums (album_name) VALUES (?)";
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, albumName);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet rs = statement.getGeneratedKeys();
                    if (rs.next()) {
                        int albumId = rs.getInt(1);
                        System.out.println("Album created with ID: " + albumId);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error creating album: " + e.getMessage());
            } finally {
                Database.closeConnection(connection);
            }
        }
    }

    public void addSongToAlbum() {
        printAllAlbums();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter album ID to add song to:");
        int albumId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.println("Enter song name:");
        String songName = scanner.nextLine();
        System.out.println("Enter song duration:");
        String songDuration = scanner.nextLine();

        Connection connection = Database.getConnection();
        if (connection != null) {
            try {
                String sql = "INSERT INTO songs (album_id, song_name, song_duration) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, albumId);
                statement.setString(2, songName);
                statement.setString(3, songDuration);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Song added to album.");
                }
            } catch (SQLException e) {
                System.out.println("Error adding song: " + e.getMessage());
            } finally {
                Database.closeConnection(connection);
            }
        }
    }

    public void removeAlbum() {
        printAllAlbums();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter album ID to remove:");
        int albumId = scanner.nextInt();

        Connection connection = Database.getConnection();
        if (connection != null) {
            try {
                String sql = "DELETE FROM albums WHERE album_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, albumId);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Album removed.");
                }
            } catch (SQLException e) {
                System.out.println("Error removing album: " + e.getMessage());
            } finally {
                Database.closeConnection(connection);
            }
        }
    }

    public void removeSongFromAlbum() {
        printAllAlbums();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter album ID to remove song from:");
        int albumId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.println("Enter song ID to remove:");
        int songId = scanner.nextInt();

        Connection connection = Database.getConnection();
        if (connection != null) {
            try {
                String sql = "DELETE FROM songs WHERE album_id = ? AND song_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, albumId);
                statement.setInt(2, songId);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Song removed from album.");
                }
            } catch (SQLException e) {
                System.out.println("Error removing song from album: " + e.getMessage());
            } finally {
                Database.closeConnection(connection);
            }
        }
    }
}
