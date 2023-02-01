package com.cgvsu;

import com.cgvsu.math.*;
import com.cgvsu.model.LoadedModel;
import com.cgvsu.model.Polygon;
import com.cgvsu.objwriter.ObjWriter;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.GraphicConveyor;
import com.cgvsu.render_engine.RenderEngine;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;

public class GuiController {

    private float TRANSLATION = 1.5F ;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private AnchorPane saveSelection;

    @FXML
    private AnchorPane enterModelName;

    @FXML
    private AnchorPane modelTrans;

    @FXML
    private AnchorPane renderingModels;

    @FXML
    private AnchorPane loadedModels;

    @FXML
    private AnchorPane loadedTextures;

    @FXML
    private AnchorPane loadedCameras;
    @FXML
    private AnchorPane enterCameraData;

    @FXML
    private Canvas canvas;

    @FXML
    private ListView<String> listViewModels;

    @FXML
    private ListView<String> listViewCameras;

    @FXML
    private ListView<String> listViewTextures;

    @FXML
    private TextField rotateX;

    @FXML
    private TextField rotateY;

    @FXML
    private TextField rotateZ;

    @FXML
    private TextField scaleX;

    @FXML
    private TextField scaleY;

    @FXML
    private TextField scaleZ;

    @FXML
    private TextField translateX;

    @FXML
    private TextField translateY;

    @FXML
    private TextField translateZ;
    @FXML
    private TextField modelName;
    @FXML
    private TextField cameraName;
    @FXML
    private TextField cameraX;
    @FXML
    private TextField cameraY;
    @FXML
    private TextField cameraZ;
    @FXML
    private javafx.scene.shape.Rectangle rightUpRect;

    @FXML
    private javafx.scene.shape.Rectangle rightDownRect;

    @FXML
    private javafx.scene.shape.Rectangle leftUpRect;
    @FXML
    private javafx.scene.shape.Rectangle leftMediumRect;
    @FXML
    private Rectangle leftDownRect;
    @FXML
    private Text rightUpText;
    @FXML
    private Text rightDownText;
    @FXML
    private Text leftUpText;
    @FXML
    private Text leftDownText;
    @FXML
    private Text leftMediumText;

    @FXML
    private Slider xSlider;

    @FXML
    private Slider ySlider;

    @FXML
    private Slider zSlider;

    @FXML
    private Slider transSlider;

    @FXML
    private Slider fovSlider;

    @FXML
    private Slider aspectSlider;

    @FXML
    private RadioMenuItem light;

    @FXML
    private RadioMenuItem dark;

    @FXML
    private CheckBox drawPolygonMesh;

    @FXML
    private CheckBox drawTextures;

    @FXML
    private CheckBox drawLighting;

    @FXML
    private CheckBox drawFilling;

    @FXML
    private ColorPicker polygonFillColor;

    @FXML
    private Slider rotateSpeed;

    @FXML
    private CheckBox hitboxWriting;

    private Timeline timeline;
    private Robot robot;
    private boolean modelIsLoaded;
    private boolean cameraIsLoaded;
    private List<TextField> list;
    private List <String> selectedModels = new ArrayList<>();
    private List <String> selectedTextures = new ArrayList<>();
    private List<String> selectedCameras = new ArrayList<>();
    private boolean textureIsLoaded;
    private boolean onActionListCameras;
    private boolean onActionTransform;
    private boolean onActionListModels;
    private boolean onActionModes;
    private boolean onActionListTextures;
    private Model mesh = null;
    private Scene scene = new Scene();
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private double xAngle;
    private double yAngle;
    private double angleListenerValue = 0.05;
    private double summaryXAngle = 0;
    private double summaryYAngle = 0;
    private Image texture;

    @FXML
    private void initialize() {
        Camera mainCamera = new Camera(new Vector3f(0, 0, 200),
                new Vector3f(0, 0, 0),
                1.0F, 1, 0.01F, 100);
        scene.setCurrentCamera(mainCamera);
        scene.getAddedCameras().put("mainCamera", mainCamera);
        listViewCameras.getItems().add("mainCamera");
        listViewModels.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        scene.getLoadedModels().put("Mesh", new LoadedModel(mesh));
        scene.currentModelName = "Mesh";
        list = getTextFields();
        List<Slider> sliders = Arrays.asList(xSlider, ySlider, zSlider);
        xSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                String degrees = String.format(Locale.ENGLISH, "%(.2f", sliders.get(0).getValue());
                if (degrees.contains("(")) {
                    degrees = degrees.substring(1, degrees.length()-1);
                    degrees = "-" + degrees;
                }
                list.get(0).setText(degrees);
            }
        });
        ySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                String degrees = String.format(Locale.ENGLISH, "%(.2f", sliders.get(1).getValue());
                if (degrees.contains("(")) {
                    degrees = degrees.substring(1, degrees.length()-1);
                    degrees = "-" + degrees;
                }
                list.get(1).setText(degrees);
            }
        });
        zSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                String degrees = String.format(Locale.ENGLISH, "%(.2f", sliders.get(2).getValue());
                if (degrees.contains("(")) {
                    degrees = degrees.substring(1, degrees.length()-1);
                    degrees = "-" + degrees;
                }
                list.get(2).setText(degrees);
            }
        });


        transSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                TRANSLATION = (float) (transSlider.getValue());
            }
        });
        fovSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scene.getCurrentCamera().setFov((float) (fovSlider.getValue()));
            }
        });
        aspectSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scene.getCurrentCamera().setAspectRatio((float) (aspectSlider.getValue()));
            }
        });

        rotateSpeed.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                angleListenerValue = rotateSpeed.getValue();
            }
        });
        TranslateTransition transitionRightUpSide = new TranslateTransition();
        TranslateTransition transitionRightDownSide = new TranslateTransition();
        TranslateTransition transitionLeftUpSide = new TranslateTransition();
        TranslateTransition transitionLeftDownSide = new TranslateTransition();
        TranslateTransition transitionLeftMediumSide = new TranslateTransition();
        transitionLeftUpSide.setNode(loadedModels);
        transitionRightUpSide.setNode(modelTrans);
        transitionRightDownSide.setNode(renderingModels);
        transitionLeftDownSide.setNode(loadedTextures);
        transitionLeftMediumSide.setNode(loadedCameras);
        transitionRightUpSide.setByX(260);
        transitionRightDownSide.setByX(260);
        transitionLeftUpSide.setByX(-235);
        transitionLeftDownSide.setByX(-235);
        transitionLeftMediumSide.setByX(-235);
        transitionLeftUpSide.play();
        transitionRightUpSide.play();
        transitionRightDownSide.play();
        transitionLeftDownSide.play();
        transitionLeftMediumSide.play();
        anchorPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double rightUpBorder = loadedModels.getLayoutX();
                double posRightUpBorder = loadedModels.getLocalToParentTransform().getTx();
                double rightMediumBorder = loadedCameras.getLayoutX();
                double posRightMediumBorder = loadedCameras.getLocalToParentTransform().getTx();
                double rightDownBorder = loadedTextures.getLayoutX();
                double posRightDownBorder = loadedTextures.getLocalToParentTransform().getTx();
                double leftUpBorder = modelTrans.getLayoutX();
                double posLeftUpBorder = modelTrans.getLocalToParentTransform().getTx();
                double leftDownBorder = renderingModels.getLayoutX();
                double posLeftDownBorder = renderingModels.getLocalToParentTransform().getTx();
                if (posLeftUpBorder == modelTrans.getLayoutX() || posLeftUpBorder == modelTrans.getLayoutX() + 260) {
                    if (modelIsLoaded && !onActionTransform && mouseEvent.getX() > leftUpBorder + 260 && mouseEvent.getX() < leftUpBorder + 285 && mouseEvent.getY() < 520) {
                        transitionRightUpSide.setByX(-260);
                        transitionRightUpSide.play();
                        onActionTransform = true;
                        disable(false);
                    } else if (onActionTransform && (mouseEvent.getX() < leftUpBorder || mouseEvent.getY() > 520)) {
                        transitionRightUpSide.setByX(260);
                        transitionRightUpSide.play();
                        onActionTransform = false;
                        disable(true);
                    }
                }
                if (posLeftDownBorder == renderingModels.getLayoutX() || posLeftDownBorder == renderingModels.getLayoutX() + 260) {
                    if (modelIsLoaded && !onActionModes && mouseEvent.getX() > leftDownBorder + 260 && mouseEvent.getX() < leftDownBorder + 285 && mouseEvent.getY() > 525) {
                        transitionRightDownSide.setByX(-260);
                        transitionRightDownSide.play();
                        onActionModes = true;
                    } else if (onActionModes && (mouseEvent.getX() < leftDownBorder|| mouseEvent.getY() < 525)) {
                        transitionRightDownSide.setByX(260);
                        transitionRightDownSide.play();
                        onActionModes = false;
                    }
                }
                if (posRightUpBorder == loadedModels.getLayoutX() || posRightUpBorder == loadedModels.getLayoutX() - 235) {
                    if (modelIsLoaded && !onActionListModels && mouseEvent.getX() > rightUpBorder && mouseEvent.getX() < rightUpBorder + 25 && mouseEvent.getY() > 30 && mouseEvent.getY() < 265) {
                        transitionLeftUpSide.setByX(235);
                        transitionLeftUpSide.play();
                        onActionListModels = true;
                    } else if (onActionListModels && (mouseEvent.getX() > rightUpBorder + 235 || mouseEvent.getY() > 265 || mouseEvent.getY() < 30)) {
                        transitionLeftUpSide.setByX(-235);
                        transitionLeftUpSide.play();
                        onActionListModels = false;
                    }
                }
                if (posRightMediumBorder == loadedCameras.getLayoutX() || posRightMediumBorder == loadedCameras.getLayoutX() - 235) {
                    if (modelIsLoaded && !onActionListCameras && mouseEvent.getX() > rightMediumBorder && mouseEvent.getX() < rightMediumBorder + 25 && mouseEvent.getY() > 275 && mouseEvent.getY() < 475) {
                        transitionLeftMediumSide.setByX(235);
                        transitionLeftMediumSide.play();
                        onActionListCameras = true;
                    } else if (onActionListCameras && (mouseEvent.getX() > rightUpBorder + 235 || mouseEvent.getY() < 275 || mouseEvent.getY() > 475)) {
                        transitionLeftMediumSide.setByX(-235);
                        transitionLeftMediumSide.play();
                        onActionListCameras = false;
                    }
                }
                if (posRightDownBorder == loadedTextures.getLayoutX() || posRightDownBorder == loadedTextures.getLayoutX() - 235) {
                    if (textureIsLoaded && modelIsLoaded && !onActionListTextures && mouseEvent.getX() > rightDownBorder && mouseEvent.getX() < rightDownBorder + 25 && mouseEvent.getY() > 485) {
                        transitionLeftDownSide.setByX(235);
                        transitionLeftDownSide.play();
                        onActionListTextures = true;
                    } else if (onActionListTextures && (mouseEvent.getX() > rightDownBorder + 235 || mouseEvent.getY() < 485)) {
                        transitionLeftDownSide.setByX(-235);
                        transitionLeftDownSide.play();
                        onActionListTextures = false;
                    }
                }
            }
        });

        anchorPane.setOnScroll(new EventHandler<>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                double deltaY = scrollEvent.getDeltaY();
                if (deltaY < 0){
                    scene.getCurrentCamera().movePosition(new Vector3f(0, 0, TRANSLATION));
                } else {
                    scene.getCurrentCamera().movePosition(new Vector3f(0, 0, -TRANSLATION));
                }
            }
        });

        anchorPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                startX = mouseEvent.getX();
                startY = mouseEvent.getY();
            }
        });

        anchorPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                endX = mouseEvent.getX();
                endY = mouseEvent.getY();
                // startX = anchorPane.getWidth() / 2;
                //startY = anchorPane.getHeight() / 2;
                //double x = ((endX*2)-(anchorPane.getWidth()-1))/(anchorPane.getWidth()-1);
                //double y = -(((endY*2)-(anchorPane.getHeight() - 1))/(anchorPane.getHeight() - 1));
                /*Vector3f first = new Vector3f(scene.getCamera().getPosition().getX() - scene.getCamera().getTarget().getX(), scene.getCamera().getPosition().getY() - scene.getCamera().getTarget().getY(), scene.getCamera().getPosition().getZ() - scene.getCamera().getTarget().getZ());
                Vector3f second = new Vector3f(scene.getCamera().getPosition().getX() - x, scene.getCamera().getPosition().getY() - y, scene.getCamera().getPosition().getZ());
                double cos = (Vector3f.dotProduct(first, second)/ (first.length() * second.length()));
                double sin = Math.sqrt(1-(cos * cos));
                текущий з домножаем на лук эт поворачиваем с помощью матрицы поворота с помощью обратного лук эт в мировое простаранство и з прибавляем к позиции*/
                //oldCameraTargetVis.normalize();
                //double angle = Math.toDegrees(Math.atan2(y - startY, x - startX));
                //double deltaX = (endX - startX)/anchorPane.getWidth();
                //double deltaY = (endY - startY)/anchorPane.getHeight();
                //xAngle = deltaX * 0.1;
                //yAngle = deltaY * 0.1;
                // Vector3f cameraTargetVis = GraphicConveyor.multiplierMatrixToVector(GraphicConveyor.rotate(new Vector3f(yAngle, xAngle, 0)), new Vector4f(curZ.getX(), curZ.getY(), curZ.getZ(), 1));
                //Vector3f cameraTargetCam = GraphicConveyor.multiplierMatrixToVector(scene.getCamera().getViewMatrix(), new Vector4f(cameraTargetVis.getX(), cameraTargetVis.getY(), cameraTargetVis.getZ(), 1));
                //Vector3f cameraTargetWindow = GraphicConveyor.multiplierMatrixToVector(scene.getCamera().getProjectionMatrix(), new Vector4f(cameraTargetVis.getX(), cameraTargetVis.getY(), cameraTargetVis.getZ(), 1));
                if (!onActionModes && !onActionListModels && !onActionTransform) {
                    //Vector3f curZ = Vector3f.subtraction(scene.getCamera().getTarget(), scene.getCamera().getPosition());
                    //Vector3f oldCameraTargetVis = GraphicConveyor.multiplierMatrixToVector(scene.getCamera().getViewMatrix(), new Vector4f(curZ.getX(), curZ.getY(), curZ.getZ(), 1));
                    Vector3f curZ = Vector3f.subtraction(new Vector3f(0, 0, 0), scene.getCurrentCamera().getPosition());
                    Vector3f oldCameraTargetVis = GraphicConveyor.multiplierMatrixToVector(scene.getCurrentCamera().getViewMatrix(), new Vector4f(curZ.getX(), curZ.getY(), curZ.getZ(), 1));
                    if (endX > startX) {
                        yAngle = angleListenerValue;
                    } else if (endX < startX) {
                        yAngle = -angleListenerValue;
                    } else {
                        yAngle = 0;
                    }
                    if (endY > startY) {
                        xAngle = angleListenerValue;
                    } else if (endY < startY) {
                        xAngle = -angleListenerValue;
                    } else {
                        xAngle = 0;
                    }
                    summaryXAngle+=xAngle;
                    summaryYAngle+=yAngle;
                    Vector3f cameraTargetVis = GraphicConveyor.multiplierMatrixToVector(GraphicConveyor.rotate(new Vector3f(summaryXAngle, summaryYAngle, 0)), new Vector4f(oldCameraTargetVis.getX(), oldCameraTargetVis.getY(), oldCameraTargetVis.getZ(), 1));
                    Vector3f cameraTargetWorld = GraphicConveyor.multiplierMatrixToVector(scene.getCurrentCamera().getViewMatrix().inversion(), new Vector4f(cameraTargetVis.getX(), cameraTargetVis.getY(), cameraTargetVis.getZ(), 1));
                    Vector3f newTargetVector = Vector3f.addition(cameraTargetWorld, scene.getCurrentCamera().getPosition());
                    scene.getCurrentCamera().setTarget(new Vector3f(newTargetVector.getX(), newTargetVector.getY(), newTargetVector.getZ()));
                    startY = endY;
                    startX = endX;
                }
            }
        });


        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        saveSelection.setStyle("-fx-background-color: lightgray;");
        enterModelName.setStyle("-fx-background-color: lightgray;");
        enterCameraData.setStyle("-fx-background-color: lightgray;");
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            modelIsLoaded = listViewModels.getItems().size() > 0;
            textureIsLoaded = listViewTextures.getItems().size() > 0;
            checkModelLoading(modelIsLoaded, textureIsLoaded);
            double width = canvas.getWidth();
            double height = canvas.getHeight();
            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            Color meshColor = Color.BLACK;
            if (TransformFieldsNotNull() && scene.currentModelName != null) {
                //scene.getMainCamera().setAspectRatio((float) (width / height));
                scene.getCurrentCamera().setAspectRatio((float) ((width / height) * aspectSlider.getValue() * 2));
                scene.getLoadedModels().get(scene.currentModelName).setRotateV(new Vector3f(Double.parseDouble(rotateX.getText()), Double.parseDouble(rotateY.getText()), Double.parseDouble(rotateZ.getText())));
                scene.getLoadedModels().get(scene.currentModelName).setScaleV(new Vector3f(Double.parseDouble(scaleX.getText()), Double.parseDouble(scaleY.getText()), Double.parseDouble(scaleZ.getText())));
                scene.getLoadedModels().get(scene.currentModelName).setTranslateV(new Vector3f(-Double.parseDouble(translateX.getText()), Double.parseDouble(translateY.getText()), Double.parseDouble(translateZ.getText())));
                for (String model : selectedModels) {
                    RenderEngine.render(canvas.getGraphicsContext2D(), scene.getCurrentCamera(),
                            scene.getLoadedModels().get(model), (int) width, (int) height,
                            scene.getLoadedModels().get(model).getRotateV(),
                            scene.getLoadedModels().get(model).getScaleV(),
                            scene.getLoadedModels().get(model).getTranslateV(),
                            meshColor, drawPolygonMesh.isSelected(), drawTextures.isSelected(),
                            drawLighting.isSelected(), drawFilling.isSelected(), polygonFillColor.getValue(),
                            texture, hitboxWriting.isSelected());
                }
            }
        });
        timeline.getKeyFrames().add(frame);
        // timeline.getKeyFrames().add(frame2);
        timeline.play();
    }

    @FXML
    private void onOpenLoadModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path path = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(path);
            Model model = ObjReader.read(fileContent, true);
            model.triangulate();
            //model.calcNormals();
            String name = file.getName();
            name = checkContainsModel(name);
            scene.getLoadedModels().put(name, new LoadedModel(model));
            scene.currentModelName = name;
            listViewModels.getItems().add(scene.currentModelName);
            listViewModels.scrollTo(scene.currentModelName);
            selectedModels = new ArrayList<>();
            selectedModels.add(scene.currentModelName);
            double maxX=0;
            double minX=0;
            double maxY=0;
            double minY=0;
            double maxZ=0;
            double minZ=0;
            for (Vector3f vertex : model.getVertices()) {
                if(vertex.getX()>maxX){
                    maxX = vertex.getX();
                }
                if(vertex.getY()>maxY){
                    maxY = vertex.getY();
                }
                if(vertex.getZ()>maxZ){
                    maxZ = vertex.getZ();
                }
                if(vertex.getX()<minX){
                    minX = vertex.getX();
                }
                if(vertex.getY()<minY){
                    minY = vertex.getY();
                }
                if(vertex.getZ()<minZ){
                    minZ = vertex.getZ();
                }
            }
            List<Vector3f> hitBoxList = new ArrayList<>();
            hitBoxList.add(new Vector3f(minX, minY, minZ));
            hitBoxList.add(new Vector3f(maxX, minY, minZ));
            hitBoxList.add(new Vector3f(maxX, minY, maxZ));
            hitBoxList.add(new Vector3f(minX, minY, maxZ));
            hitBoxList.add(new Vector3f(minX, maxY, minZ));
            hitBoxList.add(new Vector3f(maxX, maxY, minZ));
            hitBoxList.add(new Vector3f(maxX, maxY, maxZ));
            hitBoxList.add(new Vector3f(minX, maxY, maxZ));
            scene.getLoadedModels().get(scene.currentModelName).setHitBoxPoints(hitBoxList);
            cleanTransform();
            // todo: обработка ошибок
        } catch (IOException exception) {

        }
        modelIsLoaded = true;
    }

    public void onOpenSaveUnchangedModel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());

        try {
            List<String> fileContent2 = ObjWriter.write(scene.getLoadedModels().get(scene.currentModelName));
            FileWriter writer = new FileWriter(file);
            for (String s : fileContent2) {
                writer.write(s + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException ignored) {
        }
        saveSelection.setVisible(false);
    }

    public void onOpenSaveWithChangesModel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());
        try {
            Model changedModel = new Model(scene.getLoadedModels().get(scene.currentModelName).modifiedVertices(), scene.getLoadedModels().get(scene.currentModelName).getTextureVertices(), scene.getLoadedModels().get(scene.currentModelName).getNormals(), scene.getLoadedModels().get(scene.currentModelName).getPolygons());
            List<String> fileContent2 = ObjWriter.write(changedModel);
            FileWriter writer = new FileWriter(file);
            for (String s : fileContent2) {
                writer.write(s + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException ignored) {
        }
        saveSelection.setVisible(false);
    }

    public void onOpenLoadTextureMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Texture (*.jpg)", "*.jpg"));
        fileChooser.setTitle("Load Texture");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }
        String name = file.getName();
        name = checkContainsTexture(name);
        scene.getLoadedTextures().put(name, new Image(new File(file.getAbsolutePath()).toURI().toString()));
        listViewTextures.getItems().add(name);
        textureIsLoaded = true;
    }

    public void onAddCameraMenuItemClick() {
        enterCameraData.setVisible(true);
    }
    public void okCameraPane () {
        Camera newCamera = new Camera(
                new Vector3f(Integer.parseInt(cameraX.getText()), Integer.parseInt(cameraY.getText()), Integer.parseInt(cameraZ.getText())),
                new Vector3f(0, 0, 0),
                1.0F, 1, 0.01F, 100);
        Map<String, Camera> addedCameras = scene.getAddedCameras();
        String name =  cameraName.getText();
        name = checkContainsTexture(name);
        addedCameras.put(name, newCamera);
        listViewCameras.getItems().add(name);
        enterCameraData.setVisible(false);
        cameraIsLoaded = true;
    }
    public void closeCameraPane () {
        enterCameraData.setVisible(false);
    }

    public void hotkeys(KeyEvent event) {
        if (event.isShiftDown() && (event.getCode() == KeyCode.T)) {
            if(transSlider.getValue()<=1) {
                transSlider.setValue(transSlider.getValue() + 0.1);
            }else {
                transSlider.setValue(1);
            }
        }
        if (event.isShiftDown() && (event.getCode() == KeyCode.F)) {
            if(fovSlider.getValue()<=1) {
                fovSlider.setValue(fovSlider.getValue() + 0.1);
            }else {
                fovSlider.setValue(1);
            }
        }
        if (event.isShiftDown() && (event.getCode() == KeyCode.A)) {
            if(aspectSlider.getValue()<=1) {
                aspectSlider.setValue(aspectSlider.getValue() + 0.1);
            }else {
                aspectSlider.setValue(1);
            }
        }
        if (event.isShiftDown() && (event.getCode() == KeyCode.R)) {
            if(rotateSpeed.getValue()<=1) {
                rotateSpeed.setValue(rotateSpeed.getValue() + 0.01);
            }else {
                rotateSpeed.setValue(1);
            }
        }
        if (event.isControlDown() && (event.getCode() == KeyCode.T)) {
            if(transSlider.getValue()>=0) {
                transSlider.setValue(transSlider.getValue() - 0.1);
            }else {
                transSlider.setValue(0);
            }
        }
        if (event.isControlDown() && (event.getCode() == KeyCode.F)) {
            if(fovSlider.getValue()>=0.01) {
                fovSlider.setValue(fovSlider.getValue() - 0.1);
            }else {
                fovSlider.setValue(0.01);
            }
        }
        if (event.isControlDown() && (event.getCode() == KeyCode.A)) {
            if(aspectSlider.getValue()>=0.01) {
                aspectSlider.setValue(aspectSlider.getValue() - 0.1);
            }else {
                aspectSlider.setValue(0.01);
            }
        }
        if (event.isControlDown() && (event.getCode() == KeyCode.R)) {
            if(rotateSpeed.getValue()>=0) {
                rotateSpeed.setValue(rotateSpeed.getValue() - 0.01);
            }else {
                rotateSpeed.setValue(0);
            }
        }
        if (event.isShiftDown() && (event.getCode() == KeyCode.X)) {
            //Vector3f curZ = Vector3f.subtraction(scene.getCamera().getTarget(), scene.getCamera().getPosition());
            //Vector3f oldCameraTargetVis = GraphicConveyor.multiplierMatrixToVector(scene.getCamera().getViewMatrix(), new Vector4f(curZ.getX(), curZ.getY(), curZ.getZ(), 1));
            Vector3f curZ = Vector3f.subtraction(new Vector3f(0, 0, 0), scene.getCurrentCamera().getPosition());
            Vector3f oldCameraTargetVis = GraphicConveyor.multiplierMatrixToVector(scene.getCurrentCamera().getViewMatrix(), new Vector4f(curZ.getX(), curZ.getY(), curZ.getZ(), 1));
            summaryYAngle+=2;
            Vector3f cameraTargetVis = GraphicConveyor.multiplierMatrixToVector(GraphicConveyor.rotate(new Vector3f(summaryXAngle, summaryYAngle, 0)), new Vector4f(oldCameraTargetVis.getX(), oldCameraTargetVis.getY(), oldCameraTargetVis.getZ(), 1));
            Vector3f cameraTargetWorld = GraphicConveyor.multiplierMatrixToVector(scene.getCurrentCamera().getViewMatrix().inversion(), new Vector4f(cameraTargetVis.getX(), cameraTargetVis.getY(), cameraTargetVis.getZ(), 1));
            scene.getCurrentCamera().setTarget(Vector3f.addition(cameraTargetWorld, scene.getCurrentCamera().getPosition()));
            System.out.println(yAngle);
            System.out.println(scene.getCurrentCamera().getTarget().getX());
            System.out.println(scene.getCurrentCamera().getTarget().getY());
            System.out.println(scene.getCurrentCamera().getTarget().getZ());
        }
        if (event.isControlDown() && (event.getCode() == KeyCode.X)) {
            //Vector3f curZ = Vector3f.subtraction(scene.getCamera().getTarget(), scene.getCamera().getPosition());
            //Vector3f oldCameraTargetVis = GraphicConveyor.multiplierMatrixToVector(scene.getCamera().getViewMatrix(), new Vector4f(curZ.getX(), curZ.getY(), curZ.getZ(), 1));
            Vector3f curZ = Vector3f.subtraction(new Vector3f(0, 0, 0), scene.getCurrentCamera().getPosition());
            Vector3f oldCameraTargetVis = GraphicConveyor.multiplierMatrixToVector(scene.getCurrentCamera().getViewMatrix(), new Vector4f(curZ.getX(), curZ.getY(), curZ.getZ(), 1));
            summaryYAngle-=2;
            Vector3f cameraTargetVis = GraphicConveyor.multiplierMatrixToVector(GraphicConveyor.rotate(new Vector3f(summaryXAngle, summaryYAngle, 0)), new Vector4f(oldCameraTargetVis.getX(), oldCameraTargetVis.getY(), oldCameraTargetVis.getZ(), 1));
            Vector3f cameraTargetWorld = GraphicConveyor.multiplierMatrixToVector(scene.getCurrentCamera().getViewMatrix().inversion(), new Vector4f(cameraTargetVis.getX(), cameraTargetVis.getY(), cameraTargetVis.getZ(), 1));
            scene.getCurrentCamera().setTarget(Vector3f.addition(cameraTargetWorld, scene.getCurrentCamera().getPosition()));
            System.out.println(scene.getCurrentCamera().getTarget().getX() + " " + scene.getCurrentCamera().getTarget().getY() + " " + scene.getCurrentCamera().getTarget().getZ());
        }
        if (event.isShiftDown() && (event.getCode() == KeyCode.Y)) {
            //Vector3f curZ = Vector3f.subtraction(scene.getCamera().getTarget(), scene.getCamera().getPosition());
            //Vector3f oldCameraTargetVis = GraphicConveyor.multiplierMatrixToVector(scene.getCamera().getViewMatrix(), new Vector4f(curZ.getX(), curZ.getY(), curZ.getZ(), 1));
            Vector3f curZ = Vector3f.subtraction(new Vector3f(0, 0, 0), scene.getCurrentCamera().getPosition());
            Vector3f oldCameraTargetVis = GraphicConveyor.multiplierMatrixToVector(scene.getCurrentCamera().getViewMatrix(), new Vector4f(curZ.getX(), curZ.getY(), curZ.getZ(), 1));
            summaryXAngle-=2;
            Vector3f cameraTargetVis = GraphicConveyor.multiplierMatrixToVector(GraphicConveyor.rotate(new Vector3f(summaryXAngle, summaryYAngle, 0)), new Vector4f(oldCameraTargetVis.getX(), oldCameraTargetVis.getY(), oldCameraTargetVis.getZ(), 1));
            Vector3f cameraTargetWorld = GraphicConveyor.multiplierMatrixToVector(scene.getCurrentCamera().getViewMatrix().inversion(), new Vector4f(cameraTargetVis.getX(), cameraTargetVis.getY(), cameraTargetVis.getZ(), 1));
            scene.getCurrentCamera().setTarget(Vector3f.addition(cameraTargetWorld, scene.getCurrentCamera().getPosition()));
        }
        if (event.isControlDown() && (event.getCode() == KeyCode.Y)) {
            // Vector3f curZ = Vector3f.subtraction(scene.getCamera().getTarget(), scene.getCamera().getPosition());
            //Vector3f oldCameraTargetVis = GraphicConveyor.multiplierMatrixToVector(scene.getCamera().getViewMatrix(), new Vector4f(curZ.getX(), curZ.getY(), curZ.getZ(), 1));
            Vector3f curZ = Vector3f.subtraction(new Vector3f(0, 0, 0), scene.getCurrentCamera().getPosition());
            Vector3f oldCameraTargetVis = GraphicConveyor.multiplierMatrixToVector(scene.getCurrentCamera().getViewMatrix(), new Vector4f(curZ.getX(), curZ.getY(), curZ.getZ(), 1));
            summaryXAngle+=2;
            Vector3f cameraTargetVis = GraphicConveyor.multiplierMatrixToVector(GraphicConveyor.rotate(new Vector3f(summaryXAngle, summaryYAngle, 0)), new Vector4f(oldCameraTargetVis.getX(), oldCameraTargetVis.getY(), oldCameraTargetVis.getZ(), 1));
            Vector3f cameraTargetWorld = GraphicConveyor.multiplierMatrixToVector(scene.getCurrentCamera().getViewMatrix().inversion(), new Vector4f(cameraTargetVis.getX(), cameraTargetVis.getY(), cameraTargetVis.getZ(), 1));
            scene.getCurrentCamera().setTarget(Vector3f.addition(cameraTargetWorld, scene.getCurrentCamera().getPosition()));
        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        scene.getCurrentCamera().movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        scene.getCurrentCamera().movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        scene.getCurrentCamera().movePosition(new Vector3f(TRANSLATION, 0, 0));
        //scene.getMainCamera().setTarget(new Vector3f(scene.getMainCamera().getTarget().getX() + TRANSLATION, scene.getMainCamera().getTarget().getY(), scene.getMainCamera().getTarget().getZ()));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        scene.getCurrentCamera().movePosition(new Vector3f(-TRANSLATION, 0, 0));
        //camera.setTarget(new Vector3f(camera.getTarget().getX() - TRANSLATION, camera.getTarget().getY(), camera.getTarget().getZ()));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {scene.getCurrentCamera().movePosition(new Vector3f(0, TRANSLATION, 0));}

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        scene.getCurrentCamera().movePosition(new Vector3f(0, -TRANSLATION, 0));
    }

    public List<TextField> getTextFields () {
        List<TextField> list = new ArrayList<>();
        list.add(rotateX);
        list.add(rotateY);
        list.add(rotateZ);
        list.add(scaleX);
        list.add(scaleY);
        list.add(scaleZ);
        list.add(translateX);
        list.add(translateY);
        list.add(translateZ);
        return list;
    }

    public void disable (boolean disable) {
        for (TextField field : list) {
            field.setDisable(disable);
        }
        xSlider.setDisable(disable);
        ySlider.setDisable(disable);
        zSlider.setDisable(disable);
    }

    public boolean TransformFieldsNotNull() {
        String pattern = "^(-?\\d{1,3}(\\.\\d*))$";
        for (TextField field : list) {
            Matcher matcher = Pattern.compile(pattern).matcher(field.getText());
            if (!matcher.find()) {
                return false;
            }
        }
        return true;
    }

    public void cleanTransform() {
        list.get(0).setText(String.valueOf(scene.getLoadedModels().get(scene.currentModelName).getRotateV().getX()));
        list.get(1).setText(String.valueOf(scene.getLoadedModels().get(scene.currentModelName).getRotateV().getY()));
        list.get(2).setText(String.valueOf(scene.getLoadedModels().get(scene.currentModelName).getRotateV().getZ()));
        list.get(3).setText(String.valueOf(scene.getLoadedModels().get(scene.currentModelName).getScaleV().getX()));
        list.get(4).setText(String.valueOf(scene.getLoadedModels().get(scene.currentModelName).getScaleV().getY()));
        list.get(5).setText(String.valueOf(scene.getLoadedModels().get(scene.currentModelName).getScaleV().getZ()));
        list.get(6).setText(String.valueOf(scene.getLoadedModels().get(scene.currentModelName).getTranslateV().getX()));
        list.get(7).setText(String.valueOf(scene.getLoadedModels().get(scene.currentModelName).getTranslateV().getY()));
        list.get(8).setText(String.valueOf(scene.getLoadedModels().get(scene.currentModelName).getTranslateV().getZ()));
        xSlider.setValue(scene.getLoadedModels().get(scene.currentModelName).getRotateV().getX());
        ySlider.setValue(scene.getLoadedModels().get(scene.currentModelName).getRotateV().getY());
        zSlider.setValue(scene.getLoadedModels().get(scene.currentModelName).getRotateV().getZ());
    }

    public String checkContainsModel (String str) {
        int count = 0;
        for (String name : scene.getLoadedModels().keySet()) {
            if (name.contains(str)) {
                count++;
            }
        }
        if (count > 0) {
            str += "(" + count + ")";
        }
        return str;
    }

    public String checkContainsTexture (String str) {
        int count = 0;
        for (String name : scene.getLoadedTextures().keySet()) {
            if (name.contains(str)) {
                count++;
            }
        }
        if (count > 0) {
            str += "(" + count + ")";
        }
        return str;
    }
    public String checkContainsCamera (String str) {
        int count = 0;
        for (String name : scene.getAddedCameras().keySet()) {
            if (name.contains(str)) {
                count++;
            }
        }
        if (count > 0) {
            str += "(" + count + ")";
        }
        return str;
    }

    public void modelSelected (MouseEvent event) throws IOException {
        if (Objects.equals(event.getButton().toString(), "PRIMARY")) {
            int index = listViewModels.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                addSelectedModels();
                String name = listViewModels.getItems().get(index);
                scene.currentModelName = name;
                cleanTransform();
                anchorPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ENTER && selectedModels.size() > 1) {
                            enterModelName.setVisible(true);
                        }
                        if (event.getCode() == KeyCode.DELETE) {
                            for (String str : selectedModels) {
                                listViewModels.getItems().remove(str);
                                selectedModels.remove(str);
                                scene.getLoadedModels().remove(str);
                                boolean flag = listViewModels.getItems().size() > 0;
                                scene.currentModelName = flag ? selectedModels.get(selectedModels.size() - 1) : null;
                            }
                        }
                    }
                });
            }
        }
    }

    public void okGigaModelPane() {
        Model gigaModel = createGigaModel(selectedModels);
        boolean flag = modelName.getText().contains(".obj");
        String changeModelName = flag ? modelName.getText() : modelName.getText() + ".obj";
        List<String> data = ObjWriter.write(gigaModel);
        StringBuilder fileContent = new StringBuilder();
        for (String s : data) {
            fileContent.append(s).append("\n");
        }
        Model model = ObjReader.read(fileContent.toString(), true);
        //model.calcNormals();
        changeModelName = checkContainsModel(changeModelName);
        scene.getLoadedModels().put(changeModelName, new LoadedModel(model));
        scene.currentModelName = changeModelName;
        listViewModels.getItems().add(scene.currentModelName);
        listViewModels.scrollTo(scene.currentModelName);
        cleanTransform();
        enterModelName.setVisible(false);
    }

    public void closeEnterFileName () {
        enterModelName.setVisible(false);
    }

    public void addSelectedModels() {
        selectedModels = new ArrayList<>();
        List<Integer> list = listViewModels.getSelectionModel().getSelectedIndices();
        for (Integer i : list) {
            String model = listViewModels.getItems().get(i);
            selectedModels.add(model);
        }
    }

    public Model createGigaModel(List<String> selectedModels) {
        Model model = new Model();
        int amountOfVertices = 0, amountOfTextureVertices = 0, amountOfNormals = 0;
        for (String modelName : selectedModels) {
            model.getVertices().addAll(scene.getLoadedModels().get(modelName).getVertices());
            model.getNormals().addAll(scene.getLoadedModels().get(modelName).getNormals());
            model.getTextureVertices().addAll(scene.getLoadedModels().get(modelName).getTextureVertices());
            model.getPolygons().addAll(changePolygonsNumeration(scene.getLoadedModels().get(modelName).getPolygons(), amountOfVertices, amountOfTextureVertices, amountOfNormals));

            amountOfVertices += model.getVertices().size();
            amountOfTextureVertices += model.getTextureVertices().size();
            amountOfNormals += model.getNormals().size();
        }
        return model;
    }

    public static List<com.cgvsu.model.Polygon> changePolygonsNumeration(List<com.cgvsu.model.Polygon> polygonList, int amountOfVertices, int amountOfTextureVertices, int amountOfNormals) {
        List<com.cgvsu.model.Polygon> polygons = new ArrayList<>();
        for (com.cgvsu.model.Polygon oldPolygon : polygonList) {
            com.cgvsu.model.Polygon newPolygon = new Polygon();
            newPolygon.changePolygonNumeration(oldPolygon, amountOfVertices, amountOfTextureVertices, amountOfNormals);
            polygons.add(newPolygon);
        }
        return polygons;
    }

    public void textureSelected (MouseEvent event) throws IOException {
        if (Objects.equals(event.getButton().toString(), "PRIMARY")) {
            checkSelectedTextures();
            int index = listViewTextures.getSelectionModel().getSelectedIndex();
            texture = scene.getLoadedTextures().get(listViewTextures.getItems().get(index));

            anchorPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.DELETE) {
                        for (String str : selectedTextures) {
                            listViewTextures.getItems().remove(str);
                            scene.getLoadedTextures().remove(str);
                        }
                    }
                }
            });
        }
    }

    public void checkSelectedTextures () {
        selectedTextures = new ArrayList<>();
        List<Integer> list = listViewTextures.getSelectionModel().getSelectedIndices();
        for (Integer i : list) {
            selectedTextures.add(listViewTextures.getItems().get(i));
        }
    }
    public void cameraSelected (MouseEvent event) throws IOException {
        if (Objects.equals(event.getButton().toString(), "PRIMARY")) {
            checkSelectedCameras();
            int index = listViewCameras.getSelectionModel().getSelectedIndex();
            scene.setCurrentCamera(scene.getAddedCameras().get(listViewCameras.getItems().get(index)));
            anchorPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.DELETE && listViewCameras.getItems().size() > selectedCameras.size()) {
                        for (String str : selectedCameras) {
                            listViewCameras.getItems().remove(str);
                            scene.getAddedCameras().remove(str);
                            scene.setCurrentCamera(scene.getAddedCameras().get(listViewCameras.getItems().get(listViewCameras.getItems().size()-1)));
                        }
                    }
                }
            });
        }
    }

    public void checkSelectedCameras () {
        selectedCameras = new ArrayList<>();
        List<Integer> list = listViewCameras.getSelectionModel().getSelectedIndices();
        for (Integer i : list) {
            selectedCameras.add(listViewCameras.getItems().get(i));
        }
    }

    public void darkTheme () {
        if (!dark.isSelected()) {
            dark.setSelected(true);
        }
        light.setSelected(false);
        anchorPane.setStyle("-fx-background-color: #454444;");
        saveSelection.setStyle("-fx-background-color: #707070;");
        enterModelName.setStyle("-fx-background-color: #707070;");
        enterCameraData.setStyle("-fx-background-color: #707070;");
        listViewModels.setStyle("-fx-control-inner-background: #616161;");
        listViewTextures.setStyle("-fx-control-inner-background: #616161;");
        listViewCameras.setStyle("-fx-control-inner-background: #616161;");
    }

    public void lightTheme () {
        dark.setSelected(false);
        saveSelection.setStyle("-fx-background-color: lightgray;");
        enterModelName.setStyle("-fx-background-color: lightgray;");
        enterCameraData.setStyle("-fx-background-color: lightgray;");
        anchorPane.setStyle("-fx-background-color: white;");
        listViewModels.setStyle("-fx-control-inner-background: white;");
        listViewTextures.setStyle("-fx-control-inner-background: white;");
        listViewCameras.setStyle("-fx-control-inner-background: white;");
    }

    public void openSaveSelection() {
        if (modelIsLoaded) {
            saveSelection.setVisible(true);
        }
    }

    public void closeSaveSelection () {
        saveSelection.setVisible(false);
    }

    public static void exception(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Reader Exception");
        alert.setHeaderText(text);
        alert.setContentText("Please correct the data.");
        alert.showAndWait();
    }

    public void checkModelLoading (boolean visibleM, boolean visibleT) {
        String[] colors = getBackGround(dark.isSelected());
        if (!visibleM) {
            rightUpRect.setVisible(false);
            rightUpText.setVisible(false);
            rightDownRect.setVisible(false);
            rightDownText.setVisible(false);
            leftUpRect.setVisible(false);
            leftUpText.setVisible(false);
            leftMediumRect.setVisible(false);
            leftMediumText.setVisible(false);
            modelTrans.setStyle(colors[0]);
            loadedModels.setStyle(colors[0]);
            renderingModels.setStyle(colors[0]);
            loadedCameras.setStyle(colors[0]);
        } else {
            rightUpRect.setVisible(true);
            rightUpText.setVisible(true);
            rightDownRect.setVisible(true);
            rightDownText.setVisible(true);
            leftUpRect.setVisible(true);
            leftUpText.setVisible(true);
            leftMediumRect.setVisible(true);
            leftMediumText.setVisible(true);
            modelTrans.setStyle(colors[1]);
            loadedModels.setStyle(colors[1]);
            renderingModels.setStyle(colors[1]);
            loadedCameras.setStyle(colors[1]);
            loadedCameras.setStyle(colors[1]);
        }
        if (!visibleT) {
            leftDownRect.setVisible(false);
            leftDownText.setVisible(false);
            loadedTextures.setStyle(colors[0]);

        } else {
            leftDownRect.setVisible(true);
            leftDownText.setVisible(true);
            loadedTextures.setStyle(colors[1]);
        }
    }
    public String[] getBackGround(boolean theme) {
        if (theme) {
            return new String[] {"-fx-background-color: #454444;", "-fx-background-color: #707070;"};
        } else {
            return new String[] {"-fx-background-color: white;", "-fx-background-color: lightgray;"};
        }
    }
}