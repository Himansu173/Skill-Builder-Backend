package com.infy.skillbuilder.service;

import java.util.List;

import com.infy.skillbuilder.dto.AppointmentsDTO;
import com.infy.skillbuilder.dto.DailyBookingCounts;
import com.infy.skillbuilder.dto.MonthlyBookingCounts;
import com.infy.skillbuilder.dto.RejectionReasons;
import com.infy.skillbuilder.dto.UserRetentionDTO;
import com.infy.skillbuilder.dto.WeeklyBookingCounts;
import com.infy.skillbuilder.exception.SkillBuilderException;

public interface AppointmentService {
    Integer bookAppointment(AppointmentsDTO appointmentsDTO) throws SkillBuilderException;

    List<AppointmentsDTO> getAppointmentsByMentor(Integer id) throws SkillBuilderException;

    List<AppointmentsDTO> getAppointmentsByMentee(Integer id) throws SkillBuilderException;

    void acceptBooking(Integer appointmentId) throws SkillBuilderException;

    void rejectBooking(Integer appointmentId, RejectionReasons reason) throws SkillBuilderException;

    void cancleAppointment(Integer appointmentId, String reasong) throws SkillBuilderException;

    Integer rescheduleAppointment(Integer appointmentId, AppointmentsDTO appointmentsDTO) throws SkillBuilderException;

    Integer getUpcommingSessionsByMentor(Integer mentorId) throws SkillBuilderException;

    Integer getCompletedSessionsByMentor(Integer mentorId) throws SkillBuilderException;

    Integer getUpcommingSessionsByMentee(Integer menteeId) throws SkillBuilderException;

    Integer getCompletedSessionsByMentee(Integer menteeId) throws SkillBuilderException;

    Integer getTotalSessionDurationByMentee(Integer menteeId) throws SkillBuilderException;

    AppointmentsDTO getNextSessionByMentee(Integer menteeID) throws SkillBuilderException;

    Integer getConnectedMenteesByMentor(Integer mentorId) throws SkillBuilderException;

    Double getSessionsComplitionRateByMentor(Integer mentorId) throws SkillBuilderException;

    Double getRebookingRateByMentor(Integer mentorId) throws SkillBuilderException;

    List<String> getLatestAgendasByMentorId(Integer mentorId) throws SkillBuilderException;

    List<DailyBookingCounts> findDailyBookingCountsForLast7Days() throws SkillBuilderException;

    List<WeeklyBookingCounts> findWeeklyBookingCountsForLast7Weeks() throws SkillBuilderException;

    List<MonthlyBookingCounts> findMonthlyBookingCountsForLast7Months() throws SkillBuilderException;

    public List<UserRetentionDTO> getUserRetentionStats() throws SkillBuilderException;

    public double getSessionCompletionRate() throws SkillBuilderException;

    public double getAverageSessionDuration() throws SkillBuilderException;

    public void completeSession(Integer appointmentId) throws SkillBuilderException;
}