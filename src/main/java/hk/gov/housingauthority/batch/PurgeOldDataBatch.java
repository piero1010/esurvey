package hk.gov.housingauthority.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import hk.gov.housingauthority.dao.SrvyPptDao;

public class PurgeOldDataBatch  extends QuartzJobBean {
	private static final Logger logger = LoggerFactory.getLogger(PurgeOldDataBatch.class);
	
	@Override
	protected void executeInternal(JobExecutionContext jobContext) throws JobExecutionException {
		logger.info("Start PurgeOldDataBatch");
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

			SrvyPptDao srvyPptDao = (SrvyPptDao) context.getBean("srvyPptDao");
			
			srvyPptDao.purgeOldData();

		} catch (Exception e) {
			logger.info(e.toString());
		}
		logger.info("End PurgeOldDataBatch");
	}

}
