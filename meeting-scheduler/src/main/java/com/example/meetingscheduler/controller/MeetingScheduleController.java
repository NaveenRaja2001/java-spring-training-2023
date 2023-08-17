package com.example.meetingscheduler.controller;

import com.example.meetingscheduler.entity.Room;
import com.example.meetingscheduler.entity.TimeSlot;
import com.example.meetingscheduler.projection.TimeSlotView;
import com.example.meetingscheduler.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@RestController
@RequestMapping("meeting-schedule")
public class MeetingScheduleController {
@Autowired
    TimeSlotService timeSlotService;
    @GetMapping("/findrooms/{date}")
    public List<TimeSlotView>availableRoomsBasedOnDate(@PathVariable String date) {
        LocalDate date1=LocalDate.parse(date);
        return timeSlotService.findTimeSlotIdByDate(date1);
    }


}
