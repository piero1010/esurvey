package hk.gov.housingauthority.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import hk.gov.housingauthority.dao.SrvyRecDao;

public class UpdateSrvyRecBatch extends QuartzJobBean{
	private static final Logger logger = LoggerFactory.getLogger(UpdateSrvyRecBatch.class);
	
    @Override
    protected void executeInternal(JobExecutionContext jobContext) throws JobExecutionException {
    	logger.info("Start UpdateSrvyRecBatch");
		try {	
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			SrvyRecDao srvyRecDao = (SrvyRecDao) context.getBean("srvyRecDao");
			int updated = srvyRecDao.UpdateCmpltSts();
			logger.info("Total survey updated: "+updated);
		} catch (Exception e) {
			logger.info(e.toString());
		}
		logger.info("End UpdateSrvyRecBatch");
    }

}