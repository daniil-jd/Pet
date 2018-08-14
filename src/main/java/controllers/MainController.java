package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Entity;
import tools.EntityTool;

import java.util.ArrayList;

public class MainController {

    @FXML
    private ListView<Entity> myListView = new ListView<Entity>();

    @FXML
    private TextArea myTextArea;

    @FXML
    private Button addButton;

    @FXML
    private TextField myTextField;

    @FXML
    private AnchorPane anchorPane;

    private ObservableList<Entity> obList = FXCollections
            .observableArrayList(new ArrayList<Entity>());


    @FXML
    private void initialize() {

        myListView.getSelectionModel().getSelectedItems()
                .addListener(new ListChangeListener<Entity>() {
                    @Override
                    public void onChanged(Change<? extends Entity> c) {

                        String text = myListView
                                .getFocusModel()
                                .getFocusedItem()
                                .getValue();
                        myTextArea.setText(text);
                    }
                });

        myTextArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                if (myListView.getFocusModel().getFocusedItem() != null) {
                    Entity currentEntity = myListView.getFocusModel()
                            .getFocusedItem();

                    currentEntity.setValue(newValue);

                    ObservableList<Entity> tL = myListView.getItems();
                    for (int i = 0; i < tL.size(); i++) {
                        if (currentEntity.equals(tL.get(i))) {
                            tL.set(i, currentEntity);
                        }
                    }
                    myListView.setItems(tL);
                }
            }
        });


       anchorPane.sceneProperty().addListener(new ChangeListener<Scene>() {
           @Override
           public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
               if (oldValue == null && newValue != null) {
                   // scene is set for the first time. Now its the time to listen stage changes.
                   newValue.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                       if (oldWindow == null && newWindow != null) {
                           // stage is set. now is the right time to do whatever we need to the stage in the controller.
                           ((Stage) newWindow).setOnCloseRequest(new EventHandler<WindowEvent>() {
                               @Override
                               public void handle(WindowEvent event) {
                                   for (Entity entity : myListView.getItems()) {
                                       EntityTool.saveEntity(entity);
                                   }
                               }
                           });
                       }
                   });
               }
           }
       });

    }


    /**
     * Add value in ListView.
     * @param actionEvent actionEvent
     */
    public void addSomeValues(ActionEvent actionEvent) {
        obList.add(new Entity("entity " + (myListView.getItems().size() + 1)));
        myListView.setItems(obList);
//        System.out.println(myListView.getItems().size());
    }

    /**
     * Get text bolt.
     * @param actionEvent actionEvent
     */
    public void doBolt(ActionEvent actionEvent) {
        String selectedText = (myTextArea.getSelectedText());
        if (selectedText.length() > 0) {

            /*myTextArea.setStyle(
                    ".text-area *.text { \n" +
                    "    -fx-text-fill: #1e88e5; \n" +
                    "    -fx-highlight-text-fill: #d60e0e; \n" +
                    "    -fx-text-alignment: center}"
            );*/
        }
    }
}
