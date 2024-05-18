package GUI;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import java.sql.SQLException;

import Database.*;

public class DiscussionPage {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static VBox createDiscussionPage() {
        VBox discussionPage = new VBox(4);
        discussionPage.setPadding(new Insets(10));
        discussionPage.setStyle("-fx-background-color: " + color.BACKGROUND.getCode() + ";");

        Label discussionLabel = new Label("Discussion");
        discussionLabel.getStyleClass().add("title");
        discussionLabel.setPadding(new Insets(12, 0, 12, 12));

        ScrollPane feed = new ScrollPane();
        feed.setFitToWidth(true);
        VBox feedContent = new VBox(4);
        feedContent.setPadding(new Insets(2));
        feed.setContent(feedContent);

        TextField postTitle = new TextField();
        postTitle.setPromptText("Post Title");
        TextArea postDescription = new TextArea();
        postDescription.setPromptText("Post Description");
        Button addPostButton = new Button("Add Post");
        addPostButton.setOnAction(e -> addPost(feedContent, postTitle.getText(), postDescription.getText()));

        VBox newPostSection = new VBox(2, postTitle, postDescription, addPostButton);
        newPostSection.setPadding(new Insets(2));
        newPostSection.getStyleClass().add("new_post");
        newPostSection.setManaged(false);
        newPostSection.setVisible(false);

        Button togglePostSectionButton = new Button("New Post");
        togglePostSectionButton.setOnAction(e -> togglePostSection(newPostSection));

        VBox.setVgrow(feed, Priority.ALWAYS);
        discussionPage.getChildren().addAll(discussionLabel, togglePostSectionButton, newPostSection, feed);
        loadPosts(feedContent);

        return discussionPage;
    }

    private static void addPost(VBox feedContent, String title, String description) {
        if (title.isEmpty() || description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Post Title or the Description cannot be empty");
            alert.showAndWait();
            return;
        }

        String author = User.getCurrentUser().getUsername();
        String datetime = LocalDateTime.now().format(formatter);

        DiscussionDatabase.insertPost(title, description, author, datetime);

        loadPosts(feedContent);
    }

    private static void loadPosts(VBox feedContent) {
        feedContent.getChildren().clear();

        try (ResultSet rs = DiscussionDatabase.getPosts()) {
            if (!rs.isBeforeFirst()) {
                Label noPostsLabel = new Label("No Posts");
                feedContent.getChildren().add(noPostsLabel);
            } else {
                while (rs.next()) {
                    int postId = rs.getInt("id");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    String author = rs.getString("author");
                    String datetime = rs.getString("datetime");

                    Label postTitle = new Label(title);
                    postTitle.getStyleClass().add("text_black");
                    Label postDescription = new Label(description);
                    postDescription.setWrapText(true);
                    Label postAuthor = new Label("Author: " + author);
                    Label postDateTime = new Label("DateTime: " + datetime);

                    VBox post = new VBox(5, postTitle, postDescription, postAuthor, postDateTime);
                    post.setPadding(new Insets(4));
                    post.getStyleClass().add("new_post");

                    TextField commentField = new TextField();
                    commentField.setPromptText("Add a comment...");
                    Button commentButton = new Button("Comment");
                    VBox commentsSection = new VBox(5);
                    loadComments(postId, commentsSection);
                    commentButton.setOnAction(e -> addComment(postId, commentsSection, commentField.getText()));

                    VBox commentBox = new VBox(5, commentField, commentButton, commentsSection);
                    commentBox.setPadding(new Insets(10, 0, 0, 0));

                    post.getChildren().add(commentBox);
                    feedContent.getChildren().add(post);

                    FadeTransition ft = new FadeTransition(Duration.millis(500), post);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addComment(int postId, VBox commentsSection, String comment) {
        if (comment.isEmpty()) return;

        String author = User.getCurrentUser().getUsername();
        String datetime = LocalDateTime.now().format(formatter);

        DiscussionDatabase.insertComment(postId, comment, author, datetime);

        loadComments(postId, commentsSection);
    }

    private static void loadComments(int postId, VBox commentsSection) {
        commentsSection.getChildren().clear();

        try (ResultSet rs = DiscussionDatabase.getComments(postId)) {
            while (rs.next()) {
                String commentText = rs.getString("comment");
                String author = rs.getString("author");
                String datetime = rs.getString("datetime");

                Label commentLabel = new Label(commentText);
                commentLabel.setWrapText(true);
                commentLabel.setStyle("-fx-background-color: #e8e8e8; -fx-padding: 5; -fx-background-radius: 5;");
                Label commentAuthor = new Label("Author: " + author);
                Label commentDateTime = new Label("DateTime: " + datetime);

                VBox commentBox = new VBox(5, commentLabel, commentAuthor, commentDateTime);
                commentsSection.getChildren().add(commentBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void togglePostSection(VBox newPostSection) {
        boolean isVisible = newPostSection.isVisible();
        newPostSection.setVisible(!isVisible);
        newPostSection.setManaged(!isVisible);
    }
}