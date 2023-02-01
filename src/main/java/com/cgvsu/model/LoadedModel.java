package com.cgvsu.model;

import com.cgvsu.math.Matrix4f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.math.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static com.cgvsu.render_engine.GraphicConveyor.rotateScaleTranslate;

public class LoadedModel extends Model {

    private Vector3f rotateV;
    private Vector3f scaleV;
    private Vector3f translateV;

    public LoadedModel(Model model) {
        super(model);
        rotateV = new Vector3f(0, 0, 0);
        scaleV = new Vector3f(1, 1, 1);
        translateV = new Vector3f(0, 0, 0);
    }

    public LoadedModel(LoadedModel model) {
        super(model);
        rotateV = model.getRotateV();
        scaleV = model.getScaleV();
        translateV = model.getTranslateV();
    }

    public LoadedModel(Model model, Vector3f rotateV, Vector3f scaleV, Vector3f translateV) {
        super(model);
        rotateV = this.rotateV;
        scaleV = this.scaleV;
        translateV = this.translateV;
    }

    public Matrix4f getModelMatrix() {
        return rotateScaleTranslate(rotateV, scaleV, translateV);
    }

    public List<Vector3f> modifiedVertices() {
        List<Vector3f> newVertices = new ArrayList<>();
        for (int i = 0; i < super.getVertices().size(); i++) {
            Vector4f vertex4f = new Vector4f(super.getVertices().get(i).getX(),super.getVertices().get(i).getY(), super.getVertices().get(i).getZ(), 1);
            Vector4f multipliedVector = Matrix4f.multiplierVector(getModelMatrix(), vertex4f);
            newVertices.add(new Vector3f(
                    multipliedVector.getX() / multipliedVector.getW(),
                    multipliedVector.getY() / multipliedVector.getW(),
                    multipliedVector.getZ() / multipliedVector.getW()));
        }
        return newVertices;
    }
    public Model toMesh() {
        return new Model(super.getVertices(), super.getTextureVertices(), super.getNormals(), super.getPolygons());
    }

    public Vector3f getRotateV() {
        return rotateV;
    }

    public void setRotateV(Vector3f rotateV) {
        this.rotateV = rotateV;
    }

    public Vector3f getScaleV() {
        return scaleV;
    }

    public void setScaleV(Vector3f scaleV) {
        this.scaleV = scaleV;
    }

    public Vector3f getTranslateV() {
        return translateV;
    }

    public void setTranslateV(Vector3f translateV) {
        this.translateV = translateV;
    }
}
