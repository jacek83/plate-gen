package org.jack.plategen;

import org.jack.plategen.generator.DocGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@ImportResource({ "classpath*:appContext.xml" })
@SpringBootApplication
public class PlateGenApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(PlateGenApplication.class);

	private static ApplicationContext ctx;

	public void execute(String[] args) {
		ctx = SpringApplication.run(PlateGenApplication.class, args);

		doRun();
	}

	public static void main(String[] args) {
		PlateGenApplication app = new PlateGenApplication();
		app.execute(args);
	}

	private void doRun() {
		try {
			String beans[] = ctx.getBeanDefinitionNames();
			for (String bn : beans) {
				LOG.info(">>> " + bn);
			}

			DocGen dg = (DocGen) ctx.getBean("docGen");
			dg.createDoc();
		} catch (Exception ex) {
			LOG.error(ex.toString());
		}
	}

	public void run(String... args) throws Exception {
		LOG.info("EXECUTING : command line runner");

		for (int i = 0; i < args.length; ++i) {
			LOG.info("args[{}]: {}", i, args[i]);
		}
	}
}
