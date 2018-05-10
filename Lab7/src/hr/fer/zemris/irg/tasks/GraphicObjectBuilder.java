package hr.fer.zemris.irg.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.objUtil.ObjectModel;


public class GraphicObjectBuilder {

	
	public static ObjectModel generateObjectFromFile(String fileName){
		ObjectModel model=new ObjectModel();
		Path path=Paths.get(fileName);
		File file=path.toAbsolutePath().toFile();
		try (BufferedReader input = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), "UTF8"));) {
			String line;
			while (true) {
				line = input.readLine();
				if (line == null)
					break;
				processInputLine(line,model);
			}
			
		} catch (IOException e) {
			System.out.println("File could not been opened! Exiting.");
			System.exit(-1);
		}
		
		return model;
	}
	
	private static void processInputLine(String line,ObjectModel model) {
		if (line.startsWith("v")) {
			String[] elementsS = line.replace("v", "").trim().split(" ");
			double[] elementsD = new double[elementsS.length];
			int i = 0;
			for (String s : elementsS)
				elementsD[i++] = Double.parseDouble(s);
			model.addVertex(elementsD);
		} else if (line.startsWith("f")) {
			String[] elementsS = line.replace("f", "").trim().split(" ");
			int[] elementsI = new int[elementsS.length];
			int i = 0;
			for (String s : elementsS)
				elementsI[i++] = Integer.parseInt(s);
			try {
				model.addFace(elementsI);
			} catch (IllegalArgumentException ignorable) {
				return;
			}
		}
	}
}
