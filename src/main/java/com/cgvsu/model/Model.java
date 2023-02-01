package com.cgvsu.model;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.objreader.ReaderExceptions;

import java.util.*;

public class Model {

    private List<Vector3f> vertices;
    private List<Vector2f> textureVertices;
    private List<Vector3f> normals;
    private List<Polygon> polygons;
    private List<Vector3f> hitBoxPoints;

    public Model(final List<Vector3f> vertices, final List<Vector2f> textureVertices,
                 final List<Vector3f> normals, final List<Polygon> polygons) {
        this.vertices = vertices;
        this.textureVertices = textureVertices;
        this.normals = normals;
        this.polygons = polygons;
    }

    public Model() {
        vertices = new ArrayList<>();
        textureVertices = new ArrayList<>();
        normals = new ArrayList<>();
        polygons = new ArrayList<>();
    }

    public Model(Model model) {
        if (model != null) {
            vertices = model.vertices;
            textureVertices = model.textureVertices;
            normals = model.normals;
            polygons = model.polygons;
        } else {
            vertices = new ArrayList<>();
            textureVertices = new ArrayList<>();
            normals = new ArrayList<>();
            polygons = new ArrayList<>();
        }
    }

    public List<Vector3f> getHitBoxPoints() {
        return hitBoxPoints;
    }

    public void setHitBoxPoints(List<Vector3f> hitBoxPoints) {
        this.hitBoxPoints = hitBoxPoints;
    }

    public List<Vector3f> getVertices() {
        return vertices;
    }

    public List<Vector2f> getTextureVertices() {
        return textureVertices;
    }

    public List<Vector3f> getNormals() {
        return normals;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public void setVertices(final List<Vector3f> vertices) {
        this.vertices = vertices;
    }

    public void setTextureVertices(final List<Vector2f> vertices) {
        this.textureVertices = vertices;
    }

    public void setNormals(final List<Vector3f> vertices) {
        this.normals = vertices;
    }

    public void setPolygons(final List<Polygon> polygons) {
        this.polygons = polygons;
    }

    public boolean checkConsistency() {
        for (int i = 0; i < polygons.size(); i++) {
            List<Integer> vertexIndices = polygons.get(i).getVertexIndices();
            List<Integer> textureVertexIndices = polygons.get(i).getTextureVertexIndices();
            List<Integer> normalIndices = polygons.get(i).getNormalIndices();
            if (vertexIndices.size() != textureVertexIndices.size()
                    && vertexIndices.size() != 0 && textureVertexIndices.size() != 0) {
                throw new ReaderExceptions.NotDefinedUniformFormatException(
                        "The unified format for specifying polygon descriptions is not defined.");
            }
            if (vertexIndices.size() != normalIndices.size()
                    && vertexIndices.size() != 0 &&  normalIndices.size() != 0) {
                throw new ReaderExceptions.NotDefinedUniformFormatException(
                        "The unified format for specifying polygon descriptions is not defined.");
            }
            if (normalIndices.size() != textureVertexIndices.size()
                    && normalIndices.size() != 0 && textureVertexIndices.size() != 0) {
                throw new ReaderExceptions.NotDefinedUniformFormatException(
                        "The unified format for specifying polygon descriptions is not defined.");
            }
            for (int j = 0; j < vertexIndices.size(); j++) {
                if (vertexIndices.get(j) >= vertices.size()) {
                    throw new ReaderExceptions.FaceException(
                            "Polygon description is wrong.", i + 1);
                }
            }
            for (int j = 0; j < textureVertexIndices.size(); j++) {
                if (textureVertexIndices.get(j) >= textureVertices.size()) {
                    throw new ReaderExceptions.FaceException(
                            "Polygon description is wrong.", i + 1);
                }
            }
            for (int j = 0; j < normalIndices.size(); j++) {
                if (normalIndices.get(j) >= normals.size()) {
                    throw new ReaderExceptions.FaceException(
                            "Polygon description is wrong.", i + 1);
                }
            }
        }
        return true;
    }

    public void triangulate() {
        List<Polygon> triangulatedPolygons = new ArrayList<>();
        for (Polygon polygon : polygons) {
            List<Integer> vertexIndices = polygon.getVertexIndices();
            List<Integer> textureVertexIndices = polygon.getTextureVertexIndices();
            if (vertexIndices.size() > 3) {
                for (int i = 2; i < vertexIndices.size(); i++) {
                    Polygon triangle = new Polygon();
                    triangle.addVertexIndex(vertexIndices.get(0));
                    triangle.addVertexIndex(vertexIndices.get(i - 1));
                    triangle.addVertexIndex(vertexIndices.get(i));
                    if (!textureVertexIndices.isEmpty()) {
                        triangle.addTextureVertexIndex(textureVertexIndices.get(0));
                        triangle.addTextureVertexIndex(textureVertexIndices.get(i - 1));
                        triangle.addTextureVertexIndex(textureVertexIndices.get(i));
                    }
                    triangle.addNormalIndex(vertexIndices.get(0));
                    triangle.addNormalIndex(vertexIndices.get(i - 1));
                    triangle.addNormalIndex(vertexIndices.get(i));
                    triangulatedPolygons.add(triangle);
                }
            } else {
                triangulatedPolygons.add(polygon);
            }
        }
        setPolygons(triangulatedPolygons);
        calcSmoothedNormals();
    }

    private Vector3f calcPolygonNormal(Polygon polygon) {
        Vector3f a = vertices.get(polygon.getVertexIndices().get(0));
        Vector3f b = vertices.get(polygon.getVertexIndices().get(1));
        Vector3f c = vertices.get(polygon.getVertexIndices().get(2));
        Vector3f ab = Vector3f.subtraction(b, a);
        Vector3f bc = Vector3f.subtraction(c, b);
        return Vector3f.crossProduct(ab, bc);
    }

    private void calcSmoothedNormals() {
        List<Vector3f> smoothedNormals = new ArrayList<>();
        for (int vertexIndex = 0; vertexIndex < vertices.size(); vertexIndex++) {
            Vector3f sumNormals = new Vector3f();
            int k = 0;
            for (Polygon polygon : polygons) {
                List<Integer> vertexIndices = polygon.getVertexIndices();
                if (vertexIndices.contains(vertexIndex)) {
                    Vector3f normal = calcPolygonNormal(polygon);
                    sumNormals = Vector3f.addition(sumNormals, normal);
                    k++;
                }
            }
            Vector3f smoothedNormal = Vector3f.separation(sumNormals, k);
            smoothedNormals.add(smoothedNormal);
        }
        setNormals(smoothedNormals);
    }
}