package testbench;

import logging.ConsoleLogger;
import logging.ILog;
import logging.TimeUnit;
import timing.ITimer;
import timing.Timer;
import bench.IBenchmark;
import bench.cpu.CPUFixedPoint;
import bench.cpu.CPUFixedVsFloatingPointTest;
import bench.cpu.CPUNumberRepresentation;
import bench.cpu.NumberRepresentation;

public class TestCPUFixedVsFloatingPoint {

	public static void main(String[] args) {
		ITimer timer = new Timer();
		ILog log = /* new FileLogger("bench.log"); */new ConsoleLogger();
		TimeUnit timeUnit = TimeUnit.Milli;

		IBenchmark bench = new CPUFixedVsFloatingPointTest();
		bench.initialize(10000000);
		bench.warmUp();

		timer.start();
		bench.run(NumberRepresentation.FIXED);
//		bench.run(NumberRepresentation.FLOATING);
		long time = timer.stop();
		log.writeTime("Finished in", time, timeUnit);
		log.write("Result is", bench.getResult());

		bench.clean();
		log.close();
	}
}
