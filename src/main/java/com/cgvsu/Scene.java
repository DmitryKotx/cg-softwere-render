package com.cgvsu;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.LoadedModel;
import com.cgvsu.render_engine.Camera;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Scene {

    String currentModelName;
    String currentCameraName;
    String currentTextureName;

    private Map<String, LoadedModel> loadedModels = new HashMap<>();
    private Map<String, Camera> addedCameras = new HashMap<>();
    private Map<String, Image> loadedTextures = new HashMap<>();


    private Camera currentCamera;

    public String getCurrentModelName() {
        return currentModelName;
    }

    public void setCurrentModelName(String currentModelName) {
        this.currentModelName = currentModelName;
    }

    public Map<String, LoadedModel> getLoadedModels() {
        return loadedModels;
    }
    public Map<String, Image> getLoadedTextures() {
        return loadedTextures;
    }


    public void setLoadedModels(HashMap<String, LoadedModel> loadedModels) {
        this.loadedModels = loadedModels;
    }

    public Camera getCurrentCamera() {
        return currentCamera;
    }

    public Map<String, Camera> getAddedCameras() {
        return addedCameras;
    }

    public String getCurrentCameraName() {
        return currentCameraName;
    }

    public void setCurrentCamera(Camera currentCamera) {
        this.currentCamera = currentCamera;
    }
}
