package com.gerrydevstory.stockticker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

  private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

  @Autowired private SimpMessagingTemplate template;
  private TaskScheduler scheduler = new ConcurrentTaskScheduler();
  
  @PostConstruct
  private void broadcastTimePeriodically() {
    scheduler.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date()); 
        template.convertAndSend("/topic/time", time);
        logger.info("Broadcasting time " + time);
      }
    }, 1000);
  }
  
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String home(Model model) {

    return "home";
  }
  
  

}
