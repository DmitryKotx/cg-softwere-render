package com.cgvsu.math;

public class Matrix {

    private double[][] matrix;

    protected Matrix(double[][] matrix) {
        this.matrix = matrix;
    }

    protected Matrix(double[] curMatrix, double num) {
        double[][] matrix = new double[(int) num][(int) num];
        for (int i = 0; i<curMatrix.length; i++){
            int page = (int) Math.floor(i/num);
            assert false;
            matrix[page][(int) (i-page*num)] = curMatrix[i];
        }
        this.matrix=matrix;
    }

    protected Matrix() {

    }

    protected Matrix(int num) {
        this.matrix = getZeroMatrix(num).getMatrixArray();
    }

    protected Matrix(Matrix matrix) {
        this.matrix = matrix.matrix;
    }

    public double[][] getMatrixArray() {
        return matrix;
    }

    public static Matrix transpose(double[][] matrix, int n) {
        double[][] m = new double[n][n];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                m[j][i] = matrix[i][j];
            }
        }
        return new Matrix(m);
    }

    public static Matrix getZeroMatrix(int size) {
        double[][] m = new double[size][size];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                m[i][j] = 0;
            }
        }
        return new Matrix(m);
    }

    public static Matrix getOneMatrix(int size) {
        double[][] m = new double[size][size];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if(i == j){
                    m[i][j] = 1;
                }else {
                    m[i][j] = 0;
                }
            }
        }
        return new Matrix(m);
    }

    static Matrix4f inversion(double[][] matrix) {
        if (matrix.length != matrix[0].length) return null;
        int n = matrix.length;
        double[][] augmented = new double[n][n * 2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) augmented[i][j] = matrix[i][j];
            augmented[i][i + n] = 1;
        }
        solve(augmented);
        double[][] inv = new double[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) inv[i][j] = augmented[i][j + n];
        return new Matrix4f(inv);
    }

    // Solves a system of linear equations as an augmented matrix
    // with the rightmost column containing the constants. The answers
    // will be stored on the rightmost column after the algorithm is done.
    // NOTE: make sure your matrix is consistent and does not have multiple
    // solutions before you solve the system if you want a unique valid answer.
    // Time Complexity: O(r²c)
    static void solve(double[][] augmentedMatrix) {
        int nRows = augmentedMatrix.length, nCols = augmentedMatrix[0].length, lead = 0;
        for (int r = 0; r < nRows; r++) {
            if (lead >= nCols) break;
            int i = r;
            while (Math.abs(augmentedMatrix[i][lead]) < 0.00000001) {
                if (++i == nRows) {
                    i = r;
                    if (++lead == nCols) return;
                }
            }
            double[] temp = augmentedMatrix[r];
            augmentedMatrix[r] = augmentedMatrix[i];
            augmentedMatrix[i] = temp;
            double lv = augmentedMatrix[r][lead];
            for (int j = 0; j < nCols; j++) augmentedMatrix[r][j] /= lv;
            for (i = 0; i < nRows; i++) {
                if (i != r) {
                    lv = augmentedMatrix[i][lead];
                    for (int j = 0; j < nCols; j++) augmentedMatrix[i][j] -= lv * augmentedMatrix[r][j];
                }
            }
            lead++;
        }
    }
}
