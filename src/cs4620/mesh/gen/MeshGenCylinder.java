package cs4620.mesh.gen;

import cs4620.mesh.MeshData;
import egl.NativeMem;

/**
 * Generates A Cylinder Mesh
 * @author Cristian (Original)
 * @author Jimmy (Revised 8/25/2015)
 */
public class MeshGenCylinder extends MeshGenerator {
	public static final int m = 128;
	@Override
	public void generate(MeshData outData, MeshGenOptions opt) {
		// TODO#A1 SOLUTION START
		// Calculate Vertex And Index Count
		
		outData.vertexCount = (m+2)*2 + (m+1)*2;
		outData.indexCount = 4 * m * 3;

		// Create Storage Spaces
		outData.positions = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.uvs = NativeMem.createFloatBuffer(outData.vertexCount * 2);
		outData.normals = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.indices = NativeMem.createIntBuffer(outData.indexCount);

		float[] UpperCyclinderPos = new float[(m+1)*3];
		float[] LowerCyclinderPos = new float[(m+1)*3];
		for (int i = 0; i < m+1; i ++){
			UpperCyclinderPos[i*3]   = (float) Math.sin((Math.PI *2)/m * i);
			UpperCyclinderPos[i*3+1] = 1;
			UpperCyclinderPos[i*3+2] = (float) Math.cos((Math.PI *2)/m * i);
		}
		for (int i = 0; i < m+1; i ++){
			LowerCyclinderPos[i*3]   = (float) Math.sin((Math.PI *2)/m * i);
			LowerCyclinderPos[i*3+1] = -1;
			LowerCyclinderPos[i*3+2] = (float) Math.cos((Math.PI *2)/m * i);
		}	
		
		
		float[] cyclinderPosition = new float[(m+2)*3*2 + 2*(m+1)*3];//66*3
		cyclinderPosition[0]             =  0;
		cyclinderPosition[1]             =  1;
		cyclinderPosition[2]             =  0;
		System.arraycopy(UpperCyclinderPos, 0, cyclinderPosition, 3, UpperCyclinderPos.length);
		cyclinderPosition[ 3 + (m+1)*3 ] =  0;
		cyclinderPosition[ 4 + (m+1)*3 ] = -1;
		cyclinderPosition[ 5 + (m+1)*3 ] =  0;
		System.arraycopy(LowerCyclinderPos, 0, cyclinderPosition, 6+(m+1)*3, LowerCyclinderPos.length);
		System.arraycopy(UpperCyclinderPos, 0, cyclinderPosition, 6+(m+1)*3*2, UpperCyclinderPos.length);
		System.arraycopy(LowerCyclinderPos, 0, cyclinderPosition, 6+(m+1)*3*3, LowerCyclinderPos.length);
		

		
		// Add Positions Cyclinder
		outData.positions.put( cyclinderPosition );

		// Add Normals For 6 Faces
		for(int i = 0; i < m+2 ; i++) { outData.normals.put(0); outData.normals.put( 1); outData.normals.put(0); }
		for(int i = 0; i < m+2 ; i++) { outData.normals.put(0); outData.normals.put(-1); outData.normals.put(0); }
		for(int i = 0; i < m+1 ; i++) { outData.normals.put((float)Math.sin((Math.PI *2)/m * i));
									    outData.normals.put(0); 
									    outData.normals.put((float)Math.cos((Math.PI *2)/m * i)); }
		for(int i = 0; i < m+1 ; i++) { outData.normals.put((float)Math.sin((Math.PI *2)/m * i));
										outData.normals.put(0); 
										outData.normals.put((float)Math.cos((Math.PI *2)/m * i)); }

		// Add UV Coordinates
		// Top
		float[] center2 = { (float) 0.75, (float) 0.75};
		outData.uvs.put(center2);
		for ( int i = 0; i < m+1; i++){
			float[] uvs2 = {(float) (0.25*Math.sin((Math.PI *2)/m * i) +0.75),
							(float) (0.25-0.25*Math.cos((Math.PI *2)/m * i) +0.5)};
			outData.uvs.put(uvs2);
		}
		
		// Bottom
		float[] center1 = { (float) 0.25, (float) 0.75};
		outData.uvs.put(center1);
		for ( int i = 0; i < m+1; i++){
			float[] uvs1 = {(float) (0.25*Math.sin((Math.PI *2)/m * i) +0.25),
							(float) (0.25-0.25*Math.cos((Math.PI *2)/m * i) +0.5)};
			outData.uvs.put(uvs1);
		}
		
		for(float i = 0; i < m+1; i++ ) {
			float[] body1 = {(float) (i/m), (float) 0.5};
			outData.uvs.put(body1);
		}
		for(float i = 0; i < m+1; i++ ) {
			float[] body2 = {(float) (i/m), (float) 0};
			outData.uvs.put(body2);
		}
		
		// Add Indices
		for(int f = 0; f < m; f++) {
			outData.indices.put(0);
			outData.indices.put(f + 1);
			outData.indices.put(f + 2);
		}
		for(int f = 0; f < m; f++) {
			outData.indices.put(m+2);
			outData.indices.put(m + 2 + f + 1);
			outData.indices.put(m + 2 + f + 2);
		}
		for(int f = 0; f < m ; f++) {
			outData.indices.put(f + (m+2)*2  );
			outData.indices.put(f + (m+2)*2  + m + 1);
			outData.indices.put(f + (m+2)*2  + 1);
			outData.indices.put(f + (m+2)*2  + 1);
			outData.indices.put(f + (m+2)*2  + m + 1);
			outData.indices.put(f + (m+2)*2  + m + 2);
		}
		// #SOLUTION END
	}
}
