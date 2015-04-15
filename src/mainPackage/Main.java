package mainPackage;
import assistClass.Boundary;
import assistClass.InputData;
import assistClass.CorrelationCalculator;
import singleRecoveryMethods.AveragingMethod;
import singleRecoveryMethods.BezierCurve;
import singleRecoveryMethods.CombinationMethod;
import singleRecoveryMethods.TempReplacement;
import singleRecoveryMethods.TempReplacementBackward;
import singleRecoveryMethods.ZeroInsertion;


public class Main {
	
	// Settings of the whole simulation will be adjust here.
	static String originFilePath="C:\\Users\\Xuping Fang\\Documents\\origin.bvh";
	static String badFilePath="C:\\Users\\Xuping Fang\\Documents\\bad.bvh";
	
	static int packageCapacity=2;
	static double errorRate=0.5;
	
	static String zeroInsertionRecoveredFilePath="C:\\Users\\Xuping Fang\\Documents\\ZeroInsertion.bvh";
	
	static String averagingMethodRecoveredFilePath="C:\\Users\\Xuping Fang\\Documents\\AveragingMethod.bvh";
	
	static String tempReplacementRecoveredFilePath="C:\\Users\\Xuping Fang\\Documents\\TempReplacement.bvh";
	static String tempReplacementBackwardRecoveredFilePath="C:\\Users\\Xuping Fang\\Documents\\TempReplacementBack.bvh";
	
	
	static String bezierCurveRecoveredFilePath="C:\\Users\\Xuping Fang\\Documents\\bezierCurve.bvh";
	
	static String combinedRecoveredFilePath="C:\\Users\\Xuping Fang\\Documents\\combined.bvh";
	

	public static void main(String[] args) {
		
		// Run simulation.
		PackageLossSimulator pls=new PackageLossSimulator(originFilePath,badFilePath,packageCapacity,errorRate);
		
		InputData inputData=pls.simulatePackageLoss();
		
		System.out.println("=======================Test Results For each methods==========================");
		
		// Run different recovery methods.
		runBezierCurve(inputData);
		runAveragingMethod(inputData);
		
		runTempReplacement(inputData);
		runBackwardTempReplacement(inputData);
		
		runZeroInsertion(inputData);
		
		
		runCombinationMethod(inputData);

	}
	
	// Each function below takes same input, runs the specific recovery method and print the correlation value of original file and the recovered file.
	// ==================================================================================================================================================
	private static void runCombinationMethod(InputData inputData){
		
		// Setting of the combination method will be adjust here:
		Boundary avgBoundary=new Boundary(0,10);
		Boundary bezierCurveBoundary=new Boundary(0,200);
		Boundary tempReplacementBoundary=new Boundary(201,Integer.MAX_VALUE);
		
		CombinationMethod cm=new CombinationMethod(inputData,combinedRecoveredFilePath,
				true,avgBoundary,bezierCurveBoundary,tempReplacementBoundary);
		
		cm.doRecovery();
		
		CorrelationCalculator rmsC=new CorrelationCalculator();
		
		rmsC.originFilePath=originFilePath;
		rmsC.recoveredFilePath=combinedRecoveredFilePath;
		
		System.out.print("Correlation value for combined method: ");
		
		rmsC.doCaculation();
	}
	
	private static void runBezierCurve(InputData inputData){
		
		BezierCurve bc=new BezierCurve(inputData,bezierCurveRecoveredFilePath);
		
		bc.runRecovery();
		
		CorrelationCalculator rmsC=new CorrelationCalculator();
		
		rmsC.originFilePath=originFilePath;
		rmsC.recoveredFilePath=bezierCurveRecoveredFilePath;
		
		System.out.print("Correlation value for bezier curve with 4 control points: ");
		
		rmsC.doCaculation();
	}
	
	private static void runAveragingMethod(InputData inputData){
		
		AveragingMethod am=new AveragingMethod(inputData,averagingMethodRecoveredFilePath);
		
		am.runRecovery();
		
		CorrelationCalculator rmsC=new CorrelationCalculator();
		
		rmsC.originFilePath=originFilePath;
		rmsC.recoveredFilePath=averagingMethodRecoveredFilePath;
		
		System.out.print("Correlation value for averaging methods: ");
		rmsC.doCaculation();
	}
	
	private static void runTempReplacement(InputData inputData){
		
		TempReplacement tr=new TempReplacement(inputData,tempReplacementRecoveredFilePath);
		
		tr.runRecovery();
		
		CorrelationCalculator rmsC=new CorrelationCalculator();
		
		rmsC.originFilePath=originFilePath;
		rmsC.recoveredFilePath=tempReplacementRecoveredFilePath;
		
		System.out.print("Correlation value for temp replacement (previous): ");
		rmsC.doCaculation();
	}
	
	private static void runBackwardTempReplacement(InputData inputData){
		
		TempReplacementBackward tr=new TempReplacementBackward(inputData,tempReplacementBackwardRecoveredFilePath);
		
		tr.runRecovery();
		
		CorrelationCalculator rmsC=new CorrelationCalculator();
		
		rmsC.originFilePath=originFilePath;
		rmsC.recoveredFilePath=tempReplacementBackwardRecoveredFilePath;
		
		System.out.print("Correlation value for backward temp replacement: ");
		rmsC.doCaculation();
	}
	
	private static void runZeroInsertion(InputData inputData){
		
		ZeroInsertion zi=new ZeroInsertion(inputData,zeroInsertionRecoveredFilePath);
		
		zi.runRecovery();
		
		CorrelationCalculator rmsC=new CorrelationCalculator();
		
		rmsC.originFilePath=originFilePath;
		rmsC.recoveredFilePath=zeroInsertionRecoveredFilePath;
		
		System.out.print("Correlation value for zero insertion: ");
		rmsC.doCaculation();
	}

}
