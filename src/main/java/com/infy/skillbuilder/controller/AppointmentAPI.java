package com.infy.skillbuilder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.skillbuilder.dto.AppointmentsDTO;
import com.infy.skillbuilder.dto.DailyBookingCounts;
import com.infy.skillbuilder.dto.MonthlyBookingCounts;
import com.infy.skillbuilder.dto.RejectionReasons;
import com.infy.skillbuilder.dto.UserRetentionDTO;
import com.infy.skillbuilder.dto.WeeklyBookingCounts;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/appointmentapi")
public class AppointmentAPI {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private Environment environment;
    private String bookingUpdated = "API.APPOINTMENT_STATUS_UPDATED";

    @PostMapping("/bookAppointment")
    public ResponseEntity<String> bookAppointment(@RequestBody @Valid AppointmentsDTO appointmentsDTO)
            throws SkillBuilderException {
        Integer id = appointmentService.bookAppointment(appointmentsDTO);
        return new ResponseEntity<>(environment.getProperty("API.APPOINTMENT_BOOKING_SUCCESS") + id,
                HttpStatus.CREATED);
    }

    @GetMapping("getAppointmentsByMentor/{id}")
    public ResponseEntity<List<AppointmentsDTO>> getAppointmentsByMentor(@PathVariable Integer id)
            throws SkillBuilderException {
        return new ResponseEntity<>(appointmentService.getAppointmentsByMentor(id), HttpStatus.OK);
    }

    @GetMapping("getAppointmentsByMentee/{id}")
    public ResponseEntity<List<AppointmentsDTO>> getAppointmentsByMentee(@PathVariable Integer id)
            throws SkillBuilderException {
        return new ResponseEntity<>(appointmentService.getAppointmentsByMentee(id), HttpStatus.OK);
    }

    @PutMapping("/acceptBooking/{id}")
    public ResponseEntity<String> acceptBooking(@PathVariable Integer id) throws SkillBuilderException {
        appointmentService.acceptBooking(id);
        return new ResponseEntity<>(environment.getProperty(bookingUpdated), HttpStatus.OK);
    }

    @PutMapping("/completeSession/{id}")
    public ResponseEntity<String> completeSession(@PathVariable Integer id) throws SkillBuilderException {
        appointmentService.completeSession(id);
        return new ResponseEntity<>(environment.getProperty(bookingUpdated), HttpStatus.OK);
    }

    @PutMapping("/rejectBookin/{id}/{reason}")
    public ResponseEntity<String> rejectBooking(@PathVariable Integer id, @PathVariable RejectionReasons reason)
            throws SkillBuilderException {
        appointmentService.rejectBooking(id, reason);
        return new ResponseEntity<>(environment.getProperty(bookingUpdated), HttpStatus.OK);
    }

    @PutMapping("/cancleAppointment/{id}/{reason}")
    public ResponseEntity<String> cancleAppointment(@PathVariable Integer id, @PathVariable String reason)
            throws SkillBuilderException {
        appointmentService.cancleAppointment(id, reason);
        return new ResponseEntity<>(environment.getProperty(bookingUpdated), HttpStatus.OK);
    }

    @PostMapping("/rescheduleAppointment/{id}")
    public ResponseEntity<String> rescheduleAppointment(@PathVariable Integer id,
            @RequestBody @Valid AppointmentsDTO appointmentsDTO) throws SkillBuilderException {
        appointmentService.rescheduleAppointment(id, appointmentsDTO);
        return new ResponseEntity<>(environment.getProperty("API.APPOINTMENT_RESCHEDULE_REQUESTED"), HttpStatus.OK);
    }

    @GetMapping("getUpcommingSessionsByMentor/{id}")
    public ResponseEntity<Integer> getUpcommingSessionsByMentor(@PathVariable Integer id) throws SkillBuilderException {
        return new ResponseEntity<>(appointmentService.getUpcommingSessionsByMentor(id), HttpStatus.OK);
    }

    @GetMapping("getCompletedSessionsByMentor/{id}")
    public ResponseEntity<Integer> getCompletedSessionsByMentor(@PathVariable Integer id) throws SkillBuilderException {
        return new ResponseEntity<>(appointmentService.getCompletedSessionsByMentor(id), HttpStatus.OK);
    }

    @GetMapping("getUpcommingSessionsByMentee/{id}")
    public ResponseEntity<Integer> getUpcommingSessionsByMentee(@PathVariable Integer id) throws SkillBuilderException {
        return new ResponseEntity<>(appointmentService.getUpcommingSessionsByMentee(id), HttpStatus.OK);
    }

    @GetMapping("getCompletedSessionsByMentee/{id}")
    public ResponseEntity<Integer> getCompletedSessionsByMentee(@PathVariable Integer id) throws SkillBuilderException {
        return new ResponseEntity<>(appointmentService.getCompletedSessionsByMentee(id), HttpStatus.OK);
    }

    @GetMapping("getTotalSessionDurationByMentee/{id}")
    public ResponseEntity<Integer> getTotalSessionDurationByMentee(@PathVariable Integer id)
            throws SkillBuilderException {
        return new ResponseEntity<>(appointmentService.getTotalSessionDurationByMentee(id), HttpStatus.OK);
    }

    @GetMapping("getNextSessionByMentee/{id}")
    public ResponseEntity<AppointmentsDTO> getNextSessionByMentee(@PathVariable Integer id)
            throws SkillBuilderException {
        return new ResponseEntity<>(appointmentService.getNextSessionByMentee(id), HttpStatus.OK);
    }

    @GetMapping("getConnectedMenteesByMentor/{id}")
    public ResponseEntity<Integer> getConnectedMenteesByMentor(@PathVariable Integer id) throws SkillBuilderException {
        return new ResponseEntity<>(appointmentService.getConnectedMenteesByMentor(id), HttpStatus.OK);
    }

    @GetMapping("getSessionsComplitionRateByMentor/{id}")
    public ResponseEntity<Double> getSessionsComplitionRateByMentor(@PathVariable Integer id)
            throws SkillBuilderException {
        return new ResponseEntity<>(appointmentService.getSessionsComplitionRateByMentor(id), HttpStatus.OK);
    }

    @GetMapping("getRebookingRateByMentor/{id}")
    public ResponseEntity<Double> getRebookingRateByMentor(@PathVariable Integer id) throws SkillBuilderException {
        return new ResponseEntity<>(appointmentService.getRebookingRateByMentor(id), HttpStatus.OK);
    }

    @GetMapping("getLatestAgendasByMentorId/{id}")
    public ResponseEntity<List<String>> getLatestAgendasByMentorId(@PathVariable Integer id)
            throws SkillBuilderException {
        return new ResponseEntity<>(appointmentService.getLatestAgendasByMentorId(id), HttpStatus.OK);
    }

    @GetMapping("findDailyBookingCountsForLast7Days")
    public ResponseEntity<List<DailyBookingCounts>> findDailyBookingCountsForLast7Days() throws SkillBuilderException {
        return new ResponseEntity<>(appointmentService.findDailyBookingCountsForLast7Days(), HttpStatus.OK);
    }

    @GetMapping("findWeeklyBookingCountsForLast7Weeks")
    public ResponseEntity<List<WeeklyBookingCounts>> findWeeklyBookingCountsForLast7Weeks()
            throws SkillBuilderException {
        return new ResponseEntity<>(appointmentService.findWeeklyBookingCountsForLast7Weeks(), HttpStatus.OK);
    }

    @GetMapping("findMonthlyBookingCountsForLast7Months")
    public ResponseEntity<List<MonthlyBookingCounts>> findMonthlyBookingCountsForLast7Months()
            throws SkillBuilderException {
        return new ResponseEntity<>(appointmentService.findMonthlyBookingCountsForLast7Months(), HttpStatus.OK);
    }

    @GetMapping("/getUserRetentionData")
    public ResponseEntity<List<UserRetentionDTO>> getUserRetentionData() {
        return new ResponseEntity<>(appointmentService.getUserRetentionStats(), HttpStatus.OK);
    }

    @GetMapping("/getSessionCompletionRate")
    public ResponseEntity<Double> getSessionCompletionRate() {
        return new ResponseEntity<>(appointmentService.getSessionCompletionRate(), HttpStatus.OK);
    }

    @GetMapping("/getAverageSessionDuration")
    public ResponseEntity<Double> getAverageSessionDuration() {
        return new ResponseEntity<>(appointmentService.getAverageSessionDuration(), HttpStatus.OK);
    }
}