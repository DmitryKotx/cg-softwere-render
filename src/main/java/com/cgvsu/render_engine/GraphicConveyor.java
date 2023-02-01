package com.cgvsu.render_engine;

import com.cgvsu.math.*;

public class GraphicConveyor {

    public static Matrix4f rotate(final Vector3f rotateV) {
        final double xAngle = Math.toRadians(rotateV.getX());
        final double yAngle = Math.toRadians(rotateV.getY());
        final double zAngle = Math.toRadians(rotateV.getZ());
        final double[] firstLine = {
                Math.cos(yAngle) * Math.cos(zAngle),
                -Math.sin(zAngle) * Math.cos(yAngle),
                Math.sin(yAngle),
                0};
        final double[] secondLine = {
                Math.sin(xAngle) * Math.sin(yAngle) * Math.cos(zAngle) + Math.sin(zAngle) * Math.cos(xAngle),
                -Math.sin(xAngle) * Math.sin(yAngle) * Math.sin(zAngle) + Math.cos(xAngle) * Math.cos(zAngle),
                -Math.sin(xAngle) * Math.cos(yAngle),
                0};
        final double[] thirdLine = {
                Math.sin(xAngle) * Math.sin(zAngle) - Math.sin(yAngle) * Math.cos(xAngle) * Math.cos(zAngle),
                Math.sin(xAngle) * Math.cos(zAngle) + Math.sin(yAngle) * Math.sin(zAngle) * Math.cos(xAngle),
                Math.cos(xAngle) * Math.cos(yAngle),
                0};
        final double[] fourthLine = {0, 0, 0, 1};
        final double[][] matrixA = {firstLine, secondLine, thirdLine, fourthLine};
        return new Matrix4f(matrixA);
    }

    public static Matrix4f scale(final Vector3f scaleV) {
        final double xScale = scaleV.getX();
        final double yScale = scaleV.getY();
        final double zScale = scaleV.getZ();
        final double[] firstLine = {xScale, 0, 0, 0};
        final double[] secondLine = {0, yScale, 0, 0};
        final  double[] thirdLine = {0, 0, zScale, 0};
        final double[] fourthLine = {0, 0, 0, 1};
        final  double[][] matrixA = {firstLine, secondLine, thirdLine, fourthLine};
        return new Matrix4f(matrixA);
    }

    public static Matrix4f translate(final Vector3f translateV) {
        final double xTranslate = translateV.getX();
        final double yTranslate = translateV.getY();
        final double zTranslate = translateV.getZ();
        final double[] firstLine = {1, 0, 0, xTranslate};
        final double[] secondLine = {0, 1, 0, yTranslate};
        final double[] thirdLine = {0, 0, 1, zTranslate};
        final double[] fourthLine = {0, 0, 0, 1};
        final double[][] matrixA = {firstLine, secondLine, thirdLine, fourthLine};
        return new Matrix4f(matrixA);
    }

    public static Matrix4f rotateScaleTranslate(final Vector3f rotateV, final Vector3f scaleV, final Vector3f translateV) {
        return Matrix4f.matrixMultiplier(Matrix4f.matrixMultiplier(translate(translateV), scale(scaleV)), rotate(rotateV));
    }

    public static Matrix4f lookAt(final Vector3f eye, final Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4f lookAt(final Vector3f eye, final Vector3f target, final Vector3f up) {
        Vector3f resultZ = Vector3f.subtraction(target, eye);
        Vector3f resultX = Vector3f.crossProduct(up, resultZ);
        Vector3f resultY = Vector3f.crossProduct(resultZ, resultX);

        resultX.normalize();
        resultY.normalize();
        resultZ.normalize();

        double[] matrix = new double[]{
                resultX.getX(), resultX.getY(), resultX.getZ(), -Vector3f.dotProduct(resultX, eye),
                resultY.getX(), resultY.getY(), resultY.getZ(), -Vector3f.dotProduct(resultY, eye),
                resultZ.getX(), resultZ.getY(), resultZ.getZ(), -Vector3f.dotProduct(resultZ, eye),
                0, 0, 0, 1};
        return new Matrix4f(matrix, 4);
    }

    public static Matrix4f perspective(final float fov, final float aspectRatio,
                                       final float nearPlane, final float farPlane) {
        Matrix4f result = new Matrix4f();
        final float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        result.getMatrixArray()[0][0] = tangentMinusOnDegree / aspectRatio;
        result.getMatrixArray()[1][1] = tangentMinusOnDegree;
        result.getMatrixArray()[2][2] = (farPlane + nearPlane) / (farPlane - nearPlane);
        result.getMatrixArray()[3][2] = 1.0F;
        result.getMatrixArray()[2][3] = 2 * (nearPlane * farPlane) / (nearPlane - farPlane);
        return result;
    }

    public static Vector2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Vector2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }

    public static Vector3f multiplierMatrixToVector(final Matrix4f matrix, final Vector4f vector) {
        final Vector4f multipliedV = Matrix4f.multiplierVector(matrix, vector);
        return new Vector3f(
                multipliedV.getX() / multipliedV.getW(),
                multipliedV.getY() / multipliedV.getW(),
                multipliedV.getZ() / multipliedV.getW());
    }
}
