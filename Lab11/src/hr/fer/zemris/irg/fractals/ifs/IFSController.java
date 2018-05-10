package hr.fer.zemris.irg.fractals.ifs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;



public class IFSController {

	public static class FunctionCoefs implements Comparable<FunctionCoefs>{
		double a;
		double b;
		double c;
		double d;
		double e;
		double f;
		double p;
		
		@Override
		public int compareTo(FunctionCoefs o) {
			return Double.compare(this.p, o.p);
		}	
	}
	
	//function list
	private LinkedList<FunctionCoefs> functions=new LinkedList<>();
	private int pointsNum;
	private int limit;
	private int eta1;
	private int eta2;
	private int eta3;
	private int eta4;
	private Random random=new Random();
	
	public IFSController(String fileName){
		Path path=Paths.get(fileName);
		File file=path.toAbsolutePath().toFile();
		System.out.println(file);
		System.out.println(file.exists());
		try (BufferedReader input = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), "UTF8"));) {
			String line;
			int lineNum=0;
			double probabilityChecker=0;
			while (true) {
				line = input.readLine();
				if (line == null)
					break;
				System.out.println(line);
				if(lineNum==0){
					if(line.contains("#"))
						line=line.substring(0, line.indexOf("#"));
					this.pointsNum=Integer.parseInt(line.trim());
				}else if(lineNum==1){
					if(line.contains("#"))
						line=line.substring(0, line.indexOf("#"));
					this.limit=Integer.parseInt(line.trim());
				}else if(lineNum==2){
					if(line.contains("#"))
						line=line.substring(0, line.indexOf("#"));
					String[] etaS=line.trim().split(" ");
					this.eta1=Integer.parseInt(etaS[0]);
					this.eta2=Integer.parseInt(etaS[1]);
					
				}else if(lineNum==3){
					if(line.contains("#"))
						line=line.substring(0, line.indexOf("#"));
					String[] etaS=line.trim().split(" ");
					this.eta3=Integer.parseInt(etaS[0]);
					this.eta4=Integer.parseInt(etaS[1]);
				}else{
					line=line.trim();
					if(line.contains("#"))
						line=line.substring(0, line.indexOf("#"));
					String[] coefs=line.trim().split("\\s+");
					if(coefs.length!=7)
						continue;
					FunctionCoefs fc=new FunctionCoefs();
					fc.a=Double.parseDouble(coefs[0]);
					fc.b=Double.parseDouble(coefs[1]);
					fc.c=Double.parseDouble(coefs[2]);
					fc.d=Double.parseDouble(coefs[3]);
					fc.e=Double.parseDouble(coefs[4]);
					fc.f=Double.parseDouble(coefs[5]);
					fc.p=Double.parseDouble(coefs[6]);
					probabilityChecker+=fc.p;
					
					this.functions.add(fc);
				}
				lineNum++;
			}
			if(probabilityChecker<0.9875)
				throw new RuntimeException("Given function add only to "+probabilityChecker+" . Maximal error allowed is 0.9875.");
		} catch (IOException e) {
			System.out.println("File could not been opened! Exiting.");
			System.exit(-1);
		}
		Collections.sort(this.functions);
	}

	public int getPointsNum(){
		return this.pointsNum;
	}
	
	public int getLimit(){
		return this.limit;
	}
	
	public int getEta1(){
		return this.eta1;
	}
	
	public int getEta2(){
		return this.eta2;
	}
	
	public int getEta3(){
		return this.eta3;
	}
	
	public int getEta4(){
		return this.eta4;
	}
	
	public FunctionCoefs getCoeficients(double p){
		double lower=0;
		double upper=0;
		for(FunctionCoefs fc:this.functions){
			lower=upper;
			upper+=fc.p;
			if(lower<=p && p<upper)
				return fc;
			
		}
		return this.functions.getLast();
	}

	public Random getRandom() {
		return this.random;
	}
}
