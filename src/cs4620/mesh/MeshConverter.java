package cs4620.mesh;

import java.util.ArrayList;

import egl.NativeMem;
import egl.math.Vector3;
import egl.math.Vector3i;

/**
 * Performs Normals Reconstruction Upon A Mesh Of Positions
 * @author Cristian
 *
 */
public class MeshConverter {
	/**
	 * Reconstruct A Mesh's Normals So That It Appears To Have Sharp Creases
	 * @param positions List Of Positions
	 * @param tris List Of Triangles (A Group Of 3 Values That Index Into The Positions List)
	 * @return A Mesh With Normals That Lie Normal To Faces
	 */
	public static MeshData convertToFaceNormals(ArrayList<Vector3> positions, ArrayList<Vector3i> tris) {
		MeshData data = new MeshData();

		// Notice
		System.out.println("This Feature Has Been Removed For The Sake Of Assignment Consistency");
		System.out.println("This Feature Will Be Added In A Later Assignment");

		// Please Do Not Fill In This Function With Code

		// After You Turn In Your Assignment, Chuck Norris Will
		// Substitute This Function With His Fiery Will Of Steel

		// TODO#A1 SOLUTION START

		// #SOLUTION END

		return data;
	}
	/**
	 * Reconstruct A Mesh's Normals So That It Appears To Be Smooth
	 * @param positions List Of Positions
	 * @param tris List Of Triangles (A Group Of 3 Values That Index Into The Positions List)
	 * @return A Mesh With Normals That Extrude From Vertices
	 */
	public static MeshData convertToVertexNormals(ArrayList<Vector3> positions, ArrayList<Vector3i> tris) {
		MeshData data = new MeshData();

		// TODO#A1 SOLUTION START
		data.vertexCount = positions.size();
		data.indexCount  = tris.size() * 3;
		data.normals = NativeMem.createFloatBuffer(data.vertexCount * 3);
		data.positions = NativeMem.createFloatBuffer(data.vertexCount * 3);
		data.indices = NativeMem.createIntBuffer(data.indexCount);

		// Add position
		float[] position = new float[positions.size()*3];
		for (int i = 0; i < position.length; i = i + 3){
			position[i]   = positions.get(i/3).x;
			position[i+1] = positions.get(i/3).y;
			position[i+2] = positions.get(i/3).z;
		}
		// Add indice
		int[] indice = new int[tris.size()*3];
		for (int i = 0; i < indice.length; i = i + 3){
			indice[i]   = tris.get(i/3).x;
			indice[i+1] = tris.get(i/3).y;
			indice[i+2] = tris.get(i/3).z;
		}
		data.positions.put(position);
		data.indices.put(indice);

		// Create an array list to hold triangle normal value
		ArrayList<Vector3> triNormal = new ArrayList<Vector3>();
		ArrayList<Vector3> normal = new ArrayList<Vector3>();

		// Calculate vectors for triangle
		for (Vector3i triangle : tris) {
			Vector3 a= new Vector3(); 
			a.x= positions.get(triangle.y).x - positions.get(triangle.x).x;
			a.y= positions.get(triangle.y).y - positions.get(triangle.x).y;
			a.z= positions.get(triangle.y).z - positions.get(triangle.x).z;

			Vector3 b= new Vector3(); 
			b.x= positions.get(triangle.z).x - positions.get(triangle.x).x;
			b.y= positions.get(triangle.z).y - positions.get(triangle.x).y;
			b.z= positions.get(triangle.z).z - positions.get(triangle.x).z;

			// Calculate the normal of a triangle
			triNormal.add(a.cross(b));
		}

		// Initialize the normal of vertex
		for (int i = 0; i < positions.size(); i++) {
			Vector3 normalNode = new Vector3(0,0,0);
			normal.add(normalNode);
		}
		// add triangle's normal for each vertex
		for (int i = 0; i < tris.size(); i++){
			normal.get(tris.get(i).x).add(triNormal.get(i));
			normal.get(tris.get(i).y).add(triNormal.get(i));
			normal.get(tris.get(i).z).add(triNormal.get(i));
		}
		for (Vector3 normalNode : normal){
			normalNode.normalize();
		}
		// Create an array to store the normal value for all the vertices
		float[] result = new float[normal.size()*3];
		for (int i = 0; i < result.length; i = i + 3){
			result[i]   = normal.get(i/3).x;
			result[i+1] = normal.get(i/3).y;
			result[i+2] = normal.get(i/3).z;
		}

		data.normals.put(result);
		// #SOLUTION END
		return data;
	}
}
