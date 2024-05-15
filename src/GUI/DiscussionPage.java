package GUI;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DiscussionPage {
    public static VBox loadDiscussionPage(){
        VBox discussionVBox = new VBox();
        discussionVBox.getStyleClass().add("discussion_vbox");
        discussionVBox.setPadding(new Insets(0, 0, 0, 0));
        discussionVBox.setSpacing(0);
        
        Label discussionLabel = new Label("Discussion");
        discussionLabel.getStyleClass().add("title");
        discussionLabel.setPadding(new Insets(12, 0, 12, 12));
        
        VBox discussionListBox = new VBox();
        discussionListBox.getStyleClass().add("discussion_list_box");
        
        discussionVBox.getChildren().addAll(discussionLabel, discussionListBox);
        
        return discussionVBox;
    }
}
