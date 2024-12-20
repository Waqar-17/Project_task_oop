package com.example.demo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.*;
import java.util.Scanner;



public class BooksManagement {






    public static void addBook() {
        Stage addBookStage = new Stage();
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        File file2 = new File("Book.txt");
        Alert alert = new Alert(Alert.AlertType.WARNING);

        Label bookLabel = new Label("Enter Book Title:");
        TextField bookField = new TextField();
        Label bookIdLabel = new Label("Enter Book ID:");
        TextField bookIdField = new TextField();
        Label authorNameLabel = new Label("Enter Author Name:");
        TextField authorNameField = new TextField();

        Label isAvailableLabel = new Label("Is Available:");
        ToggleGroup availabilityGroup = new ToggleGroup();
        RadioButton availableButton = new RadioButton("Yes");
        availableButton.setToggleGroup(availabilityGroup);
        RadioButton notAvailableButton = new RadioButton("No");
        notAvailableButton.setToggleGroup(availabilityGroup);

        Button addButton = new Button("Add Book");

        addButton.setOnAction(e -> {
            String bookTitle = bookField.getText().trim();
            String bookID = bookIdField.getText().trim();
            String authorName = authorNameField.getText().trim();
            RadioButton selectedButton = (RadioButton) availabilityGroup.getSelectedToggle();
            String isAvailable = selectedButton != null ? selectedButton.getText() : "No";

            if (!bookTitle.isEmpty() && !bookID.isEmpty() && !authorName.isEmpty()) {
                if (isBookIdDuplicate(file2, bookID)) {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setHeaderText("Duplicate Book ID");
                    alert.setContentText("The Book ID already exists. Please use a different ID.");
                    alert.show();
                    return;
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file2, true))) {

                    writer.write(bookID + "," + bookTitle + "," + authorName + "," + isAvailable);
                    writer.newLine();

                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Book Added Successfully");
                    alert.show();
                    addBookStage.close();
                } catch (IOException ex) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setHeaderText("Error Adding Book");
                    alert.setContentText(ex.getMessage());
                    alert.show();
                }
            } else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText("All fields must be filled out");
                alert.show();
            }
        });

        BackgroundImage backgroundImage1 = new BackgroundImage(
                new Image("Icon 1/123456.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        layout.setBackground(new Background(backgroundImage1));

        HBox availabilityBox = new HBox(10);
        availabilityBox.setAlignment(Pos.CENTER);
        availabilityBox.getChildren().addAll(isAvailableLabel, availableButton, notAvailableButton);

        layout.getChildren().addAll(
                bookLabel, bookField,
                bookIdLabel, bookIdField,
                authorNameLabel, authorNameField,
                availabilityBox,
                addButton
        );

        Scene scene = new Scene(layout, 699, 450);
        addBookStage.setTitle("Add Book");
        addBookStage.setScene(scene);
        addBookStage.show();
    }


    private static boolean isBookIdDuplicate(File file, String bookID) {
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(bookID)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }




    public static void showBooks() throws FileNotFoundException {
        Stage showBookStage = new Stage();
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        box.setSpacing(20);

        BackgroundImage backgroundImage1 = new BackgroundImage(
                new Image("Icon 1/123456.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        box.setBackground(new Background(backgroundImage1));

        Label heading = new Label("Book List");
        heading.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        box.getChildren().add(heading);


        TableView<Book> tableView = new TableView<>();


        TableColumn<Book, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> availabilityColumn = new TableColumn<>("Available");
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));


        tableView.getColumns().addAll(idColumn, titleColumn, authorColumn, availabilityColumn);


        ObservableList<Book> bookList = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader("Book.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] bookDetails = line.split(",");
                if (bookDetails.length == 4) {
                    bookList.add(new Book(bookDetails[0], bookDetails[1], bookDetails[2], bookDetails[3]));
                }
            }
        } catch (IOException e) {
            Label errorLabel = new Label("Error reading the book file.");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            box.getChildren().add(errorLabel);
        }

        tableView.setItems(bookList);
        tableView.setPrefWidth(650);


        box.getChildren().add(tableView);

        ScrollPane scrollPane = new ScrollPane(box);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 699, 450);
        showBookStage.setTitle("Show Books");
        showBookStage.setScene(scene);
        showBookStage.show();
    }


    public static class Book {
        private String id;
        private String title;
        private String author;
        private String availability;

        public Book(String id, String title, String author, String availability) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.availability = availability;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getAvailability() {
            return availability;
        }
    }




    public static void borrowBook(String username) {
        Stage borrowBookStage = new Stage();
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label instructions = new Label("Enter the Book ID to Borrow:");
        instructions.setStyle("-fx-font-size: 14px;");

        TextField bookIdField = new TextField();
        bookIdField.setPromptText("Book ID");

        Button borrowButton = new Button("Borrow Book");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        borrowButton.setOnAction(e -> {
            String bookIDToBorrow = bookIdField.getText().trim();
            if (bookIDToBorrow.isEmpty()) {
                alert.setHeaderText("Error");
                alert.setContentText("Book ID cannot be empty.");
                alert.show();
                return;
            }

            File bookFile = new File("Book.txt");
            File tempFile = new File("TempBook.txt");
            boolean bookFound = false;

            try (BufferedReader reader = new BufferedReader(new FileReader(bookFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] bookDetails = line.split(",");
                    if (bookDetails.length == 4) {
                        String bookID = bookDetails[0];
                        String bookTitle = bookDetails[1];
                        String authorName = bookDetails[2];
                        String isAvailable = bookDetails[3];

                        if (bookID.equals(bookIDToBorrow) && isAvailable.equalsIgnoreCase("Yes")) {
                            writer.write(bookID + "," + bookTitle + "," + authorName + ",No");
                            writer.newLine();
                            bookFound = true;
                        } else {
                            writer.write(line);
                            writer.newLine();
                        }
                    }
                }
            } catch (IOException ex) {
                alert.setHeaderText("Error");
                alert.setContentText("An error occurred while borrowing the book.");
                alert.show();
                return;
            }

            if (bookFound) {
                if (bookFile.delete() && tempFile.renameTo(bookFile)) {
                    alert.setHeaderText("Success");
                    alert.setContentText("Book borrowed successfully.");
                    alert.show();
                    borrowBookStage.close();
                } else {
                    alert.setHeaderText("Error");
                    alert.setContentText("Could not update the book file.");
                    alert.show();
                }
            } else {
                tempFile.delete();
                alert.setHeaderText("Error");
                alert.setContentText("Book not found or already borrowed.");
                alert.show();
            }
        });

        layout.getChildren().addAll(instructions, bookIdField, borrowButton);

        Scene scene = new Scene(layout, 400, 200);
        borrowBookStage.setTitle("Borrow Book");
        borrowBookStage.setScene(scene);
        borrowBookStage.show();
    }

    public static void returnBook(String username) {
        Stage returnBookStage = new Stage();
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label instructions = new Label("Enter the Book ID to Return:");
        instructions.setStyle("-fx-font-size: 14px;");

        TextField bookIdField = new TextField();
        bookIdField.setPromptText("Book ID");

        Button returnButton = new Button("Return Book");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        returnButton.setOnAction(e -> {
            String bookIDToReturn = bookIdField.getText().trim();
            if (bookIDToReturn.isEmpty()) {
                alert.setHeaderText("Error");
                alert.setContentText("Book ID cannot be empty.");
                alert.show();
                return;
            }

            File bookFile = new File("Book.txt");
            File tempFile = new File("TempBook.txt");

            if (!bookFile.exists()) {
                alert.setHeaderText("Error");
                alert.setContentText("Book file not found.");
                alert.show();
                return;
            }

            boolean bookFound = false;
            boolean bookAlreadyAvailable = false;

            try (BufferedReader reader = new BufferedReader(new FileReader(bookFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] bookDetails = line.split(",");
                    if (bookDetails.length == 4) {
                        String bookID = bookDetails[0];
                        String bookTitle = bookDetails[1];
                        String authorName = bookDetails[2];
                        String isAvailable = bookDetails[3];

                        if (bookID.equals(bookIDToReturn)) {
                            bookFound = true;
                            if (isAvailable.equalsIgnoreCase("No")) {
                                // Mark the book as available
                                writer.write(bookID + "," + bookTitle + "," + authorName + ",Yes");
                                writer.newLine();
                            } else {
                                bookAlreadyAvailable = true;
                                writer.write(line);
                                writer.newLine();
                            }
                        } else {
                            writer.write(line);
                            writer.newLine();
                        }
                    }
                }
            } catch (IOException ex) {
                alert.setHeaderText("Error");
                alert.setContentText("An error occurred while processing the book file.");
                alert.show();
                tempFile.delete();
                return;
            }

            if (bookFound) {
                if (bookAlreadyAvailable) {
                    tempFile.delete();
                    alert.setHeaderText("Error");
                    alert.setContentText("Book is already available and cannot be returned.");
                    alert.show();
                } else {
                    if (bookFile.delete() && tempFile.renameTo(bookFile)) {
                        alert.setHeaderText("Success");
                        alert.setContentText("Book returned successfully.");
                        alert.show();
                        returnBookStage.close();
                    } else {
                        alert.setHeaderText("Error");
                        alert.setContentText("Could not update the book file.");
                        alert.show();
                    }
                }
            } else {
                tempFile.delete();
                alert.setHeaderText("Error");
                alert.setContentText("Book not found in the system.");
                alert.show();
            }
        });

        layout.getChildren().addAll(instructions, bookIdField, returnButton);

        Scene scene = new Scene(layout, 400, 200);
        returnBookStage.setTitle("Return Book");
        returnBookStage.setScene(scene);
        returnBookStage.show();
    }



    public static void searchBook() {
        Stage searchBookStage = new Stage();
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        BackgroundImage backgroundImage1 = new BackgroundImage(
                new Image("Icon 1/123456.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        layout.setBackground(new Background(backgroundImage1));

        Label instructions = new Label("Search for a Book by  ID :");
        instructions.setStyle("-fx-font-size: 14px;");

        TextField searchField = new TextField();
        searchField.setPromptText("Enter Title, ID, or Author");

        Button searchButton = new Button("Search");

        VBox resultBox = new VBox(10);
        resultBox.setAlignment(Pos.CENTER);

        ScrollPane resultScrollPane = new ScrollPane(resultBox);
        resultScrollPane.setFitToWidth(true);

        searchButton.setOnAction(e -> {
            String searchTerm = searchField.getText().trim().toLowerCase();
            resultBox.getChildren().clear();

            if (searchTerm.isEmpty()) {
                Label errorLabel = new Label("Search field cannot be empty.");
                errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                resultBox.getChildren().add(errorLabel);
                return;
            }

            File bookFile = new File("Book.txt");

            if (!bookFile.exists()) {
                Label errorLabel = new Label("Book file not found.");
                errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                resultBox.getChildren().add(errorLabel);
                return;
            }

            boolean matchFound = false;

            try (BufferedReader reader = new BufferedReader(new FileReader(bookFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] bookDetails = line.split(",");
                    if (bookDetails.length == 4) {
                        String bookID = bookDetails[0].toLowerCase();
                        String bookTitle = bookDetails[1].toLowerCase();
                        String authorName = bookDetails[2].toLowerCase();
                        String isAvailable = bookDetails[3];

                        if (bookID.equals(searchTerm) ) {
                            matchFound = true;
                            Label bookInfo = new Label(
                                    "ID: " + bookDetails[0] + " | Title: " + bookDetails[1] +
                                            " | Author: " + bookDetails[2] + " | Available: " + isAvailable
                            );
                            bookInfo.setStyle("-fx-font-size: 14px;");
                            resultBox.getChildren().add(bookInfo);
                        }
                    }
                }
            } catch (IOException ex) {
                Label errorLabel = new Label("An error occurred while reading the book file.");
                errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                resultBox.getChildren().add(errorLabel);
                return;
            }

            if (!matchFound) {
                Label noResultsLabel = new Label("No books found matching the search term.");
                noResultsLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
                resultBox.getChildren().add(noResultsLabel);
            }
        });

        layout.getChildren().addAll(instructions, searchField, searchButton, resultScrollPane);

        Scene scene = new Scene(layout, 699, 450);
        searchBookStage.setTitle("Search Book");
        searchBookStage.setScene(scene);
        searchBookStage.show();
    }



}