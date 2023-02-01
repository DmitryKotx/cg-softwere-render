package com.cgvsu.math;

import com.cgvsu.render_engine.GraphicConveyor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphicConveyorTest {

    @Test
    void rotate() {
        Vector3f vector3f = new Vector3f(0, 0, 0);
        Vector3f vector3f1 = new Vector3f(360, 360, 360);
        Vector3f vector3f2 = new Vector3f(180, 180, 180);
        Vector3f vector3f3 = new Vector3f(-180, -180, -180);
        double[] firstLine = {1, 0, 0, 0};
        double[] secondLine = {0, 1, 0, 0};
        double[] thirdLine = {0, 0, 1, 0};
        double[] fourthLine = {0, 0, 0, 1};
        Matrix4f matrixA = new Matrix4f(new double[][]{firstLine, secondLine, thirdLine, fourthLine});
        double[] firstLine1 = {1, 0, 0, 0};
        double[] secondLine1 = {0, 1, 0, 0};
        double[] thirdLine1 = {0, 0, 1, 0};
        double[] fourthLine1 = {0, 0, 0, 1};
        Matrix4f matrixA1 = new Matrix4f(new double[][]{firstLine1, secondLine1, thirdLine1, fourthLine1});
        double[] firstLine2 = {1, 0, 0, 0};
        double[] secondLine2 = {0, 1, 0, 0};
        double[] thirdLine2 = {0, 0, 1, 0};
        double[] fourthLine2 = {0, 0, 0, 1};
        Matrix4f matrixA2 = new Matrix4f(new double[][]{firstLine2, secondLine2, thirdLine2, fourthLine2});
        double[] firstLine3 = {1, 0, 0, 0};
        double[] secondLine3 = {0, 1, 0, 0};
        double[] thirdLine3 = {0, 0, 1, 0};
        double[] fourthLine3 = {0, 0, 0, 1};
        Matrix4f matrixA3 = new Matrix4f(new double[][]{firstLine3, secondLine3, thirdLine3, fourthLine3});
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(GraphicConveyor.rotate(vector3f).getMatrixArray()[i][j], matrixA.getMatrixArray()[i][j], 0.01);
                assertEquals(GraphicConveyor.rotate(vector3f1).getMatrixArray()[i][j], matrixA1.getMatrixArray()[i][j], 0.01);
                assertEquals(GraphicConveyor.rotate(vector3f2).getMatrixArray()[i][j], matrixA2.getMatrixArray()[i][j], 0.01);
                assertEquals(GraphicConveyor.rotate(vector3f3).getMatrixArray()[i][j], matrixA3.getMatrixArray()[i][j], 0.01);
            }
        }
    }

    @Test
    void scale() {
        Vector3f vector3f = new Vector3f(0, 0, 0);
        Vector3f vector3f1 = new Vector3f(1, 1, 1);
        Vector3f vector3f2 = new Vector3f(2, 3, 5);
        Vector3f vector3f3 = new Vector3f(0.5, 2.6, 1.8);
        double[] firstLine = {0, 0, 0, 0};
        double[] secondLine = {0, 0, 0, 0};
        double[] thirdLine = {0, 0, 0, 0};
        double[] fourthLine = {0, 0, 0, 1};
        Matrix4f matrixA = new Matrix4f(new double[][]{firstLine, secondLine, thirdLine, fourthLine});
        double[] firstLine1 = {1, 0, 0, 0};
        double[] secondLine1 = {0, 1, 0, 0};
        double[] thirdLine1 = {0, 0, 1, 0};
        double[] fourthLine1 = {0, 0, 0, 1};
        Matrix4f matrixA1 = new Matrix4f(new double[][]{firstLine1, secondLine1, thirdLine1, fourthLine1});
        double[] firstLine2 = {2, 0, 0, 0};
        double[] secondLine2 = {0, 3, 0, 0};
        double[] thirdLine2 = {0, 0, 5, 0};
        double[] fourthLine2 = {0, 0, 0, 1};
        Matrix4f matrixA2 = new Matrix4f(new double[][]{firstLine2, secondLine2, thirdLine2, fourthLine2});
        double[] firstLine3 = {0.5, 0, 0, 0};
        double[] secondLine3 = {0, 2.6, 0, 0};
        double[] thirdLine3 = {0, 0, 1.8, 0};
        double[] fourthLine3 = {0, 0, 0, 1};
        Matrix4f matrixA3 = new Matrix4f(new double[][]{firstLine3, secondLine3, thirdLine3, fourthLine3});
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(GraphicConveyor.scale(vector3f).getMatrixArray()[i][j], matrixA.getMatrixArray()[i][j]);
                assertEquals(GraphicConveyor.scale(vector3f1).getMatrixArray()[i][j], matrixA1.getMatrixArray()[i][j]);
                assertEquals(GraphicConveyor.scale(vector3f2).getMatrixArray()[i][j], matrixA2.getMatrixArray()[i][j]);
                assertEquals(GraphicConveyor.scale(vector3f3).getMatrixArray()[i][j], matrixA3.getMatrixArray()[i][j]);
            }
        }
    }

    @Test
    void translate() {
        Vector3f vector3f = new Vector3f(0, 0, 0);
        Vector3f vector3f1 = new Vector3f(6, 7, 3);
        Vector3f vector3f2 = new Vector3f(-0.5, 9.0, 12.1);
        double[] firstLine = {1, 0, 0, 0};
        double[] secondLine = {0, 1, 0, 0};
        double[] thirdLine = {0, 0, 1, 0};
        double[] fourthLine = {0, 0, 0, 1};
        Matrix4f matrixA = new Matrix4f(new double[][]{firstLine, secondLine, thirdLine, fourthLine});
        double[] firstLine1 = {1, 0, 0, 0};
        double[] secondLine1 = {0, 1, 0, 0};
        double[] thirdLine1 = {0, 0, 1, 0};
        double[] fourthLine1 = {6, 7, 3, 1};
        Matrix4f matrixA1 = new Matrix4f(new double[][]{firstLine1, secondLine1, thirdLine1, fourthLine1});
        double[] firstLine2 = {1, 0, 0, 0};
        double[] secondLine2 = {0, 1, 0, 0};
        double[] thirdLine2 = {0, 0, 1, 0};
        double[] fourthLine2 = {-0.5, 9.0, 12.1, 1};
        Matrix4f matrixA2 = new Matrix4f(new double[][]{firstLine2, secondLine2, thirdLine2, fourthLine2});
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(GraphicConveyor.translate(vector3f).getMatrixArray()[i][j], matrixA.getMatrixArray()[i][j]);
                assertEquals(GraphicConveyor.translate(vector3f1).getMatrixArray()[i][j], matrixA1.getMatrixArray()[i][j]);
                assertEquals(GraphicConveyor.translate(vector3f2).getMatrixArray()[i][j], matrixA2.getMatrixArray()[i][j]);
            }
        }
    }

    @Test
    void rotateScaleTranslate() {
        Vector3f vector3f = new Vector3f(0, 0, 0);
        Vector3f vector3f1 = new Vector3f(1, 1, 1);
        Vector3f vector3f2 = new Vector3f(2, 4, 6);
        Vector3f vector3f3 = new Vector3f(-7, 3.5, 5);
        double[] firstLine = {0, 0, 0, 0};
        double[] secondLine = {0, 0, 0, 0};
        double[] thirdLine = {0, 0, 0, 0};
        double[] fourthLine = {0, 0, 0, 1};
        Matrix4f matrixA = new Matrix4f(new double[][]{firstLine, secondLine, thirdLine, fourthLine});
        double[] firstLine1 = {1, 0, 0, 0};
        double[] secondLine1 = {0, 1, 0, 0};
        double[] thirdLine1 = {0, 0, 1, 0};
        double[] fourthLine1 = {0, 0, 0, 1};
        Matrix4f matrixA1 = new Matrix4f(new double[][]{firstLine1, secondLine1, thirdLine1, fourthLine1});
        double[] firstLine2 = {2, 0, 0, 0};
        double[] secondLine2 = {0, 4, 0, 0};
        double[] thirdLine2 = {0, 0, 6, 0};
        double[] fourthLine2 = {-7, 3.5, 5, 1};
        Matrix4f matrixA2 = new Matrix4f(new double[][]{firstLine2, secondLine2, thirdLine2, fourthLine2});
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(GraphicConveyor.rotateScaleTranslate(vector3f, vector3f, vector3f).getMatrixArray()[i][j], matrixA.getMatrixArray()[i][j], 0.01);
                assertEquals(GraphicConveyor.rotateScaleTranslate(vector3f, vector3f1, vector3f).getMatrixArray()[i][j], matrixA1.getMatrixArray()[i][j], 0.01);
                assertEquals(GraphicConveyor.rotateScaleTranslate(vector3f, vector3f2, vector3f3).getMatrixArray()[i][j], matrixA2.getMatrixArray()[i][j], 0.01);
            }
        }
    }
}