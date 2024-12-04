package linkedtunes;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean quit = false;
        Album album = new Album(0, ""); // Temporary album for calling methods

        while (!quit) {
            showActions();
            System.out.println("Enter action:");
            int action = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (action) {
                case 0 -> quit = true;
                case 1 -> album.printAllAlbums();
                case 2 -> album.printAllSongsInAlbum();
                case 3 -> album.createAlbum();
                case 4 -> album.addSongToAlbum();
                case 5 -> album.removeAlbum();
                case 6 -> album.removeSongFromAlbum();
                case 9 -> showActions();
                default -> System.out.println("Invalid action. Please try again.");
            }
        }
    }

    private static void showActions() {
        System.out.println("Music Application Actions:");
        System.out.println("0 - Quit");
        System.out.println("1 - List all albums");
        System.out.println("2 - List all songs in a specific album");
        System.out.println("3 - Create a new album");
        System.out.println("4 - Add a new song to an album");
        System.out.println("5 - Remove an album");
        System.out.println("6 - Remove a song from a specific album");
        System.out.println("9 - Show available actions");
    }
}
