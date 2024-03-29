import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;

public class CloudData {

	Vector[][][] advection; // in-plane regular grid of wind vectors, that evolve over time
	float [][][] convection; // vertical air movement strength, that evolves over time
	float [] linearAdvection;
	int [] linearClassification;
	int [][][] classification; // cloud type per grid point, evolving over time
	int dimx, dimy, dimt; // data dimensions
	int count = 0;

	// overall number of elements in the timeline grids
	int dim(){
		return dimt*dimx*dimy;
	}
	
	// convert linear position into 3D location in simulation grid
	void locate(int pos, int [] ind)
	{
		ind[0] = (int) pos / (dimx*dimy); // t
		ind[1] = (pos % (dimx*dimy)) / dimy; // x
		ind[2] = pos % (dimy); // y
	}
	
	// read cloud simulation data from file
	void readData(String fileName){ 
		try{ 
			Scanner sc = new Scanner(new File(fileName), "UTF-8");
			
			// input grid dimensions and simulation duration in timesteps
			dimt = sc.nextInt();
			dimx = sc.nextInt(); 
			dimy = sc.nextInt();
			
			// initialize and load advection (wind direction and strength) and convection
			advection = new Vector[dimt][dimx][dimy];
			convection = new float[dimt][dimx][dimy];
			linearAdvection = new float[dim()*3];

			for(int t = 0; t < dimt; t++) {
				for (int x = 0; x < dimx; x++) {
					for (int y = 0; y < dimy; y++) {
						advection[t][x][y] = new Vector();
						advection[t][x][y].setX(sc.nextFloat());
						linearAdvection[count++] = advection[t][x][y].getX();
						advection[t][x][y].setY(sc.nextFloat());
						linearAdvection[count++] = advection[t][x][y].getY();
						convection[t][x][y] = sc.nextFloat();
						linearAdvection[count++] = convection[t][x][y];
					}
				}
			}
			classification = new int[dimt][dimx][dimy];
			linearClassification = new int[dim()*3];
			sc.close(); 
		} 
		catch (IOException e){ 
			System.out.println("Unable to open input file "+fileName);
			e.printStackTrace();
		}
		catch (InputMismatchException e){ 
			System.out.println("Malformed input file "+fileName);
			e.printStackTrace();
		}
	}
	
	// write classification output to file
	void writeData(String fileName, Vector wind, char type){
		 try{ 
			 FileWriter fileWriter = new FileWriter(fileName);
			 PrintWriter printWriter = new PrintWriter(fileWriter);
			 printWriter.printf("%d %d %d\n", dimt, dimx, dimy);
			 printWriter.printf("%f %f\n", wind.getX(), wind.getY());

			 if(type == 'S') {
			 for(int t = 0; t < dimt; t++){
				 for(int x = 0; x < dimx; x++){
					for(int y = 0; y < dimy; y++){
						printWriter.printf("%d ", classification[t][x][y]);
					}
				 }
				 printWriter.printf("\n");
		     }
			 }
			 else if (type == 'P') {
				 for (int i = 0; i < dimt; i++) {
					 for (int j = 0; j < dimx*dimy; j++) {
						 printWriter.printf("%d ",linearClassification[i*dimx*dimy+j]);
					 }
					 printWriter.printf("\n");
				 }
			 }
			 else{
				 System.out.println("Unknown Type. S - sequential or P - parallel.");
			 }
				 
			 printWriter.close();
		 }
		 catch (IOException e){
			 System.out.println("Unable to open output file "+fileName);
				e.printStackTrace();
		 }
	}
}
