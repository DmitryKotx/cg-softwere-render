package com.cgvsu.render_engine;

import java.util.ArrayList;
import java.util.List;

import com.cgvsu.math.*;
import com.cgvsu.model.LoadedModel;
import com.cgvsu.model.Polygon;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static com.cgvsu.render_engine.GraphicConveyor.*;

public class RenderEngine {

    public static void render(final GraphicsContext graphicsContext, final Camera camera, final LoadedModel model,
                              final int width, final int height, final Vector3f rotateV, final Vector3f scaleV,
                              final Vector3f translateV, final Color meshColor, final boolean drawPolygonMesh,
                              boolean drawTextures, final boolean drawLighting, final boolean drawFilling,
                              final Color polygonFillColor, final Image img, final boolean hitbox) {
        double[][] zBuffer = new double[width][height];
        for (int i = 0; i < zBuffer.length; i++) {
            for (int j = 0; j < zBuffer[0].length; j++) {
                zBuffer[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        Matrix4f modelMatrix = rotateScaleTranslate(rotateV, scaleV, translateV);
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        Matrix4f modelViewProjectionMatrix = new Matrix4f(projectionMatrix);
        modelViewProjectionMatrix = Matrix4f.matrixMultiplier(modelViewProjectionMatrix, viewMatrix);
        modelViewProjectionMatrix = Matrix4f.matrixMultiplier(modelViewProjectionMatrix, modelMatrix);
        final int nPolygons = model.getPolygons().size();
        for (int polygonInd = 0; polygonInd < nPolygons; polygonInd++) {
            Polygon polygon = model.getPolygons().get(polygonInd);
            List<Integer> vertexIndices = polygon.getVertexIndices();
            List<Integer> textureVertexIndices = polygon.getTextureVertexIndices();

            List<Vector2f> resultPoints = new ArrayList<>();
            List<Vector3f> originalVectors = new ArrayList<>();
            List<Vector2f> texturePoints = new ArrayList<>();
            List<Vector3f> normals = new ArrayList<>();
            int i = 0;
            for (Integer vertexIndex : vertexIndices) {
                Vector3f vertex = model.getVertices().get(vertexIndex);
                Vector3f normal = model.getNormals().get(vertexIndex);

                Vector4f vertex4f = new Vector4f(vertex.getX(), vertex.getY(), vertex.getZ(), 1);

                if (!model.getTextureVertices().isEmpty()) {
                    Vector2f texturePoint = model.getTextureVertices().get(textureVertexIndices.get(i));
                    texturePoints.add(texturePoint);
                }
                Vector3f originalVector = multiplierMatrixToVector(modelViewProjectionMatrix, vertex4f);
                Vector2f resultPoint = vertexToPoint(originalVector, width, height);
                resultPoints.add(resultPoint);
                originalVectors.add(originalVector);
                normals.add(normal);
                i++;
            }

            if (img == null) {
                drawTextures = false;
            }

            if (drawFilling || drawTextures) {
                paintPolygon(resultPoints, originalVectors, texturePoints, normals, camera.getPosition(),
                        graphicsContext, zBuffer, drawTextures, drawLighting, polygonFillColor, img);
            }

            if (drawPolygonMesh) {
                graphicsContext.setStroke(meshColor);
                strokePolygon(resultPoints, originalVectors, graphicsContext, zBuffer);
            }
        }
        if(hitbox) {
            List<Vector2f> hitBoxPoint = new ArrayList<>();
            List<Vector3f> hitBoxPointMultiplier = new ArrayList<>();
            for (int i = 0; i < model.getHitBoxPoints().size(); i++) {
                Vector4f vertex4f = new Vector4f(model.getHitBoxPoints().get(i).getX(), model.getHitBoxPoints().get(i).getY(), model.getHitBoxPoints().get(i).getZ(), 1);
                hitBoxPointMultiplier.add(multiplierMatrixToVector(modelViewProjectionMatrix, vertex4f));
                hitBoxPoint.add(GraphicConveyor.vertexToPoint(hitBoxPointMultiplier.get(i), width, height));
            }
            strokeHitBox(hitBoxPoint, graphicsContext);
        }

    }

    private static void strokeHitBox(final List<Vector2f> hitBoxPoint, GraphicsContext graphicsContext){
        graphicsContext.setStroke(Color.GREEN);
        graphicsContext.strokeLine(hitBoxPoint.get(0).getX(), hitBoxPoint.get(0).getY(), hitBoxPoint.get(1).getX(), hitBoxPoint.get(1).getY());
        graphicsContext.strokeLine(hitBoxPoint.get(1).getX(), hitBoxPoint.get(1).getY(), hitBoxPoint.get(2).getX(), hitBoxPoint.get(2).getY());
        graphicsContext.strokeLine(hitBoxPoint.get(2).getX(), hitBoxPoint.get(2).getY(), hitBoxPoint.get(3).getX(), hitBoxPoint.get(3).getY());
        graphicsContext.strokeLine(hitBoxPoint.get(3).getX(), hitBoxPoint.get(3).getY(), hitBoxPoint.get(0).getX(), hitBoxPoint.get(0).getY());
        graphicsContext.strokeLine(hitBoxPoint.get(4).getX(), hitBoxPoint.get(4).getY(), hitBoxPoint.get(5).getX(), hitBoxPoint.get(5).getY());
        graphicsContext.strokeLine(hitBoxPoint.get(5).getX(), hitBoxPoint.get(5).getY(), hitBoxPoint.get(6).getX(), hitBoxPoint.get(6).getY());
        graphicsContext.strokeLine(hitBoxPoint.get(6).getX(), hitBoxPoint.get(6).getY(), hitBoxPoint.get(7).getX(), hitBoxPoint.get(7).getY());
        graphicsContext.strokeLine(hitBoxPoint.get(7).getX(), hitBoxPoint.get(7).getY(), hitBoxPoint.get(4).getX(), hitBoxPoint.get(4).getY());
        graphicsContext.strokeLine(hitBoxPoint.get(4).getX(), hitBoxPoint.get(4).getY(), hitBoxPoint.get(0).getX(), hitBoxPoint.get(0).getY());
        graphicsContext.strokeLine(hitBoxPoint.get(5).getX(), hitBoxPoint.get(5).getY(), hitBoxPoint.get(1).getX(), hitBoxPoint.get(1).getY());
        graphicsContext.strokeLine(hitBoxPoint.get(6).getX(), hitBoxPoint.get(6).getY(), hitBoxPoint.get(2).getX(), hitBoxPoint.get(2).getY());
        graphicsContext.strokeLine(hitBoxPoint.get(7).getX(), hitBoxPoint.get(7).getY(), hitBoxPoint.get(3).getX(), hitBoxPoint.get(3).getY());
    }

    private static void strokePolygon(final List<Vector2f> resultPoints,
                                      final List<Vector3f> originalVectors,
                                      final GraphicsContext graphicsContext,
                                      double[][] zBuffer) {
        for (int i = 0; i < resultPoints.size(); i++) {
            final int j = (i + 1) % 3;
            final int k = (j + 1) % 3;
            Vector2f p1 = resultPoints.get(i);
            Vector2f p2 = resultPoints.get(j);
            Vector2f p3 = resultPoints.get(k);
            Vector3f v1 = originalVectors.get(i);
            Vector3f v2 = originalVectors.get(j);
            Vector3f v3 = originalVectors.get(k);
            double dx = p2.getX() - p1.getX();
            double dy = p2.getY() - p1.getY();
            double steps = Math.max(Math.abs(dx), Math.abs(dy));
            double xStep = dx / steps;
            double yStep = dy / steps;
            double x1 = p1.getX();
            double y1 = p1.getY();
            for (double l = 0; l < steps; l += 1) {
                int x = (int) (x1 + xStep * l);
                int y = (int) (y1 + yStep * l);
                boolean inArray = x >= 0 && y >= 0 && x < zBuffer.length && y < zBuffer[0].length;
                BarycentricCoordinates bc = new BarycentricCoordinates(p1, p2, p3, new Vector2f(x, y));
                double z = bc.getU() * v1.getZ() + bc.getV() * v2.getZ() + bc.getW() * v3.getZ();
                if (inArray && z <= zBuffer[x][y]) {
                    graphicsContext.strokeLine(x, y, x, y);
                }
            }
        }
    }

    private static void paintPolygon(final List<Vector2f> resultPoints,
                                     final List<Vector3f> originalVectors,
                                     final List<Vector2f> texturePoints,
                                     final List<Vector3f> normals,
                                     final Vector3f cameraPos,
                                     final GraphicsContext graphicsContext,
                                     double[][] zBuffer,
                                     final boolean drawTextures,
                                     final boolean lighting,
                                     final Color polygonFillColor,
                                     final Image img) {
        final Vector2f p1 = resultPoints.get(0);
        final Vector2f p2 = resultPoints.get(1);
        final Vector2f p3 = resultPoints.get(2);
        final Vector3f v1 = originalVectors.get(0);
        final Vector3f v2 = originalVectors.get(1);
        final Vector3f v3 = originalVectors.get(2);
        int minX = (int) Math.max(0, Math.min(p1.getX(), Math.min(p2.getX(), p3.getX())));
        int maxX = (int) Math.min(zBuffer.length - 1, Math.max(p1.getX(), Math.max(p2.getX(), p3.getX())));
        int minY = (int) Math.max(0, Math.min(p1.getY(), Math.min(p2.getY(), p3.getY())));
        int maxY = (int) Math.min(zBuffer[0].length - 1, Math.max(p1.getY(), Math.max(p2.getY(), p3.getY())));
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                BarycentricCoordinates bc = new BarycentricCoordinates(p1, p2, p3, new Vector2f(x, y));
                double vZ = bc.getU() * v1.getZ() + bc.getV() * v2.getZ() + bc.getW() * v3.getZ();
                if (bc.belongsToTriangle() && vZ < zBuffer[x][y]) {
                    Color color;
                    if (drawTextures) {
                        final Vector2f t1 = texturePoints.get(0);
                        final Vector2f t2 = texturePoints.get(1);
                        final Vector2f t3 = texturePoints.get(2);
                        double xt = bc.getU() * t1.getX() + bc.getV() * t2.getX() + bc.getW() * t3.getX();
                        double yt = bc.getU() * t1.getY() + bc.getV() * t2.getY() + bc.getW() * t3.getY();
                        if (xt > 1) {
                            xt--;
                        }
                        if (yt > 1) {
                            yt--;
                        }
                        xt *= img.getWidth();
                        yt = img.getHeight() - yt * img.getHeight();
                        color = img.getPixelReader().getColor((int) xt, (int) yt);
                    } else {
                        color = Color.color(polygonFillColor.getRed(), polygonFillColor.getGreen(), polygonFillColor.getBlue());
                    }
                    if (lighting) {
                        final Vector3f n1 = normals.get(0);
                        final Vector3f n2 = normals.get(1);
                        final Vector3f n3 = normals.get(2);
                        Vector3f v = new Vector3f(bc.getU() * v1.getX() + bc.getV() * v2.getX() + bc.getW() * v3.getX(),
                                bc.getU() * v1.getY() + bc.getV() * v2.getY() + bc.getW() * v3.getY(),
                                vZ);
                        Vector3f direction = Vector3f.subtraction(cameraPos, v);
                        Vector3f normal = new Vector3f(bc.getU() * n1.getX() + bc.getV() * n2.getX() + bc.getW() * n3.getX(),
                                bc.getU() * n1.getY() + bc.getV() * n2.getY() + bc.getW() * n3.getY(),
                                bc.getU() * n1.getZ() + bc.getV() * n2.getZ() + bc.getW() * n3.getZ());
                        direction.normalize();
                        normal.normalize();
                        double diffuseDotProduct = Vector3f.dotProduct(direction, normal);
                        double diffuse = Math.max(0, diffuseDotProduct);
                        /*
                        Vector3f d = Vector3f.multiplier(normal, diffuse);
                        Vector3f s = Vector3f.multiplier(d, 2);
                        double specularDotProduct = Vector3f.dotProduct(Vector3f.subtraction(direction, s), direction);
                        double specular = Math.max(0, specularDotProduct);
                        double coefficient = Math.min(1, specular + diffuse);
                         */
                        color = Color.color(color.getRed() * diffuse, color.getGreen() * diffuse, color.getBlue() * diffuse);
                    }
                    graphicsContext.setStroke(color);
                    graphicsContext.strokeLine(x, y, x, y);
                    zBuffer[x][y] = vZ;
                }
            }
        }
    }
}