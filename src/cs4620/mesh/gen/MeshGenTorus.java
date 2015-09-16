package cs4620.mesh.gen;

import cs4620.mesh.MeshData;
import egl.NativeMem;
import egl.math.Matrix4;
import egl.math.Vector3;

/**
 * Generates A Torus Mesh
 * @author Cristian
 *
 */
public class MeshGenTorus extends MeshGenerator {
	public static final int n = 32; 
	public static final int m = 16; 
	public static final float r = (float) 0.25; // radius
	@Override
	public void generate(MeshData outData, MeshGenOptions opt) {
		// TODO#A1 SOLUTION START
		// Calculate Vertex And Index Count
		outData.vertexCount = (m+1) * (n+1);
		outData.indexCount  = (n * 2 * m) * 3;

		// Create Storage Spaces
		outData.positions = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.uvs = NativeMem.createFloatBuffer(outData.vertexCount * 2);
		outData.normals = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.indices = NativeMem.createIntBuffer(outData.indexCount);

		// Create The Vertices
		for(int i = 0; i < m + 1; i ++) {
			for (int j = 0; j < n + 1; j ++){
				outData.positions.put( new float[]{ (float) ((Math.cos((2*Math.PI )*j/n)*r + 1)*(Math.cos((2*Math.PI )*i/m))), 
						(float) (Math.sin((2*Math.PI )*j/n )*r),
						(float) ((Math.cos((2*Math.PI )*j/n)*r + 1)*(Math.sin((2*Math.PI )*i/m)))} );
			}
		}


		// Add Normals For 6 Faces
		for(int i = 0; i < m + 1; i ++) {
			for (int j = 0; j < n + 1; j ++){
				outData.normals.put( new float[]{ (float) ((Math.cos((2*Math.PI )*j/n)*r + 1)*(Math.cos((2*Math.PI )*i/m)) - (Math.cos((2*Math.PI )*i/m))), 
						(float) (Math.sin((2*Math.PI )*j/n )*r),
						(float) ((Math.cos((2*Math.PI )*j/n)*r + 1)*(Math.sin((2*Math.PI )*i/m)) - (Math.sin((2*Math.PI )*i/m)))} );
			}
		}

		// Add UV Coordinates
		for (float i = 0; i < m + 1; i ++){
			for (float j = 0; j < n + 1; j ++){
				outData.uvs.put( new float[]{ (float) (i/m), (float) (j/n)} );
			}
		}

		// Add Indices
		for(int i = 0; i < m ; i ++) {
			for(int j = 0; j < n ; j ++){
				outData.indices.put(j + (n+1) * i );
				outData.indices.put(j + (n+1) * i + n + 1 );
				outData.indices.put(j + (n+1) * i + 1);
				outData.indices.put(j + (n+1) * i + 1);
				outData.indices.put(j + (n+1) * i + n + 1 );
				outData.indices.put(j + (n+1) * i + n + 2 );
			}	
		} 
		// #SOLUTION END
	}
}
