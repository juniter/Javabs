package com.juniter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import com.juniter.conf.DefaultConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(DefaultConfiguration.class)
@ServletComponentScan(basePackages= {"com.juniter.conf","com.juniter.filter"})
public class SpringBootEntry implements CommandLineRunner{
	private static final Logger logger =LoggerFactory.getLogger("XXXX");
	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals("exitcode")) {
			logger.info("Main 方法参数：{}",args[0]);
		}
		SpringApplication.run(SpringBootEntry.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		if (arg0.length > 0 && arg0[0].equals("exitcode")) {
			logger.info("Main 方法参数：{}",arg0[0]);
			throw new ExitException();
		}
	}

	class ExitException extends RuntimeException implements ExitCodeGenerator {
		private static final long serialVersionUID = 1L;

		@Override
		public int getExitCode() {
			return 10;
		}
	}
}
