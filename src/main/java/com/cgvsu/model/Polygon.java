package com.cgvsu.model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private List<Integer> vertexIndices;
    private List<Integer> textureVertexIndices;
    private List<Integer> normalIndices;

    public Polygon() {
        vertexIndices = new ArrayList<Integer>();
        textureVertexIndices = new ArrayList<Integer>();
        normalIndices = new ArrayList<Integer>();
    }

    public Polygon(List<Integer> vertexIndices, List<Integer> textureVertexIndices, List<Integer> normalIndices) {
        this.vertexIndices = vertexIndices;
        this.textureVertexIndices = textureVertexIndices;
        this.normalIndices = normalIndices;
    }
    public void changePolygonNumeration(Polygon oldPolygon, int amountOfVertices, int amountOfTextureVertices, int amountOfNormals) {
        this.vertexIndices = new ArrayList<>(oldPolygon.vertexIndices);
        this.textureVertexIndices = new ArrayList<>(oldPolygon.textureVertexIndices);
        this.normalIndices = new ArrayList<>(oldPolygon.normalIndices);
        this.vertexIndices.replaceAll(integer -> integer + amountOfVertices);
        this.textureVertexIndices.replaceAll(integer -> integer + amountOfTextureVertices);
        this.normalIndices.replaceAll(integer -> integer + amountOfNormals);
    }

    public void addVertexIndex(final int vertexIndex) {
        this.vertexIndices.add(vertexIndex);
    }

    public void addTextureVertexIndex(final int textureVertexIndex) {
        this.textureVertexIndices.add(textureVertexIndex);
    }

    public void addNormalIndex(final int normalIndex) {
        this.normalIndices.add(normalIndex);
    }

    public void setVertexIndices(List<Integer> vertexIndices) {
        assert vertexIndices.size() >= 3;
        this.vertexIndices = vertexIndices;
    }

    public void setTextureVertexIndices(List<Integer> textureVertexIndices) {
        assert textureVertexIndices.size() >= 3;
        this.textureVertexIndices = textureVertexIndices;
    }

    public void setNormalIndices(List<Integer> normalIndices) {
        assert normalIndices.size() >= 3;
        this.normalIndices = normalIndices;
    }

    public int getSizePolygonsVertexIndices() {
        return vertexIndices.size();
    }

    public int getSizePolygonsTextureVertexIndices() {
        return textureVertexIndices.size();
    }

    public int getSizePolygonsNormalIndices() {
        return normalIndices.size();
    }

    public List<Integer> getVertexIndices() {
        return vertexIndices;
    }

    public List<Integer> getTextureVertexIndices() {
        return textureVertexIndices;
    }

    public List<Integer> getNormalIndices() {
        return normalIndices;
    }
}
