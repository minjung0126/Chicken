package com.chicken.project.calendar.controller;

import com.chicken.project.calendar.model.dto.CalendarDTO;
import com.chicken.project.calendar.model.service.CalendarServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/calendar/*")
public class CalendarController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CalendarServiceImpl calendarService;

    @Autowired
    public CalendarController(CalendarServiceImpl calendarService) {
        this.calendarService = calendarService;
    }


    /* 일정 조회 */
    @GetMapping("/calendar")
    public ModelAndView CalMain(ModelAndView mv){

        log.info("[CalendarController] 조회 확인 ========================================");

        mv.setViewName("calendar/calendar");
        return mv;
    }

    @GetMapping(value = {"calList"}, produces = {"application/json;"})
    @ResponseBody
    public List<CalendarDTO> selectCal(){

        return calendarService.selectCalendar();
    }

    /* 일정 추가 */
    @PostMapping("/calInsert")
    @ResponseBody
    public int calInsert(@ModelAttribute CalendarDTO cal,@RequestParam String calName, @RequestParam String calCon, @RequestParam java.sql.Date calStart, @RequestParam java.sql.Date calEnd, @AuthenticationPrincipal User user){


        cal.setCalName(calName);
        cal.setContent(calCon);
        cal.setStartDay(calStart);
        cal.setEndDay(calEnd);
        cal.setEmpId(user.getUsername());

        log.info("[CalendarController] cal : " + cal);

        int result = calendarService.insertCal(cal);

        if(result > 0){

        }

        return result;
    }

    /* 일정 삭제 */
    @PostMapping("/calDelete")
    @ResponseBody
    public int calDelete(@ModelAttribute CalendarDTO cal,@RequestParam String calName, @RequestParam String content){


        cal.setCalName(calName);
        cal.setContent(content);

        log.info("[CalendarController] calNo : " + cal);

        int result = calendarService.deleteCal(cal);

        log.info("[CalendarController] result : " + result);

        return result;
    }

    /* 일정 수정 */
    /* 일정 수정 */
    @PostMapping("/calUpdate")
    @ResponseBody
    public int calUpdate(@ModelAttribute CalendarDTO cal,@RequestParam String name, @RequestParam String cont, @RequestParam String calName, @RequestParam String calCon, @RequestParam java.sql.Date calStart, @RequestParam java.sql.Date calEnd, @AuthenticationPrincipal User user){

        cal.setCalName(name);
        cal.setContent(cont);

        CalendarDTO calendar = calendarService.selectCalNo(cal);

        log.info("[CalendarController] calNo : " + calendar.getCalNo());

        cal.setCalNo(calendar.getCalNo());
        cal.setCalName(calName);
        cal.setContent(calCon);
        cal.setStartDay(calStart);
        cal.setEndDay(calEnd);
        cal.setEmpId(user.getUsername());
        log.info("[CalendarController] cal : " + cal);

        int result = calendarService.updateCal(cal);


        log.info("[CalendarController] result : " + result);

        return result;
    }

}
