package cs4620.mesh.gen;

import cs4620.mesh.MeshData;
import egl.NativeMem;

/**
 * Generates A Sphere Mesh
 * @author Cristian
 *
 */
public class MeshGenSphere extends MeshGenerator {
	public static final int n = 32; // longitude
	public static final int m = 16; // latitude
	@Override
	public void generate(MeshData outData, MeshGenOptions opt) {
		// TODO#A1 SOLUTION START
		// Calculate Vertex And Index Count
		outData.vertexCount = n + n + 1 + (n+1)*(m-1) + 1 + n + n;
		outData.indexCount  = (2 * n + 2 * n * (m-2)) * 3;

		// Create Storage Spaces
		outData.positions = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.uvs = NativeMem.createFloatBuffer(outData.vertexCount * 2);
		outData.normals = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.indices = NativeMem.createIntBuffer(outData.indexCount);

		// Create The Vertices
		for(int i = 0; i < n ; i++){
			outData.positions.put(new float[]{0, 1, 0});
		}
		for (int j = 0; j < n+1; j++){
			outData.positions.put( new float[]{  (float) (Math.sin((Math.PI )/m) * Math.sin((Math.PI *2)/n * j)), 
					(float) (Math.cos((Math.PI )/m )),
					(float) (Math.sin((Math.PI )/m )* Math.cos((Math.PI *2)/n * j))} );
		}
		for (int i = 1; i <= m-1; i++) {
			for (int j = 0; j < n+1; j++){
				outData.positions.put( new float[]{  (float) (Math.sin((Math.PI )/m * i) * Math.sin((Math.PI *2)/n * j)), 
						(float) (Math.cos((Math.PI )/m * i)),
						(float) (Math.sin((Math.PI )/m * i )* Math.cos((Math.PI *2)/n * j))} );
			}
		}
		for (int j = 0; j < n+1; j++){
			outData.positions.put( new float[]{  (float) (Math.sin((Math.PI )/m * (m-1)) * Math.sin((Math.PI *2)/n * j)), 
					(float) (Math.cos((Math.PI )/m * (m-1))),
					(float) (Math.sin((Math.PI )/m * (m-1))* Math.cos((Math.PI *2)/n * j))} );
		}
		for(int i = 0; i < n ; i++){
			outData.positions.put(new float[]{0, -1, 0});
		}

		// Add Normals For 6 Faces
		for(int i = 0; i < n ; i++) {
			outData.normals.put(0);
			outData.normals.put(1);
			outData.normals.put(0);
		}
		for (int j = 0; j < n+1; j++) {
			outData.normals.put( new float[]{  (float) (Math.sin((Math.PI )/m) * Math.sin((Math.PI *2)/n * j)), 
					(float) (Math.cos((Math.PI )/m )),
					(float) (Math.sin((Math.PI )/m )* Math.cos((Math.PI *2)/n * j))} );
		}
		for (int i = 1; i <= m-1; i++) {
			for (int j = 0; j < n+1; j++){
				outData.normals.put( new float[]{  (float) (Math.sin((Math.PI )/m * i) * Math.sin((Math.PI *2)/n * j)), 
						(float) (Math.cos((Math.PI )/m * i)),
						(float) (Math.sin((Math.PI )/m * i )* Math.cos((Math.PI *2)/n * j))} );
			}

		}
		for (int j = 0; j < n+1; j++){
			outData.normals.put( new float[]{  (float) (Math.sin((Math.PI )/m * (m-1)) * Math.sin((Math.PI *2)/n * j)), 
					(float) (Math.cos((Math.PI )/m * (m-1))),
					(float) (Math.sin((Math.PI )/m * (m-1))* Math.cos((Math.PI *2)/n * j))} );
		}
		for(int i = 0; i < n ; i++) {
			outData.normals.put(new float[]{0, -1, 0});
		}

		// Add UV Coordinates
		for(float i = 0; i < n ; i++){
			outData.uvs.put(new float[]{(float) i/n, 1});
		}
		for (float i = 0; i < n+1; i++){
			outData.uvs.put(new float[]{(float) (i/n), (float) (1 - (float) 1/m)});
		}

		for (float i = 1; i <= m-1; i++) {
			for (float j = 0; j < n+1; j++){
				outData.uvs.put( new float[]{ (float) (j/n),(float) (1 - i/m)} );
			}
		}
		for (float i = 0; i < n+1; i++){
			outData.uvs.put(new float[]{(float) (i/n), (float) ((float)1/m)});
		}
		for(float i = 0; i < n ; i++){
			outData.uvs.put(new float[]{(float) i/n, 0});
		}

		// Add Indices
		// Top 1+n
		for(int f = 0; f < n  ; f++) {
			outData.indices.put(0 + f);
			outData.indices.put(f + 1 + n -1 );
			outData.indices.put(f + 2 + n -1);
		} 

		// Body n
		for(int i = 0; i < m-2; i++) {		
			for(int f = 0; f < n; f++) {
				outData.indices.put(f + (n+2) + (n+1) * i + n - 1);
				outData.indices.put(f + (n+2) + (n+1) * i + n + 1 + n - 1);
				outData.indices.put(f + (n+2) + (n+1) * i + 1 + n - 1);
				outData.indices.put(f + (n+2) + (n+1) * i + 1 + n - 1);
				outData.indices.put(f + (n+2) + (n+1) * i + n + 1 + n - 1);
				outData.indices.put(f + (n+2) + (n+1) * i + n + 2 + n - 1);
			}
		} 
		// Base
		for(int f = 0; f < n ; f++) {
			outData.indices.put((n+1) * (m-1) + n + 2 + f + n - 1 );
			outData.indices.put((n+1) * (m-1) + n + 2 + 1 + n + n - 1 + f);
			outData.indices.put((n+1) * (m-1) + n + 2 + 1 + f + n - 1);
		}


		// #SOLUTION END
	}
}
