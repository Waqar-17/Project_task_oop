package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert;


import java.io.*;


import static com.example.demo.BooksManagement.*;
import static com.example.demo.BooksManagement.showBooks;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {

            File file = new File("user1.ser");
    //        file.mkdir();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
           // alert.setHeaderText("This is a header text");
           // alert.setContentText("This is the content of the alert!");









            Label emailLabelone = new Label("Email: ");
            emailLabelone.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;-fx-text-fill: #ffffff");
            TextField emailfieldone =new TextField();
            emailfieldone.setStyle("-fx-padding: 10px; -fx-border-color: #0073e6;-fx-background-radius: 5px; -fx-border-width: 2px;-fx-font-size: 14px;");
            emailfieldone.setPromptText("Enter your email");



            Label passwordlabel = new Label("Password: ");
            passwordlabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

            PasswordField passwordfield = new PasswordField();
            passwordfield.setPromptText("Enter your password");
            passwordfield.setStyle("-fx-padding: 10px; -fx-background-radius: 5px; -fx-border-color: #0073e6; -fx-border-width: 2px; -fx-font-size: 14px;");










            Button loginbutton = new Button("Login", new ImageView(new Image("file:Icon 1/login.png"))); // Add correct path
            loginbutton.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: linear-gradient(#0073e6, #005bb5); -fx-background-radius: 30px; -fx-padding: 10px 20px; -fx-border-radius: 30px;");
            loginbutton.setOnMouseEntered(e -> loginbutton.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: linear-gradient(#005bb5, #0073e6); -fx-background-radius: 30px; -fx-padding: 10px 20px; -fx-border-radius: 30px;"));
            loginbutton.setOnMouseExited(e -> loginbutton.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: linear-gradient(#0073e6, #005bb5); -fx-background-radius: 30px; -fx-padding: 10px 20px; -fx-border-radius: 30px;"));


            loginbutton.setOnAction(e->{
                String emailfieldText = emailfieldone.getText();
                String passswordfieldText = passwordfield.getText();
                emailfieldText.trim();
                passswordfieldText.trim();

                try {
                    if(validity(file,emailfieldText,passswordfieldText)){

//                        alert.setHeaderText("Login Successfull!");
//                        alert.setContentText("");
//                        alert.show();

                        emailfieldone.clear();
                        passwordfield.clear();

                        Stage stage3 = new Stage();
                        GridPane pane = new GridPane();
                        VBox vbox = new VBox(20);
                        vbox.setPadding(new Insets(20));
                        vbox.setAlignment(Pos.CENTER);

                        Label welcomeLabel = new Label("Welcome to Library Management, " + emailfieldText + "!");
                        welcomeLabel.setStyle("-fx-font-size: 46px; -fx-font-weight: bold; -fx-text-fill: #82da27; -fx-background-color: #000000; -fx-background-radius: 10px;");


                        Button viewBooksButton = createStyledButton("View Books", "#4CAF50", "#357A38"); // Green
                        Button borrowBookButton = createStyledButton("Borrow Book", "#2196F3", "#1769AA"); // Blue
                        Button returnBookButton = createStyledButton("Return Book", "#FFC107", "#C79100"); // Yellow
                        Button addBookButton = createStyledButton("Add Book", "#9C27B0", "#6A0080"); // Purple
                        Button logoutButton = createStyledButton("Logout", "#F44336", "#AA2E25"); // Red
                        Button searchButton = createStyledButton("Search Book", "#82DA27FF", "#E91E63");

                        searchButton.setOnAction(t -> searchBook());

                        viewBooksButton.setOnAction(t -> {
                            try {
                                showBooks();
                            } catch (FileNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        });
                        borrowBookButton.setOnAction(t -> borrowBook(emailfieldText));
                        returnBookButton.setOnAction(t -> returnBook(emailfieldText));
                        addBookButton.setOnAction(t-> addBook());
                        logoutButton.setOnAction(t -> {
                            stage3.close();
                            stage.show();
                        });


                        vbox.getChildren().addAll(welcomeLabel, viewBooksButton, borrowBookButton, returnBookButton, addBookButton,searchButton, logoutButton);


                        BackgroundImage backgroundImage = new BackgroundImage(
                                new Image("Icon 1/Library_Book_532388_1366x768.jpg"),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
                        );
                        vbox.setBackground(new Background(backgroundImage));

                        Scene scene1 = new Scene(vbox, 1200, 620);
                        stage3.setTitle("Library Dashboard");
                        stage3.setScene(scene1);
                        stage.close();
                        stage3.show();

                    }
                    else{
                        alert.setHeaderText("Login Failed!");
                        alert.setContentText("Wrong Username or Password!");
                        alert.show();
                        emailfieldone.clear();
                        passwordfield.clear();
                        System.out.println("Wrong username or password");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }




            });










            Button signupbutton = new Button("Signup"); // Add correct path
            signupbutton.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: linear-gradient(#e60050, #b3003a); -fx-background-radius: 30px; -fx-padding: 10px 20px; -fx-border-radius: 30px;");
            signupbutton.setOnMouseEntered(e -> signupbutton.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: linear-gradient(#b3003a, #e60050); -fx-background-radius: 30px; -fx-padding: 10px 20px; -fx-border-radius: 30px;"));
            signupbutton.setOnMouseExited(e -> signupbutton.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: linear-gradient(#e60050, #b3003a); -fx-background-radius: 30px; -fx-padding: 10px 20px; -fx-border-radius: 30px;"));



            // ------------------------------------------------------------------
            signupbutton.setOnAction(e->{

               Stage stage2 = new Stage();


                Label emaillabel1 = new Label("Username: ");
                emaillabel1.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

                TextField emailfield1 = new TextField();
                emailfield1.setPromptText("Enter new username");
                emailfield1.setStyle("-fx-padding: 10px; -fx-background-radius: 5px; -fx-border-color: #0073e6; -fx-border-width: 2px; -fx-font-size: 14px;");

                Label passwordlabel1 = new Label("Password: ");
                passwordlabel1.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

                PasswordField passwordfield1 = new PasswordField();
                passwordfield1.setPromptText("Enter new password");
                passwordfield1.setStyle("-fx-padding: 10px; -fx-background-radius: 5px; -fx-border-color: #0073e6; -fx-border-width: 2px; -fx-font-size: 14px;");


                Button createaccount = new Button("Create Account");
                createaccount.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: linear-gradient(#e60050, #b3003a); -fx-background-radius: 30px; -fx-padding: 10px 20px; -fx-border-radius: 30px;");
                createaccount.setOnMouseEntered(t -> createaccount.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: linear-gradient(#b3003a, #e60050); -fx-background-radius: 30px; -fx-padding: 10px 20px; -fx-border-radius: 30px;"));
                createaccount.setOnMouseExited(t -> createaccount.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: linear-gradient(#e60050, #b3003a); -fx-background-radius: 30px; -fx-padding: 10px 20px; -fx-border-radius: 30px;"));

                // createaccount
                createaccount.setOnAction(t->{
                    String username = emailfield1.getText();
                    String password = passwordfield1.getText();
                    username.trim();
                    password.trim();
                    if(!(username.isEmpty()||password.isEmpty())) {
                        emailfield1.clear();
                        passwordfield1.clear();

                        try {
                            write(file, username, password);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        // alert.setContentText("You have successfully sign up!");
                        alert.setHeaderText("You have successfully sign up!");
                        alert.setContentText("");
                        alert.show();
                        stage2.close();
                    }
                    else{
                        alert.setHeaderText("Fill both");
                        alert.setContentText("");
                        alert.show();
                    }


                });




                GridPane gridpane = new GridPane();
                gridpane.setPadding(new Insets(20));
                gridpane.setHgap(20);
                gridpane.setVgap(20);
                gridpane.setAlignment(Pos.CENTER);


                gridpane.add(emaillabel1, 0, 0);
                gridpane.add(emailfield1, 1, 0);
                gridpane.add(passwordlabel1, 0, 1);
                gridpane.add(passwordfield1, 1, 1);
               // gridpane.add(loginbutton, 0, 2);
                gridpane.add(createaccount, 1, 2);



                StackPane stackPane1 = new StackPane();
                stackPane1.getChildren().add(gridpane);
                StackPane.setMargin(gridpane, new Insets(0,200,0,200));

                BackgroundImage backgroundImage1 = new BackgroundImage(
                        new Image("Icon 1/123456.png"),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
                );
                stackPane1.setBackground(new Background(backgroundImage1));


                Scene scene = new Scene(stackPane1, 800, 620);
                stage2.setTitle("Library Management System");
                stage2.setScene(scene);
                stage2.show();

















            });

            // GridPane layout
            GridPane grid = new GridPane();
            grid.setPadding(new Insets(20));
            grid.setHgap(20);
            grid.setVgap(20);
            grid.setAlignment(Pos.CENTER);


            grid.add(emailLabelone, 0, 0);
            grid.add(emailfieldone, 1, 0);
            grid.add(passwordlabel, 0, 1);
            grid.add(passwordfield, 1, 1);
            grid.add(loginbutton, 0, 2);
            grid.add(signupbutton, 1, 2);


            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(grid);
            StackPane.setMargin(grid, new Insets(0,0,0,290));


            BackgroundImage backgroundImage = new BackgroundImage(
                    new Image("Icon 1/Login background.png"),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            stackPane.setBackground(new Background(backgroundImage));

            // Scene setup
            Scene scene = new Scene(stackPane, 1200, 620);
            stage.setTitle("Library Management System");
            stage.setScene(scene);
            stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void write(File file , String text1,String text2) throws IOException {
       if(!file.exists()){
           file.createNewFile();
       }
       else {

           try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {
               writer.write(text1 + " " + text2);
               //writer.write("");
               // writer.write(text2);
               writer.newLine();
           } catch (FileNotFoundException e) {
               throw new RuntimeException(e);
           }
       }
    }

    public boolean validity(File file ,String username, String password) throws FileNotFoundException {
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] space = line.split(" ");
                if (space[0].equals(username) && space[1].equals(password)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void openDashboard(Stage stage3, String username) {

    }
    private Button createStyledButton(String text, String bgColor, String hoverColor) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 20px;" +
                        "-fx-padding: 10 20;"
        );

        // Adding hover effect
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: " + hoverColor + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 20px;" +
                        "-fx-padding: 10 20;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 20px;" +
                        "-fx-padding: 10 20;"
        ));

        return button;
    }














}
