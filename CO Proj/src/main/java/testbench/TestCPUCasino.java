package testbench;

import bench.cpu.SpigotAlgorithm;
import logging.ConsoleLogger;
import logging.ILog;
import logging.TimeUnit;
import timing.ITimer;
import timing.Timer;
import bench.IBenchmark;


public class TestCPUCasino {

	private IBenchmark bench = new SpigotAlgorithm();

	public SpigotAlgorithm getBench() {
		return (SpigotAlgorithm) bench;
	}

	public void TestBench() {
		ITimer timer = new Timer();
		ILog log = new ConsoleLogger();
		TimeUnit timeUnit = TimeUnit.Milli;

		bench.initialize(10000000);
		bench.warmUp();

		timer.start();

		bench.run(1);

		long time = timer.stop();
		log.writeTime("Finished in", time, timeUnit);
		log.write("Result is", bench.getResult());

		bench.clean();
		log.close();
	}
}
