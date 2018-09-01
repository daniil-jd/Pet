package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Entity;
import tools.AuthTool;
import tools.EntityTool;
import tools.PropertyTool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Main controller. Responsible for creating, editing, deleting entities.
 */
public class MainController {

    @FXML
    private ListView<Entity> myListView = new ListView<>();

    @FXML
    private TextArea myTextArea;

    @FXML
    private Button addButton;

    @FXML
    private TextField myTextField;

    /**
     * FXML loader.
     */
    private FXMLLoader fxmlLoader;

    /**
     * Parent.
     */
    private Parent fxmlEdit;

    /**
     * Edit Controller
     */
    private EditController editController;

    @FXML
    private AnchorPane anchorPane;

    /**
     * Observable List which contains entities.
     */
    private ObservableList<Entity> obList = FXCollections
            .observableArrayList(new ArrayList<>());

    /**
     * Rename stage.
     */
    private Stage renameListViewStage;


    @FXML
    private void initialize() {
        loadEntities();
        initStartData();
        initListeners();
        initEditStage();
    }

    /**
     * Load entities from xml.
     */
    private void loadEntities() {
        obList.addAll(new EntityTool(true).loadEntities());
    }

    /**
     * Init start data.
     */
    private void initStartData() {
        Entity info = new Entity("info",
                "Здесь будет информация о приложении...");
        obList.add(info);
        obList.add(new Entity("Новая запись..."));
        myListView.setItems(obList);
    }

    /**
     * Init listeners.
     */
    private void initListeners() {
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
//                                        EntityTool.saveEntity(entity);
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
     * Init edit stage.
     */
    private void initEditStage() {
        try {
            fxmlLoader  = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/Edit.fxml"));
            fxmlEdit = fxmlLoader.load();
            editController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Add value in ListView.
     * @param actionEvent actionEvent
     */
    public void addSomeValues(ActionEvent actionEvent) {
        obList.add(new Entity("Новая запись (" + (myListView.getItems().size()) + ")"));
        myListView.setItems(obList);
    }

    /**
     * On mouse click on item list view.
     * @param mouseEvent mouse event
     */
    public void onClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            Entity currentEntity = myListView.getFocusModel().getFocusedItem();
            if (currentEntity != null) {
                showEditDialog(currentEntity);
            }
        }
    }

    /**
     * Show edit dialog.
     * @param currentEntity Entity
     */
    private void showEditDialog(Entity currentEntity) {
        if (renameListViewStage == null) {
            renameListViewStage = new Stage();
            renameListViewStage.setTitle("Edit");
            renameListViewStage.setMinHeight(130);
            renameListViewStage.setMinWidth(300);
            renameListViewStage.setResizable(false);
            renameListViewStage.setScene(new Scene(fxmlEdit));
            renameListViewStage.initModality(Modality.WINDOW_MODAL);
            renameListViewStage.initOwner(myListView.getScene().getWindow());
        }

        editController.setEntity(currentEntity);
        renameListViewStage.showAndWait();
        myListView.refresh();
    }

    /**
     * Rename entity from menu bar.
     * @param actionEvent action event
     */
    public void renameFromMenuBar(ActionEvent actionEvent) {
        Entity currentEntity = myListView.getFocusModel().getFocusedItem();
        if (currentEntity != null) {
            showEditDialog(currentEntity);
        }
    }

    /**
     * Save entity from menu bar.
     * @param actionEvent action event
     */
    public void saveFromMenuBar(ActionEvent actionEvent) {
        Entity currentEntity = myListView.getFocusModel().getFocusedItem();
        PropertyTool pt = new PropertyTool();

        if (currentEntity != null) {
            Entity encodedEntity = new Entity(currentEntity.getName(),
                    AuthTool.getEncodedText(currentEntity.getValue()));
            EntityTool saveTool = new EntityTool(false);
            saveTool.saveEntityXML(encodedEntity, new PropertyTool().getPath());

            Map<String, String> map = new HashMap<>();
            map.put(currentEntity.getName(),
                    pt.getPath() + currentEntity.getName() + ".xml");
            pt.saveProperties(map);
        }
    }

    /**
     * Delete entity from menu bar.
     * @param actionEvent
     */
    public void deleteFromMenuBar(ActionEvent actionEvent) {
        Entity currentEntity = myListView.getFocusModel().getFocusedItem();

        if (myListView.getItems().size() > 2
                && currentEntity != null) {
            ObservableList<Entity> entities = myListView.getItems();
            entities.remove(currentEntity);
            myListView.setItems(entities);
        }
    }

    /**
     * Load entity from disk.
     * @param actionEvent actionEvent
     */
    public void loadFromMenuBar(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(myListView.getScene().getWindow());
        if (file != null && file.length() > 0) {
            EntityTool readTool = new EntityTool(true);
            Entity entityFromDisk = readTool.readEntityXML(file);

            ObservableList<Entity> entities = myListView.getItems();
            entities.add(entityFromDisk);
            myListView.setItems(entities);
            myListView.refresh();
        }
    }
}
