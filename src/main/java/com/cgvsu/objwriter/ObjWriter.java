package com.cgvsu.objwriter;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.List;

public class ObjWriter {
    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";

    public static List<String> write(Model mesh) {
        List<String> listFileContent = new ArrayList<>();

        writeVertices(mesh.getVertices(), listFileContent);
        listFileContent.add("# " + mesh.getVertices().size() + " vertices");

        writeTextureVertices(mesh.getTextureVertices(), listFileContent);
        listFileContent.add("# " + mesh.getTextureVertices().size() + " texture coordinates");

        writeNormals(mesh.getNormals(), listFileContent);
        listFileContent.add("# " + mesh.getNormals().size() + " vertices");

        writePolygons(mesh.getPolygons(), listFileContent);
        listFileContent.add("# " + mesh.getPolygons().size() + " polygons");
        return listFileContent;
    }

    public static void writeVertices(final List<Vector3f> vertices, List<String> outListFileContent) { //try? exception?
        for (Vector3f vertex : vertices) {
            outListFileContent.add(OBJ_VERTEX_TOKEN + " " + vertex.getX()
                    + " " + vertex.getY() + " " + vertex.getZ());
        }

    }

    public static void writeTextureVertices(final List<Vector2f> textureVertices, List<String> outListFileContent) {
        for (Vector2f textureVertex : textureVertices) {
            outListFileContent.add(OBJ_TEXTURE_TOKEN + " " + textureVertex.getX()
                    + " " + textureVertex.getY());
        }
    }

    public static void writeNormals(final List<Vector3f> normals, List<String> outListFileContent) {
        for (Vector3f normal : normals) {
            outListFileContent.add(OBJ_NORMAL_TOKEN + " " + normal.getX()
                    + " " + normal.getY() + " " + normal.getZ());
        }
    }

    public static void writePolygons(final List<Polygon> polygons, List<String> outListFileContent) {
        for (int i = 0; i < polygons.size(); i++) {
            writeOnePolygon(polygons, i, outListFileContent);
        }
    }

    public static void writeOnePolygon(final List<Polygon> polygons, int i, List<String> outListFileContent) {

        // f 1/2/3 v/vt/vn

        StringBuilder strPolygon = new StringBuilder();
        strPolygon.append(OBJ_FACE_TOKEN + " ");

        try {
            if (polygons.get(i).getSizePolygonsTextureVertexIndices() == 0 &&
                    polygons.get(i).getSizePolygonsNormalIndices() == 0) {
                for (int j = 0; j < polygons.get(i).getSizePolygonsVertexIndices(); j++) {
                    strPolygon.append(polygons.get(i).getVertexIndices().get(j) + 1).append(" ");
                }
            } else if (polygons.get(i).getSizePolygonsTextureVertexIndices() != 0 &&
                    polygons.get(i).getSizePolygonsNormalIndices() == 0) {
                for (int j = 0; j < polygons.get(i).getSizePolygonsTextureVertexIndices(); j++) {
                    strPolygon.append(polygons.get(i).getVertexIndices().get(j) + 1).append("/").
                            append(polygons.get(i).getTextureVertexIndices().get(j) + 1).append(" ");
                }
            } else if (polygons.get(i).getSizePolygonsTextureVertexIndices() == 0 &&
                    polygons.get(i).getSizePolygonsNormalIndices() != 0) {
                for (int j = 0; j < polygons.get(i).getSizePolygonsNormalIndices(); j++) {
                    strPolygon.append(polygons.get(i).getVertexIndices().get(j) + 1).append("//").
                            append(polygons.get(i).getNormalIndices().get(j) + 1).append(" ");
                }
            } else {
                for (int j = 0; j < polygons.get(i).getSizePolygonsTextureVertexIndices(); j++) {
                    strPolygon.append(polygons.get(i).getVertexIndices().get(j) + 1).append("/").append(
                            polygons.get(i).getTextureVertexIndices().get(j) + 1).append("/").append(
                            polygons.get(i).getNormalIndices().get(j) + 1).append(" ");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new ObjWriterException("Too few vertex arguments");
        }
        outListFileContent.add(String.valueOf(strPolygon));
    }
}
